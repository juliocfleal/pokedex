import React from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import axios from 'axios';
import Swal from "sweetalert2";

const PokemonTable = ({ data, currentPage, setCurrentPage, totalItems, itemsPerPage, fetchPokemonData}) => {
  const pokemonList = data.pokemons || [];
  const totalPages = Math.ceil(totalItems / itemsPerPage);


  const showPokemon = (id) => {
    axios.get(process.env.REACT_APP_API_BASE_URL + "/pokemon/getbyid/" + id)
    .then(response => {
      const { name, imageUrl, abilities, baseExperience, height, type } = response.data;

      const abilitiesText = abilities ? abilities.join(", ") : "N/A";
      const typesText = type ? type.join(", ") : "N/A";
      const htmlContent = `
        <strong>Abilities:</strong> ${abilitiesText} <br>
        <strong>Base Experience:</strong> ${baseExperience} <br>
        <strong>Height:</strong> ${height} <br>
        <strong>Type:</strong> ${typesText}
      `;
  
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
  
  }

  const handlePageChange = (newPage) => {
    if (newPage >= 1 && newPage <= totalPages) {
        fetchPokemonData(newPage);
      setCurrentPage(newPage);
    }
  };

  return (
    <div className="container mt-4">
      <table className="table table-bordered table-striped">
        <thead className="thead-dark">
          <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Type</th>
            <th>Habitat</th>
          </tr>
        </thead>
        <tbody>
          {pokemonList.length > 0 ? (
            pokemonList.map((pokemon, index) => (
                <tr 
                key={index} 
                onClick={() => showPokemon(pokemon.id)}
                style={{ cursor: 'pointer' }}>
                <td>{pokemon.id}</td>
                <td>{pokemon.name}</td>
                <td>{pokemon.type.join(", ")}</td>
                <td>{pokemon.habitat}</td>
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

      <div className="pagination-controls d-flex justify-content-center mt-4">
        <button 
          className="btn btn-secondary mx-1"
          onClick={() => handlePageChange(currentPage - 1)}
          disabled={currentPage === 1}
        >
          Previous
        </button>
        {[...Array(totalPages)].map((_, index) => (
          <button 
            key={index}
            className={`btn mx-1 ${currentPage === index + 1 ? 'btn-primary' : 'btn-outline-primary'}`}
            onClick={() => handlePageChange(index + 1)}
          >
            {index + 1}
          </button>
        ))}
        <button 
          className="btn btn-secondary mx-1"
          onClick={() => handlePageChange(currentPage + 1)}
          disabled={currentPage === totalPages}
        >
          Next
        </button>
      </div>
    </div>
  );
};

export default PokemonTable;
