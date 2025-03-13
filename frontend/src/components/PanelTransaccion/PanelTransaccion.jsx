import React from "react";
import style from "./PanelTransaccion.module.css";

export const PanelTransaccion = () => {
  return (
    <div className="wrapper">
      <div className={style["transaction-panel"]}>
        <p className={style["title"]}>Panel de operaciones</p>
        <div className={style["transaction-section"]}>
          <p>DUI</p>
          <input type="text" />
        </div>
        <div className={style["transaction-section"]}>
          <p>Cuenta</p>
          <input type="text" />
        </div>
        <div className={style["transaction-section"]}>
          <p>Operacion a realizar</p>
          <select name="" id="">
            <option value="">Retiro</option>
            <option value="">Deposito</option>
          </select>
        </div>
        <div className={style["transaction-section"]}>
          <p>Cantidad</p>
          <input type="number" />
        </div>
        <button type="submit" className={style["submit-transaction-btn"]}>
          Enviar
        </button>
      </div>
    </div>
  );
};
