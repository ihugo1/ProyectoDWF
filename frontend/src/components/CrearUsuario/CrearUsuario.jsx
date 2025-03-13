import React from "react";
import style from "./CrearUsuario.module.css";

export const CrearUsuario = () => {
  return (
    <div className="wrapper">
      <form className={style["create-user-form"]}>
        <p className={style["title"]}>Crear un usuario:</p>
        <div className={style["section"]}>
          <p>Nombre</p>
          <input type="text" />
        </div>
        <div className={style["section"]}>
          <p>DUI</p>
          <input type="text" />
        </div>
        <button type="submit" className={style["create-user-btn"]}>
          Crear
        </button>
      </form>
    </div>
  );
};
