import React, {useState, useEffect} from 'react';
import {
  View,
  Text,
  TextInput,
  Keyboard,
} from 'react-native';
import {RectButton} from 'react-native-gesture-handler'
import {Picker} from '@react-native-community/picker';
import api from '../../services/api';

import styles from '../../styles';
import stylesEntrada from './styles';

const Entrada = ({navigation}) => {
  const [selectedValue, setSelectedValue] = useState('');
  const [products, setProducts] = useState([]);
  const [qtd, setQtd] = useState('');

  const updateStock = async () => {
    if ((qtd != '') && (qtd != 0)){
      try {
          await api.post('stock/add', {
            id_produto: selectedValue,
            qtd_estoque: qtd
          });
          await navigation.navigate('Estoque');
      } catch (error) {
        alert('Ocorreu um erro ao atualizar o estoque');
        setQtd('');
      }
    } else {
      alert('É obrigatório digitar um valor maior que 0.')
    }
  }

  useEffect(() => {
    async function getListProducts() {
      const response = await api.get('products');
      setProducts(response.data);
    }
    getListProducts();
  }, [])

  return (
    <View style={styles.container}>
      <Text style={styles.title}>Entrada de Estoque</Text>
      <Text style={stylesEntrada.info}>
        Escolha abaixo o produto que deseja dar entrada no estoque, em seguida a
        quantidade que está entrando.
      </Text>
      <View style={stylesEntrada.dataInput}>
        <Text style={stylesEntrada.labels}>Produto: </Text>
        <Picker
          selectedValue={selectedValue}
          style={stylesEntrada.picker}
          onValueChange={(itemValue, itemIndex) => setSelectedValue(itemValue)}>
          {products.map(({descricao, id_produto}) => (<Picker.Item label={descricao} value={id_produto} key={id_produto} />))}
        </Picker>
      </View>
      <View style={stylesEntrada.dataInput}>
        <Text style={stylesEntrada.labels}>Quantidade: </Text>
        <TextInput
          keyboardType={'numeric'}
          maxLength={2}
          value={qtd}
          onChangeText={text => setQtd(text)}
          returnKeyType={"go"}
          onSubmitEditing={() => updateStock()}
          placeholder={'0'}
          style={stylesEntrada.inputText}
        />
      </View>
      <View>
        <RectButton
          style={styles.buttons}
          onPress={() => updateStock()}>
          <Text style={styles.textButton}>Entrada</Text>
        </RectButton>
      </View>
    </View>
  );
};

export default Entrada;
