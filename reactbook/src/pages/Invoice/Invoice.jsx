import React from "react";
import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import "./Invoice.css";
import "../../styles/base.css";
import Sidebar from "../../components/Sidebar/Sidebar";

const Invoice = () => {
  const [invoice, setInvoice] = useState([]);
  const navigate = useNavigate();

  const handleSort = (field, direction) => {
    // Thêm xử lý sắp xếp ở đây
    console.log(`Sorting ${field} in ${direction} direction`);
  };

  useEffect(() => {
    fetch("https://jsonplaceholder.typicode.com/posts")
      .then((response) => response.json())
      .then((data) => {
        setInvoice(data);
      })
      .catch((error) => console.error("Error fetching data:", error));
  }, []);

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
        <section className="bill-management">
          <h1 className="bill-management-title">Quản lý hóa đơn</h1>
          <div className="bill-filters">
            <button className="btn-view-all">Duyệt toàn bộ</button>
            <input
              type="text"
              placeholder="Tên khách hàng..."
              className="filter-text"
            />
            <input
              type="text"
              placeholder="Tên nhân viên..."
              className="filter-text"
            />
            <div className="date-range">
              <div className="date-input">
                <label>Từ ngày:</label>
                <input type="date" className="filter-date" />
              </div>
              <div className="date-input">
                <label>Đến ngày:</label>
                <input type="date" className="filter-date" />
              </div>
            </div>
            <select className="filter-paymethod">
              <option value="">Phương thức thanh toán</option>
              <option value="cash">Tiền mặt</option>
              <option value="bank">Chuyển khoản</option>
              <option value="card">Thẻ</option>
            </select>
            <button className="btn-filter">Lọc</button>
          </div>
          <table className="bill-table">
            <thead>
              <tr>
                <th>
                  Mã HĐ
                  <button
                    className="btn-sort-up"
                    onClick={() => handleSort("invoiceId", "up")}
                  >
                    ▲
                  </button>
                  <button
                    className="btn-sort-down"
                    onClick={() => handleSort("invoiceId", "down")}
                  >
                    ▼
                  </button>
                </th>
                <th>Mã ĐH</th>
                <th>Khách hàng</th>
                <th>Nhân viên</th>
                <th>
                  Ngày lập
                  <button
                    className="btn-sort-up"
                    onClick={() => handleSort("date", "up")}
                  >
                    ▲
                  </button>
                  <button
                    className="btn-sort-down"
                    onClick={() => handleSort("date", "down")}
                  >
                    ▼
                  </button>
                </th>
                <th>
                  Tổng tiền
                  <button
                    className="btn-sort-up"
                    onClick={() => handleSort("total", "up")}
                  >
                    ▲
                  </button>
                  <button
                    className="btn-sort-down"
                    onClick={() => handleSort("total", "down")}
                  >
                    ▼
                  </button>
                </th>
                <th>Phương thức thanh toán</th>
              </tr>
            </thead>
            <tbody>
              {invoice.map((inv) => (
                <tr key={inv.id} onClick={() => navigate(`/invoice/${inv.id}`)}>
                  <td>HD{String(inv.id).padStart(3, "0")}</td>
                  <td>DH{String(inv.id).padStart(3, "0")}</td>
                  <td>Nguyễn Văn B</td>
                  <td>Nhân viên A</td>
                  <td>2024-06-02</td>
                  <td>200</td>
                  <td>Tiền mặt</td>
                </tr>
              ))}
            </tbody>
          </table>
        </section>
      </main>
    </div>
  );
};

export default Invoice;
