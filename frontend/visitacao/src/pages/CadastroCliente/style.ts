import { StyleSheet } from "react-native";


export const styles = StyleSheet.create({
    container: {
      flex: 1,
      alignItems: "center",
      backgroundColor: '#332222',
      paddingVertical: 20,
      paddingHorizontal: 30,
    },

    main:{
      width: '100%',
      gap: 10,
      marginBottom: 20
    },
    
    h1:{
        color: '#fff',
        fontSize: 18,
        fontWeight: 'bold',
        marginBottom: 10,
    },

    input:{
        backgroundColor: "#fff",
        width: "100%",
        height: 50,
        borderRadius: 10,
        fontSize: 14,
        paddingLeft: 8
      },
      
      pickerContainer: {
        marginBottom: 20,
      },
      pickerLabel: {
        fontSize: 16,
        marginBottom: 5,
      },
      picker: {
        height: 40,
        borderColor: "#ccc",
        borderWidth: 1,
        borderRadius: 5,
      },
      submitButton: {
        backgroundColor: "#4CAF50",
        paddingVertical: 10,
        borderRadius: 5,
        alignItems: "center",
      },
      submitButtonText: {
        color: "#fff",
        fontSize: 18,
      },

      inputContent: {
        width: '100%',
        gap: 2
      },

      label: {
        color: '#FFFFFF',
        fontSize: 16,
      }

})