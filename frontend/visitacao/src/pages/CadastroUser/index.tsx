import React, { useState } from "react";

import { useNavigation } from "@react-navigation/native";
import {
  Button,
  KeyboardAvoidingView,
  ScrollView,
  Text,
  TextInput,
  TouchableOpacity,
  View,
} from "react-native";
import { styles } from "./style";
import { HeaderCadastro } from "../../components/HeaderCadastro";
import { postUser } from "../../service/userService";
import { TextInputField } from "../../components/TextInput";
import Toast from "react-native-toast-message";
import { useApiUrl } from "../../hooks/ApiUrlContext";

export const CadastroUser = () => {
  const navigate = useNavigation();
  const { apiUrl } = useApiUrl();

  const [form, setForm] = useState({
    nome: "",
    email: "",
    senha: "",
    confirmaSenha: "",
  });

  const handleSubmit = async () => {
    const user = {
      nome: form.nome,
      email: form.email,
      senha: form.senha,
      confirmaSenha: form.confirmaSenha,
      perfis: [
        {
          id: 2,
        },
      ],
    };

    try {
      const response = await postUser(user, apiUrl);

      Toast.show({
        type: "success",
        text1: "Usuário cadastrado!",
        text2: response?.data?.mensagem || "Cadastro realizado com sucesso!",
      });

      navigate.navigate("Login");
    } catch (error: any) {
      const primeiroErro =
        error?.erros && Array.isArray(error.erros)
          ? error.erros[0]
          : "Erro ao cadastrar usuário.";

      Toast.show({
        type: "error",
        text1: "Erro de cadastro",
        text2: primeiroErro,
        visibilityTime: 4000,
      });
    }
  };

  const showToast = () => {
    Toast.show({
      type: "success",
      position: "top",
      text1: "Sucesso!",
      text2: "A requisição foi realizada com sucesso!",
    });
  };

  return (
    <>
      <HeaderCadastro />
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
        <ScrollView contentContainerStyle={{ flexGrow: 1 }}>
          <View style={styles.container}>
            <View style={styles.formContainer}>
              <Text style={styles.title}>Cadastro de Usuário</Text>
              <TextInput
                style={styles.input}
                placeholder="Nome"
                value={form.nome}
                onChangeText={(text) => setForm({ ...form, nome: text })}
              />
              <TextInput
                style={styles.input}
                placeholder="E-mail"
                value={form.email}
                onChangeText={(text) => setForm({ ...form, email: text })}
              />
              <View style={styles.senha}>
                <TextInputField
                  placeHolder="Senha"
                  valueInput={form.senha}
                  hadleFunctionInput={(text) =>
                    setForm({ ...form, senha: text })
                  }
                  typeInput={true}
                  propsShowEye={true}
                />
              </View>
              <View style={styles.senha}>
                <TextInputField
                  placeHolder="Confirma senha"
                  valueInput={form.confirmaSenha}
                  hadleFunctionInput={(text) =>
                    setForm({ ...form, confirmaSenha: text })
                  }
                  typeInput={true}
                  propsShowEye={true}
                />
              </View>
              <TouchableOpacity style={styles.button} onPress={handleSubmit}>
                <Text style={styles.buttonText}>Cadastrar</Text>
              </TouchableOpacity>
            </View>
          </View>
        </ScrollView>
      </KeyboardAvoidingView>
    </>
  );
};
