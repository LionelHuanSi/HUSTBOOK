import React from "react";
import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import Sidebar from "../../components/Sidebar/Sidebar";
import "./Employee.css";
import "../../styles/base.css";

const Employee = () => {
  const [employee, setEmployee] = useState([]);
  const [loading, setLoading] = useState(true);
  const [filterData, setFilterData] = useState({
    name: "",
    role: "",
    salaryFrom: "",
    salaryTo: "",
  });
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

  const handleFilterChange = (e) => {
    const { name, value } = e.target;
    setFilterData((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  const handleSort = (field, type) => {
    const sortedEmployee = [...employee].sort((a, b) => {
      if (field === "id") {
        return type === "up" ? a.id - b.id : b.id - a.id;
      }
      if (field === "salary") {
        const salaryA = 15000000; // Replace with actual salary when implemented
        const salaryB = 15000000; // Replace with actual salary when implemented
        return type === "up" ? salaryA - salaryB : salaryB - salaryA;
      }
      return 0;
    });
    setEmployee(sortedEmployee);
  };

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
        <section className="employee-management">
          <h1 className="employee-management-title">Quản lý nhân viên</h1>
          <div className="employee-actions">
            <button className="btn-add">Thêm nhân viên</button>
          </div>
          <div className="employee-filters">
            <button className="btn-view-all">Duyệt toàn bộ</button>
            <input
              type="text"
              className="filter-name"
              placeholder="Tên nhân viên"
              name="name"
              value={filterData.name}
              onChange={handleFilterChange}
            />
            <select
              className="filter-role"
              name="role"
              value={filterData.role}
              onChange={handleFilterChange}
            >
              <option value="">Chọn chức vụ</option>
              <option value="admin">Quản trị viên</option>
              <option value="employee">Nhân viên</option>
            </select>
            <div className="salary-range">
              <div className="salary-input">
                <input
                  type="number"
                  className="filter-salary"
                  placeholder="Lương từ"
                  min="0"
                  name="salaryFrom"
                  value={filterData.salaryFrom}
                  onChange={handleFilterChange}
                />
              </div>
              <div className="salary-input">
                <input
                  type="number"
                  className="filter-salary"
                  placeholder="Lương đến"
                  min="0"
                  name="salaryTo"
                  value={filterData.salaryTo}
                  onChange={handleFilterChange}
                />
              </div>
            </div>
            <button className="btn-filter">Lọc</button>
          </div>
          <table className="employee-table">
            <thead>
              <tr>
                <th>
                  Mã NV
                  <button
                    className="btn-sort-up"
                    onClick={() => handleSort("id", "up")}
                  >
                    ▲
                  </button>
                  <button
                    className="btn-sort-down"
                    onClick={() => handleSort("id", "down")}
                  >
                    ▼
                  </button>
                </th>
                <th>Tên nhân viên</th>
                <th>Chức vụ</th>
                <th>
                  Lương
                  <button
                    className="btn-sort-up"
                    onClick={() => handleSort("salary", "up")}
                  >
                    ▲
                  </button>
                  <button
                    className="btn-sort-down"
                    onClick={() => handleSort("salary", "down")}
                  >
                    ▼
                  </button>
                </th>
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
