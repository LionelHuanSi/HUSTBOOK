import React from "react";
import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import Sidebar from "../../components/Sidebar/Sidebar";

const Invoice = () => {
  const [invoice, setInvoice] = useState([]);
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate();

  useEffect(() => {
    fetch("https://jsonplaceholder.typicode.com/posts")
      .then((response) => response.json())
      .then((data) => {
        setInvoice(data);
        setLoading(false);
      })
      .catch((error) => console.error("Error fetching data:", error));
  }, []);

  if (loading) {
    return <div>Loading...</div>;
  }

  return (
    <div>
      <Sidebar />
      <h1>Invoice List</h1>
      <ul>
        {invoice.map((inv) => (
          <li key={inv.id} onClick={() => navigate(`/invoice/${inv.id}`)}>
            {inv.title}
          </li>
        ))}
      </ul>
    </div>
  );
};
export default Invoice;
