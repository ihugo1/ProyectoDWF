import React from "react";
import style from "./Dependiente.module.css";
import { CrearMovimiento } from "../../components/CrearMovimiento/CrearMovimiento";

export const Dependiente = () => {
  return (
    <div className={style["dependiente-page"]}>
      {/* Seccion derecha*/}
      <div className={style["contenedor-derecho"]}>
        <CrearMovimiento/>
      </div>
    </div>
  );
};
