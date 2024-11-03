import React, { useState } from "react";
import "./../App.css";
import axios from 'axios';
import Swal from "sweetalert2";

const UserRegister = () => {
    const [name, setName] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');

    const handleSubmit = (e) => {
        e.preventDefault();
        axios.post(process.env.REACT_APP_API_BASE_URL + "/auth/register", {
            "name" : name,
            "email" : email,
            "password" : password
        })
        .then(response => {
            console.log(response);
            const token = response.data?.password;
            if (token) {
              localStorage.setItem("authToken", token);
            Swal.fire({
                icon: 'success',
                title: 'Registered!',
                text: 'Your account has been created successfully.',
            });
        }
        })
        .catch(error => {
            console.log(error.status);
            if(error.status == 409){
                Swal.fire({
                    icon: 'error',
                    title: 'Oops...',
                    text: 'The user with this email is already exist.',
                }); 
            }else{
                Swal.fire({
                    icon: 'error',
                    title: 'Oops...',
                    text: 'An error has occurred! Please try again.',
                });
            }


            console.error("Error during registration:", error);
        });
    };

    return (
        <div className="d-flex justify-content-center align-items-center vh-100">
            <div className="card p-4 shadow-lg bg-primary" style={{ width: '100%', maxWidth: '400px' }}>
                <h2 className="text-center mb-4">Register</h2>
                <form onSubmit={handleSubmit}>
                    <div className="mb-3">
                        <label htmlFor="name" className="form-label">Name</label>
                        <input 
                            type="text" 
                            className="form-control" 
                            id="name" 
                            placeholder="Enter your name" 
                            value={name}
                            onChange={(e) => setName(e.target.value)}
                            required 
                        />
                    </div>
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
                    <button type="submit" className="search-button w-100 mt-3">Register</button>
                </form>
            </div>
        </div>
    );
};

export default UserRegister;
