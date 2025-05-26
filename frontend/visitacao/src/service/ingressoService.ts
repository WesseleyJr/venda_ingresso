import axios from "axios";
import {FormDataIngresso } from "../@types/api";
import AsyncStorage from "@react-native-async-storage/async-storage";


export const postIngresso = async (data: FormDataIngresso[], url: string ) => {
  const token = await AsyncStorage.getItem("@token");

  try {
    const response = await axios.post(`${url}/ingresso`, data, {headers:{Authorization: token}});
    
    return response;
  } catch (error) {
    
    if (axios.isAxiosError(error)) {
      throw error.response?.data || error;
    } else {
      throw error;
    }
  }
};