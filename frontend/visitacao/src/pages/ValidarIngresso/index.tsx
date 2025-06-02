import { useNavigation } from "@react-navigation/native";
import { useEffect, useState } from "react";
import {
  Keyboard,
  KeyboardAvoidingView,
  ScrollView,
  Text,
  TouchableOpacity,
  TouchableWithoutFeedback,
  View,
} from "react-native";
import { HeaderReturn } from "../../components/HeaderReturn";
import { useIngresso } from "../../hooks/IngressoContext";
import { styles } from "./style";
import {
  getIngressoById,
  putIngressoById,
} from "../../service/ingressoService";
import { useApiUrl } from "../../hooks/ApiUrlContext";
import Toast from "react-native-toast-message";
import { TextInput } from "react-native-paper";

export const ValidarIngresso = () => {
  const navigation = useNavigation();
  const { idIngresso } = useIngresso();
  const { apiUrl } = useApiUrl();
  const [nome, setNome] = useState<string>("");
  const [data, setData] = useState<string>("");
  const [hora, setHora] = useState<string>("");
  const [nomeResponsavel, setNomeResponsavel] = useState<string>("");
  const [status, setStatus] = useState<string>("");

  useEffect(() => {
    if (idIngresso !== 0) {
      handleFunction(idIngresso);
    }
  }, [idIngresso]);

  const handleFunction = async (id: number) => {
    try {
      const response = await getIngressoById(id, apiUrl);
      setNome(response.nomeCompleto);
      setStatus(response.statusIngressoEnum);

      if (response.nomeResponsavel) {
        setNomeResponsavel(response.nomeResponsavel);
      }

      if (response.dataHora) {
        const dataHora = new Date(response.dataHora);

        const dataFormatada = dataHora.toLocaleDateString("pt-BR");

        const horaFormatada = dataHora.toLocaleTimeString("pt-BR", {
          hour: "2-digit",
          minute: "2-digit",
        });

        setData(dataFormatada);
        setHora(horaFormatada);
      }
    } catch (error: any) {
      Toast.show({
        type: "error",
        text1: "Erro ao buscar dados do ingresso",
        visibilityTime: 4000,
      });
    }
  };

  const handleSubmit = async () => {
    try {
      const response = await putIngressoById(idIngresso, "VALIDADO", apiUrl);
      Toast.show({
        type: "success",
        text1: "Validado com sucesso!",
        visibilityTime: 4000,
      });
      setTimeout(() => {
        navigation.navigate("Guia/Leitura");
      }, 1000);
    } catch (error: any) {
      Toast.show({
        type: "error",
        text1: "Erro ao validar ingresso",
        visibilityTime: 4000,
      });
    }
  };

  return (
    <>
      <HeaderReturn
        handleFunction={() => navigation.navigate("Guia/Leitura")}
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
                <Text style={styles.title}>Ingresso</Text>
                <TextInput
                  style={styles.input}
                  placeholder="Nome"
                  value={nome}
                  editable={false}
                />
                <TextInput
                  style={styles.input}
                  placeholder="data"
                  value={data}
                  editable={false}
                />
                <TextInput
                  style={styles.input}
                  placeholder="hora"
                  value={hora}
                  editable={false}
                />
                <TextInput
                  style={styles.input}
                  placeholder="status"
                  value={status}
                  editable={false}
                />
                {nomeResponsavel && (
                  <TextInput
                    style={styles.input}
                    placeholder="nomeResponsavel"
                    value={nomeResponsavel}
                    editable={false}
                  />
                )}

                <TouchableOpacity style={styles.button} onPress={handleSubmit}>
                  <Text style={styles.buttonText}>Validar</Text>
                </TouchableOpacity>
              </View>
            </View>
          </ScrollView>
        </KeyboardAvoidingView>
      </TouchableWithoutFeedback>
    </>
  );
};
