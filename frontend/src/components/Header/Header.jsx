import React from "react";
import style from "./Header.module.css";

export const Header = () => {
  return (
    <nav className={style["header"]}>
      <div className={style["logo"]}>Bancoagrícola</div>
      <div className={style["login-section"]}>
        <div className={style["user-name"]}>
          <p className={style["grettings"]}>Bienvenido </p>
          <p className={style["name-and-role"]}>Usuario (Rol)</p>
        </div>
        <p className={style["logout-btn"]}>Salir</p>
      </div>
    </nav>
  );
};
