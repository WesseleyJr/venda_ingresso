import { StyleSheet } from "react-native";

export const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: "center",
    alignItems: "center",
    backgroundColor: "#332222",
    paddingVertical: 50,
    paddingHorizontal: 30,
  },

  content: {
    width: "100%",
    gap: 30,
    height: "80%",
    alignItems: "center",
    justifyContent: "center", 
  },

  button: {
    width: '100%',
    paddingVertical: 30,
    justifyContent: 'center',
    alignItems: 'center',
  },
  innerButton: {
    justifyContent: "center",
    alignItems: "center",
    width: "100%",
    marginBottom: 30,
  },
  buttonText: {
    color: "#fff",
    fontSize: 20,
    fontWeight: "bold",
  },

  buttonType: {
    width: "100%",
    paddingVertical: 20,
    justifyContent: "center",
    alignItems: "center",
    backgroundColor: "#4b0a0a",
    borderRadius: 20,
  },
  buttonTypeMIssas: {
    width: "100%",
    paddingVertical: 20,
    justifyContent: "center",
    alignItems: "center",
    backgroundColor: "#4b0a0a",
    borderRadius: 20,
  },
  buttonTextType: {
    color: "#fff",
    fontSize: 18,
    fontWeight: "medium",
  },

  contentRedes: {
    marginTop: 30,
    width: "100%",
    flexDirection: "row",
    justifyContent: "space-evenly",
  },

  buttonRedes: {
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: 'rgb(189, 189, 189)',
    borderRadius: '30%',
    padding: 5,
    shadowColor: 'rgb(0, 0, 0)',
    shadowOffset: { width: 0, height: 10 },
    shadowOpacity: 0.25,
    shadowRadius: 15,
    elevation: 8,
  },
});
