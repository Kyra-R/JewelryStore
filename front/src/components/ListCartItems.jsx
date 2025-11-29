import React, {useEffect, useState} from 'react'
import '../styles/App.css';
import {listJewelryCart} from '../service/CartService'
import {deleteJewelryById} from '../service/CartService'
import {getJewelryPic} from '../service/JewelryService'
import {statsJewelry} from '../service/CartService'
import pic1 from '../images/ring1.jpg';
import "../css_files/LoginStyle.css";
const ListCartItemsComponent = (props, {setOneDeletedPrice}) => {
	

		const [jewelryCartAll, setJewelryCart] = useState([])
		const [countMoneyEqual, setCountMoneyEqual] = useState(true)
		
		const [totalPrice, setTotalPrice] = useState(["0"]);
		
		const userActive = localStorage.getItem('jwt-token');
		let response;
		let jewelry_response;
		
		const [stateList, setListState] = useState({
			data: {},
			isLoading: false,
		});
		
		

 	
		useEffect(() => { 
		if(countMoneyEqual === false){
			setCountMoneyEqual(true);
		}

if(userActive !== null){	
if(props.totalPrice !== "0") {	
	setListState({isLoading: true,})
	
	const getDataWrapper = async () => {
		console.log("going into async----------------begin");
		if(props.totalPrice !== "0") {
			
			
			jewelry_response = await listJewelryCart();
		
		if(jewelry_response) {
			setJewelryCart(jewelry_response.data);
			
			
			console.log("inside picfinder-------------------");
			response = await Promise.all(jewelry_response.data.map(
				async jewelryCart => {
					return getJewelryPic(jewelryCart.jewelry.jewelryId)})
			)

			if(response){
				setListState({data:response, isLoading: false,});
			}
			
			console.log("going from async------------------end");		
		}
		
		
		
		} else if (props.totalPrice === "0"){
			setJewelryCart([]);
		}

		};

		
			getDataWrapper();
} else if (props.totalPrice === "0"){
	console.log("empty");
			setJewelryCart([]);
		}
}

			statsJewelry().then((response) => {
			setTotalPrice(response.data.totalCost);
			}).catch(error => {
			console.error(error);
			})
			
			if(totalPrice!== props.totalPrice){
				console.log("whoops");
				setCountMoneyEqual(false);
			}
	
	},[props, countMoneyEqual, totalPrice])
	
	const setRerender = () => {
		console.log(totalPrice);
		console.log(props.totalPrice);
		window.location.reload();
	};
	
	const deleteItem = (jewelryId) => {
		console.log("delete start");
		console.log(totalPrice);
		const newjewelryCartAll = jewelryCartAll.filter((item) => item.jewelryId !== jewelryId); 
		const deletedItem = jewelryCartAll.find((item) => item.jewelryId === jewelryId);
		console.log("del " + deletedItem.jewelry.cost);
		const price = totalPrice - deletedItem.jewelry.cost;
		console.log("tot - del " + price);
		setTotalPrice(price);
		console.log(totalPrice);
		setJewelryCart(newjewelryCartAll);
		deleteJewelryById(jewelryId);
		console.log("delete end");
		};
		
		
	
	if(stateList.isLoading) {
		return(<div> </div>)
		
	} else if(props.totalPrice[0] !== totalPrice[0]){
		return (<div>{setRerender()}</div>)
	} 
	else if(jewelryCartAll.length > 0){
			return(
	      jewelryCartAll.map((jewelryCart, index) => <div key={jewelryCart.jewelry.jewelryId} class="gallery_prod">  
				<img src={`data:image/png;base64,${stateList.data[index]}`}/>

	
		<p> {jewelryCart.jewelry.name}, в наличии: {jewelryCart.jewelry.count} </p>
		<p> Цена: {jewelryCart.jewelry.cost} </p>
					<div class="inputBx">
						<input onClick={() => {deleteItem(jewelryCart.jewelryId); props.setOneDeletedPrice(jewelryCart.jewelry.cost);} } class="delete_button" type="submit" value="Удалить" />
					</div>
		</div>
		)
		)
	} 
		
}
export default ListCartItemsComponent

