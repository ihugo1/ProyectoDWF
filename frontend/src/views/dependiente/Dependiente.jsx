import React from "react";
import style from "./Dependiente.module.css";
import { CrearUsuario } from "../../components/CrearUsuario/CrearUsuario";
import { PanelTransaccion } from "../../components/PanelTransaccion/PanelTransaccion";

export const Dependiente = () => {
  return (
    <div className={style["dependiente-page"]}>
      {/* Seccion izquierda*/}
      <div className={style["contenedor-izquierdo"]}>
        {/*Info del comercio*/}
        <div className="wrapper">
          <div className={style["info-negocio"]}>
            <p className={style["label-nombre"]}>Comercio:</p>
            <p className={style["nombre"]}>NombreComercio</p>
          </div>
        </div>
        {/*Crear Usuario*/}
        <CrearUsuario/>
      </div>

      {/* Seccion derecha*/}
      <div className={style["contenedor-derecho"]}>
        <PanelTransaccion/>
      </div>
    </div>
  );
};
