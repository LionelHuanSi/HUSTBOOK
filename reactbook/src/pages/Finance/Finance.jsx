import { useState, useEffect } from "react";
import "./Finance.css";
import "../../styles/base.css";
import Sidebar from "../../components/Sidebar/Sidebar";
import {
  getExpenses,
  deleteExpense,
  getTotalExpense,
  getRevenue,
  getProfit,
} from "../../services/FinanceService";
import AddExpenseForm from "../../components/AddExpenseForm/AddExpenseForm";

const Finance = () => {
  const [finance, setFinance] = useState({
    expense: null,
    revenue: null,
    profit: null,
  });
  const [filterData, setFilterData] = useState({
    startDate: "",
    endDate: "",
  });
  const [isFilterButtonClick, setIsFilterButtonClick] = useState(false);
  const [expenses, setExpenses] = useState([]);
  const [error, setError] = useState(null);
  const [isAddFormOpen, setIsAddFormOpen] = useState(false);
  const [selectedExpense, setSelectedExpense] = useState(null);
  const [formMode, setFormMode] = useState("add");

  const handleChangeFilterData = (e) => {
    const { name, value } = e.target;
    setFilterData((prevData) => ({
      ...prevData,
      [name]: value,
    }));
  };

  useEffect(() => {
    const fetchFinance = async () => {
      setError(null);
      setIsFilterButtonClick(false);
      try {
        console.log("ftDT: ", filterData);
        const response_exp = await getTotalExpense(
          filterData.startDate,
          filterData.endDate
        );
        console.log("exp: ", response_exp);
        const response_rev = await getRevenue(
          filterData.startDate,
          filterData.endDate
        );
        console.log("rev: ", response_rev);
        const response_pro = await getProfit(
          filterData.startDate,
          filterData.endDate
        );
        console.log("pro: ", response_pro);
        setFinance({
          expense: response_exp,
          revenue: response_rev,
          profit: response_pro,
        });
      } catch (error) {
        setError(error);
      }
    };

    fetchFinance();
  }, [isFilterButtonClick]);

  useEffect(() => {
    const fetchExpenses = async () => {
      try {
        const response = await getExpenses();
        console.log("expenseData:", response);
        setExpenses(response || []);
      } catch (error) {
        console.error("Error fetching expenses:", error);
        setError(error);
        setExpenses([]);
      }
    };

    fetchExpenses();
  }, [isAddFormOpen]);

  const handleAddExpenseClick = () => {
    setFormMode("add");
    setSelectedExpense(null);
    setIsAddFormOpen(true);
  };

  const handleDeleteExpense = async (expenseId) => {
    try {
      await deleteExpense(expenseId);
      setExpenses((prevExpenses) =>
        prevExpenses.filter((exp) => exp.expenseID !== expenseId)
      );
    } catch (error) {
      setError(error);
      console.error("Error deleting expense:", error);
    }
  };

  const handleEditExpense = (expense) => {
    setFormMode("edit");
    setSelectedExpense(expense);
    setIsAddFormOpen(true);
  };

  const handleCloseForm = () => {
    setIsAddFormOpen(false);
    setSelectedExpense(null);
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
        <section className="finance-management">
          {error && <div>{error.message}</div>}
          <h1 className="finance-management-title">Quản lý tài chính</h1>
          <div className="finance-summary">
            <div className="summary-item revenue">
              <span>Doanh thu</span>
              <strong>
                {new Intl.NumberFormat("vi-VN", {
                  style: "currency",
                  currency: "VND",
                }).format(finance.revenue || 0)}
              </strong>
            </div>
            <div className="summary-item expense">
              <span>Chi phí</span>
              <strong>
                {new Intl.NumberFormat("vi-VN", {
                  style: "currency",
                  currency: "VND",
                }).format(finance.expense || 0)}
              </strong>
            </div>
            <div className="summary-item profit">
              <span>Lợi nhuận</span>
              <strong>
                {new Intl.NumberFormat("vi-VN", {
                  style: "currency",
                  currency: "VND",
                }).format(finance.profit || 0)}
              </strong>
            </div>
          </div>
          <div className="finance-actions">
            <button className="btn-add" onClick={handleAddExpenseClick}>
              Thêm chi phí
            </button>
          </div>
          <div className="finance-filters">
            <button className="btn-view-all">Duyệt toàn bộ</button>
            <input
              type="date"
              name="startDate"
              value={filterData.startDate}
              onChange={handleChangeFilterData}
              className="filter-date-from"
              placeholder="Từ ngày"
            />
            <input
              type="date"
              name="endDate"
              value={filterData.endDate}
              onChange={handleChangeFilterData}
              className="filter-date-to"
              placeholder="Đến ngày"
            />
            <button
              className="btn-filter"
              onClick={() => setIsFilterButtonClick(true)}
            >
              Lọc
            </button>
          </div>
          <table className="finance-table">
            <thead>
              <tr>
                <th>Mã CP</th>
                <th>Loại chi phí</th>
                <th>Số tiền</th>
                <th>Ngày</th>
                <th>Ghi chú</th>
                <th>Hành động</th>
              </tr>
            </thead>
            <tbody>
              {expenses && expenses.length > 0 ? (
                expenses.map((expense) => (
                  <tr key={expense.expenseID}>
                    <td>CP{String(expense.expenseID).padStart(3, "0")}</td>
                    <td>{expense.expenseType}</td>
                    <td>
                      {new Intl.NumberFormat("vi-VN", {
                        style: "currency",
                        currency: "VND",
                      }).format(expense.amount)}
                    </td>
                    <td>
                      {new Date(expense.date).toLocaleDateString("vi-VN")}
                    </td>
                    <td>{expense.description}</td>
                    <td>
                      <div className="action-buttons-cell">
                        <button
                          className="btn-edit"
                          onClick={() => handleEditExpense(expense)}
                        >
                          Sửa
                        </button>
                        <button
                          className="btn-delete"
                          onClick={() => handleDeleteExpense(expense.expenseID)}
                        >
                          Xoá
                        </button>
                      </div>
                    </td>
                  </tr>
                ))
              ) : (
                <tr>
                  <td colSpan="6" style={{ textAlign: "center" }}>
                    Không có dữ liệu chi phí
                  </td>
                </tr>
              )}
            </tbody>
          </table>
          {isAddFormOpen && (
            <AddExpenseForm
              onClose={handleCloseForm}
              mode={formMode}
              expense={selectedExpense}
            />
          )}
        </section>
      </main>
    </div>
  );
};

export default Finance;
