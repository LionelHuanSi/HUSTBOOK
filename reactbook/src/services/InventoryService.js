import axios from 'axios';

const API_URL = 'http://localhost:8080/api/products';

export const getAllProducts = async () => {
    try {
        const response = await axios.get(API_URL);
        return response.data;
    } catch (error) {
        console.error('Error fetching products:', error);
        throw error;
    }
}

export const getProductsByFilter = async (filter) => {
    try {
        const response = await axios.post(`${API_URL}/filter`, filter);
        return response.data;
    } catch (error) {
        console.error('Error fetching products by filter:', error);
        throw error;
    }
}

export const addProduct = async (product) => {
    try {
        const response = await axios.post(API_URL, product);
        return response.data;
    } catch (error) {
        console.error('Error adding product:', error);
        throw error;
    }
}

export const updateProduct = async (productId, product) => {
    try {
        const response = await axios.put(`${API_URL}/${productId}`, product);
        return response.data;
    } catch (error) {
        console.error('Error updating product:', error);
        throw error;
    }
}

export const deleteProduct = async (productId) => {
    try {
        const response = await axios.delete(`${API_URL}/${productId}`);
        return response.data;
    } catch (error) {
        console.error('Error deleting product:', error);
        throw error;
    }
}

export const getSortedProducts = async (sortData) => {
    try {
        const response = await axios.post(`${API_URL}/sort`, sortData);
        return response.data;
    } catch (error) {
        console.error('Error fetching sorted products:', error);
        throw error;
    }
}