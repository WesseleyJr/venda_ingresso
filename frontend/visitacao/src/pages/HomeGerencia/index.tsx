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
import { Header } from "../../components/Header";

export const HomeGerencia = () => {
  const navigation = useNavigation();
  const { apiUrl } = useApiUrl();

  const handleSubmit = async () => {};

  return (
    <>
      <Header />
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
                <TouchableOpacity style={styles.buttonType} onPress={()=>navigation.navigate('Cadastro/agenda')}>
                  <Text style={styles.buttonTextType}>Cadastrar agenda</Text>
                </TouchableOpacity>
                <TouchableOpacity style={styles.buttonType} onPress={()=>navigation.navigate('Cadastro/guia')}>
                  <Text style={styles.buttonTextType}>Cadastrar guia</Text>
                </TouchableOpacity>
                <TouchableOpacity style={styles.buttonType} onPress={()=>navigation.navigate('Visualizar/agendas')}>
                  <Text style={styles.buttonTextType}>Visualizar agendas</Text>
                </TouchableOpacity>
              </View>
            </View>
          </ScrollView>
        </KeyboardAvoidingView>
      </TouchableWithoutFeedback>
    </>
  );
};
