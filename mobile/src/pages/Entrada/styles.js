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
  dataInput: {
    marginBottom: hp('2%'),
  },
  labels: {
    marginStart: wp('10%'),
    fontWeight: 'bold',
    fontSize: hp('2.2%'),
  },
  picker: {
    marginHorizontal: wp('10%'),
    alignSelf: 'center',
    height: hp('6%'),
    width: '80%',
  },
  inputText: {
    marginHorizontal: wp('10%'),
    marginBottom: hp('25%'),
    height: hp('6%'),
    width: '20%',
    borderColor: '#000',
    borderWidth: wp('0.4%'),
    fontSize: hp('2.6%'),
    padding: hp('1.3%'),
  },
});

export default styles;
