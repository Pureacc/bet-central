import {combineReducers} from "redux";
import user from "./user-reducer";
import error from "./error-reducer";
import success from "./success-reducer";

export default combineReducers({user, error, success})