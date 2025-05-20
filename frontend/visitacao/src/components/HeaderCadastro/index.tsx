import { useNavigation } from "@react-navigation/native";
import { styles } from "./style";
import { Image, Text, TouchableOpacity, View } from "react-native";
import { AntDesign } from "@expo/vector-icons";
import Logo from "../../assets/images/logoCatedralHorizontal.png";

export const HeaderCadastro = () => {
  const navigate = useNavigation();

  return (
    <View style={styles.container}>
      <TouchableOpacity
        style={[styles.button, { flexDirection: "row", alignItems: "center" }]}
        onPress={() => navigate.navigate("Login")}
      >
        <AntDesign name="caretleft" size={24} color="#fff" />
        <Text style={{ color: "#fff", marginLeft: 5 }}>Voltar</Text>
      </TouchableOpacity>
      <Image style={styles.logo} source={Logo} />
    </View>
  );
};
