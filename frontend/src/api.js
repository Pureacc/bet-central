import Axios from "axios";

export function authenticate(username, password) {
    return Axios.post(`/api/user/authenticate`, {username: username, password: password});
}

export function deposit(userId, euros) {
    return Axios.post('/api/balance/deposit', {userId: userId, euros: euros});
}