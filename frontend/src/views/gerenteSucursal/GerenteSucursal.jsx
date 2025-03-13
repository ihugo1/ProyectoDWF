import React from "react";
import style from "./GerenteSucursal.module.css";

export const GerenteSucursal = () => {
  return (
    <div className={style["gerente-sucursal"]}>
      <div className={style["contenedor-izquierdo"]}>
        {/*Agregar Empleado*/}
        <div className="wrapper">
          <div className={style["agregar-empleado"]}>
            <p className={style["titulo"]}>Agregar empleado:</p>
            <div className={style["seccion"]}>
              <p className={style["label"]}>Nombre:</p>
              <input type="text" id="nombre" />
            </div>
            <div className={style["seccion"]}>
              <p className={style["label"]}>Rol:</p>
              <select className={style["seccion"]}>
                <option value="">Desplegar lista</option>
              </select>
            </div>
          </div>
        </div>
        {/*Gestionar Empleados*/}
        <div className="wrapper">
          <div className={style["gestion-empleados"]}>
            <p className={style["titulo"]}>Gestionar empleados</p>
            <input
              type="text"
              placeholder="buscar"
              className={style["buscar-empleado"]}
            />
            <div className={style["lista-empleados"]}>
              <div className={style["empleado"]}>
                <p className={style["nombre"]}>Nombre</p>
                <p className={style["rol"]}>Rol</p>
                <button className={style["dar-baja-btn"]}>Dar de baja</button>
              </div>
              <div className={style["empleado"]}>
                <p className={style["nombre"]}>Nombre</p>
                <p className={style["rol"]}>Rol</p>
                <button className={style["dar-baja-btn"]}>Dar de baja</button>
              </div>
              <div className={style["empleado"]}>
                <p className={style["nombre"]}>Nombre</p>
                <p className={style["rol"]}>Rol</p>
                <button className={style["dar-baja-btn"]}>Dar de baja</button>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div className={style["contenedor-derecho"]}>
        {/*Gestionar creditos*/}
        <div className="wrapper">
          <div className={style['panel-creditos']}>
            <p className={style['titulo']}>Administrador de créditos</p>
            <div className={style['lista-creditos']}>
              <div className={style['credito']}>
                <p className={style['info']}>Info del credito</p>
                <button className={style['aceptar-btn']}>Aceptar</button>
                <button className={style['rechazar-btn']}>Rechazar</button>
              </div>
              <div className={style['credito']}>
                <p className={style['info']}>Info del credito</p>
                <button className={style['aceptar-btn']}>Aceptar</button>
                <button className={style['rechazar-btn']}>Rechazar</button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};
