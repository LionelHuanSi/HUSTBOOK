import axios from 'axios';

const API_URL = 'http://localhost:8080/api/orders';

export const getOrders = async () => {
    try {
        const response = await axios.get(`${API_URL}`);
        return response.data;
    } catch (error) {
        console.error('Error fetching orders:', error);
        throw error;
    }
}

export const getOrdersByFilter = async (filterData) => {
    try {
        const response = await axios.post(`${API_URL}/filter`, filterData);
        return response.data;
    } catch (error) {
        throw error;
    }
};

export const getSortedOrders = async (sortData) => {
    try {
        const response = await axios.post(`${API_URL}/sort`, sortData);
        return response.data;
    } catch (error) {
        throw error;
    }
};

export const addOrder = async (order) => {
    try {
        const response = await axios.post(`${API_URL}`, order);
        return response.data;
    } catch (error) {
        console.error('Error adding order:', error);
        throw error;
    }
}

export const updateOrder = async (orderId, order) => {
    try {
        const response = await axios.post(`${API_URL}/${orderId}`, order);
        return response.data;
    } catch (error) {
        console.error('Error updating order:', error);
        throw error;
    }
}

export const deleteOrder = async (orderId) => {
    try {
        const response = await axios.delete(`${API_URL}/${orderId}`);
        return response.data;
    } catch (error) {
        console.error('Error deleting order:', error);
        throw error;
    }
}