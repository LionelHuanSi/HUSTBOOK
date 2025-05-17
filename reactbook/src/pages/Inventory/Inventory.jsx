import React, { useState } from "react";
import Sidebar from "../../components/Sidebar/Sidebar";
import "./Inventory.css";
import "../../styles/base.css";
import { useNavigate } from "react-router-dom";

const Inventory = () => {
  const [searchQuery, setSearchQuery] = useState("");
  const [selectedCategory, setSelectedCategory] = useState("");
  const [priceFrom, setPriceFrom] = useState("");
  const [priceTo, setPriceTo] = useState("");

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

        <section className="inventory-management">
          <h1>Quản lý kho hàng</h1>
          <div className="inventory-actions">
            <button className="btn-add">Thêm sản phẩm</button>
          </div>

          <div className="inventory-filters">
            <button className="btn-view-all">Duyệt toàn bộ</button>
            <select
              className="filter-category"
              value={selectedCategory}
              onChange={(e) => setSelectedCategory(e.target.value)}
            >
              <option value="">-- Thể loại --</option>
              <option value="sach-giao-khoa">Sách giáo khoa</option>
              <option value="tieu-thuyet">Tiểu thuyết</option>
              <option value="truyen-tranh">Truyện tranh</option>
            </select>
            <input
              type="text"
              className="filter-name"
              placeholder="Tìm theo tên sản phẩm"
              value={searchQuery}
              onChange={(e) => setSearchQuery(e.target.value)}
            />
            <input
              type="number"
              className="filter-price-buy"
              placeholder="Giá mua từ"
              min="0"
              value={priceFrom}
              onChange={(e) => setPriceFrom(e.target.value)}
            />
            <input
              type="number"
              className="filter-price-sell"
              placeholder="Giá bán từ"
              min="0"
              value={priceTo}
              onChange={(e) => setPriceTo(e.target.value)}
            />
            <button className="btn-filter">Lọc</button>
          </div>

          <table className="inventory-table">
            <thead>
              <tr>
                <th>Mã SP</th>
                <th>Tên sản phẩm</th>
                <th>Thể loại</th>
                <th>Giá mua</th>
                <th>Giá bán</th>
                <th>Số lượng</th>
                <th>Hành động</th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td>SP001</td>
                <td>Giáo trình Toán</td>
                <td>Sách giáo khoa</td>
                <td>50</td>
                <td>60</td>
                <td>100</td>
                <td>
                  <button className="btn-edit">Sửa</button>
                  <button className="btn-delete">Xoá</button>
                </td>
              </tr>
            </tbody>
          </table>
        </section>
      </main>
    </div>
  );
};

export default Inventory;
