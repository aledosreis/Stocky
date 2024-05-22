import {StyleSheet} from 'react-native';
import {widthPercentageToDP as wp, heightPercentageToDP as hp} from 'react-native-responsive-screen';

const styles = StyleSheet.create({
  logoImage: {
    marginTop: hp('15%'),
    width: wp('100%'),
    height: hp('10%'),
    resizeMode: "cover",
  },
  title: {
    marginHorizontal: wp('10%'),
    marginTop: hp('10%'),
    marginBottom: hp('5%'),
    fontSize: hp('3.5%'),
    textAlign: 'center',
    fontWeight: 'bold',
  },
  updateInfo: {
    marginHorizontal: wp('10%'),
    marginTop: hp('3%'),
    marginBottom: hp('2%'),
    fontSize: hp('2.2%'),
    textAlign: 'justify',
  }
});

export default styles;