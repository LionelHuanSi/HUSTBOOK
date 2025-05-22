import { useState } from "react";
import { NavLink } from "react-router-dom";
import "./Sidebar.css";
import "../../styles/base.css";

const Sidebar = () => {
  const [auth, setAuth] = useState(localStorage.getItem("token"));

  const handleLogout = () => {
    localStorage.removeItem("token");
  };

  const menuItems = [
    { path: "/inventory", icon: "store", text: "Quản lý kho" },
    { path: "/order", icon: "receipt_long", text: "Quản lý đơn hàng" },
    { path: "/invoice", icon: "receipt", text: "Quản lý hóa đơn" },
    {
      path: "/employee",
      icon: "badge",
      text: "Quản lý nhân viên",
      adminOnly: true,
    },
    {
      path: "/finance",
      icon: "request_quote",
      text: "Quản lý tài chính",
      adminOnly: true,
    },
    { path: "/", icon: "logout", text: "Đăng xuất", onClick: handleLogout },
  ];

  const filteredMenuItems = menuItems.filter(
    (item) => !item.adminOnly || auth !== "employee"
  );

  return (
    <aside>
      <div className="top">
        <div className="logo">
          <img src="/assets/images/logo.png" alt="" />
          <h2>
            <span>Hust book store</span>
          </h2>
        </div>
      </div>
      <div className="slidebar">
        {filteredMenuItems.map(({ path, icon, text, onClick }) => (
          <NavLink
            key={path}
            to={path}
            className={({ isActive }) => (isActive ? "active" : "")}
            onClick={onClick}
          >
            <span className="material-symbols-sharp">{icon}</span>
            <h3>{text}</h3>
          </NavLink>
        ))}
      </div>
    </aside>
  );
};

export default Sidebar;
