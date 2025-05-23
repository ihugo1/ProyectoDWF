import React from "react";
import style from "./ButtonSubmit.module.css";

export const ButtonSubmit = ({
  buttonText,
  exitoMensaje,
  handleEvent,
  isLoading,
  errorMessage,
  isSuccess,
}) => {
  return (
    <div className={style["button-contenedor"]}>
      <button className={style["submit-btn"]} onClick={handleEvent}>
        {isLoading ? "Procesando" : buttonText}
      </button>

      {errorMessage && (
        <div className={style["mensaje-error"]}>
          <p>Error: {errorMessage}</p>
        </div>
      )}

      {isSuccess && (
        <div className={style["mensaje-exito"]}>
          <p>Exito: {exitoMensaje}</p>
        </div>
      )}
    </div>
  );
};
