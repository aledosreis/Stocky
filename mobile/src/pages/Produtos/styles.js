import {StyleSheet} from 'react-native';
import {widthPercentageToDP as wp, heightPercentageToDP as hp} from 'react-native-responsive-screen';

const styles = StyleSheet.create({
  listProducts: {
    marginTop: hp('3%'),
    marginHorizontal: wp('5%'),
    minHeight: hp('70%'),
    maxHeight: hp('70%'),
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
});

export default styles;