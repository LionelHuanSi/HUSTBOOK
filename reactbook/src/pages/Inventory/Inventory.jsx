import React from "react";
import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import Sidebar from "../../components/Sidebar/Sidebar";

const Inventory = () => {
  const [inventory, setInventory] = useState([]);
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate();

  useEffect(() => {
    fetch("https://jsonplaceholder.typicode.com/posts")
      .then((response) => response.json())
      .then((data) => {
        setInventory(data);
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
      <h1>Inventory List</h1>
      <ul>
        {inventory.map((inv) => (
          <li key={inv.id} onClick={() => navigate(`/inventory/${inv.id}`)}>
            {inv.title}
          </li>
        ))}
      </ul>
    </div>
  );
};
export default Inventory;
