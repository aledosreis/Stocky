import React, {useState, useEffect} from 'react';
import {
  View,
  Text,
  TextInput,
  Keyboard,
} from 'react-native';
import {Picker} from '@react-native-community/picker';
import {RectButton} from 'react-native-gesture-handler';
import api from '../../services/api';

import styles from '../../styles';
import stylesSaida from './styles';

const Saida = ({navigation}) => {
  const [selectedValue, setSelectedValue] = useState('prod1');
  const [products, setProducts] = useState([]);
  const [qtd, setQtd] = useState('');

  const updateStock = async () => {
    if ((qtd != '') && (qtd != 0)){
      try {
          await api.post('stock/remove', {
            id_produto: selectedValue,
            qtd_estoque: qtd
          });
          await navigation.navigate('Estoque');
      } catch (error) {
        alert('Ocorreu um erro ao atualizar o estoque.');
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
      <Text style={styles.title}>Saída de Estoque</Text>
      <Text style={stylesSaida.info}>
        Escolha abaixo o produto que deseja retirar do estoque, em seguida a
        quantidade que está saíndo.
      </Text>
      <View style={stylesSaida.dataInput}>
        <Text style={stylesSaida.labels}>Produto: </Text>
        <Picker
          selectedValue={selectedValue}
          style={stylesSaida.picker}
          onValueChange={(itemValue, itemIndex) => setSelectedValue(itemValue)}>
          {products.map(({descricao, id_produto}) => (<Picker.Item label={descricao} value={id_produto} key={id_produto} />))}
        </Picker>
      </View>
      <View style={stylesSaida.dataInput}>
        <Text style={stylesSaida.labels}>Quantidade: </Text>
        <TextInput
          keyboardType={'numeric'}
          maxLength={2}
          value={qtd}
          onChangeText={text => setQtd(text)}
          returnKeyType={"go"}
          onSubmitEditing={() => {
            updateStock();
          }}
          placeholder={'0'}
          style={stylesSaida.inputText}
        />
      </View>
      <View>
        <RectButton
          style={styles.buttons}
          onPress={() => updateStock()}>
          <Text style={styles.textButton}>Saída</Text>
        </RectButton>
      </View>
    </View>
  );
};

export default Saida;
