import { useNavigation } from "@react-navigation/native";
import { styles } from "./style";
import { Image, TouchableOpacity, View } from "react-native";
import { Text, IconButton } from "react-native-paper";
import Logo from "../../assets/images/logoCatedralHorizontal.png";
import { useAuth } from "../../hooks/useAuth";

export const Header = () => {
  const navigate = useNavigation();
  const { handleLogOut } = useAuth();

  const handleLogout = () => {
    handleLogOut();
    navigate.navigate("Login");
  };

  return (
    <View style={styles.container}>
      <Image style={styles.logo} source={Logo} />
      <TouchableOpacity style={styles.button} onPress={handleLogout}>
        <Text style={styles.textSair}>Sair</Text>
        <IconButton icon="logout" iconColor="#fff" size={20} />
      </TouchableOpacity>
    </View>
  );
};
