import React from "react";
import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import Sidebar from "../../components/Sidebar/Sidebar";

const Finance = () => {
  const [finance, setFinance] = useState([]);
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate();

  useEffect(() => {
    fetch("https://jsonplaceholder.typicode.com/posts")
      .then((response) => response.json())
      .then((data) => {
        setFinance(data);
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
      <h1>Finance List</h1>
      <ul>
        {finance.map((fin) => (
          <li key={fin.id} onClick={() => navigate(`/finance/${fin.id}`)}>
            {fin.title}
          </li>
        ))}
      </ul>
    </div>
  );
};

export default Finance;
