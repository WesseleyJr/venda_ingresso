import AsyncStorage from "@react-native-async-storage/async-storage";
import axios from "axios";
import { FormDataGuia } from "../@types/api";

export const getListaGuia = async (apiUrl: string) => {
  const token = await AsyncStorage.getItem("@token");

  try {
    const response = await axios.get(`${apiUrl}/guia`, {headers:{Authorization: token}});
    return response.data;
  } catch (error) {
    if (axios.isAxiosError(error)) {
      throw error.response?.data || error;
    } else {
      
      throw error;
    }
  }
};



export const postGuia = async (data: FormDataGuia, apiUrl: string) => {
  const token = await AsyncStorage.getItem("@token");

  try {
    const response = await axios.post(`${apiUrl}/guia`, data, {headers:{Authorization: token}});
    return response.data;
  } catch (error) {
    if (axios.isAxiosError(error)) {
      throw error.response?.data || error;
    } else {
      
      throw error;
    }
  }
};