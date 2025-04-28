import { Text, TextInput, TouchableOpacity, View } from "react-native";
import { styles } from "./style";
import { Icon } from "react-native-elements";
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
        <>
          <View style={styles.container}>
            <TextInput
              onChangeText={hadleFunctionInput}
              style={styles.input}
              placeholder={placeHolder}
              placeholderTextColor="#000"
              secureTextEntry={propsShowEye ? viewPassword : typeInput}
              value={valueInput}
            />
            <Icon name="user" type="font-awesome-5" />
          </View>
        </>
      ) : (
        <>
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
                <Icon name="eye" type="font-awesome-5" />
              ) : (
                <Icon name="eye-slash" type="font-awesome-5" />
              )}
            </TouchableOpacity>
          </View>
        </>
      )}
    </>
  );
};
