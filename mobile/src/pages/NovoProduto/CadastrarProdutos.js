import React, { useState } from 'react';
import {
  View,
  Text,
  TextInput,
} from 'react-native';
import {RectButton} from 'react-native-gesture-handler';
import api from '../../services/api'

import styles from '../../styles';
import newProductStyles from './styles';

const Cadastra = ({navigation}) => {
  const [product, setProduct] = useState('');
  
  async function createNewProduct() {
    if (product == '') {
      alert('É obrigatório digitar o nome do produto.')
    } else {
      await api.post('products', {
        descricao: product
      });
      navigation.navigate('Produtos');
    }
  }
  
  return(
    <View>
      <Text style={styles.title}>Cadastra</Text>
      <Text style={newProductStyles.info}>
        Digite abaixo o nome do produto que que deseja cadastrar. Após isso,
        já será possível adicioná-lo em estoque.
      </Text>
      <Text style={newProductStyles.label}>Produto</Text>
      <TextInput
        style={newProductStyles.inputText}
        value={product}
        onChangeText={text => setProduct(text)}
        returnKeyType={"go"}
        onSubmitEditing={() => {
          createNewProduct();
        }}
        placeholder={'Digite o nome do produto'} />
      <View>
        <RectButton
          style={styles.buttons}
          onPress={() => {
            if (product == ''){
              alert('É obrigatório digitar o nome do produto.')
            } else {
            createNewProduct();
            navigation.navigate('Produtos');
            }
          }}>
          <Text style={styles.textButton}>Cadastrar</Text>
        </RectButton>
      </View>
    </View>
  )
};

export default Cadastra;
