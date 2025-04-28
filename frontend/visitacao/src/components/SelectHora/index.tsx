import { Text, View } from "react-native";
import { useState } from "react";
import { Picker } from "@react-native-picker/picker";
import { styles } from "./style";

type PropsSelect = {
  horas: string[];
  handleFunction: (hora: string) => void;
};

export const SelectHora = ({ horas, handleFunction }: PropsSelect) => {
  const [selectedHora, setSelectedHora] = useState<string>(horas[0] || "");

  return (
    <View style={styles.container}>
      <Text style={styles.label}>Escolha a hora:</Text>
      <View style={styles.content}>
        <Picker
          selectedValue={selectedHora}
          onValueChange={(itemValue) => {
            setSelectedHora(itemValue);
            handleFunction(itemValue)
          }}
          style={styles.picker}
        >
          {horas.map((hora) => (
            <Picker.Item key={hora} label={hora} value={hora} />
          ))}
        </Picker>
      </View>
    </View>
  );
};
