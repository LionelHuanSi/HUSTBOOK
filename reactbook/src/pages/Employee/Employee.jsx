import React from "react";
import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import Sidebar from "../../components/Sidebar/Sidebar";
import "./Employee.css";
import "../../styles/base.css";

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
    <div className="container">
      <Sidebar />
      <main>
        <div className="topbar">
          <div className="search">
            <label>
              <input type="text" placeholder="Tìm kiếm..." />
              <span className="material-symbols-sharp">search</span>
            </label>
          </div>
          <div className="user">
            <img src="/assets/Customer1.png" alt="" />
          </div>
        </div>
        <section className="user-management">
          <h1 className="user-management-title">Quản lý nhân viên</h1>
          <div className="user-actions">
            <button className="btn-add">Thêm nhân viên</button>
          </div>
          <div className="user-filters">
            <button className="btn-view-all">Duyệt toàn bộ</button>
            <input
              type="text"
              className="filter-name"
              placeholder="Tên nhân viên"
            />
            <input
              type="number"
              className="filter-salary"
              placeholder="Lương từ"
              min="0"
            />
            <button className="btn-filter">Lọc</button>
          </div>
          <table className="user-table">
            <thead>
              <tr>
                <th>Mã NV</th>
                <th>Tên nhân viên</th>
                <th>Chức vụ</th>
                <th>Lương</th>
                <th>Hành động</th>
              </tr>
            </thead>
            <tbody>
              {employee.map((emp) => (
                <tr key={emp.id}>
                  <td>NV{String(emp.id).padStart(3, "0")}</td>
                  <td>{emp.name}</td>
                  <td>Nhân viên</td>
                  <td>15000000</td>
                  <td>
                    <button
                      className="btn-edit"
                      onClick={() => navigate(`/employee/${emp.id}`)}
                    >
                      Sửa
                    </button>
                    <button className="btn-delete">Xoá</button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </section>
      </main>
    </div>
  );
};

export default Employee;
