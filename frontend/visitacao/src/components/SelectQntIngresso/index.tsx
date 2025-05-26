import { Text, View } from "react-native";
import { useEffect, useState } from "react";
import { Picker } from "@react-native-picker/picker";
import { styles } from "./style";

type PropsSelect = {
  qntIngresso: number;
  handleFunction: (qnt: number) => void;
};

export const SelectQntIngresso = ({ qntIngresso, handleFunction }: PropsSelect) => {
  const [selectedQnt, setSelectedQnt] = useState<number>(1);

  const options = Array.from({ length: qntIngresso }, (_, index) => index + 1);

  return (
    <View style={styles.container}>
      <Text style={styles.label}>Escolha a quantidade de ingressos:</Text>
      <View style={styles.content}>
        <Picker
          selectedValue={selectedQnt}
          onValueChange={(itemValue) => {
            setSelectedQnt(itemValue);
            handleFunction(itemValue);
          }}
          style={styles.picker}
        >
          {options.map((option) => (
            <Picker.Item key={option} label={`${option}`} value={option} />
          ))}
        </Picker>
      </View>
    </View>
  );
};