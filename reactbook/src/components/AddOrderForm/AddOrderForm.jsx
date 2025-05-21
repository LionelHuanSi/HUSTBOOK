import React, { useState, useEffect, use } from "react";
import "./AddOrderForm.css";
import { addOrder, updateOrder } from "../../services/OrderService";
import { getAllProducts } from "../../services/InventoryService";
import { getInvoicesByOrderID } from "../../services/InvoiceService";
import { getUsers } from "../../services/UserService";

const AddOrderForm = ({ onClose, mode = "add", order = null }) => {
  const [error, setError] = useState(null);
  const [formData, setFormData] = useState(
    mode === "detail" && order
      ? {
          orderDate:
            order.orderDate?.split("T")[0] ||
            new Date().toISOString().split("T")[0],
          customerInfo: {
            name: order.customerInfo?.name || "",
            contactInfo: order.customerInfo?.contactInfo || "",
          },
          items: order.items || [],
          totalAmount: order.totalAmount || 0,
          paid: order.paid || false,
          paymentMethod: null,
          fullName: localStorage.getItem("fullName") || "",
        }
      : {
          orderDate: new Date().toISOString().split("T")[0],
          customerInfo: {
            name: "",
            contactInfo: "",
          },
          items: [],
          totalAmount: 0,
          paid: false,
          paymentMethod: null,
          fullName: "",
        }
  );

  const [currentItem, setCurrentItem] = useState({
    product: {
      name: "",
      sellingPrice: 0,
    },
    quantity: 1,
  });

  const checkDuplicateProduct = (productName) => {
    return formData.items.some((item) => item.product.name === productName);
  };

  const [products, setProducts] = useState([]);
  const [employees, setEmployees] = useState([]);

  useEffect(() => {
    const fetchProducts = async () => {
      try {
        const data = await getAllProducts();
        setProducts(data);
      } catch (err) {
        setError(err);
        console.error("Error fetching products:", err);
      }
    };

    if (mode === "add" || (mode === "detail" && order.paid === false)) {
      fetchProducts();
    }
  }, [mode]);

  useEffect(() => {
    const fetchEmployees = async () => {
      try {
        const data = await getUsers();
        setEmployees(data);
      } catch (err) {
        setError(err);
        console.error("Error fetching employees:", err);
      }
    };
    fetchEmployees();
  }, [mode]);

  useEffect(() => {
    const fetchInvoiceByOrderID = async () => {
      try {
        const data = await getInvoicesByOrderID(order.orderID);
        setFormData((prev) => ({
          ...prev,
          paymentMethod: data.paymentMethod,
          fullName: data.employee.fullName,
        }));
      } catch (err) {
        setError(err);
        console.error("Error fetching invoice by order ID:", err);
      }

      if (mode === "detail" && order.paid === true) {
        fetchInvoiceByOrderID(order.orderID);
      }
    };
  }, [mode]);

  const handleInputChange = (e) => {
    const { id, value } = e.target;
    if (id === "name" || id === "contactInfo") {
      setFormData((prev) => ({
        ...prev,
        customerInfo: {
          ...prev.customerInfo,
          [id]: value,
        },
      }));
    } else if (id === "paid") {
      setFormData((prev) => ({
        ...prev,
        [id]: value === "true",
      }));
    } else {
      setFormData((prev) => ({
        ...prev,
        [id]: value,
      }));
    }
  };

  const handleAddItem = () => {
    if (currentItem.product.name) {
      console.log("Current item:", currentItem);
      if (checkDuplicateProduct(currentItem.product.name)) {
        setError(new Error("Sản phẩm này đã có trong đơn hàng"));
        return;
      }
      setFormData((prev) => ({
        ...prev,
        items: [
          ...prev.items,
          { ...currentItem, quantity: Number(currentItem.quantity) },
        ],
        totalAmount:
          prev.totalAmount +
          currentItem.product.sellingPrice * currentItem.quantity,
      }));
      setCurrentItem({
        product: { name: "", sellingPrice: 0 },
        quantity: 1,
      });
      setError(null); // Clear error when successfully added
    }
  };

  const handleRemoveItem = (index) => {
    setFormData((prev) => {
      const removedItem = prev.items[index];
      return {
        ...prev,
        items: prev.items.filter((_, i) => i !== index),
        totalAmount:
          prev.totalAmount -
          removedItem.product.sellingPrice * removedItem.quantity,
      };
    });
  };

  const handleQuantityChange = (index, newQuantity) => {
    console.log("New quantity:", newQuantity);
    console.log("Index:", index);
    setFormData((prev) => {
      const updatedItems = prev.items.map((item, i) =>
        i === index ? { ...item, quantity: Number(newQuantity) } : item
      );
      return {
        ...prev,
        items: updatedItems,
        totalAmount: updatedItems.reduce(
          (sum, item) => sum + item.product.sellingPrice * item.quantity,
          0
        ),
      };
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (
      formData.orderDate === "" ||
      formData.customerInfo.name === "" ||
      formData.customerInfo.contactInfo === "" ||
      formData.items.length === 0 ||
      (formData.paid && formData.paymentMethod === null)
    ) {
      setError(new Error("Vui lòng điền đầy đủ thông tin"));
      return;
    }
    for (const item of formData.items) {
      if (item.quantity <= 0) {
        setError(new Error("Số lượng tối thiểu là 1"));
        return;
      }
    }
    try {
      if (mode === "detail") {
        console.log("Updating order with ID: ", order.orderID);
        console.log("Form data: ", formData);
        await updateOrder(order.orderID, formData);
      } else {
        if (formData.fullName === "") {
          setError(new Error("Vui lòng chọn nhân viên"));
          return;
        }
        localStorage.setItem("fullName", formData.fullName);
        console.log("Adding new order: ", formData);
        await addOrder(formData);
      }
      onClose();
    } catch (error) {
      setError(error);
      console.error("Error:", error);
    }
  };

  return (
    <div className="modal-overlay">
      <div className="add-order-form">
        <div className="form-header">
          <h2>{mode === "add" ? "Thêm đơn hàng mới" : "Chi tiết đơn hàng"}</h2>
          <div onClick={onClose}>Đóng</div>
        </div>

        {error && (
          <div
            className="error-message"
            style={{
              color: "#dc3545",
              padding: "10px",
              marginBottom: "10px",
              backgroundColor: "#f8d7da",
              borderRadius: "4px",
              textAlign: "center",
            }}
          >
            {error.message || "Có lỗi xảy ra"}
          </div>
        )}

        <form className="form-grid" onSubmit={handleSubmit}>
          <div className="form-column">
            <div className="form-group">
              <label htmlFor="orderDate">Ngày đặt:</label>
              <input
                type="date"
                id="orderDate"
                value={formData.orderDate}
                onChange={handleInputChange}
                required
              />
            </div>

            <div className="customer-info">
              <h3>Thông tin khách hàng</h3>
              <div className="form-group">
                <label htmlFor="name">Tên khách hàng:</label>
                <input
                  type="text"
                  id="name"
                  value={formData.customerInfo.name}
                  onChange={handleInputChange}
                  required
                />
              </div>
              <div className="form-group">
                <label htmlFor="contactInfo">Thông tin liên hệ:</label>
                <input
                  type="text"
                  id="contactInfo"
                  value={formData.customerInfo.contactInfo}
                  onChange={handleInputChange}
                  required
                />
              </div>
            </div>

            <div className="employee-info">
              <h3>Thông tin nhân viên</h3>
              <div className="form-group">
                <label htmlFor="fullName">Tên nhân viên:</label>
                <select
                  id="fullName"
                  value={formData.fullName}
                  onChange={handleInputChange}
                  required
                >
                  <option value="">Chọn nhân viên</option>
                  {employees
                    .filter((user) => user.role === "Employee")
                    .map((employee) => (
                      <option key={employee.userID} value={employee.fullName}>
                        {employee.fullName}
                      </option>
                    ))}
                </select>
              </div>
            </div>

            <div className="payment-info">
              <div className="form-group">
                <label htmlFor="paid">Trạng thái thanh toán:</label>
                <select
                  id="paid"
                  value={formData.paid}
                  onChange={handleInputChange}
                >
                  <option value={false}>Chưa thanh toán</option>
                  <option value={true}>Đã thanh toán</option>
                </select>
              </div>
              {formData.paid && (
                <div className="form-group">
                  <label htmlFor="paymentMethod">Phương thức thanh toán:</label>
                  <select
                    id="paymentMethod"
                    value={formData.paymentMethod}
                    onChange={handleInputChange}
                    required={formData.paid}
                  >
                    <option value="">Chọn phương thức</option>
                    <option value="CASH">Tiền mặt</option>
                    <option value="CREDIT_CARD">Thẻ tín dụng</option>
                    <option value="BANK_TRANSFER">Chuyển khoản</option>
                    <option value="E_WALLET">Ví điện tử</option>
                  </select>
                </div>
              )}
              <div className="form-group">
                <label htmlFor="totalAmount">Tổng tiền:</label>
                <input
                  type="text"
                  id="totalAmount"
                  value={formData.totalAmount.toLocaleString("vi-VN")}
                  readOnly
                />
              </div>
            </div>
          </div>

          <div className="form-column">
            <div className="order-items">
              <h3 className="order-items-label">Chi tiết đơn hàng</h3>
              <div className="add-item-section">
                <select
                  value={currentItem.product.name}
                  onChange={(e) => {
                    const selectedProduct = products.find(
                      (p) => p.name === e.target.value
                    );
                    if (selectedProduct) {
                      const { productType, ...productWithoutType } =
                        selectedProduct;
                      setCurrentItem((prev) => ({
                        ...prev,
                        product: productWithoutType,
                      }));
                    } else {
                      setCurrentItem((prev) => ({
                        ...prev,
                        product: { name: "", sellingPrice: 0 },
                      }));
                    }
                  }}
                >
                  <option value="">Chọn sản phẩm</option>
                  {products.map((product) => (
                    <option key={product.productID} value={product.name}>
                      {product.name}
                    </option>
                  ))}
                </select>
                <input
                  type="number"
                  min="1"
                  value={currentItem.quantity}
                  onChange={(e) =>
                    setCurrentItem((prev) => ({
                      ...prev,
                      quantity: Number(e.target.value) || null,
                    }))
                  }
                />
                {(mode === "add" ||
                  (mode === "detail" && order.paid === false)) && (
                  <button type="button" onClick={handleAddItem}>
                    Thêm
                  </button>
                )}
              </div>

              <table className="items-table">
                <thead>
                  <tr>
                    <th>Sản phẩm</th>
                    <th>Số lượng</th>
                    <th>Hành động</th>
                  </tr>
                </thead>
                <tbody>
                  {formData.items.map((item, index) => (
                    <tr key={index}>
                      <td>{item.product.name}</td>
                      <td>
                        <input
                          type="number"
                          min="1"
                          value={item.quantity}
                          onChange={(e) =>
                            handleQuantityChange(index, e.target.value)
                          }
                          style={{
                            width: "60px",
                            padding: "4px",
                            border: "1px solid #ddd",
                            borderRadius: "4px",
                          }}
                        />
                      </td>
                      <td>
                        {(mode === "add" ||
                          (mode === "detail" && order.paid === false)) && (
                          <button
                            type="button"
                            onClick={() => handleRemoveItem(index)}
                          >
                            Xóa
                          </button>
                        )}
                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          </div>
        </form>

        {(mode === "add" || (mode === "detail" && order.paid === false)) && (
          <button type="submit" className="btn-submit" onClick={handleSubmit}>
            {mode === "add" ? "Thêm đơn hàng" : "Cập nhật"}
          </button>
        )}
      </div>
    </div>
  );
};

export default AddOrderForm;
