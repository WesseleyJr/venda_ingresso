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
  Image,
  Alert,
} from "react-native";
import { styles } from "./style";
import { Text } from "react-native";
import { useEffect, useState } from "react";
import { putCPF } from "../../service/userService";
import { useApiUrl } from "../../hooks/ApiUrlContext";
import { useAuth } from "../../hooks/useAuth";
import Toast from "react-native-toast-message";
import AsyncStorage from "@react-native-async-storage/async-storage";
import { postIngresso } from "../../service/ingressoService";
import { useCompraContext } from "../../hooks/compraContext";
import { Loading } from "../../components/Loading";
import { LoadingPagamento } from "../../components/LoadingPagamento";
import * as Clipboard from "expo-clipboard";

export const Pagamento = () => {
  const navigation = useNavigation();
  const { apiUrl } = useApiUrl();
  const { ingressos, valorTotal } = useCompraContext();
  const [aguarde, setAguarde] = useState<boolean>(true);
  const [imageQrcode, setImageQrcode] = useState<string>("");
  const [chavePix, setChavePix] = useState<string>("");

  useEffect(() => {
    handleSubmit();
  }, []);

  const handleSubmit = async () => {
    try {
      const response = await postIngresso(ingressos, apiUrl);
      setImageQrcode(response.data.pix.qrCodeBase64);
      setChavePix(response.data.pix.qrCode);
      setAguarde(false);
    } catch (error: any) {
      const primeiroErro =
        error?.erros && Array.isArray(error.erros)
          ? error.erros[0]
          : "Erro ao gerar chave pix.";

      Toast.show({
        type: "error",
        text2: primeiroErro,
        visibilityTime: 4000,
      });
    }
  };

  return (
    <>
      {aguarde ? (
        <LoadingPagamento />
      ) : (
        <>
          <HeaderReturn handleFunction={() => navigation.navigate("Home")} />
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
                    <Text style={styles.title}>Valor: R${valorTotal}</Text>
                    <Image
                      source={{ uri: `data:image/png;base64,${imageQrcode}` }}
                      style={{ width: 250, height: 250 }}
                      resizeMode="contain"
                    />
                    <Text
                      selectable
                      style={styles.textChavePix}
                    >
                      {chavePix}
                    </Text>
                    <TouchableOpacity
                      onPress={() => {
                        Clipboard.setStringAsync(chavePix);
                        Toast.show({
                          type: "success",
                          text1: "Chave PIX copiada!",
                          visibilityTime: 2000,
                        });
                      }}
                      style={styles.buttonCopiar}
                    >
                      <Text style={{ color: "#fff", fontWeight: "bold" }}>
                        Copiar chave PIX
                      </Text>
                    </TouchableOpacity>
                  </View>
                </View>
              </ScrollView>
            </KeyboardAvoidingView>
          </TouchableWithoutFeedback>
        </>
      )}
    </>
  );
};
