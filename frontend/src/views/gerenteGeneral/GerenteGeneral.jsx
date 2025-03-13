import React from "react";
import style from "./GerenteGeneral.module.css";

export const GerenteGeneral = () => {
  return (
    <div className={style["panel-gerente-general"]}>
      <div className={style["contenedor-izquierda"]}>
        <div className="wrapper">
          <div className={style["panel-crear-sucursal"]}>
            <p className={style['titulo']}>Crear sucursal: </p>
            <input type="text" />
            <button className="submit-btn">Crear</button>
          </div>
        </div>
        <div className="wrapper">
          <div className={style["peticiones-personal"]}>
            <p className={style["titulo"]}>Peticiones de personal</p>
            <div className={style["lista"]}>
              <div className={style["peticion"]}>
                <p>Info de la petición</p>
              </div>
              <div className={style["peticion"]}>
                <p>Info de la petición</p>
              </div>
              <div className={style["peticion"]}>
                <p>Info de la petición</p>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div className={style["contenedor-derecha"]}>
        <div className="wrapper">
          <div className={style["lista-movimientos"]}>
            <p className={style["titulo"]}>Movimientos</p>
            <div className={style["lista"]}>
              <div className={style["movimiento"]}>
                <p>Info del movimiento</p>
              </div>
              <div className={style["movimiento"]}>
                <p>Info del movimiento</p>
              </div>
              <div className={style["movimiento"]}>
                <p>Info del movimiento</p>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};
