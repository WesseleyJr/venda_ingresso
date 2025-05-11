import {
  ImageBackground,
  Linking,
  ScrollView,
  Text,
  TouchableOpacity,
  View,
} from "react-native";
import { styles } from "./style";
import { Header } from "../../components/Header";
import { useNavigation } from "@react-navigation/native";

import { FontAwesome5, AntDesign } from '@expo/vector-icons';

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

  const navigation = useNavigation();

  return (
    <>
      <Header />
      <View style={styles.container}>
        <View style={styles.content}>
          <TouchableOpacity style={styles.innerButton} onPress={() => navigation.navigate('Agenda')}>
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
            <FontAwesome5 name="facebook" color="#000" size={35} />
          </TouchableOpacity>
          <TouchableOpacity style={styles.buttonRedes} onPress={openInstagram}>
            <AntDesign name="instagram" color="#000" size={35} />
          </TouchableOpacity>
          <TouchableOpacity style={styles.buttonRedes} onPress={callPhone}>
            <FontAwesome5 name="phone" color="#000" size={35} />
          </TouchableOpacity>
        </View>
      </View>
    </>
  );
};
