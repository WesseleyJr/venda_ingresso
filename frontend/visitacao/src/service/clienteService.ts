import axios from "axios";
import { FormDataCliente } from "../@types/api";
import AsyncStorage from "@react-native-async-storage/async-storage";


export const postCliente = async (data: FormDataCliente[], url: string ) => {
  const token = await AsyncStorage.getItem("@token");

  try {
    const response = await axios.post(`${url}/cliente/lista`, data, {headers:{Authorization: token}});
    return response;
  } catch (error) {
    if (axios.isAxiosError(error)) {
      throw error.response?.data || error;
    } else {
      throw error;
    }
  }
};