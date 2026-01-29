import http from 'http';
import Koa from 'koa';
import WebSocket from 'ws';
import Router from 'koa-router';
import bodyParser from "koa-bodyparser";
import cors from '@koa/cors';
import { initializeWebSocketServer } from './wss.js';
import { carRouter } from './car.js';
import { authRouter } from './auth.js';

import { jwtAuth } from './utils.js';

const app = new Koa();
const server = http.createServer(app.callback());
const webSocketServer = new WebSocket.Server({ server});
initializeWebSocketServer(webSocketServer);

app.use(cors({
    origin: '*',
    allowMethods: ['GET', 'POST', 'PUT', 'DELETE', 'OPTIONS'],
    allowHeaders: ['Content-Type', 'Authorization']
}));
app.use(bodyParser());

const publicApiRouter = new Router();
publicApiRouter.prefix('/api');
publicApiRouter
    .use('/auth', authRouter.routes());
app
    .use(publicApiRouter.routes())
    .use(publicApiRouter.allowedMethods());

const protectedApiRouter = new Router();
protectedApiRouter.prefix('/api');
protectedApiRouter
    .use('/cars', jwtAuth, carRouter.routes());

app
    .use(protectedApiRouter.routes())
    .use(protectedApiRouter.allowedMethods());


server.listen(3000, () => {
    console.log('Server started on port 3000');
});