import {AUTHENTICATE, LOG_OUT} from "../actions/user";

export default function user(state = {}, action) {
    switch (action.type) {
        case AUTHENTICATE:
            return {
                ...state,
                user: action.payload.user
            };
        case LOG_OUT:
            return {
                ...state,
                user: undefined
            };
        default:
            return state;
    }
}