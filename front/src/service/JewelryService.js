import { Buffer } from 'buffer'
import axios from "axios";

const REST_API_BASE_URL = 'http://localhost:8080/jewelry'
export const listJewelry = async () => {
	return await axios.get(REST_API_BASE_URL);
}

export const listJewelryByType = async (type) => {
	return await axios.get('http://localhost:8080/jewelry/type/'+type);
}

export const listJewelryByMaterial = async (material) => {
	return await axios.get('http://localhost:8080/jewelry/material/'+material);
}

export const listJewelryByTypeAndMaterial = async (type, material) => {
	return await axios.get('http://localhost:8080/jewelry/both/' + type + '/' + material);
}

export const getJewelryPic = async (id) => {

	const data = await axios.get('http://localhost:8080/jewelry/getFile/'+id, { responseType: 'arraybuffer' }).then(response => Buffer.from(response.data, 'binary').toString('base64'));

	return data;
}
