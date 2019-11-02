import {
    AUTHENTICATE_FULFILLED,
    AUTHENTICATE_PENDING,
    AUTHENTICATE_REJECTED,
    LOG_OUT,
} from "../actions/user";

export default function user(state = {}, action) {
    switch (action.type) {
        case AUTHENTICATE_PENDING:
            return {};
        case AUTHENTICATE_FULFILLED:
            return {
                ...state,
                id: action.payload.data.userId,
                name: action.payload.data.username,
                balance: action.payload.data.balance
            };
        case AUTHENTICATE_REJECTED:
            return state;
        case LOG_OUT:
            return {};
        default:
            return state;
    }
}