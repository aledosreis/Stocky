import React from 'react';
import {View, Text, Image, TouchableOpacity, PermissionsAndroid} from 'react-native';
import update from '../../services/update';

import stockyLogo from '../../assets/stockyLogo.png';
import styles from '../../styles';
import updateStyles from './styles';

const UpdateScreen = ({url, version}) => {
  return (
    <View style={styles.container}>
      <Image source={stockyLogo} style={updateStyles.logoImage} />
      <Text style={updateStyles.title}>Atualização disponível!</Text>
      <Text style={updateStyles.updateInfo}>Existe uma nova versão do Stocky disponível! Recomendamos atualizar para receber novos recursos</Text>
      <TouchableOpacity style={styles.buttons} onPress={ async () => {
            const writeStorage = await PermissionsAndroid.request(
              PermissionsAndroid.PERMISSIONS.WRITE_EXTERNAL_STORAGE);
            if (writeStorage === PermissionsAndroid.RESULTS.GRANTED) {
              update(url,version)
            }
          }
        }>
        <Text style={styles.textButton}>Atualizar</Text>
      </TouchableOpacity>
    </View>
)};

export default UpdateScreen;