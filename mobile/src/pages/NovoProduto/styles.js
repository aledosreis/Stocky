import {StyleSheet} from 'react-native';
import {widthPercentageToDP as wp, heightPercentageToDP as hp} from 'react-native-responsive-screen';

const styles = StyleSheet.create({
  info: {
    marginHorizontal: wp('10%'),
    marginTop: hp('10%'),
    marginBottom: hp('5%'),
    fontSize: hp('2.2%'),
    textAlign: 'justify',
  },
  label: {
    marginTop: hp('5%'),
    marginStart: wp('10%'),
    fontWeight: 'bold',
    fontSize: hp('2.2%'),
  },
  inputText: {
    marginHorizontal: wp('10%'),
    marginBottom: hp('30%'),
    height: hp('6%'),
    borderColor: '#000',
    borderWidth: wp('0.4%'),
    fontSize: hp('2.6%'),
    padding: hp('1.3%'),
  },
});

export default styles;