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
import { useEffect, useState } from "react";
import Toast from "react-native-toast-message";
import { useApiUrl } from "../../hooks/ApiUrlContext";
import { postAgenda } from "../../service/agendaService";
import { getListaGuia, postGuia } from "../../service/guiaService";
import { FormDataGuia, FormDataGuiaGet, FormDataUserGet } from "../../@types/api";
import { Picker } from "@react-native-picker/picker";
import { getListaUser } from "../../service/userService";

export const CadastroGuia = () => {
  const navigation = useNavigation();
  const { apiUrl } = useApiUrl();


  const [nomeCompleto, setNomeCompleto] = useState<string>("");
  const [celular, setCelular] = useState<string>("");
  const [idUsuario, setIdUsuario] = useState<number>(0);
  const [cpf, setCpf] = useState<string>("");
  const [usuarios, setUsuarios] = useState<FormDataUserGet[]>([])

  useEffect(() => {
    const handleListarUsuarios = async () => {
      try {
        const response = await getListaUser(apiUrl);
        setUsuarios(response);
      } catch (error) {
        Toast.show({
          type: "error",
          text1: "Erro ao buscar usuarios",
        });
      }
    };

    handleListarUsuarios();
  }, []);

  const handleSubmit = async () => {
    const payload = {
      nomeCompleto: nomeCompleto,
      celular: celular.replace(/\D/g, ""),
      cpf: cpf,
      idUsuario: idUsuario,
    };

    try {
      await postGuia(payload, apiUrl);
      Toast.show({
        type: "success",
        text1: "Guia cadastrado com sucesso!",
      });
      navigation.goBack();
    } catch (error) {
      Toast.show({
        type: "error",
        text1: "Erro ao cadastrar guia",
      });
    }
  };

  
  const maskPhone = (value: string) => {
    value = value.replace(/\D/g, "");

    if (value.length > 11) {
      value = value.substring(0, 11);
    }

    if (value.length <= 10) {
      return value.replace(/(\d{2})(\d{4})(\d{0,4})/, "($1)$2-$3");
    }

    return value.replace(/(\d{2})(\d{5})(\d{0,4})/, "($1)$2-$3");
  };

  return (
    <>
      <HeaderReturn
        handleFunction={() => navigation.navigate("Home/gerencia")}
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
                <Text style={styles.title}>Cadastro Guia</Text>

                <View style={styles.inputContent}>
                  <Text style={styles.label}>Nome completo:</Text>
                  <TextInput
                    style={styles.input}
                    placeholder="Digite o nome completo"
                    onChangeText={(text) => setNomeCompleto(text)}
                  />
                </View>
                <View style={styles.inputContent}>
                  <Text style={styles.label}>Celular (Somente numeros):</Text>
                  <TextInput
                    style={styles.input}
                    placeholder="Celular"
                    value={celular}
                    keyboardType="numeric"
                    onChangeText={(text) => {
                      text = maskPhone(text);
                      setCelular(text);
                    }}
                  />
                </View>
                <View style={styles.inputContent}>
                  <Text style={styles.label}>CPF (Somente numeros):</Text>
                  <TextInput
                    style={styles.input}
                    placeholder="99999999999"
                    keyboardType="numeric"
                    onChangeText={(text) => setCpf(text)}
                  />
                </View>

                <View style={styles.inputContent}>
                  <Text style={styles.label}>Selecione um usuario:</Text>
                  <View style={[styles.input, { padding: 0 }]}>
                    <Picker
                      selectedValue={idUsuario}
                      onValueChange={(itemValue) => setIdUsuario(itemValue)}
                    >
                      <Picker.Item label="Selecione um guia" value={0} />
                      {usuarios.map((usuario) => (
                        <Picker.Item
                          key={usuario.id}
                          label={usuario.nome}
                          value={usuario.id}
                        />
                      ))}
                    </Picker>
                  </View>
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
