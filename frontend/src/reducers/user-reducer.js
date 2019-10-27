import {
    AUTHENTICATE_FULFILLED,
    AUTHENTICATE_PENDING,
    AUTHENTICATE_REJECTED,
    LOG_OUT,
} from "../actions/user";

export default function user(state = {}, action) {
    switch (action.type) {
        case AUTHENTICATE_PENDING:
            return {
                ...state,
                name: undefined,
                balance: undefined
            };
        case AUTHENTICATE_FULFILLED:
            return {
                ...state,
                name: action.payload.username,
                balance: action.payload.balance
            };
        case AUTHENTICATE_REJECTED:
            return state;
        case LOG_OUT:
            return {
                ...state,
                name: undefined,
                balance: undefined
            };
        default:
            return state;
    }
}