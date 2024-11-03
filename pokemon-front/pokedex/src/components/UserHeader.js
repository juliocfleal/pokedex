import React from "react";
import brPokemonLogo from "./../assets/brPokemonLogo.png";
import "./../App.css";
import SearchBox from "./SearchBox";
import { useNavigate } from "react-router-dom";

const UserHeader = ({ onSearch, setTotalItems, fetchPokemonData }) => {
  const navigate = useNavigate();

  return (
    <div className="Header d-flex flex-column align-items-center bg-danger p-3">
      <div className="d-flex align-items-center mb-2">
        <img src={brPokemonLogo} alt="Logo Pokémon" className="me-2 logo-img" />
        <span className="fs-1 fw-bold pokedex-title">Pokedéx</span>
      </div>
      <button
        className="btn btn-light btn-sm mb-2"
        onClick={() => navigate('/')}
        style={{ alignSelf: 'flex-start' }}
      >
        Back
      </button>
    </div>
  );
};

export default UserHeader;
