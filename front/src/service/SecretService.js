import { Buffer } from 'buffer'
import axios from "axios";
let token = localStorage.getItem("jwt-token")

const REST_API_BASE_URL = 'http://localhost:8080/secret'

export const getMessage = async () => {
	return await axios.get(REST_API_BASE_URL, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
}

export const createMessage = (secretMessage) => {
	
	return axios({
        method: 'POST',
        url: 'http://localhost:8080/secret',
        headers: {
          Authorization: `Bearer ${token}`,
		  "Content-Type": "application/json"
        },
        data:{
			message: secretMessage,
		}
      })
}
