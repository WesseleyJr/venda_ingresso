import { createNativeStackNavigator } from '@react-navigation/native-stack';
import { Login } from '../pages/Login';
import { CadastroUser } from '../pages/CadastroUser';
import { EsqueceuSenha } from '../pages/EsqueceuSenha';
import { Home } from '../pages/Home';

const Stack = createNativeStackNavigator();

export const StackRouters = () => {


    return(
        <Stack.Navigator initialRouteName='Login' screenOptions={{headerShown: false}}>
            <Stack.Screen name='Login' component={Login}/>
            <Stack.Screen name='Cadastro' component={CadastroUser}/>
            <Stack.Screen name='Esqueceu/senha' component={EsqueceuSenha}/>
            <Stack.Screen name='Home' component={Home}/>
        </Stack.Navigator>
    )
}