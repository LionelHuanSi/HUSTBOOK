import React from "react";
import { useState, useEffect } from "react";
import Sidebar from "../../components/Sidebar/Sidebar";
import AddEmployeeForm from "../../components/AddEmployeeForm/AddEmployeeForm";
import "./Employee.css";
import "../../styles/base.css";
import { getUsers } from "../../services/UserService";
import { getUsersByFilter } from "../../services/UserService";
import { getSortedUsers } from "../../services/UserService";
import { deleteUser } from "../../services/UserService";

const Employee = () => {
  const [error, setError] = useState(null);
  const [employee, setEmployee] = useState([]);
  const [filterData, setFilterData] = useState({
    name: "",
    salaryFrom: "",
    salaryTo: "",
  });
  const [isAddFormOpen, setIsAddFormOpen] = useState(false);
  const [selectedEmployee, setSelectedEmployee] = useState(null);
  const [formMode, setFormMode] = useState("add");

  useEffect(() => {
    const fetchEmployees = async () => {
      setError(null);
      try {
        const data = await getUsers();
        const employees = data.filter((user) => user.role === "Employee");
        setEmployee(employees);
      } catch (error) {
        setError(error);
      }
    };
    fetchEmployees();
  }, [isAddFormOpen]);

  const handleFilterChange = (e) => {
    const { name, value } = e.target;
    setFilterData((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  const handleFilterSubmit = async (e) => {
    e.preventDefault();
    setError(null);
    try {
      const filterPayload = {
        name: filterData.name || null,
        salaryFrom: filterData.salaryFrom
          ? Number(filterData.salaryFrom)
          : null,
        salaryTo: filterData.salaryTo ? Number(filterData.salaryTo) : null,
      };

      if (
        filterPayload.salaryFrom &&
        filterPayload.salaryTo &&
        filterPayload.salaryFrom > filterPayload.salaryTo
      ) {
        setError("Lương từ phải nhỏ hơn hoặc bằng lương đến");
        return;
      }

      const response = await getUsersByFilter(filterPayload);
      setEmployee(response);
      setError(null);
    } catch (error) {
      setError(error);
    }
  };

  const handleGetAllEmployees = async () => {
    setError(null);
    try {
      const data = await getUsers();
      const employees = data.filter((user) => user.role === "Employee");
      setEmployee(employees);
    } catch (error) {
      setError(error);
    }
  };

  const handleSort = async (field, type) => {
    setError(null);
    try {
      const ids = employee.map((emp) => emp.userID);
      const sortData = {
        field: field === "id" ? "userID" : "salary",
        type: type,
        userIDList: ids,
      };
      const response = await getSortedUsers(sortData);
      setEmployee(response);
    } catch (error) {
      setError(error);
    }
  };

  const handleDelete = async (userID) => {
    setError(null);
    console.log("userID", userID);
    try {
      await deleteUser(userID);
      setEmployee((prev) => prev.filter((emp) => emp.userID !== userID));
    } catch (error) {
      setError(error);
    }
  };

  const handleAddEmployeeClick = () => {
    setFormMode("add");
    setSelectedEmployee(null);
    setIsAddFormOpen(true);
  };

  const handleEditEmployee = (emp) => {
    setFormMode("detail");
    setSelectedEmployee(emp);
    setIsAddFormOpen(true);
  };

  const handleCloseForm = () => {
    setIsAddFormOpen(false);
    setSelectedEmployee(null);
  };

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
          {error && <div className="error">{error.message}</div>}
          <h1 className="employee-management-title">Quản lý nhân viên</h1>
          <div className="employee-actions">
            <button className="btn-add" onClick={handleAddEmployeeClick}>
              Thêm nhân viên
            </button>
          </div>
          <div className="employee-filters">
            <button className="btn-view-all" onClick={handleGetAllEmployees}>
              Duyệt toàn bộ
            </button>
            <input
              type="text"
              className="filter-name"
              placeholder="Tên nhân viên"
              name="name"
              value={filterData.name}
              onChange={handleFilterChange}
            />
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
            <button className="btn-filter" onClick={handleFilterSubmit}>
              Lọc
            </button>
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
                <th>Số điện thoại</th>
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
                <tr key={emp.userID}>
                  <td>NV{String(emp.userID).padStart(3, "0")}</td>
                  <td>{emp.fullName}</td>
                  <td>{emp.phoneNumber}</td>
                  <td>
                    {(emp.basicSalary * emp.coefficient).toLocaleString()}
                  </td>
                  <td>
                    <button
                      className="btn-edit"
                      onClick={() => handleEditEmployee(emp)}
                    >
                      Sửa
                    </button>
                    <button
                      className="btn-delete"
                      onClick={() => handleDelete(emp.userID)}
                    >
                      Xoá
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
          {isAddFormOpen && (
            <AddEmployeeForm
              onClose={handleCloseForm}
              mode={formMode}
              employee={selectedEmployee}
            />
          )}
        </section>
      </main>
    </div>
  );
};

export default Employee;
