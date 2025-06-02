import { View } from "react-native";
import { Text } from "react-native";

import { NavigationContainer } from "@react-navigation/native";
import { StackRouters } from "./StackRouters";
import { AuthProvider } from "../hooks/useAuth";
import { ApiUrlProvider } from "../hooks/ApiUrlContext";
import { CompraContextProvider } from "../hooks/compraContext";
import { IngressoProvider } from "../hooks/IngressoContext";

export const Routers = () => {
  return (
    <NavigationContainer>
      <ApiUrlProvider>
        <AuthProvider>
          <CompraContextProvider>
            <IngressoProvider>
              <StackRouters />
            </IngressoProvider>
          </CompraContextProvider>
        </AuthProvider>
      </ApiUrlProvider>
    </NavigationContainer>
  );
};
