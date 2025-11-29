/* global $ */
import $ from 'jquery';
$(document).ready(function () {
    $(".login_button").click((event) => {
        event.preventDefault();
		console.log("click");
		const userActive = localStorage.getItem('jwt-token');
		console.log(userActive);
		if(userActive !== null){
			localStorage.clear();
			window.location.href = "../";
		} else {
		
        $.ajax({
            url: 'https://security-jewelry.onrender.com/auth/login',
            type: 'POST',
            data: JSON.stringify({
                login: $("#login_username").val(),
                password: $("#login_password").val()
            }),
            contentType: 'application/json; charset=utf-8',
            dataType: 'json',
            success: (data) => {
                if (data.hasOwnProperty('jwt-token')) {
					localStorage.clear()
                    localStorage.setItem('jwt-token', data["jwt-token"]);
					localStorage.setItem('name', data["name"]);
					alert('Success!');
					window.location.href = "../gallery";
                } else {
                    alert('Invalid credentials');
                }
            }
        });
		}
    });

    $(".register_button").click((event) => {
		
		
		
		console.log("register")
        event.preventDefault();
        $.ajax({
            url: 'https://security-jewelry.onrender.com/auth/registration',
            type: 'POST',
            data: JSON.stringify({
                login: $("#register_username").val(),
                name: $("#register_name").val(),
                email: $("#register_email").val(),
                password: $("#register_password").val()
            }),
            contentType: 'application/json; charset=utf-8',
            dataType: 'json',
            success: (data) => {
                if (data.hasOwnProperty('jwt-token')) {
					localStorage.clear()
                    localStorage.setItem('jwt-token', data["jwt-token"]);
					localStorage.setItem('name', data["name"]);
					alert('Success!');
					window.location.href = "../gallery";
                } else {
                    alert('Invalid credentials');
                }
            }
        });
		
    });


});
