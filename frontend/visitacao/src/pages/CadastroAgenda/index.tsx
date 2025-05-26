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
import Toast from "react-native-toast-message";
import { useApiUrl } from "../../hooks/ApiUrlContext";
import { postAgenda } from "../../service/agendaService";

// Função para aplicar máscara de data: AAAA-MM-DD
const formatDate = (value: string) => {
  const digits = value.replace(/\D/g, "").slice(0, 8);
  if (digits.length <= 4) return digits;
  if (digits.length <= 6) return `${digits.slice(0, 4)}-${digits.slice(4)}`;
  return `${digits.slice(0, 4)}-${digits.slice(4, 6)}-${digits.slice(6)}`;
};

// Função para aplicar máscara de hora: HH:MM
const formatTime = (value: string) => {
  const digits = value.replace(/\D/g, "").slice(0, 4);
  if (digits.length <= 2) return digits;
  return `${digits.slice(0, 2)}:${digits.slice(2)}`;
};

export const CadastroAgenda = () => {
  const navigation = useNavigation();
  const { apiUrl } = useApiUrl();

  const [data, setData] = useState("");
  const [hora, setHora] = useState("");
  const [capacidade, setCapacidade] = useState("");
  const [preco, setPreco] = useState("");

  const handleSubmit = async () => {
    if (!data || !hora || !capacidade || !preco) {
      Toast.show({
        type: "error",
        text1: "Preencha todos os campos!",
      });
      return;
    }

    const dataHora = `${data}T${hora}:00`;

    const payload = {
      dataHora,
      preco: parseFloat(preco.replace(",", ".")),
      capacidade: parseInt(capacidade),
      idGuia: 1,
    };

    try {
      await postAgenda(payload, apiUrl);
      Toast.show({
        type: "success",
        text1: "Agenda cadastrada com sucesso!",
      });
      navigation.goBack();
    } catch (error) {
      Toast.show({
        type: "error",
        text1: "Erro ao cadastrar agenda",
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
                <Text style={styles.title}>Cadastro Agenda</Text>

                <View style={styles.inputContent}>
                  <Text style={styles.label}>Escolha a data:</Text>
                  <TextInput
                    style={styles.input}
                    placeholder="AAAA-MM-DD"
                    value={data}
                    keyboardType="numeric"
                    onChangeText={(text) => setData(formatDate(text))}
                  />
                </View>

                <View style={styles.inputContent}>
                  <Text style={styles.label}>Escolha a hora:</Text>
                  <TextInput
                    style={styles.input}
                    placeholder="HH:MM"
                    value={hora}
                    keyboardType="numeric"
                    onChangeText={(text) => setHora(formatTime(text))}
                  />
                </View>

                <View style={styles.inputContent}>
                  <Text style={styles.label}>
                    Digite a capacidade de pessoas:
                  </Text>
                  <TextInput
                    style={styles.input}
                    placeholder="Capacidade"
                    keyboardType="numeric"
                    value={capacidade}
                    onChangeText={setCapacidade}
                  />
                </View>

                <View style={styles.inputContent}>
                  <Text style={styles.label}>Digite o preço unitário:</Text>
                  <TextInput
                    style={styles.input}
                    placeholder="Preço"
                    keyboardType="numeric"
                    value={preco}
                    onChangeText={setPreco}
                  />
                </View>

                <TouchableOpacity style={styles.button} onPress={handleSubmit}>
                  <Text style={styles.buttonText}>Cadastrar</Text>
                </TouchableOpacity>
              </View>
            </View>
          </ScrollView>
        </KeyboardAvoidingView>
      </TouchableWithoutFeedback>
    </>
  );
};
