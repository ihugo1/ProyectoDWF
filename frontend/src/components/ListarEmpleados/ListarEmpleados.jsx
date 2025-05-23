import React, { useEffect, useState } from "react";
import { useAuth } from "../../context/AuthContext";
import style from "./ListarEmpleados.module.css";
import { useGet } from "../../hooks/useGet";

export const ListarEmpleados = () => {
  const { user } = useAuth();
  const { data, getData } = useGet();

  useEffect(() => {
    if (user?.id) {
      if (user.rol === "GERENTE_GENERAL") {
        getData("usuarios/usuarios-no-activos");
      } else {
        getData("usuarios/empleados-activos");
      }
    }
  }, [user?.id]);


  const handleCambiarEstado = async (id, nuevoEstado) => {
    try {
      const response = await fetch(
        `http://localhost:8080/api/usuarios/${id}/estado?nuevoEstado=${nuevoEstado}`,
        {
          method: "PUT",
          headers: {
            Authorization: `Bearer ${user?.token}`,
            "Content-Type": "application/json",
          },
        }
      );

      if (!response.ok) {
        const errorData = await response.json().catch(() => ({}));
        throw new Error(
          errorData.message ||
            `Error ${response.status}: ${response.statusText}`
        );
      }
    } catch (err) {
      console.error("Error al cambiar estado:", err);
    }
  };

  return (
    <div className="wrapper">
      <div className={style["lista-empleados"]}>
        {user?.rol == "GERENTE_GENERAL" ? (
          <h3>Aprobar empleados</h3>
        ) : (
          <h3>Dar de baja empleados</h3>
        )}
        {data.length > 0 ? (
          data.map((empleado) => (
            <div key={empleado.id_usuario} className={style.empleado}>
              <div className={style["empleado-info"]}>
                <div className={style["empleado-seccion"]}>
                  <label>Nombre: </label>
                  <p>{empleado.nombre}</p>
                </div>
                <div className={style["empleado-seccion"]}>
                  <label>Rol: </label>
                  <p>{empleado.rol}</p>
                </div>
                <div className={style["empleado-seccion"]}>
                  <label>Sucursal: </label>
                  <p>{empleado?.sucursal.nombre}</p>
                </div>
              </div>
              <div className={style["empleado-acciones"]}>
                {user?.rol == "GERENTE_GENERAL" ? (
                  <button
                    className={style["activar-btn"]}
                    onClick={() =>
                      handleCambiarEstado(empleado.id_usuario, "ACTIVO")
                    }
                  >
                    ACTIVAR
                  </button>
                ) : (
                  <button
                    className={style["dar-baja-btn"]}
                    onClick={() =>
                      handleCambiarEstado(empleado.id_usuario, "INACTIVO")
                    }
                  >
                    DAR BAJA
                  </button>
                )}
              </div>
            </div>
          ))
        ) : (
          <div className={style.message}>No hay empleados</div>
        )}
      </div>
    </div>
  );
};
