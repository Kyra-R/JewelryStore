import '../styles/App.css';
import '../styles/body.css';
import Chat from "./Chat";
import '../css_files/gallery_product.css';
import logo from '../logo.svg';
import pic1 from '../images/ring1.jpg';
import { useState } from "react";
import ListJewelryComponent from './ListJewelry';
import React, {useEffect} from 'react'



function Body() {
	
	
  const [filter, setFilter] = useState("NONE");
  const [type, setType] = useState("NONE");
  
  const increaseType = () => {
	  if(type === "FIRST"){
		  setType("SECOND");
	  } else {
		  setType("FIRST");
	  }
	  console.log(type);
  }

  const handleFilterChange = (e) => {
    setFilter(e.target.value);
    setType("NONE");
  };
  
   const handleTypeChange = (e) => {
    setType(e.target.value);
  };
  
  
	const [firstFilter, setFirstFilter] = useState("NONE");
	const [secondFilter, setSecondFilter] = useState("NONE");
	const [jewelryType, setJewelryType] = useState("NONE");
	const [material, setMaterial] = useState("NONE");
	const [price, setPrice] = useState("NONE");
  
  
    const handleFirstFilterChange = (e) => {
    setFirstFilter(e.target.value);
	if(!(material === "NONE" && jewelryType === "NONE") ){
		increaseType();
	}
    setJewelryType("NONE");
	setMaterial("NONE");
  };
  
   const handleMaterialChange = (e) => {
    setMaterial(e.target.value);
	increaseType();
  };
  
  
   const handleJewelryTypeChange = (e) => {
    setJewelryType(e.target.value);
	increaseType();
  };
  
  
  
    const handleSecondFilterChange = (e) => {
    setSecondFilter(e.target.value);
	if(price !== "NONE"){
	  increaseType();
      setPrice("NONE");
	}

  };
  
   const handlePriceChange = (e) => {
    setPrice(e.target.value);
	increaseType();
  };
  

  
  const [country, setCountry] = useState("");
  const [state, setState] = useState("");

  const handleCountryChange = (e) => {
    setCountry(e.target.value);
    setState("");
  };
	
    return (
	 <div>
		<Chat />
	
        <div class='body_gallery'>
		
		
		
		<div class="dropdown_container">
	<div class="dropdown">
      <select onChange={handleFirstFilterChange} value={firstFilter}>
        <option value="NONE">Выбрать фильтр</option>
        <option value="filtring">Фильтры</option>

      </select>
      <select onChange={handleJewelryTypeChange} value={jewelryType} disabled={firstFilter === "NONE"}>
        <option value="NONE">Выбрать тип</option>
        {firstFilter === "filtring" && (
          <><option value="RING">Кольца</option>
		  <option value="SIGNET">Печатки</option>
		  <option value="CHAIN">Цепочки</option>
		  <option value="PENDANT">Подвески</option>
		  <option value="EARRING">Серьги</option>
		  <option value="BRACELET">Браслеты</option></>
        )}
      </select>
	  <select onChange={handleMaterialChange} value={material} disabled={firstFilter === "NONE"}>
        <option value="NONE">Выбрать материал</option>
        {firstFilter === "filtring" && (
          <><option value="GOLD_YELLOW">Желтое золото</option>
		  <option value="GOLD_WHITE">Белое золото</option>
		  <option value="SILVER">Серебро</option>
		  <option value="COPPER">Медь</option></>
        )}

      </select>
	  
    </div>
		<div class="dropdown">
      <select onChange={handleSecondFilterChange} value={secondFilter}>
        <option value="NONE">Выбрать ценовой диапазон</option>
        <option value="price">Цена</option>

      </select>
      <select onChange={handlePriceChange} value={price} disabled={secondFilter === "NONE"}>
        <option value="NONE">Выбрать опции</option>
        {secondFilter === "price" && (
          <><option value="1">Менее 10 000</option>
		  <option value="2">10-50 тысяч</option>
		  <option value="3">50-100 тысяч</option>
		  <option value="4">Более 100 000</option></>
        )}
      </select>
    </div>
	
    </div>
		
		
		
  
     <div class="main_prod">
	 
	 <ListJewelryComponent filter={filter} filterChoice={type} filterChoiceMaterial={material} filterChoiceJewelryType={jewelryType} filterChoicePrice={price}/> 
      

			
        </div>
		 </div>
		 </div>
		 
    );
}


export default Body;
