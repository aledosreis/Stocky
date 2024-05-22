import {StyleSheet} from 'react-native';
import {widthPercentageToDP as wp, heightPercentageToDP as hp} from 'react-native-responsive-screen';

const styles = StyleSheet.create({
  listEstoque: {
    marginTop: hp('3%'),
    marginHorizontal: wp('4%'),
    minHeight: hp('65%'),
    maxHeight: hp('65%'),
  },
  tableHeader: {
    backgroundColor: '#ddd',
  },
  textHeader: {
    fontSize: hp('2.6%'),
    fontWeight: 'bold',
    color: '#000',
    textAlign:'center',
  },
  textRowsEntrada: {
    fontSize: hp('2.6%'),
    color: '#228b22',
    textAlign:'center',
  },
  textRowsSaida: {
    fontSize: hp('2.6%'),
    color: '#ff0000',
    textAlign:'center',
  },
  legenda: {
    flexDirection: "row",
    justifyContent:"space-between",
    margin: hp('3%'),
    width: wp('50%'),
  },
  legendaTitulo: {
    fontWeight: "bold",
    fontSize: hp('2%'),
  },
  legendaEntrada: {
    color: '#228b22',
    fontSize: hp('2%'),
  },
  legendaSaida: {
    color: '#ff0000',
    fontSize: hp('2%'),
  }
});

export default styles;