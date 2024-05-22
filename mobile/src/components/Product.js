import React from 'react';
import { View, Text, Alert } from 'react-native';
import styleProduct from './styles';
import { RectButton } from 'react-native-gesture-handler';
import api from '../services/api';

async function removeItem (id, product) {
  try {
    const {status} = await api.post('products/delete', {id_produto: id});
    if (status == 200) {
      alert(`Produto ${product} removido com sucesso!`);
    }
  } catch (error) {
    alert('Ocorreu um erro ao excluir o produto.');
  }
}

const Product = ({product, id}) => {
  return (
    <View style={styleProduct.product}>
      <Text style={styleProduct.productText}>{product}</Text>
      <RectButton style={styleProduct.productButton} onPress={() => {
        Alert.alert(
          "Confirmação",
          "Tem certeza que deseja remover esse produto?",
          [
            {
              text: "Não",
              onPress: () => {},
              style: "cancel"
            },
            {
              text: "Sim",
              onPress: () => removeItem(id, product),
            }
          ],
          {cancelable: false}
        )
        }}>
        <Text style={styleProduct.productButtonText}>Remover</Text>
      </RectButton>
    </View>
  );
}

export default Product;