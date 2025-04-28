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
import React, { useState } from "react";
import { ButtonType } from "../../components/ButtonType";
import { useNavigation } from "@react-navigation/native";
import { postLogin } from "../../service/loginService";
import Toast from "react-native-toast-message";
import { useAuth } from "../../hooks/useAuth";
import { Loading } from "../../components/Loading";

export const Login = () => {
  const [form, setForm] = useState({
    username: "",
    password: "",
  });
  const { isLoading, checkAuthentication } = useAuth();

  const navigate = useNavigation();


  return (
    <React.Fragment>
      {isLoading ? (
        <Loading />
      ) : (
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
                    placeHolder="Digite seu email"
                    valueInput={form.username}
                    hadleFunctionInput={(text) =>{ 
                      text = text.toLocaleLowerCase()
                      setForm({ ...form, username: text })}
                    }
                  />
                </View>
  
                <View style={styles.senha}>
                  <TextInputField
                    placeHolder="Digite sua senha"
                    valueInput={form.password}
                    hadleFunctionInput={(text) =>
                      setForm({ ...form, password: text })
                    }
                    typeInput={true}
                    propsShowEye={true}
                  />
                </View>
  
                <ButtonType
                  title="Entrar"
                  handleFunction={()=>checkAuthentication(form.username, form.password)}
                  propsBackgroundColor="#661111"
                />
  
                <View style={styles.boxEsqueceuSenha}>
                  <Text style={styles.textoCadastro}>Esqueceu a senha?</Text>
  
                  <View>
                    <TouchableOpacity
                      style={styles.buttonEntrar}
                      onPress={() => navigate.navigate("Esqueceu/senha")}
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
                      onPress={() => navigate.navigate("Cadastro/usuario")}
                    >
                      <Text style={styles.buttonText}>Cadastre-se</Text>
                    </TouchableOpacity>
                  </View>
                </View>
              </View>
            </ScrollView>
          </KeyboardAvoidingView>
        </TouchableWithoutFeedback>
      )}
    </React.Fragment>
  );
  
};
