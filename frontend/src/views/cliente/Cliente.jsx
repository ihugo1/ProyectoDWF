import React from "react";
import style from "./Cliente.module.css";
import { CrearCuenta } from "../../components/CrearCuenta/CrearCuenta";
import { ListarCuentas } from "../../components/ListarCuentas/ListarCuentas";
import { ListarMovimientos } from "../../components/ListarMovimientos/ListarMovimientos";

export const Cliente = () => {
  return (
    <div className={style["panel-cliente"]}>
      {/* Seccion izquierda*/}
      <div className={style["contenedor-izquierdo"]}>
        <ListarCuentas />
        <CrearCuenta />
      </div>
      {/* Seccion derecha*/}
      <div className={style["contenedor-derecha"]}>
        <ListarMovimientos />
      </div>
    </div>
  );
};
