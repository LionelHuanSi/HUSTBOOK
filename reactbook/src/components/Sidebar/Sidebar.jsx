import React from "react";
import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";

const Sidebar = () => {
  const [sidebarData, setSidebarData] = useState([]);
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate();

  useEffect(() => {
    fetch("https://jsonplaceholder.typicode.com/posts")
      .then((response) => response.json())
      .then((data) => {
        setSidebarData(data);
        setLoading(false);
      })
      .catch((error) => console.error("Error fetching data:", error));
  }, []);

  if (loading) {
    return <div>Loading...</div>;
  }

  return (
    <div>
      <h1>Sidebar List</h1>
      <ul>
        {sidebarData.map((item) => (
          <li key={item.id} onClick={() => navigate(`/sidebar/${item.id}`)}>
            {item.title}
          </li>
        ))}
      </ul>
    </div>
  );
};
export default Sidebar;
