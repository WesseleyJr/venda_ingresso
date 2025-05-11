import AsyncStorage from "@react-native-async-storage/async-storage";
import axios from "axios";

export const getAgendaMes = async (mes: number, apiUrl: string) => {
  const token = await AsyncStorage.getItem("@token");

  try {
    const response = await axios.get(`${apiUrl}/agenda/mes/${mes}`, {headers:{Authorization: token}});
    console.log(response);
    
    return response.data;
  } catch (error) {
    if (axios.isAxiosError(error)) {
      throw error.response?.data || error;
    } else {
      console.log(error);
      
      throw error;
    }
  }
};

export const getAgendaDia = async (mes: number, dia: number, apiUrl: string) => {
  const token = await AsyncStorage.getItem("@token");

  try {
    const response = await axios.get(`${apiUrl}/agenda/mes/dia/${mes}/${dia}`, {headers:{Authorization: token}});
    return response.data;
  } catch (error) {
    if (axios.isAxiosError(error)) {
      throw error.response?.data || error;
    } else {
      throw error;
    }
  }
};
