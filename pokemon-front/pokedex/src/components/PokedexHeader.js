import React, { useState } from "react";
import brPokemonLogo from "./../assets/brPokemonLogo.png";
import "./../App.css";
import SearchBox from "./SearchBox";

const PokedexHeader = ({ onSearch, setTotalItems, fetchPokemonData }) => {
  const [authToken, setAuthToken] = useState(localStorage.getItem("authToken"));

  const logout = () => {
    localStorage.removeItem("authToken");
    setAuthToken(null);
  };

  return (
    <div className="Header d-flex flex-column align-items-center bg-danger p-3">
      <div className="d-flex align-items-center mb-2">
        <img src={brPokemonLogo} alt="Logo Pokémon" className="me-2 logo-img" />
        <span className="fs-1 fw-bold pokedex-title">Pokedéx</span>
      </div>

      <div className="d-flex justify-content-center mb-2">
        {authToken ? (
          <>
            <button className="btn btn-primary btn-sm me-2">My Account</button>
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
