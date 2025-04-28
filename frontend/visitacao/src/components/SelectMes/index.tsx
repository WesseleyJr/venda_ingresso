import { Text, View } from "react-native";
import { useState, useEffect } from "react";
import { Picker } from "@react-native-picker/picker";
import { styles } from "./style";

type PropsSelect = {
  handleFunction: (month: string) => void;
};

export const SelectMes = ({ handleFunction }: PropsSelect) => {
  const [selectedMonth, setSelectedMonth] = useState<string>('01');
  const [mesesDisponiveis, setMesesDisponiveis] = useState<any[]>([]);

  useEffect(() => {
    const dataAtual = new Date();
    const mesAtual = dataAtual.getMonth();
    const meses = [];
    
    for (let i = 0; i < 4; i++) {
      const mesFuturo = (mesAtual + i) % 12;
      const anoFuturo = mesAtual + i >= 12 ? dataAtual.getFullYear() + 1 : dataAtual.getFullYear();
      meses.push({
        mes: mesFuturo + 1,
        ano: anoFuturo,
        label: new Date(anoFuturo, mesFuturo)
          .toLocaleString('pt-BR', { month: 'long' })
          .charAt(0).toUpperCase() + new Date(anoFuturo, mesFuturo).toLocaleString('pt-BR', { month: 'long' }).slice(1),
      });
    }
    
    setMesesDisponiveis(meses);
    setSelectedMonth(meses[0].mes.toString().padStart(2, '0'));
  }, []);

  return (
    <View style={styles.container}>
      <Text style={styles.label}>Escolha o mês:</Text>
      <View style={styles.content}>
        <Picker
          selectedValue={selectedMonth}
          onValueChange={(itemValue) => {
            setSelectedMonth(itemValue);
            handleFunction(itemValue); 
          }}
          style={styles.picker}
        >
          {mesesDisponiveis.map((mes) => (
            <Picker.Item key={mes.mes} label={`${mes.label} ${mes.ano}`} value={mes.mes.toString().padStart(2, '0')} />
          ))}
        </Picker>
      </View>
    </View>
  );
};
