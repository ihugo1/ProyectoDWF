import React, { useEffect } from "react";
import { useGet } from "../../hooks/useGet";
import { useAuth } from "../../context/AuthContext";
import style from "./ListarMovimientos.module.css";

export const ListarMovimientos = () => {
  const { user } = useAuth();
  const { data, getData } = useGet();

  useEffect(() => {
    if (user?.id) {
      if (user.rol === "GERENTE_GENERAL") {
        getData("movimientos");
      } else {
        getData("movimientos/usuario/" + user.id);
      }
    }
  }, [user?.id]);

  return (
    <div className="wrapper">
      <div className={style["lista-movimientos"]}>
        <h3>Movimientos</h3>
        {data.length > 0 ? (
          data.map((movimiento) => (
            <div key={movimiento.id_movimiento} className={style["movimiento"]}>
              <div className={style["movimiento-tipo"]}>
                <p>Tipo: </p>
                <p className={style["tipo"]}>{movimiento.tipo}</p>
              </div>
              <div className={style["movimiento-monto"]}>
                <p>Monto: </p>
                <p className={style["monto"]}>${movimiento.monto}</p>
              </div>
              <div className={style["movimiento-cuenta"]}>
                <p>Cuenta: </p>
                <p className={style["cuenta"]}>
                  {movimiento.cuenta.numero_cuenta}
                </p>
              </div>
              <div className={style["movimiento-usuario"]}>
                <p>Realizado por: </p>
                <p className={style["usuario"]}>
                  {movimiento.usuario.nombre} ({movimiento.usuario.rol})
                </p>
              </div>
            </div>
          ))
        ) : (
          <div>
            <p>No se han encontrado movimientos</p>
          </div>
        )}
      </div>
    </div>
  );
};
