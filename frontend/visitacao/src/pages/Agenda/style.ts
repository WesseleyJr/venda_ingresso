import { StyleSheet } from "react-native";


export const styles = StyleSheet.create({
    container: {
      flex: 1,
      alignItems: "center",
      backgroundColor: '#332222',
      paddingVertical: 50,
      paddingHorizontal: 30,
      gap: 10
    },

    h1:{
        color: '#fff',
        fontSize: 18,
        fontWeight: 'bold'
    },
    h2:{
        color: '#fff',
        fontSize: 15,
        fontWeight: 'medium'
    },

    selectArea:{
      marginTop: 20,
      width: '100%',
      gap: 20
    },

    inputQnt:{
      backgroundColor: "#fff",
      width: "85%",
      height: 50,
      borderRadius: 10,
      fontSize: 14,
      paddingLeft: 8
    }
    

})