import React, { useState, useEffect } from 'react';
import './App.css';
import PokedexHeader from './components/PokedexHeader';
import PokemonTable from './components/PokemonTable';
import axios from 'axios';


function App() {
  const [pokemonData, setPokemonData] = useState([]);
  const [currentPage, setCurrentPage] = useState(1);
  const [totalItems, setTotalItems] = useState(0);
  const itemsPerPage = 10;

  const fetchPokemonData = async (page) => {
    try {
      page = page != "" ? page -1 : 0;
      const response = await axios.get(process.env.REACT_APP_API_BASE_URL + '/pokemon?size' + itemsPerPage + "&&page=" + page);
      setPokemonData(response.data);
      setTotalItems(response.data.pagedDetails.totalPokemons);
    } catch (error) {
      console.error('Error fetching data:', error);
    }
  };

  useEffect(() => {
    fetchPokemonData(1);
  }, []);

  const handlePokemonData = (data) => {
    setPokemonData(data);
  };

  return (
    <>
      <header className="Header">
        <PokedexHeader onSearch={handlePokemonData}
          setTotalItems={setTotalItems} 
          fetchPokemonData={fetchPokemonData}
          />
      </header>
      <div className="App">
        <PokemonTable data={pokemonData}
          currentPage={currentPage}
          setCurrentPage={setCurrentPage}
          totalItems={totalItems}
          itemsPerPage={itemsPerPage}
          fetchPokemonData={fetchPokemonData}
        />
      </div>
    </>
  );
}

export default App;
