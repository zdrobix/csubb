import Router from 'koa-router';
import jwt from 'jsonwebtoken';
import DataStore from 'nedb-promise';
import { jwtConfiguration } from './utils.js';


export class UserStore {
    constructor({ filename, autoload }) {
        this.store = DataStore({ filename, autoload });
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

const userStore = new UserStore({ filename: './data/Users.json', autoload: true });

const createToken = (user) => {
    return jwt.sign({ username: user.username, id:user.id }, jwtConfiguration.secret, { expiresIn: 60 * 60 * 60});
};

export const authRouter = new Router();


authRouter.post('/login', async (ctx) => {
    const credentials = ctx.request.body;
    const user = await userStore.findOne({ username: credentials.username });

    if (user && credentials.password === user.password) {
        ctx.response.body = { token: createToken(user ) };
        ctx.response.status = 201;
    } else {
        ctx.response.body = { error: 'Invalid credentials.' };
        ctx.response.status = 400;
    }
});