import React, { useEffect, useState } from "react";
import style from "./GerenteGeneral.module.css";
import { ListarEmpleados } from "../../components/ListarEmpleados/ListarEmpleados";
import { CrearSucursal } from "../../components/CrearSucursal/CrearSucursal";
import { ListarMovimientos } from "../../components/ListarMovimientos/ListarMovimientos";
import { CrearUsuario } from "../../components/CrearUsuario/CrearUsuario";

export const GerenteGeneral = () => {
  return (
    <div className={style["panel-gerente-general"]}>
      <div className={style["contenedor-izquierda"]}>
        <CrearSucursal/>
        <CrearUsuario/>
      </div>
      <div className={style["contenedor-derecha"]}>
        <ListarEmpleados/>
        <ListarMovimientos/>
      </div>
    </div>
  );
};
