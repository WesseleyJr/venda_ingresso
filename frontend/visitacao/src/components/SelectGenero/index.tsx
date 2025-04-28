import { Text, View } from "react-native";
import { useState } from "react";
import { Picker } from "@react-native-picker/picker";
import { styles } from "./style";

type PropsSelect = {
  handleFunction: (genero: string) => void;
};

export const SelectGenero = ({ handleFunction }: PropsSelect) => {
  const [selectedGenero, setSelectedGenero] = useState<string>("");

  return (
    <View style={styles.container}>
      <Text style={styles.label}>Escolha a hora:</Text>
      <View style={styles.content}>
        <Picker
          selectedValue={selectedGenero}
          onValueChange={(itemValue) => {
            setSelectedGenero(itemValue);
            handleFunction(itemValue)
          }}
          style={styles.picker}
        >
            <Picker.Item key={'MASCULINO'} label={'Masculino'} value={'MASCULINO'} />
            <Picker.Item key={'FEMININO'} label={'Feminino'} value={'FEMININO'} />
            <Picker.Item key={'OUTROS'} label={'Outros'} value={'OUTROS'} />
        </Picker>
      </View>
    </View>
  );
};
