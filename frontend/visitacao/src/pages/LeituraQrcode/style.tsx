import { StyleSheet } from "react-native";

export const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: "#332222",
    alignItems: "center",
    padding: 1,
    marginTop: 50,
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
  formContainer: {
    padding: 20,
    width: "90%",
    alignItems: "center",
    gap: 15,
  },
  button: {
    backgroundColor: "#661111",
    borderRadius: 10,
    width: "100%",
    alignItems: "center",
    marginTop: 20,
    paddingVertical: 10,
  },
  buttonText: {
    color: "#FFF",
    fontSize: 18,
    fontWeight: "bold",
  },
  inputContent: {
    width: "100%",
    gap: 2,
  },
  label: {
    color: "#FFFFFF",
    fontSize: 16,
  },
  // Adicione no seu arquivo styles.ts

cameraWrapper: {
  marginTop: 20,
  width: '100%',
  aspectRatio: 1,
  borderRadius: 12,
  overflow: 'hidden',
},

camera: {
  flex: 1,
},

qrOverlay: {
  flex: 1,
  justifyContent: 'center',
  alignItems: 'center',
},

qrBox: {
  width: 250,
  height: 250,
  borderWidth: 2,
  borderColor: '#fff',
  borderRadius: 16,
  backgroundColor: 'rgba(255, 255, 255, 0.1)',
},

});
