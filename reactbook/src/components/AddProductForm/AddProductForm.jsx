import React from "react";
import { useState, useRef } from "react";
import "./AddProductForm.css";
import { addProduct } from "../../services/InventoryService";

const AddProductForm = ({ onClose }) => {
  const [newProduct, setNewProduct] = useState({
    name: "",
    productType: "",
    purchasePrice: null,
    sellingPrice: null,
    quantity: null,
    publisher: "",
    author: "",
    ISBN: null,
    brand: "",
    stationaryType: "",
    suitableAge: null,
  });
  const [error, setError] = useState(null);

  const handleInputChange = (e) => {
    const { id, value } = e.target;
    setNewProduct((prevProduct) => ({
      ...prevProduct,
      [id]: value,
    }));
  };

  const handleAddProduct = async () => {
    setError(null);
    if (
      !newProduct.name ||
      !newProduct.productType ||
      !newProduct.purchasePrice ||
      !newProduct.sellingPrice ||
      !newProduct.quantity
    ) {
      setError({ message: "Vui lòng điền đầy đủ thông tin sản phẩm." });
      return;
    }
    try {
      console.log("Sending product data:", newProduct);
      await addProduct(newProduct);
      onClose();
    } catch (error) {
      console.error("Error response:", error.response?.data);
      setError(error);
    }
  };

  return (
    <div className="modal-overlay">
      <div className="add-product-form">
        <div className="form-header">
          <h2>Thêm sản phẩm mới</h2>
          <div onClick={onClose}>Đóng</div>
        </div>
        <form className="form-grid">
          <div className="form-column">
            <div className="form-group">
              <label htmlFor="productName">Tên sản phẩm:</label>
              <input
                type="text"
                id="name"
                placeholder="Nhập tên sản phẩm"
                value={newProduct.name}
                onChange={handleInputChange}
                required
              />
            </div>
            <div className="form-group">
              <label htmlFor="purchasePrice">Giá mua:</label>
              <input
                type="number"
                id="purchasePrice"
                placeholder="Nhập giá mua"
                min="0"
                value={newProduct.purchasePrice}
                onChange={handleInputChange}
                required
              />
            </div>
          </div>
          <div className="form-column">
            <div className="form-group">
              <label htmlFor="sellingPrice">Giá bán:</label>
              <input
                type="number"
                id="sellingPrice"
                placeholder="Nhập giá bán"
                min="0"
                value={newProduct.sellingPrice}
                onChange={handleInputChange}
                required
              />
            </div>
            <div className="form-group">
              <label htmlFor="quantity">Số lượng:</label>
              <input
                type="number"
                id="quantity"
                placeholder="Nhập số lượng"
                min="0"
                value={newProduct.quantity}
                onChange={handleInputChange}
                required
              />
            </div>
            <div className="form-group">
              <label htmlFor="productType">Thể loại:</label>
              <select
                id="productType"
                value={newProduct.productType}
                required
                onChange={handleInputChange}
              >
                <option value="">Chọn thể loại</option>
                <option value="book">Sách</option>
                <option value="stationary">Văn phòng phẩm</option>
                <option value="toy">Đồ chơi</option>
              </select>
            </div>
            {newProduct.productType === "book" && (
              <>
                <div className="form-group">
                  <label htmlFor="publisher">Nhà xuất bản:</label>
                  <input
                    type="text"
                    id="publisher"
                    placeholder="Nhập nhà xuất bản"
                    value={newProduct.publisher}
                    onChange={handleInputChange}
                  />
                </div>
                <div className="form-group">
                  <label htmlFor="author">Tác giả:</label>
                  <input
                    type="text"
                    id="author"
                    placeholder="Nhập tác giả"
                    value={newProduct.author}
                    onChange={handleInputChange}
                  />
                </div>
                <div className="form-group">
                  <label htmlFor="ISBN">ISBN:</label>
                  <input
                    type="text"
                    id="ISBN"
                    placeholder="Nhập ISBN"
                    value={newProduct.ISBN}
                    onChange={handleInputChange}
                  />
                </div>
              </>
            )}
            {newProduct.productType === "stationary" && (
              <>
                <div className="form-group">
                  <label htmlFor="brand">Thương hiệu:</label>
                  <input
                    type="text"
                    id="brand"
                    placeholder="Nhập thương hiệu"
                    value={newProduct.brand}
                    onChange={handleInputChange}
                  />
                </div>
                <div className="form-group">
                  <label htmlFor="stationaryType">Loại văn phòng phẩm:</label>
                  <input
                    type="text"
                    id="stationaryType"
                    placeholder="Nhập loại văn phòng phẩm"
                    value={newProduct.stationaryType}
                    onChange={handleInputChange}
                  />
                </div>
              </>
            )}
            {newProduct.productType === "toy" && (
              <>
                <div className="form-group">
                  <label htmlFor="brand">Thương hiệu:</label>
                  <input
                    type="text"
                    id="brand"
                    placeholder="Nhập thương hiệu"
                    value={newProduct.brand}
                    onChange={handleInputChange}
                  />
                </div>
                <div className="form-group">
                  <label htmlFor="suitableAge">Tuổi sử dụng:</label>
                  <input
                    type="text"
                    id="suitableAge"
                    placeholder="Nhập tuổi sử dụng"
                    value={newProduct.suitableAge}
                    onChange={handleInputChange}
                  />
                </div>
              </>
            )}
          </div>
        </form>
        {error && (
          <div className="error-message">
            {error.message || "Có lỗi xảy ra"}
          </div>
        )}
        <button className="btn-save" onClick={handleAddProduct}>
          Lưu
        </button>
      </div>
    </div>
  );
};

export default AddProductForm;
