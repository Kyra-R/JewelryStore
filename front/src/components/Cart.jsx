import '../css_files/gallery_product.css';

import logo from '../logo.svg';
import pic1 from '../images/ring1.jpg';
import { useState } from "react";
import ListCartItemsComponent from './ListCartItems';
import React, {useEffect} from 'react'
import {statsJewelry} from '../service/CartService'
import {orderJewelry} from '../service/CartService'
import '../css_files/gallery_product.css';
import { useNavigate } from "react-router-dom";



function Body() {
	const [totalPrice, setTotalPrice] = useState(["0"]);
	const [loginInto, setLoginInto] = useState(["Войдите в аккаунт"]);
	const [userActive, setUserActive] = useState([""]);
	const navigate = useNavigate();

useEffect(() => {
   const userActive = localStorage.getItem('jwt-token');
   console.log(userActive);
   
   
   
   
   
   
   		if(userActive) {
			setLoginInto("")
			console.log("no null");
			const element2 = document.getElementById("comment_body");
			if(element2){
				element2.remove();
			}
			statsJewelry().then((response) => {
			setTotalPrice(response.data.totalCost);
			}).catch(error => {
			console.error(error);
			})
		} else {
			setLoginInto("Войдите в аккаунт")
			const element1 = document.getElementById("cart_body");
			if(element1){
				element1.remove();
			}
		}
   
}, []);

const deleteAllItems = () => {
const userActive = localStorage.getItem('jwt-token');
if(userActive){
	if(totalPrice !== 0){
		alert("Ordering!");
	}
    setTotalPrice("0");
	orderJewelry();
}
};

const setOneDeletedPrice = (price) => {
	const userActive = localStorage.getItem('jwt-token');
	if(userActive){
		setLoginInto("")
		statsJewelry().then((response) => {
			if(response.data.totalCost - price >= 0) {
			setTotalPrice(response.data.totalCost - price);
			} else {
			setTotalPrice(0);
			}
		}).catch(error => {
			console.error(error);
		})
	} else {
		setLoginInto("Войдите в аккаунт")
	}
};

const handleClick = () => {
    navigate("/login");
  }
	
    return (
	<div>
		<div id="comment_body" class="comment_pos">	
			<div class="comment" onClick={handleClick}>{loginInto}</div>
		</div>
	<div>
     <div id="cart_body" class='body_gallery'> 
	 
			<div>Итоговая цена: {totalPrice}</div>
	 <div>...</div>
	 <input onClick={() => deleteAllItems()} type="submit" value="Заказать" />
	 
     <div class="main_prod">

            <ListCartItemsComponent totalPrice={totalPrice}  setOneDeletedPrice={setOneDeletedPrice}  /> 

			
        </div>
		 </div>
		 </div>
		  </div>
    );
}


export default Body;
