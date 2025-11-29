import '../styles/App.css';
import '../styles/body.css';
import logo from '../logo.svg';
import "../css_files/LoginStyle.css";
import React, {useEffect, useState} from 'react'
import loadMyScript from './script';
import { useNavigate } from "react-router-dom";

function Body(props) {
	
const [userActive, setUserActive] = useState(["Войти"]);
const navigate = useNavigate();
	
useEffect(() => {
  const script = document.createElement('script');
  
  
  const userActive = localStorage.getItem('jwt-token');
  if(userActive){
	  setUserActive("Выйти")
	  const element1 = document.getElementById("login_username")
	  const element2 = document.getElementById("login_password")
	  const element3 = document.getElementById("register_p")
	  if(element1 && element2 && element3){
	  element1.remove();
	  element2.remove();
	  element3.remove();
	}
  } else {
	  const element4 = document.getElementById("secret_p")
	   if(element4){
		   element4.remove();
	   }
  }

  script.src = "./script";
  script.type = "text/babel";
  script.async = true;

  document.body.appendChild(script);

  return () => {
    document.body.removeChild(script);
  }
}, []);
	
	const secretMessage = () => {
		const userActive = localStorage.getItem('jwt-token');
		if(userActive){
			navigate("/secret");
		} 
	};
	
    return (
	   
        <div className='body'>
		
            
  
   <div class="register-section">

    <div class="box">
	



        <div class="form">
		<div class="secret_div" onClick={() => secretMessage()}><p id="secret_p" >Тык</p></div>

          <h2>Вход в аккаунт</h2>
          <form action="">
            <div class="inputBx">
              <input id="login_username" type="text" placeholder="Login" required="required" />
            </div>
            <div class="inputBx password">
              <input id="login_password" type="password" placeholder="Password" required="required"/>
            </div>
            <div class="inputBx">
              <input class="login_button" type="submit" value={userActive} />
            </div>
          </form>
          <p id="register_p">
          Нет активного аккаунта? <a href="/register">Регистрация</a></p>
		  
        </div>


    </div>
  </div>

			
        </div>
    );
}


export default Body;
