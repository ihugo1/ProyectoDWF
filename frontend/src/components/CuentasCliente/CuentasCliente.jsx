import React, { useState, useEffect } from "react";
import { usePost } from "../../hooks/usePost";
import { ButtonSubmit } from "../ButtonSubmit/ButtonSubmit";

export const CuentasCliente = () => {
  const { postData, errorMessage, isLoading, isSuccess } = usePost();

  const handlePostCuenta = () => {
    postData("cuentas");
  };

  return (
    <div>
      <h1>Cuentas Cliente</h1>
      <ButtonSubmit
        isLoading={isLoading}
        isSuccess={isSuccess}
        buttonText="Crear cuenta"
        handleEvent={handlePostCuenta}
        errorMessage={errorMessage}
        exitoMensaje="Cuenta creada"
      />
    </div>
  );
};
