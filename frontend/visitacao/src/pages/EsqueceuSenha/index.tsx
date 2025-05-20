import { useNavigation } from "@react-navigation/native";
import {
  Keyboard,
  KeyboardAvoidingView,
  ScrollView,
  Text,
  TextInput,
  TouchableOpacity,
  TouchableWithoutFeedback,
  View,
} from "react-native";
import { styles } from "./style";
import { HeaderCadastro } from "../../components/HeaderCadastro";
import { useEffect, useState } from "react";
import { postEmail, postRedefinirSenha } from "../../service/esqueciSenha";
import { useApiUrl } from "../../hooks/ApiUrlContext";
import {
  FormDataEmailEsqueceuSenha,
  FormDataRedefinirSenha,
} from "../../@types/api";
import Toast from "react-native-toast-message";
import { TextInputField } from "../../components/TextInput";
import { useAuth } from "../../hooks/useAuth";

export const EsqueceuSenha = () => {
  const { apiUrl } = useApiUrl();
  const navigation = useNavigation();
  const [email, setEmail] = useState<string>("");
  const [emailEnviado, setEmailEnviado] = useState<boolean>(false);
  const [aguarde, setAguarde] = useState<boolean>(false);
  const [form, setForm] = useState<FormDataRedefinirSenha>({
    token: "",
    novaSenha: "",
    confirmaSenha: "",
  });

  const { handleLogOut } = useAuth();
  
  const handleEmail = async () => {
    setAguarde(true);
    const data: FormDataEmailEsqueceuSenha = {
      email: email,
    };
    try {
      const response = await postEmail(data, apiUrl);
      setEmailEnviado(true);
      setAguarde(false);
      Toast.show({
        type: "success",
        text1: response.data,
      });
    } catch (error: any) {
      setAguarde(false);
      Toast.show({
        type: "error",
        text1: "Erro ao enviar email",
        text2: "email não cadastrado",
        visibilityTime: 4000,
      });
    }
  };

  const handleRedefinirSenha = async () => {
    setAguarde(true);
    handleLogOut()
    try {
      const response = await postRedefinirSenha(form, apiUrl);
      Toast.show({
        type: "success",
        text1: response.data,
      });
      setTimeout(() => {
        navigation.navigate("Login");
      }, 1000);
    } catch (error: any) {
      Toast.show({
        type: "error",
        text1: "Token invalido",
        visibilityTime: 4000,
      });
    }
  };

  return (
    <>
      <HeaderCadastro />
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
              <View style={styles.formContainer}>
                <Text style={styles.title}>Esqueceu sua senha?</Text>
                {!emailEnviado && (
                  <>
                    <TextInput
                      style={styles.input}
                      placeholder="Email"
                      onChangeText={(text) => setEmail(text)}
                    />
                    <TouchableOpacity
                      style={styles.button}
                      onPress={handleEmail}
                    >
                      <Text style={styles.buttonText}>Enviar</Text>
                    </TouchableOpacity>
                  </>
                )}
                {aguarde && <Text style={styles.title}>Aguarde...</Text>}
                {emailEnviado && (
                  <>
                    <TextInput
                      style={styles.input}
                      placeholderTextColor="#000"
                      placeholder="Token"
                      keyboardType="numeric"
                      maxLength={6}
                      onChangeText={(text) => {
                        const onlyNumbers = text.replace(/[^0-9]/g, "");
                        setForm({ ...form, token: onlyNumbers });
                      }}
                    />
                    <TextInputField
                      placeHolder="Senha"
                      valueInput={form.novaSenha}
                      hadleFunctionInput={(text) =>
                        setForm({ ...form, novaSenha: text })
                      }
                      typeInput={true}
                      propsShowEye={true}
                    />
                    <TextInputField
                      placeHolder="Confirma senha"
                      valueInput={form.confirmaSenha}
                      hadleFunctionInput={(text) =>
                        setForm({ ...form, confirmaSenha: text })
                      }
                      typeInput={true}
                      propsShowEye={true}
                    />
                    <TouchableOpacity
                      style={styles.button}
                      onPress={handleRedefinirSenha}
                    >
                      <Text style={styles.buttonText}>Enviar</Text>
                    </TouchableOpacity>
                  </>
                )}
              </View>
            </View>
          </ScrollView>
        </KeyboardAvoidingView>
      </TouchableWithoutFeedback>
    </>
  );
};
