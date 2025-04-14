import { View } from "react-native"
import { Text } from "react-native"

import { NavigationContainer } from '@react-navigation/native';
import { StackRouters } from "./StackRouters";


export const Routers = () => {

    return(
            <NavigationContainer>
                <StackRouters/>
            </NavigationContainer>
    )
}