import { useEffect, useState } from "react";
import { useAuth } from "../../context/AuthContext";
import { usePost } from "../../hooks/usePost";
import { Input } from "../Input/Input";
import { useGet } from "../../hooks/useGet";
import { ButtonSubmit } from "../ButtonSubmit/ButtonSubmit";
import style from "./CrearUsuario.module.css";

export const CrearUsuario = () => {
  const { user } = useAuth();
  const { postData, errorMessage, isLoading, isSuccess } = usePost();
  const { getData, data } = useGet();

  const [usuario, setUsuario] = useState({
    nombre: "",
    dui: "",
    salario: 0,
    rol: null,
    password: "",
    sucursal: {
      id_sucursal: null,
    },
    nombre_comercio: null,
  });

  useEffect(() => {
    getData("sucursales");
  }, []);

  const roles = [
    "CLIENTE",
    "DEPENDIENTE",
    "CAJERO",
    "PERSONAL_LIMPIEZA",
    "RECEPCIONISTA",
    "ASESOR_FINANCIERO",
    "GERENTE_SUCURSAL",
    "GERENTE_GENERAL",
  ];

  const handlePostUsuario = () => postData("usuarios", usuario);

  if (!user) {
    return (
      <div className="wrapper">
        <p>Debe iniciar sesión para crear usuarios</p>
      </div>
    );
  }

  return (
    <div className="wrapper">
      <div className={style["panel-crear-usuario"]}>
        <h4 className={style["title"]}>Crear un usuario:</h4>
        <div className={style["contenedor-select"]}>
          <p className={style["label-select"]}>Rol:</p>
          <select
            value={usuario.rol}
            onChange={(e) => setUsuario({ ...usuario, rol: e.target.value })}
          >
            <option value={null}>Seleccione un rol</option>
            {roles?.map((rol) => (
              <option key={rol} value={rol}>
                {rol}
              </option>
            ))}
          </select>
        </div>
        <Input
          label={"Nombre completo:"}
          placeholder="Ej: Hugo Alejandro Cea Laínez"
          type={"text"}
          value={usuario.nombre}
          onChange={(e) => setUsuario({ ...usuario, nombre: e.target.value })}
        />
        <Input
          label={"DUI (Incluir guión):"}
          placeholder="Ej: 12345678-9"
          type={"text"}
          value={usuario.dui}
          onChange={(e) => setUsuario({ ...usuario, dui: e.target.value })}
        />
        <Input
          label={"Contraseña:"}
          placeholder=""
          type={"password"}
          value={usuario.password}
          onChange={(e) => setUsuario({ ...usuario, password: e.target.value })}
        />
        <Input
          label={"Salario (Dos decimales):"}
          type={"number"}
          value={usuario.salario}
          onChange={(e) => setUsuario({ ...usuario, salario: e.target.value })}
        />
        <Input
          label={"Negocio (Dependientes):"}
          type={"text"}
          value={usuario.nombre_comercio}
          onChange={(e) =>
            setUsuario({ ...usuario, nombre_comercio: e.target.value })
          }
        />
        <div className={style["contenedor-select"]}>
          <p className={style["label-select"]}>Sucursal (Empleado):</p>

          <select
            value={usuario.sucursal?.id_sucursal || ""}
            onChange={(e) =>
              setUsuario({
                ...usuario,
                sucursal: e.target.value
                  ? { id_sucursal: Number(e.target.value) }
                  : null,
              })
            }
          >
            <option value="">Seleccione una sucursal</option>
            {data.length > 0 ? (
              data.map((sucursal) => (
                <option value={sucursal.id_sucursal}>{sucursal.nombre}</option>
              ))
            ) : (
              <option value="">No se entontraron sucursales</option>
            )}
          </select>
        </div>
        <ButtonSubmit
          buttonText="Crear usuario"
          exitoMensaje="Usuario creado"
          handleEvent={handlePostUsuario}
          isLoading={isLoading}
          isSuccess={isSuccess}
          errorMessage={errorMessage}
        />
      </div>
    </div>
  );
};
