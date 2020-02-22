import {
    AUTHENTICATE_REJECTED,
    DEPOSIT_FULFILLED,
    DEPOSIT_REJECTED,
    GET_USER_REJECTED,
    LOG_OUT_FULFILLED,
    LOG_OUT_REJECTED,
    PLACE_BET_FULFILLED,
    PLACE_BET_REJECTED
} from "../actions/user";
import {CLEAR_MESSAGE} from "../actions/message";

export default function message(state = {}, action) {
    switch (action.type) {
        case DEPOSIT_FULFILLED:
            return setMessage("Deposit successful!", "success");
        case PLACE_BET_FULFILLED:
            return setMessage("Bet placed!", "success");
        case LOG_OUT_FULFILLED:
            return setMessage("You are now logged out.", "success");
        case AUTHENTICATE_REJECTED:
        case DEPOSIT_REJECTED:
        case PLACE_BET_REJECTED:
        case GET_USER_REJECTED:
        case LOG_OUT_REJECTED:
            return setMessage(action.payload.response.data, "error");
        case CLEAR_MESSAGE:
            return setMessage("", state.variant);
        default:
            return state;
    }
}

function setMessage(message, variant) {
    return {
        message: message,
        variant: variant
    };
}