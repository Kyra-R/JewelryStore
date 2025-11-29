import React, {useEffect, useState} from 'react'
import '../styles/App.css';
import {listJewelry} from '../service/JewelryService'
import {listJewelryByType} from '../service/JewelryService'
import {listJewelryByMaterial} from '../service/JewelryService'
import {listJewelryByTypeAndMaterial} from '../service/JewelryService'
import {getJewelryPic} from '../service/JewelryService'
import {addJewelry} from '../service/CartService'
import pic1 from '../images/ring1.jpg';
import "../css_files/LoginStyle.css";
import { useNavigate } from "react-router-dom";


const ListJewelryComponent = (props) => {
	
		const navigate = useNavigate();
		
		const [jewelryAll, setJewelry] = useState([])
		
		const [mapReady, setMapReady] = useState(true)
		
		const [prevProps, setPrevProps] = useState("FIRST")
		
		const [res, setRes] = useState()
		
		
		
		
		const [stateList, setListState] = useState({
			data: {},
			isLoading: false,
		});
		var picData = [];
		let response;
		let jewelry_response;
		
		
		
		const filterPrice = async (jewelry) => {
			console.log("filtering");
			let response;
			if(props.filterChoicePrice === "NONE"){
			response = await jewelry; 
			} else
			if(props.filterChoicePrice === "1"){
			response = await jewelry.filter((item) => item.cost < 10000); 
			} else
			if(props.filterChoicePrice === "2"){
			response = await jewelry.filter((item) => ((item.cost >= 10000) && (item.cost < 50000))); 
			} else
			if(props.filterChoicePrice === "3"){
			response = await jewelry.filter((item) => ((item.cost >= 50000) && (item.cost < 100000))); 
			} else
			if(props.filterChoicePrice === "4"){
			response = await jewelry.filter((item) => item.cost >= 100000); 
			}
			return response;
		}
		
 	
		useEffect(() => {
		
		if(prevProps !== props.filterChoice){
			setListState({isLoading: true,})
			setPrevProps(props.filterChoice);
		}
		
		const getDataWrapper = async () => {
			if(props.filterChoiceJewelryType !== "NONE" && props.filterChoiceMaterial === "NONE"){
				jewelry_response = await listJewelryByType(props.filterChoiceJewelryType )
			} else if(props.filterChoiceJewelryType === "NONE" && props.filterChoiceMaterial !== "NONE"){
				jewelry_response = await listJewelryByMaterial(props.filterChoiceMaterial)
			} else if(props.filterChoiceJewelryType !== "NONE" && props.filterChoiceMaterial !== "NONE"){
				jewelry_response = await listJewelryByTypeAndMaterial(props.filterChoiceJewelryType, props.filterChoiceMaterial)
			} else {
				jewelry_response = await listJewelry()
			}
			
		if(jewelry_response) {
			
			setJewelry(await filterPrice(jewelry_response.data));

			response = await Promise.all(jewelry_response.data.map(
				async jewelry => {
					console.log(jewelry);
					console.log(getJewelryPic(jewelry.jewelryId));
					return getJewelryPic(jewelry.jewelryId)})
			)

			if(response){
				setListState({data:response, isLoading: false,});
			}		
		}
		};	
			getDataWrapper();	
	},[props.filterChoice, stateList.isLoading])
	
	
	const addItem = (jewelryId, amount) => {
		const userActive = localStorage.getItem('jwt-token');
		if(userActive){
			if(amount > 0){
			addJewelry(jewelryId);
			} else {
				alert("Jewelry is out of stock");
			}
		} else {
			 navigate("/login");
		}
	};
	
	
const [imageSrc, setImageSrc] = useState('');




	
	if (stateList.isLoading) {
		return(<div> Loading...  </div>)
		
	} else {
		return(
	      jewelryAll.map((jewelry, index) => <div key={jewelry.jewelryId} class="gallery_prod">  

<img src={`data:image/png;base64,${stateList.data[index]}`}/>

	
		<p> {jewelry.name} </p>
		<p> Цена: {jewelry.cost}, в наличии: {jewelry.count} </p>
		            <div class="inputBx">
						<input onClick={() => addItem(jewelry.jewelryId, jewelry.count)} class="buyButton" type="submit" value="Купить!" />
					</div>
		</div>
		)
		)
	}
		
}

export default ListJewelryComponent