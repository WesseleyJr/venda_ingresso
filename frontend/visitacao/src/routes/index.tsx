import { View } from "react-native";
import { Text } from "react-native";

import { NavigationContainer } from "@react-navigation/native";
import { StackRouters } from "./StackRouters";
import { AuthProvider } from "../hooks/useAuth";
import { ApiUrlProvider } from "../hooks/ApiUrlContext";
import { CompraContextProvider } from "../hooks/compraContext";

export const Routers = () => {
  return (
    <NavigationContainer>
      <ApiUrlProvider>
        <AuthProvider>
          <CompraContextProvider>
            <StackRouters />
          </CompraContextProvider>
        </AuthProvider>
      </ApiUrlProvider>
    </NavigationContainer>
  );
};
