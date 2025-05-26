import axios from "axios";
import { PostUserData, PutCPFData } from "../@types/api";


export const postUser = async (data: PostUserData, url: string) => {
  try {
    const response = await axios.post(`${url}/usuario`, data);
    return response;
  } catch (error) {
    if (axios.isAxiosError(error)) {
      throw error.response?.data || error;
    } else {
      throw error;
    }
  }
};
export const putCPF = async (cpf: string, url: string, idUser: number) => {

  const data = {
    cpf: cpf
  }

  try {
    const response = await axios.put(`${url}/usuario/cpf/${idUser}`, data);
    return response;
  } catch (error) {
    if (axios.isAxiosError(error)) {
      throw error.response?.data || error;
    } else {
      throw error;
    }
  }
};
export const buscarUserPorId = async ( url: string, idUser: number) => {

  try {
    const response = await axios.get(`${url}/usuario/${idUser}`);
    return response;
  } catch (error) {
    if (axios.isAxiosError(error)) {
      throw error.response?.data || error;
    } else {
      throw error;
    }
  }
};
