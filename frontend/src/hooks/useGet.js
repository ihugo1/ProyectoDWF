import { useState } from "react";

export const useGet = () => {
  const [data, setData] = useState({});

  const getData = async (endpoint) => {
    try {
      const respuesta = await fetch("http://localhost:8080/api/" + endpoint, {
        method: "GET",
        headers: { "Content-Type": "application/json" },
      });

      if (respuesta.ok) {
        const json = await respuesta.json();
        setData(json);
      } else {
        console.error("Error inesperado:", respuesta.status);
      }
    } catch (error) {
      console.error("Error de red:", error);
    }
  };

  return { getData, data };
};

