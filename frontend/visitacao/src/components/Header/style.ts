import { StyleSheet } from "react-native";

export const styles = StyleSheet.create({
  container: {
    backgroundColor: "#332222",
    flexDirection: "row",
    width: "100%",
    height:100,
    paddingTop: 30,
    alignItems: 'center',
    justifyContent: 'space-between',
    paddingHorizontal: 20
  },


  logo: {
    height: 60,
    width: 155,
  },
  
  text: {
    color: "#FFF",
    fontSize: 12,
    textAlign: 'center',
  },

  button: {
    flexDirection: 'row',
    alignItems: 'center'
  },

  textSair:{
    color: "#FFF",
    marginRight: 5
  }
});
