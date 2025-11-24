import Router from 'koa-router';
import DataStore from '@seald-io/nedb';

import { fileURLToPath } from 'url';
import { dirname, join } from 'path';
import { type } from 'os';
import { randomInt } from 'crypto';
const __filename = fileURLToPath(import.meta.url);
const __dirname = dirname(__filename);

export class CarStore {
    constructor({ filename, autoload }) {
        this.store = new DataStore({ filename, autoload });
    }

    async find(properties) {
        return this.store.find(properties);
    }

    async findOne(properties) {
        return this.store.findOne(properties);
    }

    async findAll() {
        return this.store.find({});
    }

    async insert(car) {
        if ( !car.name || !car.registration_number) {
            throw new Error('Invalid car data');
        }
        return this.store.insert(car);
    }

    async update(properties, car) {
        return this.store.update(properties, car);
    }

    async remove(properties) {
        return this.store.remove(properties);
    }
}

const carStore = new CarStore({ filename: join(__dirname, 'data', 'Cars.db'), autoload: true });

export const carRouter = new Router();

carRouter.get('/', async ctx => {
    ctx.body = await carStore.findAll();
    ctx.response.status = 200;
});

carRouter.get('/:id_user', async ctx => {
    const id = parseInt(ctx.params.id_user);
    const cars = await carStore.find({ id_user: id });
    ctx.body = cars;
    ctx.response.status = 200;
});

carRouter.post('/', async ctx => {
    const newCar = ctx.request.body;
    console.log('Received new car data:', newCar);
    if ( !newCar.name || !newCar.registration_number) {
        ctx.response.status = 400;
        ctx.body = { error: 'Invalid car data' };
        return;
    } 

    const insertedCar = await carStore.insert(newCar);
    BroadcastChannel({type: 'car_added', payload: insertedCar});

    ctx.response.status = 201;
    ctx.body = insertedCar;
});



