import Router from 'koa-router';
import dataStore from 'nedb-promise';

export class CarStore {
    constructor({ filename, autoload }) {
        this.store = dataStore({ filename, autoload });
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
        if (!car.id || !car.name || !car.registration_number) {
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

const carStore = new CarStore({ filename: './data/Cars.json', autoload: true });

export const carRouter = new Router();

carRouter.get('/', async ctx => {
    ctx.body = await carStore.findAll();
    ctx.response.status = 200;
});

