import React, { useEffect, useState } from "react";
import { createNativeStackNavigator } from "@react-navigation/native-stack";
import AsyncStorage from "@react-native-async-storage/async-storage";

import { Login } from "../pages/Login";
import { CadastroUser } from "../pages/CadastroUser";
import { EsqueceuSenha } from "../pages/EsqueceuSenha";
import { Home } from "../pages/Home";
import { Agenda } from "../pages/Agenda";
import { CadastroIngresso } from "../pages/CadastroIngresso";

const Stack = createNativeStackNavigator();

export const StackRouters = () => {
  const [isLoading, setIsLoading] = useState(true);
  const [isAuthenticated, setIsAuthenticated] = useState(false);

  useEffect(() => {
    const checkToken = async () => {
      const token = await AsyncStorage.getItem("@token");
      console.log(token);
      

      if (token) {
        setIsAuthenticated(true);
      } else {
        setIsAuthenticated(false);
      }

      setIsLoading(false);
    };

    checkToken();
  }, []);

  if (isLoading) {
    return null;
  }

  return (
    <Stack.Navigator
      initialRouteName={isAuthenticated ? "Home" : "Login"}
      screenOptions={{ headerShown: false }}
    >
      <Stack.Screen name="Login" component={Login} />
      <Stack.Screen name="Cadastro/usuario" component={CadastroUser} />
      <Stack.Screen name="Esqueceu/senha" component={EsqueceuSenha} />
      <Stack.Screen name="Home" component={Home} />
      <Stack.Screen name="Agenda" component={Agenda} />
      <Stack.Screen name="Cadastro/ingresso" component={CadastroIngresso} />
    </Stack.Navigator>
  );
};
