import React from "react";
import { CrearUsuario } from "../../components/CrearUsuario/CrearUsuario";
import { ListarPrestamos } from "../../components/ListarPrestamos/ListarPrestamos";
import style from "./GerenteSucursal.module.css";
import { ListarEmpleados } from "../../components/ListarEmpleados/ListarEmpleados";

export const GerenteSucursal = () => {
  return (
    <div className={style["gerente-sucursal"]}>
      <div className={style["contenedor-izquierdo"]}>
        <CrearUsuario/>
      </div>
      <div className={style["contenedor-derecho"]}>
        <ListarPrestamos/>
        <ListarEmpleados/>
      </div>
    </div>
  );
};
