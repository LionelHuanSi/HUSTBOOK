import axios from 'axios';

const API_URL = 'http://localhost:8080/api/invoices';

export const getInvoices = async () => {
    try {
        const response = await axios.get(API_URL);
        return response.data;
    } catch (error) {
        console.error('Error fetching invoices:', error);
        throw error;
    }
};

export const getSortedInvoices = async (sortData) => {
    try {
        const response = await axios.post(`${API_URL}/sort`, sortData);
        return response.data;
    } catch (error) {
        console.error('Error sorting invoices:', error);
        throw error;
    }
};

export const getInvoicesByFilter = async (filterData) => {
    try {
        const response = await axios.post(`${API_URL}/filter`, filterData);
        return response.data;
    } catch (error) {
        console.error('Error filtering invoices:', error);
        throw error;
    }
};