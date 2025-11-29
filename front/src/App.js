
import Body from './components/Body';
import Login from './components/Login';
import Gallery from './components/Gallery';
import Cart from './components/Cart';
import Register from './components/Register';
import Secret from './components/Secret';
import pic from './pic.svg';
import {BrowserRouter, Routes, Route} from 'react-router-dom';

import React, {useEffect, useState} from 'react'
import "./css_files/grid_form.css";
import "./css_files/grid_additions.css";
import "./css_files/gallery.css";

function App() {
	
	
	
const [welcome, setWelcome] = useState(["гость"]);

useEffect(() => {
  console.log("guest");
  setWelcome("гость");
  const welcome = localStorage.getItem('name');
  if (welcome) {
   setWelcome(welcome);
  }
}, []);
	
	
  return (
  
  

    <div className="App">
	
	
	<div class="grid navigation">
        <div class="nav block-1-1">
            <div class="btn"><a href="/gallery">Товары</a></div>
        </div>
        <div class="nav block-2-1">
             <div class="btn"><a href="/">Главная</a></div>
        </div>
        <div class="nav block-3-1">
             <div class="btn"><a href="/cart">Корзина</a></div>
        </div>
        <div class="nav block-4-1">
             <div class="btn"><a href="/login">Профиль</a></div>
        </div>
    </div>
	
     <div class="header header"><p>Ювелирный магазин MagName</p><div class="welcome"> Приветствуем, {welcome}!</div></div>
		
	<BrowserRouter>
	
	
		<Routes> 
		<Route exact path="/" element = {<Body />} />
		<Route exact path="/login" element = {<Login />} />
		<Route exact path="/register" element = {<Register />} />
		<Route exact path="/gallery" element = {<Gallery />} />
		<Route exact path="/cart" element = {<Cart />} />
		<Route exact path="/secret" element = {<Secret />} />
		
		</Routes>
	</BrowserRouter>
	 
     
    </div>
  );
}

export default App;
