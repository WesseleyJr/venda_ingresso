import {
  ImageBackground,
  Keyboard,
  KeyboardAvoidingView,
  Linking,
  ScrollView,
  Text,
  TouchableOpacity,
  TouchableWithoutFeedback,
  View,
} from "react-native";
import { styles } from "./style";
import { Header } from "../../components/Header";
import { Icon } from "react-native-elements";
import { useNavigation } from "@react-navigation/native";

export const Home = () => {
  const openInstagram = () => {
    Linking.openURL("https://www.instagram.com/catedraldepetropolis/");
  };

  const openFacebook = () => {
    Linking.openURL("https://www.facebook.com/pascomcatedralsaopedrodealcantara/");
  };

  const openYouTube = () => {
    Linking.openURL('https://www.youtube.com/c/CatedraldePetr%C3%B3polis');
  };

  const openSite = () => {
    Linking.openURL("https://www.catedraldepetropolis.org.br/");
  };

  const callPhone = () => {
    Linking.openURL("tel:+552422379027");
  };
  const navigation = useNavigation()
  return (
    <>
      <Header />
      <View style={styles.container}>
        <View style={styles.content}>
          <TouchableOpacity style={styles.innerButton} onPress={()=> navigation.navigate('Agenda')}>
            <ImageBackground
              source={require("../../assets/images/CatedralKoeller.png")} 
              style={styles.button}
              imageStyle={{ borderRadius: 20 }}
            >
              <Text style={styles.buttonText}>Visitação da torre</Text>
            </ImageBackground>
          </TouchableOpacity>

          <TouchableOpacity style={styles.buttonType} onPress={openSite}>
            <Text style={styles.buttonTextType}>Visite o nosso site</Text>
          </TouchableOpacity>
          <TouchableOpacity style={styles.buttonType} onPress={openYouTube}>
            <Text style={styles.buttonTextType}>Missas</Text>
          </TouchableOpacity>
        </View>
        <View style={styles.contentRedes}>
          <TouchableOpacity style={styles.buttonRedes} onPress={openFacebook}>
            <Icon
              name="facebook"
              type="fontawesome5brands"
              color="#000"
              size={35}
            />
          </TouchableOpacity>
          <TouchableOpacity style={styles.buttonRedes} onPress={openInstagram}>
            <Icon name="instagram" type="antdesign" color="#000" size={35} />
          </TouchableOpacity>
          <TouchableOpacity style={styles.buttonRedes} onPress={callPhone}>
            <Icon name="phone" type="FontAwesome5" color="#000" size={35} />
          </TouchableOpacity>
        </View>
      </View>
    </>
  );
};
