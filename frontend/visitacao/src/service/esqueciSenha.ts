import axios from "axios";
import { FormDataEmailEsqueceuSenha, FormDataRedefinirSenha } from "../@types/api";
import AsyncStorage from "@react-native-async-storage/async-storage";


export const postEmail = async (data: FormDataEmailEsqueceuSenha, url: string ) => {
  try {
    const response = await axios.post(`${url}/senha/esqueci`, data);
    return response;
  } catch (error) {
    console.log(error);
    
    if (axios.isAxiosError(error)) {
      throw error.response?.data || error;
    } else {
      throw error;
    }
  }
};
export const postRedefinirSenha = async (data: FormDataRedefinirSenha, url: string ) => {

  try {
    const response = await axios.post(`${url}/senha/redefinir`, data);
    return response;
  } catch (error) {
    console.log(error);
    
    if (axios.isAxiosError(error)) {
      throw error.response?.data || error;
    } else {
      throw error;
    }
  }
};