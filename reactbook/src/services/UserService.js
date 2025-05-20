import axios from 'axios';

const API_URL = 'http://localhost:8080/api/users';

export const getUsers = async () => {
    const response = await axios.get(API_URL);
    return response.data;
}

export const getUsersByFilter = async (filterData) => {
    try {
        const response = await axios.post(`${API_URL}/filter`, filterData);
        return response.data;
    } catch (error) {
        console.error('Error filtering users:', error);
        throw error;
    }
};

export const getSortedUsers = async (sortData) => {
    try {
        const response = await axios.post(`${API_URL}/sort`, sortData);
        return response.data;
    } catch (error) {
        console.error('Error sorting users:', error);
        throw error;
    }
};

export const addUser = async (userData) => {
    try {
        const response = await axios.post(`${API_URL}`, userData);
        return response.data;
    } catch (error) {
        throw error;
    }
};

export const updateUser = async (userData) => {
    try {
        const response = await axios.post(`${API_URL}/${userData.userID}`, userData);
        return response.data;
    } catch (error) {
        throw error;
    }
};

export const deleteUser = async (user_ID) => {
    const response = await axios.delete(`${API_URL}/${user_ID}`);
    return response.data;
}