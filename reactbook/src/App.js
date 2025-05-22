import logo from './logo.svg';
import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import './App.css';
import Finance from './pages/Finance/Finance';
import Employee from './pages/Employee/Employee';
import Order from './pages/Order/Order';
import Invoice from './pages/Invoice/Invoice';
import Inventory from './pages/Inventory/Inventory';
import Login from './pages/Login/Login';


function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Login />} />
        <Route path="/inventory" element={<Inventory />} />
        <Route path="/employee" element={<Employee />} />
        <Route path="/order" element={<Order />} />
        <Route path="/invoice" element={<Invoice />} />
        <Route path="/finance" element={<Finance />} />
      </Routes>
    </Router>
  );
}

export default App;
