import '../styles/App.css';
import '../styles/body.css';
import logo from '../logo.svg';
import "../css_files/LoginStyle.css";
import React, {useEffect, useState} from 'react'
import {getMessage} from '../service/SecretService'
import {createMessage} from '../service/SecretService'
import { useNavigate } from "react-router-dom";



function Body(props) {
	
const [userActive, setUserActive] = useState(["Войти"]);
const [message, setMessage] = useState([""]);
const navigate = useNavigate();
let response;
		const [stateList, setListState] = useState({
			data: "",
			isLoading: false,
		});


	
useEffect(() => {
  const script = document.createElement('script');
  

  
  const getDataWrapper = async () => {
	  let response = await getMessage();
	  console.log("async mes:" + response.data);
	  if(response){
		  
				console.log("setstate: " + response.data.message);
				setListState({data:response.data.message, isLoading: true,});

		}
  }
  getDataWrapper();

  
  const userActive = localStorage.getItem('jwt-token');


  script.src = "./script";
  script.type = "text/babel";
  script.async = true;

  document.body.appendChild(script);

  return () => {
    document.body.removeChild(script);
  }
}, [stateList.isLoading]);
	
	const handleClick = () => {
		if(message === "Мудро" || message === "мудро"){
			alert("Не в этом смысле");
		} else {
		console.log("MESSAGE "+ message)
		createMessage(message);
		navigate("/login");
		}
	};
	


	
    if(stateList.data === "NULL"){return (
	   
        <div className='body'>
		
            
  
   <div class="register-section">

    <div class="box">
	



        <div class="form">
		

		<h3>Вы можете отправить тайное послание владельцам сайта!</h3>
		<div class="descr">
		<p> Например, рассказать Ваши впечатления от наших товаров. </p>
		<p> Но выбирайте мудро, ведь у Вас лишь одна попытка... </p>
		</div>
          <form action="">
            <div class="inputBx">
              <input value={message} onChange={e => setMessage(e.target.value)} id="secretMessage" type="text" placeholder="Мудро..." required="required" />
            </div>
			<div onClick={() => handleClick()}>  <input class="sumbit_message" type="submit" value="Готово" /> </div>
          </form>
		  
        </div>


    </div>
  </div>

			
        </div>
    );
} else { return (
 <div className='body'>
		
            
  
   <div class="register-section">

    <div class="box">
	        <div class="form">
		

		<h3>Вы уже отправляли сообщение!</h3>
		<div class="descr">
		<p> Вот оно: {stateList.data} </p> 
		</div>
          </div>
		  
        </div>


    </div>
  </div>

)};
}


export default Body;
