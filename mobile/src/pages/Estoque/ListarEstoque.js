import React, { useState, useEffect } from 'react';
import {View, Text} from 'react-native';
import {RectButton, ScrollView} from 'react-native-gesture-handler';
import { Table, Row } from 'react-native-table-component';
import api from '../../services/api';

import estoqueStyles from './styles';
import styles from '../../styles';
import {widthPercentageToDP as wp} from 'react-native-responsive-screen';

const ListarEstoque = ({navigation}) => {
  const header = ['Produto', 'Qtd'];
  const [stock, setStock] = useState([]);
  const columnSize = [wp('68%'),wp('19.7%')];

  useEffect(() => {
    let isMounted = true;
    async function getEstoque() {
      const response = await api.get('stock');
      if (isMounted) {
        const estoque = response.data.map(({descricao, qtd_estoque}) => {
          return [
            descricao,
            qtd_estoque
          ]
        });
        setStock(estoque);  
      }
    }
    getEstoque();
    return () => isMounted = false;
  }, [stock]);

  return (
  <View style={styles.container}>
    <Text style={styles.title}>Estoque</Text>
    <View style={estoqueStyles.listEstoque}>
      <View>
        <Table borderStyle={{borderWidth: 2, borderColor: '#000'}}>
          <Row data={header} style={estoqueStyles.tableHeader} textStyle={estoqueStyles.textHeader} widthArr={columnSize}/>
        </Table>
      </View>
      <ScrollView>
        <Table  borderStyle={{borderWidth: 2, borderColor: '#000'}}>
          {stock.map(estoque => (<Row data={estoque} key={estoque} textStyle={estoqueStyles.textRows} widthArr={columnSize}/>))}
        </Table>
      </ScrollView>
    </View>
    <View style={estoqueStyles.viewButton}>
      <RectButton
        onPress={() => {
          navigation.navigate('EntradaEstoque');
        }}
        style={[styles.buttons, estoqueStyles.buttons]}>
        <Text style={styles.textButton}>Entrada</Text>
      </RectButton>
      <RectButton
        onPress={() => {
          navigation.navigate('SaidaEstoque');
        }}
        style={[styles.buttons, estoqueStyles.buttons]}>
        <Text style={styles.textButton}>Saída</Text>
      </RectButton>
    </View>
  </View>
  )
};

export default ListarEstoque;
