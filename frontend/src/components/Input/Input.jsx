import React from "react";
import style from "./Input.module.css";

export function Input({ placeholder, label, type, value, onChange }) {
  return (
    <div className={style["contenedor-input"]}>
      <p className={style["label-input"]}>{label}</p>
        <input
          type={type}
          placeholder={placeholder}
          value={value}
          onChange={onChange}
        />
    </div>
  );
}
