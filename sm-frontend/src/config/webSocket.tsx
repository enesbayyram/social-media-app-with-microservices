import SockJS from "sockjs-client";
import Stomp, { Client } from 'stompjs';
import storageService from "../services/StorageService";
import { ACCESS_TOKEN } from "../constants/GlobalConstants";


const API_URL = "http://api.enesbayram.com:30080";

export const connectWebSocket = (path: string): Promise<Client> => {
    return new Promise((resolve, reject) => {
        const socket = new SockJS(`${API_URL}${path}`);
        const client = Stomp.over(socket);

        client.connect(
            { Authorization: `Bearer ${storageService.get(ACCESS_TOKEN)}` },
            () => {
                console.log(`[WebSocket] Connected to ${path}`);
                resolve(client);
            },
            (error) => {
                console.error(`[WebSocket] Connection error (${path}):`, error);
                reject(error);
            }
        );
    });
};

export const listenChannel = (client: Client, channelName: string, onMessageFunc: (messages: any) => void): void => {
    if (client && client.connected) {
        client.subscribe(channelName, (messages) => {
            onMessageFunc(messages.body);
        });
    } else {
        console.warn(`[WebSocket] Not connected, cannot subscribe: ${channelName}`);
    }
};

export const sendMessage = (client: Client, destination: string, payload: any): void => {
    if (client && client.connected) {
        client.send(destination, {}, JSON.stringify(payload));
    } else {
        console.warn(`[WebSocket] Not connected, cannot send to: ${destination}`);
    }
};