import {
    AUTHENTICATE_FULFILLED,
    AUTHENTICATE_PENDING,
    GET_USER_FULFILLED,
    GET_USER_PENDING,
    LOG_OUT_FULFILLED,
    LOG_OUT_PENDING
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
        case GET_USER_PENDING:
            return state;
        case GET_USER_FULFILLED:
            return {
                ...state,
                name: action.payload.data.username,
                balance: action.payload.data.balance
            };
        case LOG_OUT_PENDING:
            return state;
        case LOG_OUT_FULFILLED:
            return {};
        default:
            return state;
    }
}