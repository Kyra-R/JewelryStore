import '../styles/App.css';
import '../styles/body.css';
import logo from '../logo.svg';
import "../css_files/LoginStyle.css";
import React, {useEffect} from 'react'
import loadMyScript from './script';

function Body(props) {
	
useEffect(() => {
  const script = document.createElement('script');

  script.src = "./script";
  script.type = "text/babel";
  script.async = true;

  document.body.appendChild(script);

  return () => {
    document.body.removeChild(script);
  }
}, []);
	
    return (
        <div className='body'>
		
            
  
   <div class="register-section">

    <div class="box">




        <div class="form register">
          <h2>Регистрация</h2>
          <form action="">
            <div class="inputBx">
              <input id="register_username" placeholder="Login" required="required" />
            </div>
			<div class="inputBx">
              <input id="register_name" placeholder="Name" required="required" />
            </div>
            <div class="inputBx password">
              <input id="register_password" placeholder="Password" name="password" required="required"/>
            </div>
			<div class="inputBx">
              <input id="register_email" placeholder="Email" type="email" name="email" required="required"/>
            </div>
            <div class="inputBx">
              <input class="register_button" type="submit" value="ОК" />
            </div>
          </form>
          <p>
          </p>
        </div>


    </div>
  </div>

			
        </div>
    );
}


export default Body;
