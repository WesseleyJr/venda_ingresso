import { useNavigation } from "@react-navigation/native";
import { styles } from "./style";
import { Image, TouchableOpacity, View } from "react-native";
import { Text } from "react-native-elements";
import { Icon } from "react-native-elements";
import Logo from "../../assets/images/logoCatedralHorizontal.png";
import { useAuth } from "../../hooks/useAuth";


export const Header = () => {
  const navigate = useNavigation();
  const {handleLogOut} = useAuth();

  const handleLogout = () => {
    handleLogOut()
    navigate.navigate('Login')
  }
  
  return (
    <View style={styles.container}>
        {/* <TouchableOpacity style={styles.button} onPress={()=> navigate.navigate('Login')}>
          <Icon name="caretleft" type="antdesign" color="#fff" />
          <Text style={{ color: "#fff" }}>Voltar</Text>
        </TouchableOpacity> */}
        <Image style={styles.logo} source={Logo} />
        <TouchableOpacity style={styles.button} onPress={handleLogout}>
          <Text style={styles.textSair}>Sair</Text>
          <Icon name="exit-to-app" type="MaterialCommunityIcons" color="#fff" />
        </TouchableOpacity>
    </View>
  );
};
