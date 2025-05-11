import React, { useState } from 'react';
import { View, Platform, Text, TouchableOpacity } from 'react-native';
import DateTimePicker, { DateTimePickerEvent } from '@react-native-community/datetimepicker';
import { styles } from './style';
import { FontAwesome5 } from '@expo/vector-icons';  // Substituindo o Icon do react-native-elements

interface DataNascimentoProps {
  onDateSelected: (date: Date) => void;
}

const DataNascimento: React.FC<DataNascimentoProps> = ({ onDateSelected }) => {
  const [date, setDate] = useState<Date>(new Date());
  const [showPicker, setShowPicker] = useState<boolean>(false);

  const handleChange = (event: DateTimePickerEvent, selectedDate?: Date) => {
    if (event.type === 'set' && selectedDate) {
      setDate(selectedDate);
      onDateSelected(selectedDate);
    }
    setShowPicker(false);
  };

  const showDatePicker = () => {
    setShowPicker(true);
  };

  return (
    <View style={styles.container}>
      <Text style={styles.selectedDate}>Data de nascimento:</Text>
      <TouchableOpacity style={styles.button} onPress={showDatePicker}>
        <Text style={styles.buttonText}>{date.toLocaleDateString('pt-BR')}</Text>
        <FontAwesome5 name="calendar" size={24} color="black" /> {/* Substituindo o Icon por FontAwesome5 */}
      </TouchableOpacity>

      {showPicker && (
        <DateTimePicker
          value={date}
          mode="date"
          display={Platform.OS === 'ios' ? 'spinner' : 'calendar'}
          maximumDate={new Date()}
          onChange={handleChange}
        />
      )}
    </View>
  );
};

export default DataNascimento;
