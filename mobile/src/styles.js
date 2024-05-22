import {StyleSheet} from 'react-native';
import {widthPercentageToDP as wp, heightPercentageToDP as hp} from 'react-native-responsive-screen';

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#fff',
  },
  title: {
    marginTop: hp('3%'),
    fontSize: hp('2.8%'),
    fontWeight: 'bold',
    color: '#000',
    textAlign: 'center',
  },
  buttons: {
    height: hp('5%'),
    borderRadius: hp('6%'),
    borderColor: '#CECECE',
    backgroundColor: '#cecece',
    justifyContent: 'center',
    alignItems: 'center',
    margin: hp('3%'),
  },
  textButton: {
    fontSize: hp('2.8%'),
    fontWeight: 'bold',
    color: '#000',
  },
});

export default styles;