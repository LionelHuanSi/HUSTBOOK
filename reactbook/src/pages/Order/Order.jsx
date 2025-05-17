import React from "react";
import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import "./Order.css";
import "../../styles/base.css";
import Sidebar from "../../components/Sidebar/Sidebar";

const Order = () => {
  const [order, setOrder] = useState([]);
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate();

  useEffect(() => {
    fetch("https://jsonplaceholder.typicode.com/posts")
      .then((response) => response.json())
      .then((data) => {
        setOrder(data);
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
        <section className="order-management">
          <h1 className="order-management-title">Quản lý đơn hàng</h1>
          <div className="order-actions">
            <button className="btn-add">Thêm đơn hàng</button>
          </div>
          <div className="order-filters">
            <button className="btn-view-all">Duyệt toàn bộ</button>
            <select className="filter-product">
              <option value="">-- Sản phẩm --</option>
              <option value="sp001">Giáo trình Toán</option>
              <option value="sp002">Tiểu thuyết A</option>
            </select>
            <input type="date" className="filter-date" />
            <input
              type="text"
              className="filter-customer"
              placeholder="Tên khách hàng"
            />
            <button className="btn-filter">Lọc</button>
          </div>
          <table className="order-table">
            <thead>
              <tr>
                <th>Mã ĐH</th>
                <th>Khách hàng</th>
                <th>Sản phẩm</th>
                <th>Ngày đặt</th>
                <th>Số lượng</th>
                <th>Tổng tiền</th>
                <th>Hành động</th>
              </tr>
            </thead>
            <tbody>
              {order.map((ord) => (
                <tr key={ord.id}>
                  <td>DH{String(ord.id).padStart(3, "0")}</td>
                  <td>Nguyễn Văn A</td>
                  <td>Giáo trình Toán</td>
                  <td>2024-06-01</td>
                  <td>2</td>
                  <td>120</td>
                  <td>
                    <button className="btn-edit">Sửa</button>
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

export default Order;
