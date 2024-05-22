import React, {useState, useEffect} from 'react';
import {View, Text, TextInput, PermissionsAndroid} from 'react-native';
import {Picker} from '@react-native-community/picker';
import {RectButton, ScrollView} from 'react-native-gesture-handler';
import {Table, Row} from 'react-native-table-component';
import api from '../../services/api';
import print from '../../services/print';

import styles from '../../styles';
import stylesPedidos from './styles';

import {widthPercentageToDP as wp, heightPercentageToDP as hp} from 'react-native-responsive-screen';

const Pedidos = () => {
  const [selectedValue, setSelectedValue] = useState('');
  const [selectedMonth, setSelectedMonth] = useState('Janeiro');
  const header = ['Produto', 'Qtd'];
  const [products, setProducts] = useState([]);
  const meses = [
    {"id": 1, "mes": "Janeiro"},
    {"id": 2, "mes": "Fevereiro"},
    {"id": 3, "mes": "Março"},
    {"id": 4, "mes": "Abril"},
    {"id": 5, "mes": "Maio"},
    {"id": 6, "mes": "Junho"},
    {"id": 7, "mes": "Julho"},
    {"id": 8, "mes": "Agosto"},
    {"id": 9, "mes": "Setembro"},
    {"id": 10, "mes": "Outubro"},
    {"id": 11, "mes": "Novembro"},
    {"id": 12, "mes": "Dezembro"},
  ];
  const [typedQtd, setTypedQtd] = useState('');
  const [pedidos, setPedidos] = useState([]);
  const columnSize = [wp('68%'),wp('21.4%')];

  function adicionaPedido() {
    if (typedQtd != 0 && typedQtd != '') {
      const pedido = [selectedValue, typedQtd];
      setPedidos([...pedidos, pedido]);
      setTypedQtd('');
    } else {
      alert('É necessário inserir um valor maior que 0.')
    }
  }

  useEffect(() => {
    async function getListProducts() {
      const response = await api.get('products');
      setProducts(response.data);
    }
    getListProducts();
  }, [])

  return(
    <View style={styles.container}>
      <Text style={styles.title}>Fazer pedido</Text>
      <View style={stylesPedidos.listPedidos}> 
        <View>
          <Table borderStyle={{borderWidth: 2, borderColor: '#000'}}>
            <Row data={header} style={stylesPedidos.tableHeader} textStyle={stylesPedidos.textHeader} widthArr={columnSize}/>
          </Table>
        </View>
        <ScrollView>
          <Table borderStyle={{borderWidth: 2, borderColor: '#000'}}>
            {pedidos.map(pedido => (<Row data={pedido} key={pedido[0]} textStyle={stylesPedidos.textRows} widthArr={columnSize}/>))}
          </Table>
        </ScrollView>
      </View>
        <View style={stylesPedidos.dataInput}>
          <Text style={stylesPedidos.labels}>Produto</Text>
          <Picker 
            selectedValue={selectedValue}
            style={stylesPedidos.picker}
            onValueChange={(itemValue, itemIndex) => setSelectedValue(itemValue)}>
            {products.map(({descricao, id_produto}) => (<Picker.Item label={descricao} value={descricao} key={id_produto} />))}
          </Picker>
        </View>
        <View style={stylesPedidos.dataInput}>
          <Text style={stylesPedidos.labels}>Quantidade</Text>
          <TextInput
            keyboardType={'numeric'}
            placeholder={'0'}
            maxLength={2}
            value={typedQtd}
            returnKeyType={"go"}
            onSubmitEditing={() => adicionaPedido()}
            onChangeText={text => setTypedQtd(text)}
            style={stylesPedidos.inputText}
          />
        </View>
        <View style={stylesPedidos.dataInput}>
          <Text style={stylesPedidos.labels}>Mẽs</Text>
          <Picker
            selectedValue={selectedMonth}
            style={stylesPedidos.picker}
            onValueChange={(itemValue, itemIndex) => setSelectedMonth(itemValue)}>
            {meses.map(({id, mes}) => (<Picker.Item label={mes} value={mes} key={id} />))}
          </Picker>
        </View>
        <View style={stylesPedidos.viewButton}>
        <RectButton
          style={[styles.buttons,stylesPedidos.buttons]}
          onPress={() => {
            adicionaPedido();
          }}>
          <Text style={styles.textButton}>Adicionar</Text>
        </RectButton>
        <RectButton style={[styles.buttons,stylesPedidos.buttons]} onPress={async () => {
            const writeStorage = await PermissionsAndroid.request(
              PermissionsAndroid.PERMISSIONS.WRITE_EXTERNAL_STORAGE);
            if (writeStorage === PermissionsAndroid.RESULTS.GRANTED) {
              print(selectedMonth,pedidos)
            }
          }
        }>
          <Text style={styles.textButton}>Imprimir</Text>
        </RectButton>
        </View>
    </View>
  );
};

export default Pedidos;
