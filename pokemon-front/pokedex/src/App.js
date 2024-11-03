import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Login from './components/UserLogin';
import Register from './components/UserRegister';
import Home from './components/Home';
import UserAccount from './components/UserAccount';

const App = () => {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route path="/account" element={<UserAccount />} />
      </Routes>
    </Router>
  );
};

export default App;
