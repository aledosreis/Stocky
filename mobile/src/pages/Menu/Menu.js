import { RectButton } from 'react-native-gesture-handler';
import React from 'react';
import {
  View,
  Text,
  StatusBar,
  Image,
} from 'react-native';

import stockyLogo from '../../assets/stockyLogo.png';

import styles from '../../styles'
import menuStyles from './styles'

const Menu = ({navigation}) => (
  <View style={styles.container}>
    <StatusBar barStyle="light-content" backgroundColor="#DA553F" />
    <Image source={stockyLogo} style={menuStyles.logoImage} />
    <View style={menuStyles.viewButton}>
      <RectButton
        style={styles.buttons}
        onPress={() => {
          navigation.navigate('Produtos');
        }}>
        <Text style={styles.textButton}>Produtos</Text>
      </RectButton>
      <RectButton
        style={styles.buttons}
        onPress={() => {
          navigation.navigate('Estoque');
        }}>
        <Text style={styles.textButton}>Estoque</Text>
      </RectButton>
      <RectButton
        style={styles.buttons}
        onPress={() => {
          navigation.navigate('HistoricoEstoque');
        }}>
        <Text style={styles.textButton}>Histórico</Text>
      </RectButton>
      <RectButton
        style={styles.buttons}
        onPress={() => {
          navigation.navigate('Pedido');
        }}>
        <Text style={styles.textButton}>Pedido</Text>
      </RectButton>
    </View>
    <Text style={menuStyles.creator}>Created by Alessandro Reis, 2020.</Text>
  </View>
);

export default Menu;
