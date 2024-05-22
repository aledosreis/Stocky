import React, {useState, useEffect} from 'react';
import {View, Text} from 'react-native';
import {RectButton, ScrollView} from 'react-native-gesture-handler';
import api from '../../services/api';

import styles from '../../styles';
import produtoStyles from './styles';
import Product from '../../components/Product';


const Produtos = ({navigation}) => {
  const [products, setProducts] = useState([]);

  useEffect(() => {
    let isMounted = true;
    async function getListProducts() {
      const response = await api.get('products');
      if (isMounted) {
        const produtos = response.data.map(({id_produto,descricao}) => {
          return {
            id_produto,
            descricao: [descricao]
          }

        });
        setProducts(produtos);  
      }
    }
    getListProducts();
    return () => isMounted = false;
  }, [products]);

  return(
    <View style={styles.container}>
      <Text style={styles.title}>Produtos</Text>
      <ScrollView style={produtoStyles.listProducts}>
        {products.map(({id_produto, descricao}) => (<Product product={descricao} key={id_produto} id={id_produto} />))}
      </ScrollView>
      <RectButton
        style={styles.buttons}
        onPress={() => {
          navigation.navigate('CadastrarProdutos');
        }}>
        <Text style={styles.textButton}>Novo Produto</Text>
      </RectButton>
    </View>
  )
};

export default Produtos;
