import { useEffect, useState } from "react";
import style from "./CrearSucursal.module.css";
import { Input } from "../Input/Input";
import { usePost } from "../../hooks/usePost";
import { ButtonSubmit } from "../ButtonSubmit/ButtonSubmit";
import { useAuth } from "../../context/AuthContext";

export const CrearSucursal = () => {
  const { user } = useAuth();
  const { postData, errorMessage, isLoading, isSuccess } = usePost();
  const [sucursal, setSucursal] = useState({ nombre: "", direccion: "" });

  const handlePostSucursal = () => postData("sucursales", sucursal);

  useEffect(() => {
    console.log(sucursal);
  }, [sucursal]);

  if (!user) {
    return (
      <div className="wrapper">
        <p>Debe iniciar sesión para crear una sucursal</p>
      </div>
    );
  }

  return (
    <div className="wrapper">
      <div className={style["panel-crear-sucursal"]}>
        <p className={style["titulo"]}>Crear sucursal: </p>
        <Input
          placeholder="Nombre"
          value={sucursal.nombre}
          onChange={(e) => setSucursal({ ...sucursal, nombre: e.target.value })}
        />
        <Input
          placeholder="Dirección"
          value={sucursal.direccion}
          onChange={(e) =>
            setSucursal({ ...sucursal, direccion: e.target.value })
          }
        />
        <ButtonSubmit
          isLoading={isLoading}
          isSuccess={isSuccess}
          buttonText="Crear sucursal"
          handleEvent={handlePostSucursal}
          errorMessage={errorMessage}
          exitoMensaje="Sucursal creada"
        />
      </div>
    </div>
  );
};
