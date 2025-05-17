import React from "react";
import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import Sidebar from "../../components/Sidebar/Sidebar";

const Order = () => {
  const [order, setOrder] = useState([]);
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate();

  useEffect(() => {
    fetch("https://jsonplaceholder.typicode.com/posts")
      .then((response) => response.json())
      .then((data) => {
        setOrder(data);
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
      <h1>Order List</h1>
      <ul>
        {order.map((ord) => (
          <li key={ord.id} onClick={() => navigate(`/order/${ord.id}`)}>
            {ord.title}
          </li>
        ))}
      </ul>
    </div>
  );
};
export default Order;
