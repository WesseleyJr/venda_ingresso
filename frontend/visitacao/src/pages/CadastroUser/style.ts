import { StyleSheet } from "react-native";

export const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: "#332222",
    alignItems: "center",
    padding: 1,
    marginTop: 50
  },

  header: {    
    flexDirection: "row",
    width: "90%",
    alignItems: "center",
    justifyContent: "flex-start",
    paddingHorizontal: 20,
    marginBottom: 100
  },

  logo: {
    width: 50, 
    height: 50,
    left: -100,
  },

  appName: {
    textAlign: "center",
    marginLeft: -50, 
    fontSize: 16,
    fontWeight: "bold",
    color: "#fff",

  },

  formContainer: {
    padding: 20,
    width: "90%",
    alignItems: "center",
    gap: 15
  },

  title: {
    fontSize: 20,
    fontWeight: "bold",
    color: "#fff",
    marginBottom: 30,
  },

  input: {
    backgroundColor: "#F0F0F0",
    borderRadius: 10,
    padding: 10,
    marginBottom: 10,
    width: "100%",
  },

  picker: {
    backgroundColor: "#F0F0F0",
    borderWidth: 1,
    borderColor: "#13293D",
    borderRadius: 10,
    width: "100%",
    marginBottom: 15,
  },

  button: {
    backgroundColor: "#661111",
    borderRadius: 10,
    width: "100%",
    alignItems: "center",
    marginTop: 20,
    paddingVertical: 10
  },
  buttonText: {
    color: "#FFF",
    fontSize: 18,
    fontWeight: "bold",
  },
});


