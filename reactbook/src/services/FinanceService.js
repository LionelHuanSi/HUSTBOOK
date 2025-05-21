import axios from "axios";

const API_URL = 'http://localhost:8080/api';

export const getExpenses = async () => {
    try {
        const response = await axios.get(`${API_URL}/expenses`);
        return response.data;
    } catch (error) {
        throw error;
    }
}

export const addExpense = async (expenseData) => {
    try {
        const response = axios.post(`${API_URL}/expenses`, expenseData);
        return (await response).data;
    } catch (error) {
        throw error;
    }
}

export const deleteExpense = async (expenseID) => {
    try {
        const response = await axios.delete(`${API_URL}/expenses/${expenseID}`);
        return response.data;
    } catch (error) {
        throw error;
    }
} 

export const updateExpense = async (expenseID, expenseData) => {
    try {
        const response = await axios.post(`${API_URL}/expenses/${expenseID}`, expenseData);
        return response.data;
    } catch (error) {
        throw error;
    }
}

export const getTotalExpense = async (startDate, endDate) => {
    try {
        const response = await axios.post(`${API_URL}/finances/totalexpense`,{
            startDate,
            endDate,
        });
        return response.data;
    } catch (error) {
        throw error;
    }
}

export const getRevenue = async (startDate, endDate) => {
    try {
        const response = await axios.post(`${API_URL}/finances/revenue`, {
            startDate,
            endDate,
        });
        return response.data;
    } catch (error) {
        throw error;
    }
}

export const getProfit = async (startDate, endDate) => {
    try {
        const response = await axios.post(`${API_URL}/finances/profit`, {
            startDate,
            endDate,
        });
        return response.data;
    } catch (error) {
        throw error;
    }
}


