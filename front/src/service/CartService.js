import axios from "axios";
let token = localStorage.getItem("jwt-token")

const REST_API_BASE_URL = 'http://localhost:8080/cart_jewelry/all'

let axiosConfig = {
  headers: {
      'Content-Type': 'application/json;charset=UTF-8',
  }
}

export const listJewelryCart =  async () => {
	
	return await axios.get(REST_API_BASE_URL, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
}

export const statsJewelry = () => {
	
	return axios.get('http://localhost:8080/cart_jewelry/stats', {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
}

export const addJewelry = (jewelryId) => {
	console.log(jewelryId + " add");
	
	
	return axios({
        method: 'POST',
        url: 'http://localhost:8080/cart_jewelry/'+jewelryId+'/1',
        headers: {
          Authorization: `Bearer ${token}`
        },
        data: ""
      })
	
	return axios.post('http://localhost:8080/cart_jewelry/'+jewelryId+'/1',{
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
}

export const orderJewelry = () => {
	console.log("posting");
	
	return axios.delete('http://localhost:8080/cart_jewelry/make', {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
}

export const deleteJewelryById = (id) => {
	return axios.delete('http://localhost:8080/cart_jewelry/'+id, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
}

