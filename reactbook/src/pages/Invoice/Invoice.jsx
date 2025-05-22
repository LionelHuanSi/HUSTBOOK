import React from "react";
import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import "./Invoice.css";
import "../../styles/base.css";
import Sidebar from "../../components/Sidebar/Sidebar";
import {
  getInvoices,
  getSortedInvoices,
  getInvoicesByFilter,
} from "../../services/InvoiceService";

const Invoice = () => {
  const [auth, setAuth] = useState(localStorage.getItem("token"));

  const [invoice, setInvoice] = useState([]);
  const [error, setError] = useState(null);
  const navigate = useNavigate();
  const [filterData, setFilterData] = useState({
    customerName: "",
    employeeName: "",
    startDate: "",
    endDate: "",
    paymentMethod: "",
  });

  useEffect(() => {
    const fetchInvoices = async () => {
      try {
        setError(null);
        const data = await getInvoices();
        setInvoice(data);
        console.log("Fetched invoices:", data);
      } catch (error) {
        setError(error);
        console.error("Error fetching invoices:", error);
      }
    };
    fetchInvoices();
  }, []);

  const handleGetAllInvoices = async () => {
    try {
      setError(null);
      console.log("Fetching all invoices...");
      const response = await getInvoices();
      setInvoice(response);
    } catch (error) {
      setError(error);
      console.error("Error fetching all invoices:", error);
    }
  };

  const handleSort = async (field, type) => {
    const ids = invoice.map((inv) => inv.invoiceID);
    const sortData = {
      field: field,
      type: type,
      invoiceIDList: ids,
    };
    try {
      setError(null);
      console.log("Sort data:", sortData);
      const response = await getSortedInvoices(sortData);
      console.log("Sorted response:", response);
      setInvoice(response);
    } catch (error) {
      setError(error);
      console.error("Error sorting invoices:", error);
    }
  };

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
      const response = await getInvoicesByFilter(filterData);
      setInvoice(response);
    } catch (error) {
      setError(error);
    }
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
        <section className="bill-management">
          <h1 className="bill-management-title">Quản lý hóa đơn</h1>
          <div className="bill-filters">
            <button className="btn-view-all" onClick={handleGetAllInvoices}>
              Duyệt toàn bộ
            </button>
            <input
              type="text"
              placeholder="Tên khách hàng..."
              className="filter-text"
              name="customerName"
              value={filterData.customerName}
              onChange={handleFilterChange}
            />
            <input
              type="text"
              placeholder="Tên nhân viên..."
              className="filter-text"
              name="employeeName"
              value={filterData.employeeName}
              onChange={handleFilterChange}
            />
            <div className="date-range">
              <div className="date-input">
                <label>Từ ngày:</label>
                <input
                  type="date"
                  name="startDate"
                  value={filterData.startDate}
                  onChange={handleFilterChange}
                />
              </div>
              <div className="date-input">
                <label>Đến ngày:</label>
                <input
                  type="date"
                  name="endDate"
                  value={filterData.endDate}
                  onChange={handleFilterChange}
                />
              </div>
            </div>
            <select
              name="paymentMethod"
              value={filterData.paymentMethod}
              onChange={handleFilterChange}
            >
              <option value="">Phương thức thanh toán</option>
              <option value="CASH">Tiền mặt</option>
              <option value="CREDIT_CARD">Thẻ</option>
              <option value="BANK_TRANSFER">Chuyển khoản</option>
              <option value="E_WALLET">Ví điện tử</option>
            </select>
            <button className="btn-filter" onClick={handleFilterSubmit}>
              Lọc
            </button>
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
                <tr
                  key={inv.invoiceID}
                  onClick={() => navigate(`/invoice/${inv.invoiceID}`)}
                >
                  <td>HD{String(inv.invoiceID).padStart(3, "0")}</td>
                  <td>DH{String(inv.order.orderID).padStart(3, "0")}</td>
                  <td>{inv.order.customerInfo.name}</td>
                  <td>{inv.employee.fullName}</td>
                  <td>{new Date(inv.invoiceDate).toLocaleDateString()}</td>
                  <td>{inv.totalAmount.toLocaleString()}</td>
                  <td>
                    {inv.paymentMethod === "CASH"
                      ? "Tiền mặt"
                      : inv.paymentMethod === "CREDIT_CARD"
                      ? "Thẻ"
                      : inv.paymentMethod === "BANK_TRANSFER"
                      ? "Chuyển khoản"
                      : inv.paymentMethod === "E_WALLET"
                      ? "Ví điện tử"
                      : ""}
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

export default Invoice;
