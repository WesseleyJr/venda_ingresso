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

export const EsqueceuSenha = () => {
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
                <TextInput
                  style={styles.input}
                  placeholder="Codigo"
                  onChangeText={() => console.log("teste")}
                />
                <TouchableOpacity style={styles.button}>
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
