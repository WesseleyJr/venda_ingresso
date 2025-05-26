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

export const LeituraQrcode = () => {
  

  return (
    <>
      <Header/>
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
                <Text style={styles.title}>Leitura qrcode</Text>
              </View>
            </View>
          </ScrollView>
        </KeyboardAvoidingView>
      </TouchableWithoutFeedback>
    </>
  );
};
