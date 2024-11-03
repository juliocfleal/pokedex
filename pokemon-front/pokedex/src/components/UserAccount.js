import React, { useState, useEffect } from 'react';
import axios from 'axios';
import "./../App.css";
import Swal from "sweetalert2";
import { useNavigate } from "react-router-dom";
import UserHeader from './UserHeader';

const UserAccount = () => {
    const [authToken] = useState(localStorage.getItem("authToken"));
    const [userDetail, setUserDetails] = useState(null);
    const navigate = useNavigate();

    const removePokemonFromUser = (id) =>{
        axios
            .delete(`${process.env.REACT_APP_API_BASE_URL}/user/removePokemon/` + id, {
                headers: {
                    Authorization: `Bearer ${authToken}`,
                },
            })
            .then((response) => {
                fetchUserDetails();
                Swal.fire({
                    icon: 'success',
                    title: 'Success!',
                    text: "Pokemon is deleted sucessfully",
                  });
            })
            .catch((error) => {
                console.error("Error fetching user details:", error);
                Swal.fire({
                    icon: 'error',
                    title: 'Error',
                    text: 'Failed to delete user pokemon. Please try again.',
                });
            });
    };

    const removeUserAccount = () =>{


        Swal.fire({
            title: 'Are you sure you want to delete your account?',
            text: "Your account will be deleted definitly.",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Yes, delete!',
            cancelButtonText: 'No, cancel!'
          }).then((result) => {
            if (result.isConfirmed) {
                axios
                .delete(`${process.env.REACT_APP_API_BASE_URL}/user/removeUser`, {
                    headers: {
                        Authorization: `Bearer ${authToken}`,
                    },
                })
                .then((response) => {
                    localStorage.removeItem("authToken");
                    Swal.fire({
                        icon: 'success',
                        title: 'Success!',
                        text: "User is deleted sucessfully",
                      });
                      navigate('/');

                })
                .catch((error) => {
                    console.error("Error fetching user details:", error);
                    Swal.fire({
                        icon: 'error',
                        title: 'Error',
                        text: 'Failed to delete user. Please try again.',
                    });
                });
            }
          });
    };

    const fetchUserDetails = () =>{
        axios
            .get(`${process.env.REACT_APP_API_BASE_URL}/user/userDetails`, {
                headers: {
                    Authorization: `Bearer ${authToken}`,
                },
            })
            .then((response) => {
                setUserDetails(response.data);
            })
            .catch((error) => {
                console.error("Error fetching user details:", error);
                Swal.fire({
                    icon: 'error',
                    title: 'Error',
                    text: 'Failed to load user details. Please try again.',
                });
                if (error.response && error.response.status === 401) {
                    localStorage.removeItem("authToken");
                    navigate('/');
                }
            });
    };

    useEffect(() => {
        if (!authToken) {
            Swal.fire({
                icon: 'warning',
                title: 'Session Expired',
                text: 'Please log in to access your account.',
            }).then(() => navigate('/'));
            return;
        }
        fetchUserDetails();

    }, [authToken, navigate]);

    return (
        <>
        <UserHeader/>
            <div className="d-flex flex-column align-items-center p-3">
                {userDetail ? (
                    <>
                        <h2 className="text mt-3">Welcome, {userDetail.user.name}!</h2>
                        <p className="text">Email: {userDetail.user.email}</p>
                        <button
                                                    type="button"
                                                    className="btn btn-danger btn-sm"
                                                    onClick={(e) => {
                                                        e.stopPropagation();
                                                        removeUserAccount();
                                                    }}
                                                >
                                                    Delete my account
                                                </button>
                        <div className="pokemon-list mt-4">
                            <h3 className="text mb-3">Your Pokémons</h3>
                            {userDetail.pokemons && userDetail.pokemons.length > 0 ? (
                                <ul className="list-group">
                                    {userDetail.pokemons.map((pokemon, index) => (
                                        <li key={index} className="list-group-item d-flex align-items-center">
                                            <img
                                                src={pokemon.thumbnailUrl}
                                                alt={pokemon.name}
                                                className="me-3 rounded"
                                                style={{ width: "50px", height: "50px" }}
                                            />
                                            <div>
                                                <h5 className="mb-1">{pokemon.name}</h5>
                                                <p className="mb-1">Habitat: {pokemon.habitat}</p>
                                                <p className="mb-0">
                                                    Type: {pokemon.type.join(", ")}
                                                </p>
                                                <button
                                                    type="button"
                                                    className="btn btn-danger btn-sm"
                                                    onClick={(e) => {
                                                        e.stopPropagation();
                                                        removePokemonFromUser(pokemon.id);
                                                    }}
                                                >
                                                    Remove this Pokémon
                                                </button>
                                            </div>
                                        </li>
                                    ))}
                                </ul>
                            ) : (
                                <p className="text">You don't have any Pokémon yet.</p>
                            )}
                        </div>
                    </>
                ) : (
                    <p className="text-white">Loading user details...</p>
                )}

            </div>
        </>
    );
};

export default UserAccount;
