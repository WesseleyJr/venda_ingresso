import { StyleSheet } from "react-native";


export const styles = StyleSheet.create({
    container: {
      flex: 1,
      justifyContent: "center",
      alignItems: "center",
      backgroundColor: '#332222',
      paddingVertical: 50,
      paddingHorizontal: 30,
    },

    content:{
      width: '100%',
      gap: 30,
      height: '80%',
      alignItems: 'center',
      justifyContent:'center',
    },

    button: {
      width: '100%',
      paddingVertical: 30,
      justifyContent: 'center',
      alignItems: 'center',
    },
    innerButton: {
      justifyContent: 'center',
      alignItems: 'center',
      width: '100%',
      marginBottom:30,
    },
    buttonText: {
      color: '#fff',
      fontSize: 20,
      fontWeight: 'bold'
    },
  
    buttonType:{
      width: '100%',
      paddingVertical: 20,
      justifyContent: 'center',
      alignItems: 'center',
      backgroundColor: '#ffffffb3',
      borderRadius: 20
    },
    buttonTextType: {
      color: '#000',
      fontSize: 18,
      fontWeight: 'medium'
    },

    contentRedes:{
      marginTop: 30,
      width: '100%',
      flexDirection: 'row',
      justifyContent: 'space-evenly'

    },

    buttonRedes:{
      justifyContent: 'center',
      alignItems: 'center',
      backgroundColor: '#ffffffb3',
      borderRadius: '50%',
      padding: 5
    },



})