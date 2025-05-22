import { useState, useEffect } from "react";
import "./Order.css";
import "../../styles/base.css";
import Sidebar from "../../components/Sidebar/Sidebar";
import { getOrders } from "../../services/OrderService";
import { getOrdersByFilter } from "../../services/OrderService";
import { getSortedOrders } from "../../services/OrderService";
import { deleteOrder } from "../../services/OrderService";
import AddOrderForm from "../../components/AddOrderForm/AddOrderForm";

const Order = () => {
  const [auth, setAuth] = useState(localStorage.getItem("token"));
  const [order, setOrder] = useState([]);
  const [error, setError] = useState(null);

  const [filterData, setFilterData] = useState({
    customerName: "",
    startDate: "",
    endDate: "",
    isPaid: "",
    productName: "",
  });

  const [isAddFormOpen, setIsAddFormOpen] = useState(false);
  const [selectedOrder, setSelectedOrder] = useState(null);
  const [formMode, setFormMode] = useState("add");

  useEffect(() => {
    const fetchOrders = async () => {
      try {
        const data = await getOrders();
        setOrder(data);
        console.log("Fetched orders:", data);
      } catch (error) {
        console.error("Error fetching orders:", error);
      }
    };
    fetchOrders();
  }, [isAddFormOpen]);

  const handleFilterChange = (e) => {
    const { name, value } = e.target;
    setFilterData((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  const handleFilterSubmit = async (e) => {
    e.preventDefault();
    try {
      if (filterData.startDate && filterData.endDate) {
        if (new Date(filterData.startDate) > new Date(filterData.endDate)) {
          setError(
            new Error("Ngày bắt đầu phải nhỏ hơn hoặc bằng ngày kết thúc")
          );
          return;
        }
      }
      console.log("Filter data:", filterData);
      const response = await getOrdersByFilter(filterData);
      setOrder(response);
    } catch (error) {
      setError(error);
    }
  };

  const handleSort = async (field, type) => {
    const ids = order.map((ord) => ord.orderID);
    const sortData = {
      field: field,
      type: type,
      orderIDList: ids,
    };
    try {
      console.log("Sort data:", sortData);
      const response = await getSortedOrders(sortData);
      console.log("Sorted response:", response);
      setOrder(response);
    } catch (error) {
      setError(error);
    }
  };

  const handleGetAllOrders = async () => {
    try {
      console.log("Fetching all orders...", order);
      const response = await getOrders();
      setOrder(response);
    } catch (error) {
      setError(error);
    }
  };

  const handleAddOrderClick = () => {
    setFormMode("add");
    setSelectedOrder(null);
    setIsAddFormOpen(true);
  };

  const handleDeleteOrder = async (orderId) => {
    try {
      console.log("Order :", orderId);
      await deleteOrder(orderId);
      setOrder((prevOrders) =>
        prevOrders.filter((ord) => ord.orderID !== orderId)
      );
    } catch (error) {
      setError(error);
      console.error("Error deleting order:", error);
    }
  };

  const handleEditOrder = (ord) => {
    try {
      setFormMode("detail");
      setSelectedOrder(ord);
      setIsAddFormOpen(true);
    } catch (error) {
      setError(error);
      console.error("Error in edit order:", error);
    }
  };

  const handleCloseForm = () => {
    setIsAddFormOpen(false);
    setSelectedOrder(null);
  };

  if (auth === "invalid") {
    return (
      <>
        <div className="auth">Bạn chưa đăng nhập</div>
      </>
    );
  }

  return (
    <div className="container">
      <Sidebar />
      <main>
        <div className="topbar"></div>
        <section className="order-management">
          {error && (
            <div className="error-message">
              <p>Đã xảy ra lỗi: {error.message}</p>
            </div>
          )}
          <h1 className="order-management-title">Quản lý đơn hàng</h1>
          <div className="order-actions">
            <button className="btn-add" onClick={handleAddOrderClick}>
              Thêm đơn hàng
            </button>
          </div>
          <div className="order-filters">
            <button className="btn-view-all" onClick={handleGetAllOrders}>
              Duyệt toàn bộ
            </button>
            <input
              type="text"
              className="filter-customer"
              placeholder="Tên khách hàng"
              name="customerName"
              value={filterData.customerName}
              onChange={handleFilterChange}
            />
            <div className="date-range">
              <div className="date-input">
                <label>Từ ngày:</label>
                <input
                  type="date"
                  className="filter-date"
                  name="startDate"
                  value={filterData.startDate}
                  onChange={handleFilterChange}
                />
              </div>
              <div className="date-input">
                <label>Đến ngày:</label>
                <input
                  type="date"
                  className="filter-date"
                  name="endDate"
                  value={filterData.endDate}
                  onChange={handleFilterChange}
                />
              </div>
            </div>
            <select
              className="filter-payment-status"
              name="isPaid"
              value={filterData.isPaid}
              onChange={handleFilterChange}
            >
              <option value="">Trạng thái thanh toán</option>
              <option value="true">Đã thanh toán</option>
              <option value="false">Chưa thanh toán</option>
            </select>
            <input
              type="text"
              className="filter-product"
              placeholder="Tên sản phẩm"
              name="productName"
              value={filterData.productName}
              onChange={handleFilterChange}
            />
            <button className="btn-filter" onClick={handleFilterSubmit}>
              Lọc
            </button>
          </div>
          <table className="order-table">
            <thead>
              <tr>
                <th>
                  Mã ĐH
                  <button
                    className="btn-sort-up"
                    onClick={() => handleSort("orderID", "up")}
                  >
                    ▲
                  </button>
                  <button
                    className="btn-sort-down"
                    onClick={() => handleSort("orderID", "down")}
                  >
                    ▼
                  </button>
                </th>
                <th>Khách hàng</th>
                <th>Sản phẩm</th>
                <th>
                  Ngày đặt
                  <button
                    className="btn-sort-up"
                    onClick={() => handleSort("orderDate", "up")}
                  >
                    ▲
                  </button>
                  <button
                    className="btn-sort-down"
                    onClick={() => handleSort("orderDate", "down")}
                  >
                    ▼
                  </button>
                </th>
                <th>Số lượng</th>
                <th>
                  Tổng tiền
                  <button
                    className="btn-sort-up"
                    onClick={() => handleSort("totalAmount", "up")}
                  >
                    ▲
                  </button>
                  <button
                    className="btn-sort-down"
                    onClick={() => handleSort("totalAmount", "down")}
                  >
                    ▼
                  </button>
                </th>
                <th>Thanh toán</th>
                <th>Hành động</th>
              </tr>
            </thead>
            <tbody>
              {order.map((ord) => (
                <tr key={ord.orderID}>
                  <td>DH{String(ord.orderID).padStart(3, "0")}</td>
                  <td>{ord.customerInfo.name}</td>
                  <td>
                    {ord.items.map((item) => item.product.name).join(", ")}
                  </td>
                  <td>{ord.orderDate.split("T")[0]}</td>
                  <td>
                    {ord.items.reduce((sum, item) => sum + item.quantity, 0)}
                  </td>
                  <td>{ord.totalAmount.toFixed(2)}</td>
                  <td style={{ color: ord.paid ? "#4CAF50" : "#f44336" }}>
                    {ord.paid ? "Đã thanh toán" : "Chưa thanh toán"}
                  </td>
                  <td>
                    <div className="action-buttons-cell">
                      {ord.paid === false ? (
                        <button
                          className="btn-edit"
                          onClick={() => handleEditOrder(ord)}
                        >
                          Sửa
                        </button>
                      ) : (
                        <button
                          className="btn-edit"
                          onClick={() => handleEditOrder(ord)}
                        >
                          Chi tiết
                        </button>
                      )}
                      <button
                        className="btn-delete"
                        onClick={() => handleDeleteOrder(ord.orderID)}
                      >
                        Xoá
                      </button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
          {isAddFormOpen && (
            <AddOrderForm
              onClose={handleCloseForm}
              mode={formMode}
              order={selectedOrder}
            />
          )}
        </section>
      </main>
    </div>
  );
};

export default Order;
