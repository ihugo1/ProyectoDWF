import React, { useEffect, useState } from "react";
import style from "./CrearMovimiento.module.css";
import { useAuth } from "../../context/AuthContext";
import { useGet } from "../../hooks/useGet";
import { usePost } from "../../hooks/usePost";
import { ButtonSubmit } from "../ButtonSubmit/ButtonSubmit";
import { Input } from "../Input/Input";

export const CrearMovimiento = () => {
  const { user } = useAuth();
  const [duiCuenta, setDuiCuenta] = useState("");
  const { data, getData } = useGet();
  const { postData, errorMessage, isLoading, isSuccess } = usePost();
  const [movimiento, setMovimiento] = useState({
    cuenta: {
      id_cuenta: null,
    },
    tipo: null,
    monto: 0,
  });

  useEffect(() => {
    if (duiCuenta.length === 10) {
      getData("cuentas/dui/" + duiCuenta);
    }
  }, [duiCuenta]);

  useEffect(() => {
    console.clear();
    console.log(movimiento);
  }, [movimiento]);

  const handleCrearMovimiento = () => postData("movimientos", movimiento);

  if (!user) {
    return (
      <div className="wrapper">
        <p>Debe iniciar sesión para hacer movimientos</p>
      </div>
    );
  }

  return (
    <div className="wrapper">
      <div className={style["crear-movimiento-panel"]}>
        <h3>Realizar un movimiento</h3>

        <Input
          type="text"
          placeholder="Ej: 12345678-9"
          label="DUI:"
          value={duiCuenta}
          onChange={(e) => setDuiCuenta(e.target.value)}
        />

        <div className={style['contenedor-select']}> 
          <label className={style['label-select']}>Cuentas asociadas:</label>
          <select
            value={movimiento.cuenta.id_cuenta}
            onChange={(e) =>
              setMovimiento({
                ...movimiento,
                cuenta: {
                  id_cuenta: parseInt(e.target.value),
                },
              })
            }
          >
             <option value="">Seleccione una cuenta</option>
            {data.length > 0 && data ? (
              data.map((cuenta) => (
                <option value={cuenta.id_cuenta}>{cuenta.numero_cuenta}</option>
              ))
            ) : (
              <option value="">No se han encontrado cuentas</option>
            )}
          </select>
        </div>

        <div className={style['contenedor-select']}>
          <label className={style['label-select']}>Tipo de movimiento</label>
          <select
            value={movimiento.tipo}
            onChange={(e) =>
              setMovimiento({ ...movimiento, tipo: e.target.value })
            }
          >
            <option value="">Seleccione un tipo</option>
            <option value="DEPOSITO">DEPOSITO</option>
            <option value="RETIRO">RETIRO</option>
          </select>
        </div>

        <Input
          type="number"
          placeholder="0.00"
          label="Monto:"
          value={movimiento.monto}
          onChange={(e) =>
              setMovimiento({ ...movimiento, monto: parseInt(e.target.value) })}
        />

        <ButtonSubmit
          buttonText="Realizar moviemiento"
          exitoMensaje="Movimiento realizado"
          handleEvent={handleCrearMovimiento}
          isLoading={isLoading}
          isSuccess={isSuccess}
          errorMessage={errorMessage}
        />
      </div>
    </div>
  );
};
