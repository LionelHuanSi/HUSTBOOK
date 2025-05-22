import { useState, useEffect } from "react";
import "./Finance.css";
import "../../styles/base.css";
import Sidebar from "../../components/Sidebar/Sidebar";
import {
  getExpenses,
  deleteExpense,
  filterExpense,
  getTotalEmployeeSalary,
  getTotalExpense,
  getRevenue,
  getProfit,
} from "../../services/FinanceService";
import AddExpenseForm from "../../components/AddExpenseForm/AddExpenseForm";

const Finance = () => {
  const [auth, setAuth] = useState(localStorage.getItem("token"));
  const [finance, setFinance] = useState({
    expense: null,
    revenue: null,
    profit: null,
  });
  const [salary, setSalary] = useState(0);
  const [filterData, setFilterData] = useState({
    startDate: "2025-01-01",
    endDate: new Date().toISOString().split("T")[0],
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
    const fetchFilterExpense = async () => {
      try {
        const response = await filterExpense(filterData);
        setExpenses(response);
      } catch (error) {
        setError(error);
      }
    };
    fetchFilterExpense();
  }, [isFilterButtonClick]);

  useEffect(() => {
    const fetchFinance = async () => {
      setError(null);
      setIsFilterButtonClick(false);
      if (
        filterData.startDate < "2025-01-01" ||
        filterData.endDate > new Date().toISOString().split("T")[0]
      ) {
        setError({
          message:
            "Không có dữ liệu, ngày bắt đầu phải từ 01-01-2025, ngày kết thúc là ngày hiện tại",
        });
        setFinance({
          expense: 0,
          revenue: 0,
          profit: 0,
        });
        setSalary(0);
        return;
      }
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
        const response_sal = await getTotalEmployeeSalary(
          filterData.startDate,
          filterData.endDate
        );
        setSalary(response_sal);
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

  if (auth === "invalid") {
    return (
      <>
        <div className="auth">Bạn chưa đăng nhập</div>
      </>
    );
  } else if (auth === "employee") {
    return (
      <>
        <div className="auth">Bạn không có quyền truy cập vào trang này</div>
      </>
    );
  }

  return (
    <div className="container">
      <Sidebar />
      <main>
        <div className="topbar"></div>
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
            <div className="summary-item salary">
              <span>Tổng lương nhân viên</span>
              <strong style={{ color: "var(--color-expense, #ff7782)" }}>
                {new Intl.NumberFormat("vi-VN", {
                  style: "currency",
                  currency: "VND",
                }).format(salary || 0)}
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
