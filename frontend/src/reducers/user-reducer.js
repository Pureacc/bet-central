import {
    AUTHENTICATE_FULFILLED, AUTHENTICATE_PENDING, AUTHENTICATE_REJECTED,
    GET_USER_FULFILLED, GET_USER_PENDING, GET_USER_REJECTED,
    DEPOSIT_FULFILLED, DEPOSIT_PENDING, DEPOSIT_REJECTED,
    LOG_OUT_FULFILLED, LOG_OUT_PENDING, LOG_OUT_REJECTED
} from "../actions/user";

export default function user(state = {}, action) {
    switch (action.type) {
        case AUTHENTICATE_PENDING:
            return state;
        case AUTHENTICATE_FULFILLED:
            return {
                ...state,
                id: action.payload.data
            };
        case AUTHENTICATE_REJECTED:
            return {
                ...state,
                error: action.payload.message
            };
        case GET_USER_PENDING:
            return state;
        case GET_USER_FULFILLED:
            return {
                ...state,
                name: action.payload.data.username,
                balance: action.payload.data.balance
            };
        case GET_USER_REJECTED:
            return state;
        case DEPOSIT_PENDING:
            return state;
        case DEPOSIT_FULFILLED:
            return {
                ...state,
                balance: action.payload.data.balance
            };
        case DEPOSIT_REJECTED:
            return state;
        case LOG_OUT_PENDING:
            return state;
        case LOG_OUT_FULFILLED:
            return {};
        case LOG_OUT_REJECTED:
            return state;
        default:
            return state;
    }
}