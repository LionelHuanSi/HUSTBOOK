import React from "react";
import { useState } from "react";
import "./AddProductForm.css";
import { addProduct, updateProduct } from "../../services/InventoryService";

const AddProductForm = ({ onClose, mode = "add", product = null }) => {
  const [productId, setProductId] = useState(product?.productID || undefined);
  const [formData, setFormData] = useState(
    mode === "detail" && product
      ? {
          name: product.name,
          productType: product.productType,
          quantity: product.quantity,
          purchasePrice: product.purchasePrice,
          sellingPrice: product.sellingPrice,
          author: product.author || null,
          publisher: product.publisher || null,
          isbn: product.isbn || null,
          brand: product.brand || null,
          stationaryType: product.stationaryType || null,
          suitableAge: product.suitableAge || null,
        }
      : {
          name: "",
          productType: "",
          quantity: "",
          purchasePrice: "",
          sellingPrice: "",
          author: null,
          publisher: null,
          isbn: null,
          brand: null,
          stationaryType: null,
          suitableAge: null,
        }
  );
  const [error, setError] = useState(null);

  const handleInputChange = (e) => {
    const { id, value } = e.target;
    setFormData((prevData) => ({
      ...prevData,
      [id]: value,
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError(null);
    if (
      !formData.name ||
      !formData.productType ||
      !formData.purchasePrice ||
      !formData.sellingPrice ||
      !formData.quantity
    ) {
      setError({ message: "Vui lòng điền đầy đủ thông tin sản phẩm." });
      return;
    }
    try {
      if (mode === "detail" && productId) {
        console.log("Updating product data:", formData);
        await updateProduct(productId, formData);
      } else {
        console.log("Adding product data:", formData);
        await addProduct(formData);
      }
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
          <h2>{mode === "add" ? "Thêm sản phẩm mới" : "Chi tiết sản phẩm"}</h2>
          <div onClick={onClose}>Đóng</div>
        </div>
        <form className="form-grid" onSubmit={handleSubmit}>
          <div className="form-column">
            <div className="form-group">
              <label htmlFor="name">Tên sản phẩm:</label>
              <input
                type="text"
                id="name"
                placeholder="Nhập tên sản phẩm"
                value={formData.name}
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
                value={formData.purchasePrice}
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
                value={formData.sellingPrice}
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
                value={formData.quantity}
                onChange={handleInputChange}
                required
              />
            </div>
            <div className="form-group">
              <label htmlFor="productType">Thể loại:</label>
              <select
                id="productType"
                value={formData.productType}
                required
                onChange={handleInputChange}
              >
                <option value="">Chọn thể loại</option>
                <option value="Book">Sách</option>
                <option value="Stationary">Văn phòng phẩm</option>
                <option value="Toy">Đồ chơi</option>
              </select>
            </div>
            {formData.productType === "Book" && (
              <>
                <div className="form-group">
                  <label htmlFor="publisher">Nhà xuất bản:</label>
                  <input
                    type="text"
                    id="publisher"
                    placeholder="Nhập nhà xuất bản"
                    value={formData.publisher}
                    onChange={handleInputChange}
                  />
                </div>
                <div className="form-group">
                  <label htmlFor="author">Tác giả:</label>
                  <input
                    type="text"
                    id="author"
                    placeholder="Nhập tác giả"
                    value={formData.author}
                    onChange={handleInputChange}
                  />
                </div>
                <div className="form-group">
                  <label htmlFor="ISBN">ISBN:</label>
                  <input
                    type="text"
                    id="isbn"
                    placeholder="Nhập mã isbn"
                    value={formData.isbn}
                    onChange={handleInputChange}
                  />
                </div>
              </>
            )}
            {formData.productType === "Stationary" && (
              <>
                <div className="form-group">
                  <label htmlFor="brand">Thương hiệu:</label>
                  <input
                    type="text"
                    id="brand"
                    placeholder="Nhập thương hiệu"
                    value={formData.brand}
                    onChange={handleInputChange}
                  />
                </div>
                <div className="form-group">
                  <label htmlFor="stationarytype">Loại văn phòng phẩm:</label>
                  <input
                    type="text"
                    id="stationarytype"
                    placeholder="Nhập loại văn phòng phẩm"
                    value={formData.stationaryType}
                    onChange={handleInputChange}
                  />
                </div>
              </>
            )}
            {formData.productType === "Toy" && (
              <>
                <div className="form-group">
                  <label htmlFor="brand">Thương hiệu:</label>
                  <input
                    type="text"
                    id="brand"
                    placeholder="Nhập thương hiệu"
                    value={formData.brand}
                    onChange={handleInputChange}
                  />
                </div>
                <div className="form-group">
                  <label htmlFor="suitableAge">Độ tuổi phù hợp:</label>
                  <input
                    type="text"
                    id="suitableAge"
                    placeholder="Nhập độ tuổi phù hợp"
                    value={formData.suitableAge}
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
        <button type="submit" className="btn-submit" onClick={handleSubmit}>
          {mode === "add" ? "Thêm sản phẩm" : "Cập nhật"}
        </button>
      </div>
    </div>
  );
};

export default AddProductForm;
