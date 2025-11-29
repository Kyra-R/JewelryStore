import React, { useState } from "react";
import '../css_files/chat.css';
import { sendChatMessage } from "../service/LLMService";

export default function ChatWidget() {
    const [open, setOpen] = useState(false);
    const [messages, setMessages] = useState([]);
    const [input, setInput] = useState("");

    const toggleOpen = () => setOpen(!open);

    const send = async () => {
        if (!input.trim()) return;

        const userMessage = { sender: "user", text: input };
        setMessages(prev => [...prev, userMessage]);

        setInput("");

        const reply = (await sendChatMessage(input)).data;

        const botMessage = { sender: "bot", text: reply };
        setMessages(prev => [...prev, botMessage]);
    };

    return (
        <>
           
            <button className="chat-button" onClick={toggleOpen}>💬</button>

            
            {open && (
                <div className="chat-window">
                    <div className="chat-messages">
                        {messages.map((msg, i) => (
                            <div
                                key={i}
                                className={msg.sender === "user" ? "msg user" : "msg bot"}
                            >
                                {msg.text}
                            </div>
                        ))}
                    </div>

                    <div className="chat-input">
                        <input className="chat-input-base"
                            value={input}
                            onChange={e => setInput(e.target.value)}
                            placeholder="Задайте боту вопрос..."
                        />
                        <button onClick={send}>▶</button>
                    </div>
                </div>
            )}
        </>
    );
}