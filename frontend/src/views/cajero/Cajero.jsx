import React from "react";
import style from "./Empleado.module.css";
import { CrearUsuario } from "../../components/CrearUsuario/CrearUsuario";
import { CrearMovimiento } from "../../components/CrearMovimiento/CrearMovimiento";
import { CrearPrestamo } from "../../components/CrearPrestamo/CrearPrestamo";

export const Cajero = () => {
  return (
    <div className={style["empleado-page"]}>
      <div className={style["container-left"]}>
        <CrearUsuario />
      </div>
      <div className={style["container-right"]}>
        <CrearMovimiento />
        <CrearPrestamo />
      </div>
    </div>
  );
};
