import React from 'react';
import { View, StatusBar, Platform } from 'react-native';
import { Routers } from './src/routes';
import Toast from 'react-native-toast-message';

export default function App() {
  return (
    <>
      {Platform.OS === 'android' && (
        <View style={{ height: StatusBar.currentHeight, backgroundColor: '#332222' }} />
      )}

      <StatusBar
        barStyle="light-content"
        backgroundColor="#332222"
        translucent={false}
      />

      <Routers />
      <Toast />
    </>
  );
}
