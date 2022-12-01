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
import GestorPromocoes from './components/pages/GestorPromocoes';
import Promocoes from './components/pages/Promocoes';
import ConsultaPerfil from './components/pages/ConsultaPerfil';
import Historico from './components/pages/Historico';

function App() {
  /*Possible userStates:
    loggedOff: no current logged user
    gambler: gambler logged in
    expert: expert logged in
    admin: admin logged in
  */
  const [userState, setUserState] = useState('admin')
  const [userId, setUserId] = useState(1)

  return (
    <>
      <Router>
        <Navbar userState={userState} setUserState={setUserState} />
        <Routes>
          {/*Mutual pages*/}
          <Route path="/" element={<Sport sportType="any" userState={userState} userId={userId} />} />
          <Route path='/futebol' element={<Sport sportType="Football" userState={userState} userId={userId} />} />
          <Route path='/nba' element={<Sport sportType="NBA" userState={userState} userId={userId} />} />
          <Route path='/f1' element={<Sport sportType="F1" userState={userState} userId={userId} />} />
          <Route path='/nfl' element={<Sport sportType="NFL" userState={userState} userId={userId} />} />
          <Route path='/login' element={<Login setUserState={setUserState} setUserId={setUserId} />} />

          {/*TODO organizar o resto das pages*/}
          <Route path='/perfil' element={<Perfil userId={userId} />} />
          <Route path='/promocoes' element={<Promocoes userId={userId} />} />
          <Route path='/historico' element={<Historico />} />
          <Route path='/registo' element={<Registo userState={userState} expertMode="false" />} />

          {/*Admin TOOLS*/}
          <Route path='/admin_Options' element={<AdminMenu userState={userState} />} />
          <Route path='/admin_Options/registo_Expert' element={<Registo userState={userState} expertMode="true" />} />
          <Route path='/admin_Options/coins' element={<MenuCoins userState={userState} />} />
          <Route path='/admin_Options/promocoes' element={<GestorPromocoes userState={userState} />} />
          <Route path='/admin_Options/consultaPerfil' element={<ConsultaPerfil userState={userState} setUserState={setUserState} setUserId={setUserId} />} />

        </Routes>
      </Router>
    </>
  );
}

export default App;
