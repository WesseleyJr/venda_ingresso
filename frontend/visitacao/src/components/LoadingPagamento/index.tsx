import { ActivityIndicator, Text, View } from "react-native"
import { styles } from "./style"

export const LoadingPagamento = () => {

  return (
    <View style={styles.container}>
      <ActivityIndicator size={100} color="#fff" />
      <Text style={styles.title}>Aguarde, estamos gerando a chave PIX</Text>
    </View>
  )
}