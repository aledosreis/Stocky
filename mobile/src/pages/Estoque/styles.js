import {StyleSheet} from 'react-native';
import {widthPercentageToDP as wp, heightPercentageToDP as hp} from 'react-native-responsive-screen';

const styles = StyleSheet.create({
  listEstoque: {
    margin: hp('3%'),
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
  textRows: {
    fontSize: hp('2.6%'),
    color: '#000',
    textAlign:'center',
  },
  viewButton: {
    flexDirection: 'row',
    alignContent: 'space-between',
  },
  buttons: {
    marginEnd: wp('3%'),
    marginStart: wp('5%'),
    width: '40%',
  },
});

export default styles;