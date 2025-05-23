import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { useAuth } from "../../context/AuthContext";
import { login } from "../../services/authService";
import style from "./Login.module.css";

export const LoginPage = () => {
  const navigate = useNavigate();
  const { login: authLogin } = useAuth();

  // 1. Define los estados para dui y password
  const [dui, setDui] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const userData = await login(dui, password);
      authLogin(userData); // Guarda en contexto

      // Redirige según rol
      switch (userData.rol) {
        case "CLIENTE":
          navigate("/cliente");
          break;

        case "CAJERO":
          navigate("/cajero");
          break;

        case "DEPENDIENTE":
          navigate("/dependiente");
          break;

        case "GERENTE_SUCURSAL":
          navigate("/gerentesucursal");
          break;

        case "GERENTE_GENERAL":
          navigate("/gerentegeneral");
          break;

        default:
          navigate("/login");
      }
    } catch (error) {
      alert(error.message);
    }
  };

  return (
    <form onSubmit={handleSubmit} className={style["formulario-logeo"]}>
      <input
        type="text"
        value={dui}
        onChange={(e) => setDui(e.target.value)}
        placeholder="DUI"
      />
      <input
        type="password"
        value={password}
        onChange={(e) => setPassword(e.target.value)}
        placeholder="Contraseña"
      />
      <button type="submit">Ingresar</button>
      {error && <p style={{ color: "red" }}>{error}</p>}
    </form>
  );
};
