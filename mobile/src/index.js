import 'react-native-gesture-handler';
import React, { useEffect, useState } from 'react';
import SplashScreen from 'react-native-splash-screen';
import {getVersion} from 'react-native-device-info';
import Routes from './routes';
import UpdateScreen from './pages/Update/Update';
import api from './services/api';

const App = () => {
  const [lastVersion, setLastVersion] = useState();
  const [downLink, setDownLink] = useState('');
  const [appVersion, setAppVersion] = useState();

  useEffect(() => {
    setAppVersion(getVersion);
    async function hasUpdate() {
      const response = await api.get('update');
      const version = response.data.version;
      const url = response.data.url;
      setLastVersion(version);        
      setDownLink(url);
    }
    hasUpdate();
    SplashScreen.hide();
  }, []);
  
  if (lastVersion > appVersion){
    return(
      <UpdateScreen url = {downLink} version= {lastVersion} />
    );
  } else {
    return(
      <Routes />
    );
  }
}

export default App;
