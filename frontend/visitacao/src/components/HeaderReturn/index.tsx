import { useNavigation } from "@react-navigation/native";
import { styles } from "./style";
import { Image, TouchableOpacity, View } from "react-native";
import { Text } from "react-native-elements";
import { Icon } from "react-native-elements";
import Logo from "../../assets/images/brasaoCatedral.png";
import { useAuth } from "../../hooks/useAuth";

type PropsHeader = {
  handleFunction: ()=> void;
}

export const HeaderReturn = ({handleFunction}: PropsHeader) => {
  const navigate = useNavigation();
  const {handleLogOut} = useAuth();

  const handleLogout = () => {
    handleLogOut()
    navigate.navigate('Login')
  }
  
  return (
    <View style={styles.container}>
        <TouchableOpacity style={styles.button} onPress={handleFunction}>
          <Icon name="caretleft" type="antdesign" color="#fff" />
          <Text style={{ color: "#fff" }}>Voltar</Text>
        </TouchableOpacity>
        <Image style={styles.logo} source={Logo} />
        <TouchableOpacity style={styles.button} onPress={handleLogout}>
          <Text style={styles.textSair}>Sair</Text>
          <Icon name="exit-to-app" type="MaterialCommunityIcons" color="#fff" />
        </TouchableOpacity>
    </View>
  );
};
