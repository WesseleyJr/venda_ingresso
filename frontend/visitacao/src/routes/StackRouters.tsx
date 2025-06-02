import React, { useEffect, useState } from "react";
import { createNativeStackNavigator } from "@react-navigation/native-stack";
import AsyncStorage from "@react-native-async-storage/async-storage";

import { Login } from "../pages/Login";
import { CadastroUser } from "../pages/CadastroUser";
import { EsqueceuSenha } from "../pages/EsqueceuSenha";
import { Home } from "../pages/Home";
import { Agenda } from "../pages/Agenda";
import { CadastroIngresso } from "../pages/CadastroIngresso";
import { UserCpf } from "../pages/UserCpf";
import { Pagamento } from "../pages/Pagamento";
import { LeituraQrcode } from "../pages/LeituraQrcode";
import { CadastroAgenda } from "../pages/CadastroAgenda";
import { HomeGerencia } from "../pages/HomeGerencia";
import { VisualizarAgendas } from "../pages/VisualizarAgendas";
import { ValidarIngresso } from "../pages/ValidarIngresso";
import { CadastroGuia } from "../pages/CadastroGuia";

const Stack = createNativeStackNavigator();

export const StackRouters = () => {
  const [isLoading, setIsLoading] = useState(true);
  const [initialRoute, setInitialRoute] = useState<string>("Login");

  useEffect(() => {
    const checkAuthAndRole = async () => {
      const token = await AsyncStorage.getItem("@token");
      const role = await AsyncStorage.getItem("@role");

      if (token && role) {
        switch (role) {
          case "ROLE_USER":
            setInitialRoute("Home");
            break;
          case "ROLE_GERENCIA":
            setInitialRoute("Home/gerencia");
            break;
          case "ROLE_GUIA":
            setInitialRoute("Guia/Leitura");
            break;
          default:
            setInitialRoute("Login");
            break;
        }
      } else {
        setInitialRoute("Login");
      }

      setIsLoading(false);
    };

    checkAuthAndRole();
  }, []);

  if (isLoading) {
    return null; // Ou um splash/loading
  }

  return (
    <Stack.Navigator
      initialRouteName={initialRoute}
      screenOptions={{ headerShown: false }}
    >
      <Stack.Screen name="Login" component={Login} />
      <Stack.Screen name="Cadastro/usuario" component={CadastroUser} />
      <Stack.Screen name="Esqueceu/senha" component={EsqueceuSenha} />
      <Stack.Screen name="Home" component={Home} />
      <Stack.Screen name="Agenda" component={Agenda} />
      <Stack.Screen name="Cadastro/ingresso" component={CadastroIngresso} />
      <Stack.Screen name="Cadastro/user/cpf" component={UserCpf} />
      <Stack.Screen name="Pagamento" component={Pagamento} />
      <Stack.Screen name="Cadastro/agenda" component={CadastroAgenda} />
      <Stack.Screen name="Home/gerencia" component={HomeGerencia} />
      <Stack.Screen name="Visualizar/agendas" component={VisualizarAgendas} />
      <Stack.Screen name="Guia/Leitura" component={LeituraQrcode} />
      <Stack.Screen name="Guia/validar" component={ValidarIngresso} />
      <Stack.Screen name="Cadastro/guia" component={CadastroGuia} />
    </Stack.Navigator>
  );
};
