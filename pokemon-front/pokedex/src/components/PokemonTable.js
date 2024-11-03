import React from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import axios from 'axios';
import Swal from "sweetalert2";
import catchPokeball from "./../assets/catchPokemon.png";

const PokemonTable = ({ data, currentPage, setCurrentPage, totalItems, itemsPerPage, handlePageChange, fetchPokemonData }) => {
  const pokemonList = data || [];
  const totalPages = Math.ceil(totalItems / itemsPerPage);

  const showPokemon = (id) => {
    axios.get(process.env.REACT_APP_API_BASE_URL + "/pokemon/getbyid/" + id)
      .then(response => {
        const { name, imageUrl, abilities, baseExperience, height, type, trainerName} = response.data;

        const abilitiesText = abilities ? abilities.join(", ") : "N/A";
        const typesText = type ? type.join(", ") : "N/A";
        let htmlContent = `
          <strong>Abilities:</strong> ${abilitiesText} <br>
          <strong>Base Experience:</strong> ${baseExperience} <br>
          <strong>Height:</strong> ${height} <br>
          <strong>Type:</strong> ${typesText}
        `;
        if(trainerName != null){
          htmlContent = htmlContent + `<br><strong>Trainer:</strong> ${trainerName}`;
        }
        
        Swal.fire({
          title: name,
          html: htmlContent,
          imageUrl: imageUrl,
          imageWidth: 200,
          imageHeight: 200,
          showCloseButton: true,
          imageAlt: "Custom image"
        });
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

  const handleNewPageChange = (newPage) => {
    if (newPage >= 1 && newPage <= totalPages) {
      handlePageChange(newPage);
      setCurrentPage(newPage);
    }
  };

  const catchPokemon = (pokemonId, index) => {
    axios.post(
      `${process.env.REACT_APP_API_BASE_URL}/user/insertPokemonToUser`,
      { "pokemonId": pokemonId },
      {
        headers: {
          Authorization: `Bearer ${localStorage.getItem("authToken")}`,
        },
      }
    )
    .then(response => {
      Swal.fire({
        icon: 'success',
        title: 'Gotcha!',
        text: "You've caught the Pokémon!",
      });
      handleNewPageChange(index);
      console.log(response.data);
    })
    .catch(error => {
      Swal.fire({
        icon: 'error',
        title: 'Oops...',
        text: error.response.data.message,
      });
      handleNewPageChange(index);
      console.error("Error inserting Pokémon:", error);
    });    
    console.log(`Catching Pokémon with ID: ${pokemonId}`);
  };

  return (
    <div className="container mt-4">
      <table className="table table-bordered table-striped">
        <thead className="thead-dark">
          <tr>
            <th></th>
            <th>Name</th>
            <th>Type</th>
            <th>Habitat</th>
            <th>Trainer</th>
          </tr>
        </thead>
        <tbody>
          {pokemonList.length > 0 ? (
            pokemonList.map((pokemon, index) => (
              <tr 
                key={index} 
                onClick={() => showPokemon(pokemon.id)}
                style={{ cursor: 'pointer' }}>
                <td><img src={pokemon.thumbnailUrl} className="h-50"/></td>
                <td>{pokemon.name}</td>
                <td>{pokemon.type.join(", ")}</td>
                <td>{pokemon.habitat}</td>
                <td>
            {pokemon.nameTrainer ? (
              pokemon.nameTrainer
            ) : (
              ""
            )}
          </td>
          {
            localStorage.getItem("authToken") ?
            <td>            <button 
            type="button" 
            className="btn btn-outline-success btn-sm" 
            onClick={(e) => {
              e.stopPropagation();
              catchPokemon(pokemon.id, currentPage)
            }}
          >Catch!
                <img 
      src={catchPokeball} 
      alt="Pokeball" 
      style={{ width: "80px", height: "90px", marginLeft: "5px" }}
    />
          </button></td>:""
          }

              </tr>
            ))
          ) : (
            <tr>
              <td colSpan="4" className="text-center">
                No data available
              </td>
            </tr>
          )}
        </tbody>
      </table>

      <div className="pagination-controls mt-4">
        <button 
          className="btn mx-1"
          onClick={() => handleNewPageChange(currentPage - 1)}
          disabled={currentPage === 1}
        >
          <i className="bi bi-arrow-left"></i>
        </button>
        {[...Array(totalPages)].map((_, index) => (
          <button 
            key={index}
            className={`btn mx-1 page-btn ${currentPage === index + 1 ? 'active' : ''}`}
            onClick={() => handleNewPageChange(index + 1)}
          >
            {index + 1}
          </button>
        ))}
        <button 
          className="btn mx-1"
          onClick={() => handleNewPageChange(currentPage + 1)}
          disabled={currentPage === totalPages}
        >
          <i className="bi bi-arrow-right"></i> 
        </button>
      </div>
    </div>
  );
};

export default PokemonTable;
