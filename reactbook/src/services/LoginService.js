import axios from "axios";

const API_URL = 'http://localhost:8080/api/auth'

export const getAuth = async (user) => {
    try {
        const response = await axios.post(API_URL, user);
        return response.data;
    } catch (error) {
        throw error;
    }
}