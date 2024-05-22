import React, { useState, useEffect } from 'react';
import {View, Text} from 'react-native';
import {Table, Row} from 'react-native-table-component';
import { ScrollView } from 'react-native-gesture-handler';
import api from '../../services/api';

import styles from '../../styles';
import styleHistorico from './styles';
import {widthPercentageToDP as wp} from 'react-native-responsive-screen';

const Historico = () => {
  const header = ['Data', 'Produto', 'Qtd'];
  const [history, setHistory] = useState([]);
  const columnSize = [wp('20%'),wp('51.4%'),wp('20%')];

  function formataData(data) {
    const day = data.slice(8,10);
    const month = data.slice(5,7);
    const newDate = day + '/' + month;
    return newDate;
  }

  useEffect(() => {
    async function getHistory() {
      const response = await api.get('history');
      const historico = response.data.map(({id_transacao, dt_transacao, descricao, qtd_movimentada, tipo_transacao}) => {
        const data_transacao = formataData(dt_transacao);
        return {
          id_transacao,
          transacao: [
            data_transacao,
            descricao,
            qtd_movimentada
          ],
          tipo_transacao
        }
      });
      setHistory(historico);
    }
    getHistory();
  },[])
  
  return (
    <View style={styles.container}>
      <Text style={styles.title}>Historico</Text>
      <View style={styleHistorico.listEstoque}>
        <View>
          <Table borderStyle={{borderWidth: 2, borderColor: '#000'}}>
            <Row data={header} style={styleHistorico.tableHeader} textStyle={styleHistorico.textHeader} widthArr={columnSize}/>
          </Table>
        </View>
        <ScrollView>
          <Table borderStyle={{borderWidth: 2, borderColor: '#000'}}>
            {history.map(({id_transacao, transacao, tipo_transacao}) => {
              if (tipo_transacao == 2) {
                return <Row data={transacao} key={id_transacao} textStyle={styleHistorico.textRowsSaida} widthArr={columnSize}/>
              } else {
                return <Row data={transacao} key={id_transacao} textStyle={styleHistorico.textRowsEntrada} widthArr={columnSize}/>
              }})}
          </Table>
        </ScrollView>
      </View>
      <View style={styleHistorico.legenda}>
        <Text style={styleHistorico.legendaTitulo}>Legenda:</Text>
        <Text style={styleHistorico.legendaEntrada}>Entrada</Text>
        <Text style={styleHistorico.legendaSaida}>Saída</Text>
      </View>
    </View>
  );
};

export default Historico;
