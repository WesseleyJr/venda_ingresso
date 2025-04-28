import { Text, View } from "react-native";
import { useEffect, useState } from "react";
import { Picker } from "@react-native-picker/picker";
import { styles } from "./style";

type PropsSelect = {
  dias: number[];
  handleFunction: (day: number) => void;
};

export const SelectDia = ({dias, handleFunction}: PropsSelect) => {
  const [selectedDay, setSelectedDay] = useState<number>(dias[0] || 1);

  return (
    <View style={styles.container}>
      <Text style={styles.label}>Escolha o dia:</Text>
      <View style={styles.content}>
        <Picker
          selectedValue={selectedDay}
          onValueChange={(itemValue) => {
            setSelectedDay(itemValue);
            handleFunction(itemValue)
          }}
          style={styles.picker}
        >
          {dias.map((dia) => (
            <Picker.Item key={dia} label={`Dia ${dia}`} value={dia} />
          ))}
        </Picker>
      </View>
    </View>
  );
};
