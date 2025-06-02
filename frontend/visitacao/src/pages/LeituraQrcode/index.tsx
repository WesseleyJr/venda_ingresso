import { useEffect, useRef, useState, useCallback } from "react";
import {
  ActivityIndicator,
  Keyboard,
  KeyboardAvoidingView,
  ScrollView,
  Text,
  TouchableWithoutFeedback,
  View,
} from "react-native";
import { Header } from "../../components/Header";
import { styles } from "./style";

import {
  CameraView,
  useCameraPermissions,
  BarcodeScanningResult,
} from "expo-camera";
import type { CameraView as CameraViewType } from "expo-camera";
import { useNavigation, useFocusEffect, useIsFocused } from "@react-navigation/native";
import { useIngresso } from "../../hooks/IngressoContext";

export const LeituraQrcode = () => {
  const [permission, requestPermission] = useCameraPermissions();
  const [scanned, setScanned] = useState(false);
  const navigation = useNavigation();
  const isFocused = useIsFocused(); // 👈 Importante!
  const { setIdIngresso } = useIngresso();
  const cameraRef = useRef<CameraViewType | null>(null);

  useEffect(() => {
    if (!permission) {
      requestPermission();
    }
  }, []);

  // Resetar o scanner quando a tela for reaberta
  useFocusEffect(
    useCallback(() => {
      setScanned(false);
      console.log("Tela LeituraQrcode reaberta, scanner resetado");
    }, [])
  );

  const handleBarCodeScanned = (result: BarcodeScanningResult) => {
    if (!scanned) {
      setScanned(true);
      console.log("QR Code Lido:", result.data);

      try {
        const parsedData = JSON.parse(result.data);
        console.log("Objeto do QR Code:", parsedData);
        setIdIngresso(parsedData.id);
        navigation.navigate("Guia/validar");
      } catch (error) {
        console.warn("QR Code inválido. Não é um JSON:", result.data);
      }
    }
  };

  return (
    <>
      <Header />
      <TouchableWithoutFeedback onPress={Keyboard.dismiss}>
        <KeyboardAvoidingView
          style={{
            flex: 1,
            flexDirection: "column",
            justifyContent: "center",
            backgroundColor: "#332222",
          }}
          behavior="padding"
          enabled
          keyboardVerticalOffset={10}
        >
          <ScrollView style={{ backgroundColor: "#332222" }}>
            <View style={styles.container}>
              <View style={styles.formContainer}>
                <Text style={styles.title}>Leitura QR Code</Text>

                {!permission && <ActivityIndicator size="large" color="#fff" />}

                {permission && !permission.granted && (
                  <Text style={{ color: "#fff" }}>
                    Sem permissão para usar a câmera.
                  </Text>
                )}

                {/* ✅ Só mostra a câmera se a tela estiver focada */}
                {isFocused && permission?.granted && (
                  <View style={styles.cameraWrapper}>
                    <CameraView
                      ref={cameraRef}
                      style={styles.camera}
                      facing="back"
                      barcodeScannerSettings={{
                        barcodeTypes: ["qr"],
                      }}
                      onBarcodeScanned={handleBarCodeScanned}
                    >
                      <View style={styles.qrOverlay}>
                        <View style={styles.qrBox} />
                      </View>
                    </CameraView>
                  </View>
                )}
              </View>
            </View>
          </ScrollView>
        </KeyboardAvoidingView>
      </TouchableWithoutFeedback>
    </>
  );
};
