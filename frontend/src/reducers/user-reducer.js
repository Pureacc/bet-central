import {
    AUTHENTICATE_FULFILLED,
    AUTHENTICATE_PENDING,
    AUTHENTICATE_REJECTED, DEPOSIT_FULFILLED, DEPOSIT_PENDING, DEPOSIT_REJECTED,
    LOG_OUT,
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
        case LOG_OUT:
            return {};
        default:
            return state;
    }
}