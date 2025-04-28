import axios from "axios";
import { PostUserData } from "../@types/api";


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
