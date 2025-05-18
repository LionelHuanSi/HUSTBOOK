import React, { useState, useEffect } from "react";
import Sidebar from "../../components/Sidebar/Sidebar";
import AddProductForm from "../../components/AddProductForm/AddProductForm";
import "./Inventory.css";
import "../../styles/base.css";
import { getAllProducts } from "../../services/InventoryService";
import { deleteProduct } from "../../services/InventoryService";
import { getProductsByFilter } from "../../services/InventoryService";
import { getSortedProducts } from "../../services/InventoryService";

const Inventory = () => {
  const [inventory, setInventory] = useState([]);
  const [filterData, setFilterData] = useState({
    category: "",
    name: "",
    purchasePriceFrom: null,
    purchasePriceTo: null,
    sellingPriceFrom: null,
    sellingPriceTo: null,
  });
  const [error, setError] = useState(null);
  const [isAddButtonClicked, setIsAddButtonClicked] = useState(false);

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

  const handleFilterChange = (e) => {
    const { name, value } = e.target;
    setFilterData((prevData) => ({
      ...prevData,
      [name]: value,
    }));
  };

  const handleFilterSubmit = async (e) => {
    e.preventDefault();
    try {
      console.log("Filter data:", filterData);
      const response = await getProductsByFilter(filterData);
      setInventory(response);
    } catch (error) {
      setError(error);
    }
  };

  const handleSort = async (field, type) => {
    const ids = inventory.map((product) => product.productID);
    const newSortData = {
      field: field,
      type: type,
      productIDList: ids,
    };
    try {
      console.log("Sort data:", newSortData);
      const response = await getSortedProducts(newSortData);
      console.log("Sorted response:", response);
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
              value={filterData.category}
              onChange={handleFilterChange}
              name="category"
            >
              <option value="">Tất cả thể loại</option>
              <option value="Book">Sách</option>
              <option value="Stationary">Văn phòng phẩm</option>
              <option value="Toy">Đồ chơi</option>
            </select>
            <input
              type="text"
              className="filter-name"
              placeholder="Tìm theo tên sản phẩm"
              value={filterData.name}
              onChange={handleFilterChange}
              name="name"
            />
            <input
              type="number"
              className="filter-price-buy-from"
              placeholder="Giá mua từ"
              min="0"
              value={filterData.purchasePriceFrom}
              onChange={handleFilterChange}
              name="purchasePriceFrom"
            />
            <input
              type="number"
              className="filter-price-buy-to"
              placeholder="Giá mua đến"
              min="0"
              value={filterData.purchasePriceTo}
              onChange={handleFilterChange}
              name="purchasePriceTo"
            />
            <input
              type="number"
              className="filter-price-sell-from"
              placeholder="Giá bán từ"
              min="0"
              value={filterData.sellingPriceFrom}
              onChange={handleFilterChange}
              name="sellingPriceFrom"
            />
            <input
              type="number"
              className="filter-price-sell-to"
              placeholder="Giá bán đến"
              min="0"
              value={filterData.sellingPriceTo}
              onChange={handleFilterChange}
              name="sellingPriceTo"
            />
            <button className="btn-filter" onClick={handleFilterSubmit}>
              Lọc
            </button>
          </div>

          <table className="inventory-table">
            <thead>
              <tr>
                <th>
                  Mã SP
                  <button
                    className="btn-sort-up"
                    onClick={() => handleSort("productID", "up")}
                  >
                    ▲
                  </button>
                  <button
                    className="btn-sort-down"
                    onClick={() => handleSort("productID", "down")}
                  >
                    ▼
                  </button>
                </th>
                <th>Tên sản phẩm</th>
                <th>Thể loại</th>
                <th>
                  Giá mua
                  <button
                    className="btn-sort-up"
                    onClick={() => handleSort("purchasePrice", "up")}
                  >
                    ▲
                  </button>
                  <button
                    className="btn-sort-down"
                    onClick={() => handleSort("purchasePrice", "down")}
                  >
                    ▼
                  </button>
                </th>
                <th>
                  Giá bán
                  <button
                    className="btn-sort-up"
                    onClick={() => handleSort("sellingPrice", "up")}
                  >
                    ▲
                  </button>
                  <button
                    className="btn-sort-down"
                    onClick={() => handleSort("sellingPrice", "down")}
                  >
                    ▼
                  </button>
                </th>
                <th>
                  Số lượng
                  <button
                    className="btn-sort-up"
                    onClick={() => handleSort("quantity", "up")}
                  >
                    ▲
                  </button>
                  <button
                    className="btn-sort-down"
                    onClick={() => handleSort("quantity", "down")}
                  >
                    ▼
                  </button>
                </th>
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
