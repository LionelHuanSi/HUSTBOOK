import React, { useState } from "react";
import { addUser, updateUser } from "../../services/UserService";
import "./AddEmployeeForm.css";

const AddEmployeeForm = ({ onClose, mode = "add", employee = null }) => {
  const [formData, setFormData] = useState(
    mode === "detail" && employee
      ? {
          username: employee.username,
          password: "",
          fullName: employee.fullName,
          phoneNumber: employee.phoneNumber,
          basicSalary: employee.basicSalary,
          coefficient: employee.coefficient,
          role: "Employee",
        }
      : {
          username: "",
          password: "",
          fullName: "",
          phoneNumber: "",
          basicSalary: 5000000,
          coefficient: 1,
          role: "Employee",
        }
  );
  const [error, setError] = useState(null);

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError(null);

    if (!formData.username || !formData.fullName || !formData.phoneNumber) {
      setError("Vui lòng điền đầy đủ thông tin");
      return;
    }

    console.log(formData);

    try {
      if (mode === "detail") {
        await updateUser({
          ...formData,
          userID: employee.userID,
        });
      } else {
        if (!formData.password) {
          setError("Vui lòng nhập mật khẩu");
          return;
        }
        await addUser(formData);
      }
      onClose();
    } catch (error) {
      setError(error.message);
    }
  };

  return (
    <div className="modal-overlay">
      <div className="add-employee-form">
        <div className="form-header">
          <h2>
            {mode === "add" ? "Thêm nhân viên mới" : "Chi tiết nhân viên"}
          </h2>
          <button onClick={onClose}>✕</button>
        </div>

        <form onSubmit={handleSubmit}>
          <div className="form-group">
            <label htmlFor="username">Tên đăng nhập:</label>
            <input
              type="text"
              id="username"
              name="username"
              value={formData.username}
              onChange={handleInputChange}
              required
            />
          </div>

          {mode === "add" && (
            <div className="form-group">
              <label htmlFor="password">Mật khẩu:</label>
              <input
                type="password"
                id="password"
                name="password"
                value={formData.password}
                onChange={handleInputChange}
                required
              />
            </div>
          )}

          <div className="form-group">
            <label htmlFor="fullName">Họ và tên:</label>
            <input
              type="text"
              id="fullName"
              name="fullName"
              value={formData.fullName}
              onChange={handleInputChange}
              required
            />
          </div>

          <div className="form-group">
            <label htmlFor="phoneNumber">Số điện thoại:</label>
            <input
              type="text"
              id="phoneNumber"
              name="phoneNumber"
              value={formData.phoneNumber}
              onChange={handleInputChange}
              required
            />
          </div>

          <div className="form-group">
            <label htmlFor="basicSalary">Lương cơ bản:</label>
            <input
              type="number"
              id="basicSalary"
              name="basicSalary"
              value={formData.basicSalary}
              onChange={handleInputChange}
              min="0"
              required
            />
          </div>

          <div className="form-group">
            <label htmlFor="coefficient">Hệ số lương:</label>
            <input
              type="number"
              id="coefficient"
              name="coefficient"
              value={formData.coefficient}
              onChange={handleInputChange}
              min="1"
              max="3"
              step="0.1"
              required
            />
          </div>

          {error && <div className="error-message">{error}</div>}

          <button type="submit" className="btn-submit">
            {mode === "add" ? "Thêm nhân viên" : "Cập nhật"}
          </button>
        </form>
      </div>
    </div>
  );
};

export default AddEmployeeForm;
