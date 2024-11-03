import React, { useState } from "react";
import brPokemonLogo from "./../assets/brPokemonLogo.png";
import "./../App.css";
import SearchBox from "./SearchBox";
import Swal from "sweetalert2";
import { useNavigate } from "react-router-dom";

const PokedexHeader = ({ onSearch, setTotalItems, fetchPokemonData }) => {
  const [authToken, setAuthToken] = useState(localStorage.getItem("authToken"));
  const navigate = useNavigate();
  
  const logout = () => {
    Swal.fire({
      title: 'Are you sure you want to logout?',
      text: "You will need to log in again to access your account.",
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Yes, logout!',
      cancelButtonText: 'No, stay logged in'
    }).then((result) => {
      if (result.isConfirmed) {
        localStorage.removeItem("authToken");
        window.location.reload();
      }
    });
  } ;

  return (
    <div className="Header d-flex flex-column align-items-center bg-danger p-3">
      <div className="d-flex align-items-center mb-2">
        <img src={brPokemonLogo} alt="Logo Pokémon" className="me-2 logo-img" />
        <span className="fs-1 fw-bold pokedex-title">Pokedéx</span>
      </div>

      <div className="d-flex justify-content-center mb-2">
        {authToken ? (
          <>
            <button className="btn btn-primary btn-sm me-2" onClick={() => window.location.href = '/account'}>My Account</button>
            <button className="btn btn-primary btn-sm" onClick={logout}>Logout</button>
          </>
        ) : (
          <>
            <button className="btn btn-light btn-sm me-2" onClick={() => window.location.href = '/register'}>
              Register
            </button>
            <button className="btn btn-light btn-sm" onClick={() => window.location.href = '/login'}>
              Login
            </button>
          </>
        )}
      </div>

      <SearchBox onSearch={onSearch} setTotalItems={setTotalItems} fetchPokemonData={fetchPokemonData} />
    </div>
  );
};

export default PokedexHeader;
