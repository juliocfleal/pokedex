import React from "react";
import brPokemonLogo from "./../assets/brPokemonLogo.png";
import "./../App.css";
import SearchBox from "./SearchBox";

const PokedexHeader = ({ onSearch, setTotalItems, fetchPokemonData }) => {
  return (
    <div className="Header d-flex flex-column flex-md-row align-items-center justify-content-between p-3 bg-danger">
      <div className="d-none d-md-flex align-items-center mb-3 mb-md-0">
        <img src={brPokemonLogo} alt="Logo Pokémon" className="me-2 logo-img" />
        <span className="fs-1 fw-bold pokedex-title">Pokedéx</span>
      </div>
      <SearchBox onSearch={onSearch} setTotalItems={setTotalItems} fetchPokemonData={fetchPokemonData} />
    </div>
  );
};

export default PokedexHeader;
