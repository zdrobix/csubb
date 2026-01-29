import WebSocket from "ws";
import jwt from "jsonwebtoken";
import { jwtConfiguration } from "./utils.js";

let webSocketServer;

export const initializeWebSocketServer = value => {
    webSocketServer = value;
    webSocketServer.on('connection', socket => {
        socket.on('message', message => {
            const {type, payload: {token}} = JSON.parse(message);
            if (type !== 'authorization')
            {
                socket.close();
                return
            }
            try {
                socket.user = jwt.verify(token, jwtConfiguration.secret);
            } catch (e) {
                socket.close();
            }
        });
    });
}

export const broadcast = (userId, data) => {
    if (!webSocketServer) return;
    webSocketServer.clients.forEach(client => {
        if (client.readyState === WebSocket.OPEN && client.id === userId) {
            console.log('broadcast to ', userId);
            client.send(JSON.stringify(
                data
            ));
        }
    });
}