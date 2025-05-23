import React from "react";
import { useAuth } from "../../context/AuthContext";
import { useNavigate } from "react-router-dom"; // Importa useNavigate
import style from "./Header.module.css";


export const Header = () => {
  const { user, logout } = useAuth(); // Obtiene usuario y función de logout del contexto
  const navigate = useNavigate(); 

    const handleLogout = () => {
    logout(); // Cierra la sesión en el contexto
    navigate("/login"); // Redirige al login
  };

  return (
    <nav className={style["header"]}>
      <div className={style["logo"]}>Bancoagrícola</div>
      
      {user ? (
        <div className={style["login-section"]}>
          <div className={style["user-name"]}>
            <p className={style["grettings"]}>Bienvenido</p>
            <p className={style["name-and-role"]}>
              {user.nombre} ({user.rol})
            </p>
          </div>
          <button 
            onClick={handleLogout} // Usa la nueva función
            className={style["logout-btn"]}
          >
            Salir
          </button>
        </div>
      ) : (
        <div></div>
      )}
    </nav>
  );
};
