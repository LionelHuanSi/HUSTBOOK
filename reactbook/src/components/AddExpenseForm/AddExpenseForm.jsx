import { useState, useEffect } from "react";
import "./AddExpenseForm.css";
import { addExpense, updateExpense } from "../../services/FinanceService";

const EXPENSE_TYPES = [
  "Employee Salaries",
  "Electricity",
  "Water",
  "Facility Maintenance",
];

const AddExpenseForm = ({ onClose, mode, expense }) => {
  const [formData, setFormData] = useState({
    expenseType: "",
    amount: "",
    date: "",
    description: "",
  });

  useEffect(() => {
    if (expense && mode === "edit") {
      setFormData({
        expenseType: expense.expenseType,
        amount: expense.amount,
        date: expense.date.split("T")[0],
        description: expense.description,
      });
    }
  }, [expense, mode]);

  const handleSubmit = async (e) => {
    e.preventDefault();
    console.log("fromDATA:", formData);
    try {
      if (mode === "edit") {
        console.log("expenseID là: ", expense.expenseID);
        await updateExpense(expense.expenseID, formData);
      } else {
        await addExpense(formData);
      }
      onClose();
    } catch (error) {
      console.error("Error saving expense:", error);
    }
  };

  return (
    <div className="modal">
      <div className="modal-content">
        <h2>{mode === "edit" ? "Sửa chi phí" : "Thêm chi phí mới"}</h2>
        <form onSubmit={handleSubmit}>
          <div className="form-group">
            <label>Loại chi phí:</label>
            <select
              value={formData.expenseType}
              onChange={(e) =>
                setFormData({ ...formData, expenseType: e.target.value })
              }
              required
            >
              <option value="">Chọn loại chi phí</option>
              {EXPENSE_TYPES.map((type) => (
                <option key={type} value={type}>
                  {type}
                </option>
              ))}
            </select>
          </div>
          <div className="form-group">
            <label>Số tiền:</label>
            <input
              type="number"
              value={formData.amount}
              onChange={(e) =>
                setFormData({ ...formData, amount: e.target.value })
              }
              required
            />
          </div>
          <div className="form-group">
            <label>Ngày:</label>
            <input
              type="date"
              value={formData.date}
              onChange={(e) =>
                setFormData({ ...formData, date: e.target.value })
              }
              required
            />
          </div>
          <div className="form-group">
            <label>Ghi chú:</label>
            <textarea
              value={formData.description}
              onChange={(e) =>
                setFormData({ ...formData, description: e.target.value })
              }
            />
          </div>
          <div className="form-actions">
            <button type="submit">
              {mode === "edit" ? "Cập nhật" : "Thêm"}
            </button>
            <button type="button" onClick={onClose}>
              Hủy
            </button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default AddExpenseForm;
