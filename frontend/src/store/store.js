import { createStore, applyMiddleware } from 'redux'
import rootReducer from "../reducers/root";
import {composeWithDevTools} from "redux-devtools-extension";
import Promise from 'redux-promise-middleware'
import ImmutableState from 'redux-immutable-state-invariant'

export const store = createStore(rootReducer, composeWithDevTools(
    applyMiddleware(Promise, ImmutableState())
));