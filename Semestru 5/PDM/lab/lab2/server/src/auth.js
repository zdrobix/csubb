import Router from 'koa-router';
import jwt from 'jsonwebtoken';
import DataStore from '@seald-io/nedb';
import { jwtConfiguration } from './utils.js';

import { fileURLToPath } from 'url';
import { dirname, join } from 'path';
const __filename = fileURLToPath(import.meta.url);
const __dirname = dirname(__filename);


export class UserStore {
    constructor({ filename, autoload }) {
        this.store = new DataStore({ filename, autoload });
    }

    async findOne(properties) {
        return this.store.findOne(properties);
    }
    
    async test() {
        const users = await this.store.find({});
        console.log(users);
    }
    
    async insert(user) {
        if (!user.username || !user.password) {
            throw new Error('Invalid user data');
        }
        return this.store.insert(user);
    }
}


const userStore = new UserStore({ filename: join(__dirname, 'data', 'Users.db'), autoload: true });

const createToken = (user) => {
    return jwt.sign({ username: user.username, id:user._id }, jwtConfiguration.secret, { expiresIn: 60 * 60 * 60});
};

export const authRouter = new Router();


authRouter.post('/login', async (ctx) => {
    const credentials = ctx.request.body;
    const user = await userStore.findOne({ username: credentials.username });

    if (user && credentials.password === user.password) {
        ctx.response.body = { token: createToken(user ), user_id: user._id };
        ctx.response.status = 201;
    } else {
        ctx.response.body = { error: 'Invalid credentials.' };
        ctx.response.status = 400;
    }
});