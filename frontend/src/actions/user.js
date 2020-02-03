import * as api from "../api";

import {ActionType} from 'redux-promise-middleware';

const AUTHENTICATE = "AUTHENTICATE";
export const AUTHENTICATE_PENDING = `${AUTHENTICATE}_${ActionType.Pending}`;
export const AUTHENTICATE_FULFILLED = `${AUTHENTICATE}_${ActionType.Fulfilled}`;
export const AUTHENTICATE_REJECTED = `${AUTHENTICATE}_${ActionType.Rejected}`;
const GET_USER = "GET_USER";
export const GET_USER_PENDING = `${GET_USER}_${ActionType.Pending}`;
export const GET_USER_FULFILLED = `${GET_USER}_${ActionType.Fulfilled}`;
export const GET_USER_REJECTED = `${GET_USER}_${ActionType.Rejected}`;
const DEPOSIT = "DEPOSIT";
export const DEPOSIT_PENDING = `${DEPOSIT}_${ActionType.Pending}`;
export const DEPOSIT_FULFILLED = `${DEPOSIT}_${ActionType.Fulfilled}`;
export const DEPOSIT_REJECTED = `${DEPOSIT}_${ActionType.Rejected}`;
const PLACE_BET = "PLACE_BET";
export const PLACE_BET_PENDING = `${PLACE_BET}_${ActionType.Pending}`;
export const PLACE_BET_FULFILLED = `${PLACE_BET}_${ActionType.Fulfilled}`;
export const PLACE_BET_REJECTED = `${PLACE_BET}_${ActionType.Rejected}`;
const LOG_OUT = "LOG_OUT";
export const LOG_OUT_PENDING = `${LOG_OUT}_${ActionType.Pending}`;
export const LOG_OUT_FULFILLED = `${LOG_OUT}_${ActionType.Fulfilled}`;
export const LOG_OUT_REJECTED = `${LOG_OUT}_${ActionType.Rejected}`;

export function authenticate(username, password) {
    return {
        type: AUTHENTICATE,
        payload: api.authenticate(username, password)
    }
}

export function getUser(userId) {
    return {
        type: GET_USER,
        payload: api.getUser(userId)
    }
}

export function deposit(userId, euros) {
    return (dispatch) => {
        const response = dispatch(doDeposit(userId, euros));
        response.then(() => {
            dispatch(getUser(userId));
        })
    }
}

export function placeBet(userId, euros, odds) {
    return (dispatch) => {
        const response = dispatch(doPlaceBet(userId, euros, odds));
        response.then(() => {
            dispatch(getUser(userId));
        })
    }
}

export function logOut() {
    return {
        type: LOG_OUT,
        payload: api.logOut()
    }
}

function doDeposit(userId, euros) {
    return {
        type: DEPOSIT,
        payload: api.deposit(userId, euros)
    }
}

function doPlaceBet(userId, euros, odds) {
    return {
        type: PLACE_BET,
        payload: api.placeBet(userId, euros, odds)
    }
}