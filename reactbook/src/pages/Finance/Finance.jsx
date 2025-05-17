import React from "react";
import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import "./Finance.css";
import "../../styles/base.css";
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
        <section className="finance-management">
          <h1 className="finance-management-title">Quản lý tài chính</h1>
          <div className="finance-summary">
            <div className="summary-item revenue">
              <span>Doanh thu</span>
              <strong>500,000,000</strong>
            </div>
            <div className="summary-item expense">
              <span>Chi phí</span>
              <strong>300,000,000</strong>
            </div>
            <div className="summary-item profit">
              <span>Lợi nhuận</span>
              <strong>200,000,000</strong>
            </div>
          </div>
          <div className="finance-filters">
            <button className="btn-view-all">Duyệt toàn bộ</button>
            <input
              type="date"
              className="filter-date-from"
              placeholder="Từ ngày"
            />
            <input
              type="date"
              className="filter-date-to"
              placeholder="Đến ngày"
            />
            <button className="btn-filter">Lọc</button>
          </div>
          <table className="finance-table">
            <thead>
              <tr>
                <th>Mã CP</th>
                <th>Loại chi phí</th>
                <th>Số tiền</th>
                <th>Ngày</th>
                <th>Ghi chú</th>
              </tr>
            </thead>
            <tbody>
              {finance.map((fin) => (
                <tr key={fin.id}>
                  <td>CP{String(fin.id).padStart(3, "0")}</td>
                  <td>Nhập hàng</td>
                  <td>100,000,000</td>
                  <td>2024-06-01</td>
                  <td>Nhập sách giáo khoa</td>
                </tr>
              ))}
            </tbody>
          </table>
        </section>
      </main>
    </div>
  );
};

export default Finance;
