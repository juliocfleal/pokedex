import React, { useState } from "react";
import "./../App.css";
import "./../SearchBox.css";
import axios from 'axios';
import Swal from "sweetalert2";

const SearchBox = ({ onSearch, setTotalItems, fetchPokemonData }) => {
  const [name, setName] = useState("");
  const [type, setType] = useState("");
  const [habitat, setHabitat] = useState("");
  const pokemonTypes = [
    "normal", "fighting", "flying", "poison", "ground",
    "rock", "bug", "ghost", "steel", "fire",
    "water", "grass", "electric", "psychic", "ice",
    "dragon", "dark", "fairy", "stellar", "unknown"
  ];
  const pokemonHabitats = [
    "cave", "forest", "grassland", "mountain", "rare",
    "rough-terrain", "sea", "urban", "waters-edge"
  ];

  const handleSearch = () => {
    onSearch({ name, type, habitat });

  };

  return (
    <div className="search-box">
      <input
        type="text"
        placeholder="Enter Pokémon name"
        className="search-input"
        value={name}
        onChange={(e) => setName(e.target.value)}
      />
<select
        className="search-select"
        value={type}
        onChange={(e) => setType(e.target.value)}
      >
        <option value="">Select Type</option>
        {pokemonTypes.map((type, index) => (
          <option key={index} value={type}>
            {type.charAt(0).toUpperCase() + type.slice(1)}
          </option>
        ))}
      </select>
      <select
        className="search-select"
        value={habitat}
        onChange={(e) => setHabitat(e.target.value)}
      >
        <option value="">Select Habitat</option>
        {pokemonHabitats.map((habitat, index) => (
          <option key={index} value={habitat}>
            {habitat.split('-').map(word => word.charAt(0).toUpperCase() + word.slice(1)).join(' ')}
          </option>
        ))}
      </select>
      <button className="search-button" onClick={handleSearch}>
        Search
      </button>
    </div>
  );
};

export default SearchBox;
