import React, { useEffect, useState } from "react";
import style from "./CrearPrestamo.module.css";
import { Input } from "../Input/Input";
import { usePost } from "../../hooks/usePost";
import { ButtonSubmit } from "../ButtonSubmit/ButtonSubmit";

export const CrearPrestamo = () => {
  const { postData, errorMessage, isLoading, isSuccess } = usePost();
  const [prestamo, setPrestamo] = useState({
    monto_solicitado: 0,
    usuario: {
      dui: "12345678-8",
    },
  });

  useEffect(() => {
    console.clear();
    console.log(prestamo);
  }, [prestamo]);

  const handleCrearPrestamo = () => postData("prestamos", prestamo);

  return (
    <div className="wrapper">
      <div className={style["crear-prestamo-panel"]}>
        <h3 className={style["title"]}>Crear un prestamo</h3>
        <Input
          type={"text"}
          placeholder={"Ej: 12345678-0"}
          label={"DUI"}
          onChange={(e) => {
            setPrestamo({ ...prestamo, usuario: { dui: e.target.value } });
          }}
        />

        <Input
          type={"number"}
          placeholder={"Ej: 20000"}
          label={"Monto solicitado:"}
          value={prestamo.monto_solicitado}
          onChange={(e) => {
            setPrestamo({
              ...prestamo,
              monto_solicitado: Number(e.target.value),
            });
          }}
        />

        <ButtonSubmit
          buttonText="Crear prestamo"
          exitoMensaje="Prestamo creado"
          handleEvent={handleCrearPrestamo}
          isLoading={isLoading}
          isSuccess={isSuccess}
          errorMessage={errorMessage}
        />
      </div>
    </div>
  );
};
