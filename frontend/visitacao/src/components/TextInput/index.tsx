import { Ionicons, FontAwesome5 } from '@expo/vector-icons';
import { Text, TextInput, TouchableOpacity, View } from "react-native";
import { styles } from "./style";
import { useState } from "react";

interface PropsInput {
  placeHolder: string;
  typeInput?: boolean;
  valueInput: string;
  hadleFunctionInput: (value: string) => void;
  propsShowEye?: boolean;
}

export const TextInputField = ({
  placeHolder,
  typeInput,
  valueInput,
  propsShowEye = false,
  hadleFunctionInput,
}: PropsInput) => {
  const [viewPassword, setViewPassword] = useState<boolean>(true);

  return (
    <>
      {propsShowEye === false ? (
        <View style={styles.container}>
          <TextInput
            onChangeText={hadleFunctionInput}
            style={styles.input}
            placeholder={placeHolder}
            placeholderTextColor="#000"
            secureTextEntry={propsShowEye ? viewPassword : typeInput}
            value={valueInput}
          />
          <FontAwesome5 name="user" size={20} color="#000" />
        </View>
      ) : (
        <View style={styles.container}>
          <TextInput
            onChangeText={hadleFunctionInput}
            style={styles.input}
            placeholder={placeHolder}
            placeholderTextColor="#000"
            secureTextEntry={propsShowEye ? viewPassword : typeInput}
            value={valueInput}
          />
          <TouchableOpacity onPress={() => setViewPassword(!viewPassword)}>
            {viewPassword ? (
              <Ionicons name="eye" size={24} color="#000" />
            ) : (
              <Ionicons name="eye-off" size={24} color="#000" />
            )}
          </TouchableOpacity>
        </View>
      )}
    </>
  );
};
