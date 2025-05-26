import { useEffect, useState } from "react";
import { useNavigation } from "@react-navigation/native";
import {
  Keyboard,
  KeyboardAvoidingView,
  ScrollView,
  TouchableWithoutFeedback,
  View,
  Text,
  ActivityIndicator,
} from "react-native";
import { styles } from "./style";
import { listarAgenda } from "../../service/agendaService";
import { useApiUrl } from "../../hooks/ApiUrlContext";
import { Header } from "../../components/Header";

export const VisualizarAgendas = () => {
  const { apiUrl } = useApiUrl();
  const navigation = useNavigation();

  const [agendas, setAgendas] = useState<any[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchAgendas = async () => {
      try {
        const response = await listarAgenda(apiUrl);
        setAgendas(response.data);
      } catch (error) {
        console.error("Erro ao carregar agendas:", error);
      } finally {
        setLoading(false);
      }
    };

    fetchAgendas();
  }, [apiUrl]);

  const formatarDataHora = (dataHoraString: string) => {
    const date = new Date(dataHoraString);
    const dataFormatada = date.toLocaleDateString("pt-BR");
    const horaFormatada = date.toLocaleTimeString("pt-BR", {
      hour: "2-digit",
      minute: "2-digit",
    });
    return { dataFormatada, horaFormatada };
  };

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
                <Text style={styles.title}>Visualizar Agendas</Text>

                {loading ? (
                  <ActivityIndicator color="#fff" size="large" />
                ) : agendas.length === 0 ? (
                  <Text style={{ color: "#fff" }}>Nenhuma agenda encontrada.</Text>
                ) : (
                  agendas.map((agenda) => {
                    const { dataFormatada, horaFormatada } = formatarDataHora(agenda.dataHora);
                    return (
                      <View key={agenda.id} style={{ marginBottom: 16 }}>
                        <Text style={{ color: "#fff", fontSize: 16 }}>
                          📅 Data: {dataFormatada} 🕒 Hora: {horaFormatada}
                        </Text>
                      </View>
                    );
                  })
                )}
              </View>
            </View>
          </ScrollView>
        </KeyboardAvoidingView>
      </TouchableWithoutFeedback>
    </>
  );
};
