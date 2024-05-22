import {StyleSheet} from 'react-native';
import {widthPercentageToDP as wp, heightPercentageToDP as hp} from 'react-native-responsive-screen';

const styles = StyleSheet.create({
  logoImage: {
    marginTop: hp('10%'),
    width: wp('100%'),
    height: hp('10%'),
    resizeMode: "cover",
  },
  viewButton: {
    marginHorizontal: wp('5%'),
    marginTop: hp('8%'),
  },
  creator: {
    fontSize: hp('1.7%'),
    marginTop: hp('13%'),
    marginLeft: wp('35%'),
  },
});

export default styles;