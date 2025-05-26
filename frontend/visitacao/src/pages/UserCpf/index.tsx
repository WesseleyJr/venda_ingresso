import { useNavigation } from "@react-navigation/native";
import { HeaderReturn } from "../../components/HeaderReturn";
import {
  Keyboard,
  KeyboardAvoidingView,
  ScrollView,
  TextInput,
  TouchableOpacity,
  TouchableWithoutFeedback,
  View,
} from "react-native";
import { styles } from "./style";
import { Text } from "react-native";
import { useState } from "react";
import { putCPF } from "../../service/userService";
import { useApiUrl } from "../../hooks/ApiUrlContext";
import { useAuth } from "../../hooks/useAuth";
import Toast from "react-native-toast-message";
import AsyncStorage from "@react-native-async-storage/async-storage";

export const UserCpf = () => {
  const navigation = useNavigation();
  const { apiUrl } = useApiUrl();
  

  const [cpf, setCpf] = useState<string>('');
const handleSubmit = async () => {

  try {
    const userIdString = await AsyncStorage.getItem("@userId");
    if (!userIdString) {
      Toast.show({
        type: "error",
        text1: "Usuário não encontrado",
      });
      return;
    }

    const userId = parseInt(userIdString, 10);

    const response = await putCPF(cpf, apiUrl, userId);
    setTimeout(() => {
      navigation.navigate("Pagamento");
    }, 1000);
  } catch (error: any) {
    Toast.show({
      type: "error",
      text1: "Erro ao cadastrar CPF",
      visibilityTime: 4000,
    });
  }
};


  return (
    <>
      <HeaderReturn
        handleFunction={() => navigation.navigate("Cadastro/ingresso")}
      />
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
                <Text style={styles.title}>Digite o CPF do usuário</Text>

                <View style={styles.inputContent}>
                  <Text style={styles.label}>Digite somente números:</Text>
                  <TextInput
                    style={styles.input}
                    placeholder="CPF"
                    keyboardType="numeric"
                    onChangeText={(text) => {
                      setCpf(text)
                    }}
                  />
                </View>
                <TouchableOpacity
                  style={styles.button}
                  onPress={handleSubmit}
                >
                  <Text style={styles.buttonText}>Enviar</Text>
                </TouchableOpacity>
              </View>
            </View>
          </ScrollView>
        </KeyboardAvoidingView>
      </TouchableWithoutFeedback>
    </>
  );
};
