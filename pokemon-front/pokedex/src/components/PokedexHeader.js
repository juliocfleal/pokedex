import React from "react";
import brPokemonLogo from "./../assets/brPokemonLogo.png";
import "./../App.css";
import SearchBox from "./SearchBox";

const PokedexHeader = () => {
  return (
    <div className="Header">
      <div className="LogoHeader" >
        <img src={brPokemonLogo} alt="Logo Pokémon" />
      </div>
      <SearchBox/>
    </div>
  );
};

export default PokedexHeader;