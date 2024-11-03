import React, { useState, useEffect } from 'react';
import './App.css';
import PokedexHeader from './components/PokedexHeader';
import PokemonTable from './components/PokemonTable';
import axios from 'axios';
import Swal from "sweetalert2";
import UserLogin from './components/UserLogin';
import UserRegister from './components/UserRegister';

function App() {
  const [pokemonData, setPokemonData] = useState([]);
  const [currentPage, setCurrentPage] = useState(1);
  const [totalItems, setTotalItems] = useState(0);
  const [isSearchActive, setIsSearchActive] = useState(false);
  const [searchParams, setSearchParams] = useState({});
  const itemsPerPage = 10;

  const fetchPokemonData = async (page) => {
    try {
      const baseUrl = process.env.REACT_APP_API_BASE_URL;
      let params = `?page=${page - 1}&size=${itemsPerPage}`;
      if (isSearchActive) {
        if (searchParams.name) params += `&name=${searchParams.name}`;
        if (searchParams.type) params += `&type=${searchParams.type}`;
        if (searchParams.habitat) params += `&habitat=${searchParams.habitat}`;

        const response = await axios.get(`${baseUrl}/pokemon/filter${params}`);
        setPokemonData(response.data.pokemons);
        setTotalItems(response.data.pagedDetails.totalPokemons);
      } else {
        const response = await axios.get(`${baseUrl}/pokemon${params}`);
        setPokemonData(response.data.pokemons);
        setTotalItems(response.data.pagedDetails.totalPokemons);
      }
    } catch (error) {
      Swal.fire({
        icon: 'error',
        title: 'Oops...',
        text: 'An error has occurred!',
      });
      console.error('Error fetching data:', error);
    }
  };

  useEffect(() => {
    if (isSearchActive || Object.keys(searchParams).length === 0) {
      fetchPokemonData(1);
    }
  }, [isSearchActive, searchParams]);

  const handleSearch = (params) => {
    if (params.name === "" && params.type === "" && params.habitat === "") {
      setIsSearchActive(false);
      setSearchParams({});
    } else {
      setIsSearchActive(true);
      setSearchParams(params);
    }
    setCurrentPage(1);
  };


  const handlePageChange = (page) => {
    setCurrentPage(page);
    fetchPokemonData(page);
  };

  useEffect(() => {
    fetchPokemonData(1);
  }, []);

  return (
    <>
      <header className="Header">
        <PokedexHeader onSearch={handleSearch} />
      </header>
      <div className="App">
        {/* <UserRegister/> */}
        {/* <UserLogin/> */}
        <PokemonTable
          data={pokemonData}
          currentPage={currentPage}
          setCurrentPage={setCurrentPage}
          totalItems={totalItems}
          itemsPerPage={itemsPerPage}
          fetchPokemonData={handlePageChange}
        />
      </div>
    </>
  );
}

export default App;
