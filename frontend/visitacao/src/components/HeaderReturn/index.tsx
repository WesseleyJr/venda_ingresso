import { useNavigation } from "@react-navigation/native";
import { styles } from "./style";
import { Image, TouchableOpacity, View, Text } from "react-native";
import { FontAwesome5, AntDesign, MaterialCommunityIcons } from "@expo/vector-icons";
import Logo from "../../assets/images/brasaoCatedral.png";
import { useAuth } from "../../hooks/useAuth";

type PropsHeader = {
  handleFunction: () => void;
};

export const HeaderReturn = ({ handleFunction }: PropsHeader) => {
  const navigate = useNavigation();
  const { handleLogOut } = useAuth();

  const handleLogout = () => {
    handleLogOut();
    navigate.navigate("Login");
  };

  return (
    <View style={styles.container}>
      <TouchableOpacity style={styles.button} onPress={handleFunction}>
        <View style={{ flexDirection: "row", alignItems: "center" }}>
          <AntDesign name="caretleft" size={24} color="#fff" />
          <Text style={{ color: "#fff", marginLeft: 5 }}>Voltar</Text>
        </View>
      </TouchableOpacity>

      <Image style={styles.logo} source={Logo} />

      <TouchableOpacity style={styles.button} onPress={handleLogout}>
        <View style={{ flexDirection: "row", alignItems: "center" }}>
          <Text style={styles.textSair}>Sair</Text>
          <MaterialCommunityIcons
            name="exit-to-app"
            size={24}
            color="#fff"
            style={{ marginLeft: 5 }}
          />
        </View>
      </TouchableOpacity>
    </View>
  );
};
