import React, { useState, useEffect } from "react";
import Sidebar from "../../components/Sidebar/Sidebar";
import AddProductForm from "../../components/AddProductForm/AddProductForm";
import "./Inventory.css";
import "../../styles/base.css";
import { getAllProducts } from "../../services/InventoryService";
import { deleteProduct } from "../../services/InventoryService";

const Inventory = () => {
  const [inventory, setInventory] = useState([]);
  const [error, setError] = useState(null);
  const [isAddButtonClicked, setIsAddButtonClicked] = useState(false);
  const [searchQuery, setSearchQuery] = useState("");
  const [selectedCategory, setSelectedCategory] = useState("");
  const [priceFrom, setPriceFrom] = useState("");
  const [priceTo, setPriceTo] = useState("");

  useEffect(() => {
    const fetchInventory = async () => {
      try {
        const response = await getAllProducts();
        setInventory(response);
      } catch (error) {
        setError(error);
      }
    };

    fetchInventory();
  }, [isAddButtonClicked]);

  const handleAddButtonClick = () => {
    setIsAddButtonClicked(true);
  };

  const handleCloseAddProductForm = () => {
    setIsAddButtonClicked(false);
  };

  const hanleGetAllProducts = async () => {
    try {
      const response = await getAllProducts();
      setInventory(response);
    } catch (error) {
      setError(error);
    }
  };

  const handleDeleteProduct = async (productID) => {
    try {
      const response = await deleteProduct(productID);
      if (response === false) {
        alert("Xóa sản phẩm không thành công");
        return;
      }
      setInventory((prevInventory) =>
        prevInventory.filter((product) => product.productID !== productID)
      );
    } catch (error) {
      setError(error);
    }
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

        <section className="inventory-management">
          {error && (
            <div className="error-message">
              <p>Đã xảy ra lỗi khi tải dữ liệu: {error.message}</p>
            </div>
          )}
          <h1>Quản lý kho hàng</h1>
          <div className="inventory-actions">
            <button className="btn-add" onClick={handleAddButtonClick}>
              Thêm sản phẩm
            </button>
          </div>
          {isAddButtonClicked && (
            <AddProductForm onClose={handleCloseAddProductForm} />
          )}
          <div className="inventory-filters">
            <button className="btn-view-all" onClick={hanleGetAllProducts}>
              Duyệt toàn bộ
            </button>
            <select
              className="filter-category"
              value={selectedCategory}
              onChange={(e) => setSelectedCategory(e.target.value)}
            >
              <option value="">Tất cả thể loại</option>
              <option value="sach-giao-khoa">Sách</option>
              <option value="tieu-thuyet">Văn phòng phẩm</option>
              <option value="truyen-tranh">Đồ chơi</option>
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
            {inventory.length > 0 ? (
              <tbody>
                {inventory.map((product) => (
                  <tr key={product.productID}>
                    <td>{product.productID}</td>
                    <td>{product.name}</td>
                    <td>{product.productType}</td>
                    <td>{product.purchasePrice}</td>
                    <td>{product.sellingPrice}</td>
                    <td>{product.quantity}</td>
                    <td>
                      <button className="btn-edit">Sửa</button>
                      <button
                        className="btn-delete"
                        onClick={() => handleDeleteProduct(product.productID)}
                      >
                        Xóa
                      </button>
                    </td>
                  </tr>
                ))}
              </tbody>
            ) : (
              <tbody>
                <tr>
                  <td colSpan="7">Không có sản phẩm nào trong kho.</td>
                </tr>
              </tbody>
            )}
          </table>
        </section>
      </main>
    </div>
  );
};

export default Inventory;
