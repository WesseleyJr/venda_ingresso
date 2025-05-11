import {
  KeyboardAvoidingView,
  Platform,
  ScrollView,
  Text,
  TextInput,
  View,
} from "react-native";
import { HeaderReturn } from "../../components/HeaderReturn";
import { useNavigation } from "@react-navigation/native";
import { styles } from "./style";
import { useEffect, useState } from "react";
import { FormDataCliente, FormDataIngresso } from "../../@types/api";
import DataNascimento from "../../components/DataNascimento";
import { SelectGenero } from "../../components/SelectGenero";
import { ButtonType } from "../../components/ButtonType";
import { useCompraContext } from "../../hooks/compraContext";
import { postIngresso } from "../../service/ingressoService";
import { useApiUrl } from "../../hooks/ApiUrlContext";
import Toast from "react-native-toast-message";
import AsyncStorage from "@react-native-async-storage/async-storage";

export const CadastroIngresso = () => {
    const navigation = useNavigation();
    const { qntIngresso, agendaId } = useCompraContext();
    const { apiUrl } = useApiUrl();
    const [formDataList, setFormDataList] = useState<FormDataIngresso[]>([]);
    
    useEffect(() => {
      const fetchUserId = async () => {
        const storedIdUsuario = await AsyncStorage.getItem("@userId");
        const parsedIdUsuario = storedIdUsuario ? Number(storedIdUsuario) : 0;
        const parsedIdAgenda = agendaId ? agendaId : 0;
    
        if (parsedIdUsuario !== null) {
          const initialFormData = Array.from({ length: qntIngresso }, () => ({
            statusIngressoEnum: "PENDENTE",
            idAgenda: parsedIdAgenda,
            nomeCompleto: "",
            celular: "",
            dataNascimento: "",
            nomeResponsavel: '',
            idUsuario: parsedIdUsuario,
            idPagamento: 0,
          }));
    
          setFormDataList(initialFormData);
        }
      };
    
      fetchUserId();
    }, [qntIngresso]);

  const handleInputChange = (
    index: number,
    field: keyof FormDataIngresso,
    value: string
  ) => {
    setFormDataList((prev) => {
      const updated = [...prev];
      updated[index] = {
        ...updated[index],
        [field]: value,
      };
      return updated;
    });
  };

  const [menorIdadeList, setMenorIdadeList] = useState<boolean[]>([]);

  const handleIdade = (index: number, item: Date) => {
    const hoje = new Date();
    const idade = hoje.getFullYear() - item.getFullYear();
    const mes = hoje.getMonth() - item.getMonth();
    const dia = hoje.getDate() - item.getDate();

    let idadeFinal = idade;

    if (mes < 0 || (mes === 0 && dia < 0)) {
      idadeFinal--;
    }

    const isMenor = idadeFinal < 18;

    setMenorIdadeList((prev) => {
      const updated = [...prev];
      updated[index] = isMenor;
      return updated;
    });

    const dataFormatada = item.toISOString().split("T")[0];

    handleInputChange(index, "dataNascimento", dataFormatada);
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

  const handleSubmit = async () => {

    const cleanFormDataList = formDataList.map((formData) => ({
        ...formData,
        celular: formData.celular.replace(/\D/g, ""),
      }));

      console.log(cleanFormDataList);
      
    try {
      const response = await postIngresso(cleanFormDataList, apiUrl);
      Toast.show({
        type: "success",
        text1: "Cliente cadastrado!",
        text2: response?.data?.mensagem || "Cadastro realizado com sucesso!",
      });
    } catch (error: any) {
      const primeiroErro =
        error?.erros && Array.isArray(error.erros)
          ? error.erros[0]
          : "Erro ao cadastrar clientes.";

      Toast.show({
        type: "error",
        text1: "Erro de cadastro",
        text2: primeiroErro,
        visibilityTime: 4000,
      });
    }
  };

  return (
    <>
      <HeaderReturn handleFunction={() => navigation.navigate("Agenda")} />
      <KeyboardAvoidingView
        style={{
          flex: 1,
          flexDirection: "column",
          justifyContent: "center",
          backgroundColor: "#332222",
        }}
        behavior="padding"
        enabled
        keyboardVerticalOffset={Platform.OS === "ios" ? 80 : 20}
      >
        <ScrollView contentContainerStyle={{ flexGrow: 1 }}>
          <View style={styles.container}>
            {formDataList.map((formData, index) => (
              <View key={index} style={styles.main}>
                <Text style={styles.h1}>Ingresso {index + 1}</Text>
                <View style={styles.inputContent}>
                  <Text style={styles.label}>Nome Completo:</Text>
                  <TextInput
                    style={styles.input}
                    placeholder="Nome Completo"
                    value={formData.nomeCompleto}
                    onChangeText={(text) =>
                      handleInputChange(index, "nomeCompleto", text)
                    }
                  />
                </View>
                <View style={styles.inputContent}>
                  <Text style={styles.label}>Celular:</Text>
                  <TextInput
                    style={styles.input}
                    placeholder="Celular"
                    value={formData.celular}
                    onChangeText={(text) => {
                      text = maskPhone(text);
                      handleInputChange(index, "celular", text);
                    }}
                  />
                </View>
                <DataNascimento
                  onDateSelected={(date) => handleIdade(index, date)}
                />
                {menorIdadeList[index] && (
                  <View style={styles.inputContent}>
                    <Text style={styles.label}>Nome do responsável:</Text>
                    <TextInput
                      style={styles.input}
                      placeholder="Nome responsável"
                      value={formData.nomeResponsavel}
                      onChangeText={(text) =>
                        handleInputChange(index, "nomeResponsavel", text)
                      }
                    />
                  </View>
                )}
              </View>
            ))}
            <ButtonType
              title={"Continuar"}
              handleFunction={handleSubmit}
              propsBackgroundColor="#800909"
            />
          </View>
        </ScrollView>
      </KeyboardAvoidingView>
    </>
  );
};
