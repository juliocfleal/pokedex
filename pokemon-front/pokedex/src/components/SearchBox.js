import React, { useState } from "react";
import "./../App.css";
import "./../SearchBox.css";
import axios from 'axios';
import Swal from "sweetalert2";

const SearchBox = () => {
  const [name, setName] = useState("");
  const [type, setType] = useState("");
  const [habitat, setHabitat] = useState("");

  const handleSearch = () => {
    console.log(`Searching for: Name = ${name}, Type = ${type}, Habitat = ${habitat}`);
        if(name == "" && type == "" && habitat == ""){
            Swal.fire({
                icon: 'warning',
                title: 'Oops...',
                text: 'Add any filter!',
              })
              return;
        }

        let params = "?";
        if (name !== "")  params += `name=${name}`;
        if (params !== "?" && type !== "")  params += `&&`;
        if (type !== "")  params += `type=${type}`;
        if (params !== "?" && habitat !== "") params += `&&`;
        if (habitat !== "") params += `habitat=${habitat}`;
        console.log(params );
        axios.get("http://localhost:8080/pokemon/filter" + params)
        .then(response => {
          console.log(response);
        })
        .catch(error => {
          Swal.fire({
            icon: 'error',
            title: 'Oops...',
            text: 'An error has occurred!',
          });
          console.log(error);
        });

      
    
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
        <option value="fire">Fire</option>
        <option value="water">Water</option>
        <option value="grass">Grass</option>
      </select>
      <select
        className="search-select"
        value={habitat}
        onChange={(e) => setHabitat(e.target.value)}
      >
        <option value="">Select Habitat</option>
        <option value="forest">Forest</option>
        <option value="cave">Cave</option>
        <option value="sea">Sea</option>
      </select>
      <button className="search-button" onClick={handleSearch}>
        Search
      </button>
    </div>
  );
};

export default SearchBox;
