import {AUTHENTICATE_REJECTED, DEPOSIT_REJECTED, GET_USER_REJECTED, LOG_OUT_REJECTED} from "../actions/user";
import {CLEAR_ERROR} from "../actions/error";

export default function error(state = {}, action) {
    switch (action.type) {
        case AUTHENTICATE_REJECTED:
        case DEPOSIT_REJECTED:
        case GET_USER_REJECTED:
        case LOG_OUT_REJECTED:
            return {
                message: action.payload.response.data
            };
        case CLEAR_ERROR:
            return {};
        default:
            return state;
    }
}