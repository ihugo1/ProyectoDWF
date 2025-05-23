import { useState } from "react";
import { useAuth } from "../context/AuthContext";

export const usePost = () => {
  const { user } = useAuth();
  const [errorMessage, setErrorMessage] = useState(null);
  const [isLoading, setIsLoading] = useState(false);
  const [isSuccess, setIsSuccess] = useState(false);

  const postData = async (endpoint, body) => {
    setErrorMessage(null);
    setIsLoading(true);
    setIsSuccess(false);

    try {
      const response = await fetch("http://localhost:8080/api/" + endpoint, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${user?.token}`, // Envía tu token simple
        },
        body: JSON.stringify(body),
      });

      const textResponse = await response.text();
      const data = textResponse ? JSON.parse(textResponse) : null;

      if (!response.ok) {
        const message =
          typeof data === "object"
            ? data?.message || JSON.stringify(data)
            : data || "Error en la solicitud";

        setErrorMessage(message);
        throw new Error(message);
      }

      setIsSuccess(true);
      return data;
    } catch (error) {
      if (!errorMessage) {
        setErrorMessage(
          error.message.includes("Failed to fetch")
            ? "Error de conexión"
            : error.message
        );
      }
      throw error;
    } finally {
      setIsLoading(false);
    }
  };

  return {
    postData,
    isLoading,
    isSuccess,
    errorMessage,
    clearError: () => setErrorMessage(null),
  };
};
