import axios from 'axios';
import { PostLoginData } from '../@types/api';

export const postLogin = async (data: PostLoginData, apiUrl: string) => {
  try {
    const response = await axios.post(`${apiUrl}/login`, data);

    let token = response.headers.authorization;
    token = token.replace('Bearer ', '');

    return token;
  } catch (error) {
    if (axios.isAxiosError(error)) {
      throw error.response?.data || error;
    } else {
      throw error;
    }
  }
};
