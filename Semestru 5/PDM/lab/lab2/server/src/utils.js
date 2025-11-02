import jwt from 'jsonwebtoken';

export const jwtConfiguration = { secret: 'my-secret'};

export const jwtAuth = async (ctx, next) => {
    const authHeader = ctx.headers['authorization'];
    if (!authHeader) {
        ctx.status = 401;
        ctx.body = { message: 'Authorization header missing' };
        return;
    }

    const token = authHeader.split(' ')[1];
    if (!token) {
        ctx.status = 401;
        ctx.body = { message: 'Token missing' };
        return;
    }

    try {
        const decoded = jwt.verify(token, jwtConfiguration.secret);
        ctx.state.user = decoded;
        await next();
    } catch (err) {
        ctx.status = 403;
        ctx.body = { message: 'Invalid token' };
    }
};
