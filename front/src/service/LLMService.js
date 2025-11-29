import axios from "axios";

const CHAT_API_URL = "http://localhost:8080/llm/ask";


export const sendChatMessage = (question) => {
  return axios.post(
    "http://localhost:8080/llm/ask?text=" + question
  );
};