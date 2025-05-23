import { BrowserRouter as Router, Routes, Route, Navigate } from "react-router-dom";
import { AuthProvider } from "./context/AuthContext";
import { Cliente } from "./views/cliente/Cliente";
import { Cajero } from "./views/cajero/Cajero";
import { Dependiente } from "./views/dependiente/Dependiente";
import { GerenteSucursal } from "./views/gerenteSucursal/GerenteSucursal";
import { GerenteGeneral } from "./views/gerenteGeneral/GerenteGeneral";
import { LoginPage } from "./views/LoginPage/LoginPage";
import { Header } from "./components/Header/Header";

export function App() {
  return (
    <Router>
      <AuthProvider>
        <Header/>
        <Routes>
          {/* Ruta pública */}
          <Route path="/login" element={<LoginPage />} />
          <Route path="/cliente" element={<Cliente />} />
          <Route path="/cajero" element={<Cajero />} />
          <Route path="/dependiente" element={<Dependiente />} />
          <Route path="/gerentesucursal" element={<GerenteSucursal />} />
          <Route path="/gerentegeneral" element={<GerenteGeneral />} />
          <Route path="/" element={<Navigate to="/login" />} />
        </Routes>
      </AuthProvider>
    </Router>
  );
}
