import Axios from "axios";

export function authenticate(username, password) {
    return Axios.post(`/login?username=${username}&password=${password}`);
}

export function getUser(userId) {
    return Axios.get(`/api/user?userId=${userId}`);
}

export function deposit(userId, euros) {
    return Axios.post('/api/balance/deposit', {userId: userId, euros: euros});
}

export function logOut() {
    return Axios.post('/logout');
}