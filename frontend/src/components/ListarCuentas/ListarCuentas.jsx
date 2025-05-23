import React, { useEffect } from "react";
import { useAuth } from "../../context/AuthContext";
import { useGet } from "../../hooks/useGet";
import style from "./ListarCuentas.module.css";

export const ListarCuentas = () => {
  const { user } = useAuth();
  const { data, getData } = useGet(); 

  useEffect(() => {
    if (user?.id) { 
      getData(`cuentas/usuario/${user.id}`);
    }
  }, [user?.id]); 

  return (
    <div className="wrapper">
      <div className={style["lista-cuentas"]}>
        <h2>Sus cuentas</h2>
        {data.length > 0 ? (
          data.map((cuenta) => (
            <div key={cuenta.id_cuenta} className={style["cuenta"]}>
              <div className={style["cuenta-numero"]}>
                <p>Número: </p>
                <p className={style["numero"]}>{cuenta.numero_cuenta}</p>
              </div>
              <div className={style["cuenta-saldo"]}>
                <p>Saldo: </p>
                <p className={style["saldo"]}>${cuenta.saldo.toFixed(2)}</p>
              </div>
            </div>
          ))
        ) : (
          <div>
            <p>No se han encontrado cuentas</p>
          </div>
        )}
      </div>
    </div>
  );
};