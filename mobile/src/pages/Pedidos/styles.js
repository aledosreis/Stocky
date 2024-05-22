import {StyleSheet} from 'react-native';
import {widthPercentageToDP as wp, heightPercentageToDP as hp} from 'react-native-responsive-screen';


const styles = StyleSheet.create({
  listPedidos: {
    margin: wp('5%'),
    minHeight: hp('40%'),
    maxHeight: hp('40%'),
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
  dataInput: {
    marginBottom: hp('2%'),
    flexDirection: 'row',
    alignContent: 'space-between',
  },
  labels: {
    alignSelf: 'flex-start',
    marginHorizontal: wp('7%'),
    fontWeight: 'bold',
    fontSize: hp('2.2%'),
    marginTop: hp('1.5%'),
  },
  picker: {
    alignSelf: 'flex-start',
    height: hp('6%'),
    width: wp('60%'),
  },
  inputText: {
    alignSelf: 'flex-end',
    marginTop: hp('1.5%'),
    height: hp('6%'),
    width: wp('15%'),
    borderColor: '#000',
    borderWidth: 1,
    fontSize: hp('2.6%'),
    padding: hp('1.3%'),
  },
  viewButton: {
    flexDirection: 'row',
    alignContent: 'space-between',
  },
  buttons: {
    marginTop: hp('2%'),
    marginEnd: wp('5%'),
    marginStart: wp('5%'),
    padding: hp('3%'),
    width: wp('40%'),
  },
});

export default styles;