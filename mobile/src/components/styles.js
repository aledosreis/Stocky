import {StyleSheet} from 'react-native';
import {widthPercentageToDP as wp, heightPercentageToDP as hp} from 'react-native-responsive-screen';

const styles = StyleSheet.create({
  product: {
    backgroundColor: '#007fff',
    flexDirection: 'row',
    alignContent: 'space-between',
    margin: hp('2%'),
    padding: hp('1.5%'),
    borderRadius: hp('2%')
  },
  productText: {
    textAlign: 'center',
    width: wp('50%'),
    fontSize: hp('2.5%'),
    fontWeight: 'bold',
    color: '#fff',
    marginVertical: hp('1%')
  },
  productButton: {
    width: wp('25%'),
    borderRadius: hp('2%'),
    textAlign: 'center',
    backgroundColor: '#004fff',
    marginVertical: hp('1%')
  },
  productButtonText: {
    color: '#f00',
    textAlign: 'center',
    fontWeight: 'bold',
    padding: hp('1%'),
    fontSize: hp('1.8%')
  }
})

export default styles;