import * as api from "../api";

import {ActionType} from 'redux-promise-middleware';
const AUTHENTICATE = "AUTHENTICATE";
export const AUTHENTICATE_PENDING = `${AUTHENTICATE}_${ActionType.Pending}`;
export const AUTHENTICATE_FULFILLED = `${AUTHENTICATE}_${ActionType.Fulfilled}`;
export const AUTHENTICATE_REJECTED = `${AUTHENTICATE}_${ActionType.Rejected}`;
const DEPOSIT = "DEPOSIT";
export const DEPOSIT_PENDING = `${DEPOSIT}_${ActionType.Pending}`;
export const DEPOSIT_FULFILLED = `${DEPOSIT}_${ActionType.Fulfilled}`;
export const DEPOSIT_REJECTED = `${DEPOSIT}_${ActionType.Rejected}`;

export const LOG_OUT = "LOG_OUT";

export function authenticate(username, password) {
    return {
        type: AUTHENTICATE,
        payload: api.authenticate(username, password)
    }
}

export function deposit(userId, euros) {
    return {
        type: DEPOSIT,
        payload: api.deposit(userId, euros)
    }
}

export function logOut() {
    return {
        type: LOG_OUT,
    }
}