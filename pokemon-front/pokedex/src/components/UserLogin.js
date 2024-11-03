import React , {useState}from "react";
import "./../App.css";
import axios from 'axios';
import Swal from "sweetalert2";
import "./../App.css";
import { useNavigate } from "react-router-dom";
import UserHeader from "./UserHeader";

const UserLogin = ({ onSearch, setTotalItems, fetchPokemonData }) => {

    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const navigate = useNavigate();

    const handleSubmit = (e) => {
      e.preventDefault();

      axios.post(process.env.REACT_APP_API_BASE_URL + "/auth/login",{
        "email":email,
        "password":password
      })
      .then(response => {

        const token = response.data?.password;
        if (token) {
          localStorage.setItem("authToken", token);
          Swal.fire({
            icon: 'success',
            title: 'Logged in!',
            text: 'You have successfully logged in.',
          }).then(navigate("/"));
        } else {
          Swal.fire({
            icon: 'warning',
            title: 'Login failed',
            text: 'No token returned. Please check your credentials.',
          });
        }
      })
      .catch(error => {
        Swal.fire({
          icon: 'error',
          title: 'Oops...',
          text: 'An error has occurred! Please try again.',
        });
        console.error("Error during login:", error);
      });
    };

  
    return (
      <>
        <UserHeader/>
      <div className="d-flex justify-content-center align-items-center vh-100">
        <div className="card p-4 shadow-lg bg-danger" style={{ width: '100%', maxWidth: '400px' }}>
          <h2 className="text-center mb-4">Login</h2>
          <form onSubmit={handleSubmit}>
          <div className="mb-3">
            <label htmlFor="email" className="form-label">Email</label>
            <input 
              type="email" 
              className="form-control" 
              id="email" 
              placeholder="Enter your email" 
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              required 
            />
          </div>
          <div className="mb-3">
            <label htmlFor="password" className="form-label">Password</label>
            <input 
              type="password" 
              className="form-control" 
              id="password" 
              placeholder="Enter your password" 
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required 
            />
          </div>
            <button type="submit" className="search-button w-100 mt-3">Login</button>
          </form>
        </div>
      </div>
      </>
    );
  
};

export default UserLogin;