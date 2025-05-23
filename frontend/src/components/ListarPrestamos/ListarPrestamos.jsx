import React, { useEffect, useState } from "react";
import { useAuth } from "../../context/AuthContext";
import style from "./ListarPrestamos.module.css";
import { useGet } from "../../hooks/useGet";

export const ListarPrestamos = () => {
  const { user } = useAuth();
  const { data, getData } = useGet();
  const [error, setError] = useState(null);

  useEffect(() => {
    if (user?.id) {
      getData("prestamos/pendientes");
    }
  }, [user?.id]);

  const handleCambiarEstado = async (id, nuevoEstado) => {
    setError(null);
    try {
      const response = await fetch(
        `http://localhost:8080/api/prestamos/${id}/estado?nuevoEstado=${nuevoEstado}`,
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
      await getData("prestamos/pendientes");
    } catch (err) {
      console.error("Error al cambiar estado:", err);
      setError(err.message || "Error al cambiar estado");
    }
  };

  if (!user?.id) {
    return (
      <div className={style.message}>Debe estar logeado para realizar esto</div>
    );
  }

  return (
    <div className="wrapper">
      <div className={style["lista-prestamos"]}>
        {error && <div className={style.error}>{error}</div>}

        {data.length > 0 ? (
          data.map((prestamo) => (
            <div key={prestamo.id_prestamo} className={style.prestamo}>
              <div className={style["prestamo-info"]}>
                <div className={style["prestamo-seccion"]}>
                  <label>Cliente: </label>
                  <p>{prestamo.usuario.nombre}</p>
                </div>
                <div className={style["prestamo-seccion"]}>
                  <label>DUI: </label>
                  <p>{prestamo.usuario.dui}</p>
                </div>
                <div className={style["prestamo-seccion"]}>
                  <label>Cajero: </label>
                  <p>{prestamo.cajero.nombre}</p>
                </div>
                <div className={style["prestamo-seccion"]}>
                  <label>Monto:</label>
                  <p>${prestamo.monto_solicitado?.toLocaleString()}</p>
                </div>
                <div className={style["prestamo-seccion"]}>
                  <label>Cuota:</label>
                  <p>${prestamo.cuota_mensual?.toFixed(2)}</p>
                </div>
                <div className={style["prestamo-seccion"]}>
                  <label>Plazo: </label>
                  <p>{prestamo.plazo_anios} años</p>
                </div>
                <div className={style["prestamo-seccion"]}>
                  <label>Tasa interés: </label>
                  <p>{(prestamo.tasa_interes * 100)?.toFixed(2)}%</p>
                </div>
              </div>
              <div className={style["prestamo-acciones"]}>
                <button
                  className={style["aceptar-btn"]}
                  onClick={() =>
                    handleCambiarEstado(prestamo.id_prestamo, "APROBADO")
                  }
                >
                  "Aprobar"
                </button>
                <button
                  className={style["rechazar-btn"]}
                  onClick={() =>
                    handleCambiarEstado(prestamo.id_prestamo, "RECHAZADO")
                  }
                >
                  Rechazar
                </button>
              </div>
            </div>
          ))
        ) : (
          <div className={style.message}>No hay préstamos pendientes</div>
        )}
      </div>
    </div>
  );
};
