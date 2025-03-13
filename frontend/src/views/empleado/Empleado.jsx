import React from "react";
import style from "./Empleado.module.css";
import { CrearUsuario } from "../../components/CrearUsuario/CrearUsuario";
import { PanelTransaccion } from "../../components/PanelTransaccion/PanelTransaccion";

export const Empleado = () => {
  return (
    <div className={style["empleado-page"]}>
      <div className={style["container-left"]}>
        <CrearUsuario />
        <div className="wrapper">
          <div className={style["create-dependent"]}>
            <p className={style["title"]}>Crear un dependiente</p>
            <div className={style["section"]}>
              <p>Nombre</p>
              <input type="text" />
            </div>
            <div className={style["section"]}>
              <p>Comercio</p>
              <input type="text" />
            </div>
            <button className={style["create-dependent-btn"]}>Crear</button>
          </div>
        </div>
      </div>

      <div className={style["container-right"]}>
      <PanelTransaccion />
        <div className="wrapper">
          <div className={style["panel-prestamo"]}>
            <p className={style["titulo"]}>Abrir un prestamo</p>
            <div className={style['formulario-prestamo']}>
              <div className={style["seccion"]}>
                <p className={style['label']}>DUI:</p>
                <input type="text" name="" id="" />
              </div>
              <p className={style["seccion"]}>
                <p className={style['label']}>Salario:</p>
                <input type="number" />
              </p>
              <div className={style["seccion"]}>
                <p className={style['label']}>Cantidad</p>
                <div className={style['cantidad-rango']}>
                  <p>$10,000</p>
                  <input type="range" />
                  <p>Máx</p>
                </div>
              </div>
              <div className={style["seccion"]}>
                <p className={style['label']}>Interés</p>
                <p>%</p>
              </div>
              <button className={style["enviar-prestamo-btn"]}>Enviar</button>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};
