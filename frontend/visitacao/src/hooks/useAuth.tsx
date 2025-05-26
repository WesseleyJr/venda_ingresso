import { useContext, createContext, useState, ReactNode } from "react";
import { postLogin } from "../service/loginService";
import { useNavigation } from "@react-navigation/native";
import { JwtPayload, PostLoginData } from "../@types/api";
import { jwtDecode } from "jwt-decode";
import { useApiUrl } from "./ApiUrlContext";
import Toast from "react-native-toast-message";
import AsyncStorage from "@react-native-async-storage/async-storage";

type PropsContext = {
  email: string;
  setEmail: (value: string) => void;
  userId: number | null;
  setUserId: (value: number | null) => void;
  token: string | null;
  setToken: (value: string | null) => void;
  timeOut: number | null;
  setTimeOut: (value: number | null) => void;
  role: string | null;
  setRole: (value: string | null) => void;
  checkAuthentication: (email: string, password: string) => Promise<void>;
  handleLogOut: () => void;
  isLoading: boolean;
  setIsLoading: (value: boolean) => void;
};

const AuthContext = createContext<PropsContext>({
  email: "",
  setEmail: () => {},
  userId: null,
  setUserId: () => {},
  token: null,
  setToken: () => {},
  timeOut: null,
  setTimeOut: () => {},
  role: null,
  setRole: () => {},
  checkAuthentication: async () => {},
  handleLogOut: () => {},
  isLoading: false,
  setIsLoading: () => {},
});

type AuthProviderProps = {
  children: ReactNode;
};

export const AuthProvider = ({ children }: AuthProviderProps) => {
  const navigation = useNavigation();
  const { apiUrl } = useApiUrl();

  const [email, setEmail] = useState<string>("");
  const [token, setToken] = useState<string | null>(null);
  const [isLoading, setIsLoading] = useState<boolean>(false);
  const [userId, setUserId] = useState<number | null>(null);
  const [timeOut, setTimeOut] = useState<number | null>(null);
  const [role, setRole] = useState<string | null>(null);

  const checkAuthentication = async (email: string, password: string) => {
    setIsLoading(true);

    const dataLogin = {
      username: email,
      password: password,
    };

    try {
      const token = await postLogin(dataLogin, apiUrl);

      if (token) {
        setToken(token);
        const decoded = jwtDecode<JwtPayload>(token);

        setEmail(decoded.sub);
        setUserId(decoded.id);
        setTimeOut(decoded.exp);
        setRole(decoded.role);
        console.log(decoded.role);
        

        await AsyncStorage.setItem("@token", token);
        await AsyncStorage.setItem("@email", decoded.sub);
        await AsyncStorage.setItem("@userId", String(decoded.id));
        await AsyncStorage.setItem("@timeOut", String(decoded.exp));

        Toast.show({
          type: "success",
          text1: "Sucesso!",
        });

        if (decoded.role.includes('ROLE_USER')){
          navigation.navigate('Home');
        }else if(decoded.role.includes('ROLE_GUIA')){
          navigation.navigate('Guia/Leitura');
        }else if(decoded.role.includes('ROLE_GERENCIA')){
          navigation.navigate('Home/gerencia');
        }
        
      }
    } catch (error: any) {
      Toast.show({
        type: "error",
        text1: "Erro ao efetuar o login",
        text2: "Email ou senha invalido",
        visibilityTime: 4000,
      });
    } finally {
      setIsLoading(false);
    }
  };

  const handleLogOut = async  () => {
    setEmail("");
    setToken(null);
    setUserId(null);
    setTimeOut(null);
    setRole(null);

    await AsyncStorage.multiRemove([
      '@token',
      '@email',
      '@userId',
      '@timeOut'
    ]);
  };

  return (
    <AuthContext.Provider
      value={{
        email,
        setEmail,
        token,
        setToken,
        timeOut,
        setTimeOut,
        isLoading,
        setIsLoading,
        userId,
        setUserId,
        role,
        setRole,
        checkAuthentication,
        handleLogOut,
      }}
    >
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => useContext(AuthContext);
