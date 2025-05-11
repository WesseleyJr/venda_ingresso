import { useNavigation } from "@react-navigation/native";
import { styles } from "./style";
import { Image, Text, TouchableOpacity, View } from "react-native";
import { AntDesign } from "@expo/vector-icons"; // Importando ícone do @expo/vector-icons
import Logo from "../../assets/images/logoCatedralHorizontal.png";

export const HeaderCadastro = () => {
  const navigate = useNavigation();
  
  return (
    <View style={styles.container}>
      <TouchableOpacity style={styles.button} onPress={() => navigate.navigate('Login')}>
        <AntDesign name="caretleft" size={24} color="#fff" />  {/* Substituindo Icon por AntDesign */}
        <Text style={{ color: "#fff" }}>Voltar</Text>
      </TouchableOpacity>
      <Image style={styles.logo} source={Logo} />
    </View>
  );
};
