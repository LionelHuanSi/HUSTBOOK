import React from "react";
import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import Sidebar from "../../components/Sidebar/Sidebar";

const Employee = () => {
  const [employee, setEmployee] = useState([]);
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate();

  useEffect(() => {
    fetch("https://jsonplaceholder.typicode.com/users")
      .then((response) => response.json())
      .then((data) => {
        setEmployee(data);
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
      <h1>Employee List</h1>
      <ul>
        {employee.map((emp) => (
          <li key={emp.id} onClick={() => navigate(`/employee/${emp.id}`)}>
            {emp.name}
          </li>
        ))}
      </ul>
    </div>
  );
};

export default Employee;
