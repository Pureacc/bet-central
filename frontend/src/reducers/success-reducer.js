import {DEPOSIT_FULFILLED, LOG_OUT_FULFILLED, PLACE_BET_FULFILLED} from "../actions/user";
import {CLEAR_SUCCESS} from "../actions/success";

export default function success(state = {}, action) {
    switch (action.type) {
        case DEPOSIT_FULFILLED:
            return {
                message: "Deposit successful!"
            };
        case PLACE_BET_FULFILLED:
            return {
                message: "Bet placed!"
            };
        case LOG_OUT_FULFILLED:
            return {
                message: "You are now logged out."
            };
        case CLEAR_SUCCESS:
            return {};
        default:
            return state;
    }
}