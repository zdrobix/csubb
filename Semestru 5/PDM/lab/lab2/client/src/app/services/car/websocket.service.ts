import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';     

@Injectable({
    providedIn: 'root'
})
export class WebSocketService {
    private socket!: WebSocket;
    public messages$?: Subject<any>;

    connect(url: string, token: string) {
        this.socket = new WebSocket(url);

        this.socket.onopen = () => {
            console.log('WebSocket connection established');
            this.socket.send(JSON.stringify({ type: 'authorization', payload: token }));
        }

        this.socket.onmessage = (event) => {
            const data = JSON.parse(event.data);
            this.messages$?.next(data);
        };

        this.socket.onerror = (error) => {
            console.error('WebSocket error:', error);
        };
        this.socket.onclose = () => {
            console.log('WebSocket connection closed');
        };
    } 

    disconnect() {
        if (this.socket) {
            this.socket.close();
        }
    }
}
