import { useAuth } from "../../context/AuthContext";
import { usePost } from "../../hooks/usePost";
import style from "./CrearCuenta.module.css";
import { ButtonSubmit } from "../ButtonSubmit/ButtonSubmit";

export const CrearCuenta = () => {
  const { user } = useAuth();
  const { postData, errorMessage, isLoading, isSuccess } = usePost();

  const handleCrearCuenta = () => postData("cuentas");

  if (!user) {
    return (
      <div className="wrapper">
        <p>Debe iniciar sesión para crear una cuenta</p>
      </div>
    );
  }

  return (
    <div className="wrapper">
      <div className={style["panel-crear-cuenta"]}>
        <ButtonSubmit
          buttonText="Crear cuenta"
          exitoMensaje="Cuenta creada"
          handleEvent={handleCrearCuenta}
          isLoading={isLoading}
          isSuccess={isSuccess}
          errorMessage={errorMessage}
        />
      </div>
    </div>
  );
};
