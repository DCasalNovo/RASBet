import React, { useState, useEffect } from 'react';
import Navbar from './components/Navbar';
import './App.css';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Login from './components/pages/Login';
import Perfil from './components/pages/Perfil';
import Registo from './components/pages/Registo';
import Sport from './components/pages/Sport';
import AdminMenu from './components/pages/AdminMenu';
import MenuCoins from './components/pages/MenuCoins';
import MenuPromocoes from './components/pages/MenuPromocoes';
import ConsultaPerfil from './components/pages/ConsultaPerfil';

function App() {
  /*Possible userStates:
    loggedOff: no current logged user
    gambler: gambler logged in
    expert: expert logged in
    admin: admin logged in
  */
  const [userState, setUserState] = useState('admin');


  return (
    <>
      <Router>
        <Navbar userState={userState} />
        <Routes>
          <Route path="/" element={<Sport sportType="any" userState={userState} />} />
          <Route path='/futebol' element={<Sport sportType="futebol" userState={userState} />} />
          <Route path='/basquetebol' element={<Sport sportType="basquetebol" userState={userState} />} />
          <Route path='/tenis' element={<Sport sportType="tenis" userState={userState} />} />
          <Route path='/motogp' element={<Sport sportType="motogp" userState={userState} />} />
          <Route path='/login' element={<Login />} />
          <Route path='/perfil' element={<Perfil />} />
          <Route path='/registo' element={<Registo userState={userState} expertMode="false" />} />
          <Route path='/admin_Options' element={<AdminMenu userState={userState} />} />
          <Route path='/admin_Options/registo_Expert' element={<Registo userState={userState} expertMode="true" />} />
          <Route path='/admin_Options/coins' element={<MenuCoins userState={userState} />} />
          <Route path='/admin_Options/promocoes' element={<MenuPromocoes userState={userState} />} />
          <Route path='/admin_Options/consultaPerfil' element={<ConsultaPerfil userState={userState} />} />
        </Routes>
      </Router>
    </>
  );
}

export default App;
