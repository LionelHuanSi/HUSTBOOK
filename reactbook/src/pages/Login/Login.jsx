import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faEye, faEyeSlash } from "@fortawesome/free-regular-svg-icons";
import "./Login.css";
import { getAuth } from "../../services/LoginService";

const Login = () => {
  const navigate = useNavigate();
  const [user, setUser] = useState({
    username: "",
    password: "",
  });
  const [error, setError] = useState(null);
  const [showPassword, setShowPassword] = useState(false);

  const togglePasswordVisibility = () => {
    setShowPassword(!showPassword);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError(null);
    try {
      const response = await getAuth(user);
      console.log("ket qua: ", response.token);
      if (response) {
        localStorage.setItem("token", response.token);
        if (response.token !== "invalid") {
          navigate("/inventory");
        } else {
          setError({
            message: "Username hoặc mật khẩu không đúng, vui lòng thử lại",
          });
        }
      }
    } catch (error) {
      setError({
        message: "Username hoặc mật khẩu không đúng, vui lòng thử lại",
      });
    }
  };

  return (
    <div className="login-page">
      <div className="login-container">
        <div className="login-form">
          <h1>HUST BOOK</h1>
          <h2>Đăng Nhập</h2>
          <form>
            <div className="form-group">
              <input
                type="text"
                placeholder="Username"
                required
                value={user.username}
                onChange={(e) => setUser({ ...user, username: e.target.value })}
              />
            </div>
            <div className="form-group password-wrapper">
              <input
                type={showPassword ? "text" : "password"}
                placeholder="Mật khẩu"
                className="password-input"
                required
                value={user.password}
                onChange={(e) => setUser({ ...user, password: e.target.value })}
              />
              <span
                className="toggle-password"
                onClick={togglePasswordVisibility}
              >
                <FontAwesomeIcon icon={showPassword ? faEyeSlash : faEye} />
              </span>
            </div>
            <a href="#" className="forgot-password">
              Quên mật khẩu?
            </a>
            {error && <div className="error-message">{error.message}</div>}
            <button
              type="submit"
              className="login-button"
              onClick={handleSubmit}
            >
              Đăng Nhập
            </button>
          </form>
        </div>
      </div>
    </div>
  );
};

export default Login;
