import React from "react";
import style from "./Cliente.module.css";

export const Cliente = () => {
  return (
    <div className={style["panel-cliente"]}>
      {/* Seccion izquierda*/}
      <div className={style["contenedor-izquierdo"]}>
        {/* Cuentas del cliente*/}
        <div className="wrapper">
          <div className={style["cuentas-cliente"]}>
            <p className={style["titulo"]}>Sus cuentas</p>
            <table className="table">
              <tr>
                <th>Cuenta</th>
                <th>Saldo</th>
              </tr>
              <tr>
                <td>Cuenta 1</td>
                <td>Saldo 1</td>
              </tr>
              <tr>
                <td>Cuenta 2</td>
                <td>Saldo 2</td>
              </tr>
            </table>
          </div>
        </div>
        {/* Creacion de cuenta izquierda*/}
        <div className="wrapper">
          <button className="submit-btn">
            Crear una cuenta nueva
          </button>
        </div>
      </div>
      {/* Seccion derecha*/}
      <div className={style["contenedor-derecha"]}>
        <div className="wrapper">
          <div className={style["transacciones"]}>
            <p className={style["titulo"]}>Sus transacciones</p>
            <ul className={style['lista-transacciones']}>
              <div className={style['transaccion']}>
                Información de la transacción
              </div>
              <div className={style['transaccion']}>
                Información de la transacción
              </div>
              <div className={style['transaccion']}>
                Información de la transacción
              </div>
              <div className={style['transaccion']}>
                Información de la transacción
              </div>
              <div className={style['transaccion']}>
                Información de la transacción
              </div>
              <div className={style['transaccion']}>
                Información de la transacción
              </div>
              <div className={style['transaccion']}>
                Información de la transacción
              </div>
              <div className={style['transaccion']}>
                Información de la transacción
              </div>
              <div className={style['transaccion']}>
                Información de la transacción
              </div>
              
            </ul>
          </div>
        </div>
      </div>
    </div>
  );
};
