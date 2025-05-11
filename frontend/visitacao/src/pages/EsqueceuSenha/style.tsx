import { StyleSheet } from "react-native";

export const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: "#332222",
    alignItems: "center",
    padding: 1,
    marginTop: 50
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
    backgroundColor: "#fff",
    borderRadius: 10,
    padding: 10,
    marginBottom: 10,
    width: "100%",
    height: 50,
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
})