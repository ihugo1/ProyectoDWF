import { Header } from "./components/Header/Header";
import { Cliente } from "./views/cliente/Cliente";
import { Dependiente } from "./views/dependiente/Dependiente";
import { Empleado } from "./views/empleado/Empleado";
import { GerenteGeneral } from "./views/gerenteGeneral/GerenteGeneral";
import { GerenteSucursal } from "./views/gerenteSucursal/GerenteSucursal";


function App() {
  return (
    <>
      <Header />
      <GerenteGeneral />
    </>
  );
}

export default App;
