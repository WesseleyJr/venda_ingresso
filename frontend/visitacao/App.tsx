import React from 'react';
import { StatusBar } from 'expo-status-bar';
import { Routers } from './src/routes';
import Toast from 'react-native-toast-message';

export default function App() {
  return (
    <>
          <StatusBar style="light" backgroundColor="#332222"/>
          <Routers/>
          <Toast />
    </>
  )

};
