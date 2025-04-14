import {
  Image,
  Keyboard,
  KeyboardAvoidingView,
  ScrollView,
  Text,
  TouchableOpacity,
  TouchableWithoutFeedback,
  View,
} from "react-native";
import { styles } from "./style";
import Logo from "../../assets/images/logoCatedralHorizontal.png";
import { TextInputField } from "../../components/TextInput";
import { useState } from "react";
import { ButtonType } from "../../components/ButtonType";
import { useNavigation } from "@react-navigation/native";

export const Login = () => {
  const [email, setEmail] = useState<string>("");
  const [password, setPassword] = useState<string>("");

  const navigate = useNavigation()

  const handlePassword = (value: string) => {
    setPassword(value);
  };

  const handleEmail = (value: string) => {
    setEmail(value);
  };

  const handleLogin = () => {

    navigate.navigate('Home')
  };

  return (
    <TouchableWithoutFeedback onPress={Keyboard.dismiss}>
      <KeyboardAvoidingView
        style={{
          flex: 1,
          flexDirection: "column",
          justifyContent: "center",
          backgroundColor: "#332222",
        }}
        behavior="padding"
        enabled
        keyboardVerticalOffset={10}
      >
        <ScrollView style={{ backgroundColor: "#332222" }}>
          <View style={styles.container}>
            <Image style={styles.logo} source={Logo} />
            <Text style={styles.text}>Faça seu Login</Text>

            <View style={styles.inserir}>
              <TextInputField
                propsLabel="Email"
                placeHolder="Digite seu email"
                valueInput={email}
                hadleFunctionInput={handleEmail}
              />
            </View>

            <View style={styles.senha}>
              <TextInputField
                propsLabel="Senha"
                placeHolder="Digite sua senha"
                valueInput={password}
                hadleFunctionInput={handlePassword}
                typeInput={true}
                propsShowEye={true}
              />
            </View>

            <ButtonType
              title="Entrar"
              handleFunction={handleLogin}
              propsBackgroundColor="#661111"
            />

            <View style={styles.boxEsqueceuSenha}>
              <Text style={styles.textoCadastro}>Esqueceu a senha?</Text>

              <View>
                <TouchableOpacity
                  style={styles.buttonEntrar}
                  onPress={() => navigate.navigate('Esqueceu/senha')}
                >
                  <Text style={styles.buttonText}>Clique aqui</Text>
                </TouchableOpacity>
              </View>
            </View>
            <View style={styles.boxCadrastro}>
              <Text style={styles.textoCadastro}>Não tem conta?</Text>

              <View>
                <TouchableOpacity
                  style={styles.buttonEntrar}
                  onPress={() => navigate.navigate('Cadastro')}
                >
                  <Text style={styles.buttonText}>Cadastre-se</Text>
                </TouchableOpacity>
              </View>
            </View>
          </View>
        </ScrollView>
      </KeyboardAvoidingView>
    </TouchableWithoutFeedback>
  );
};
