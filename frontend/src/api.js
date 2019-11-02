import Axios from "axios";

export function authenticate(username, password) {
    return Axios.post(`/api/user/authenticate`, {username: username, password: password});
    // return new Promise(function(resolve, reject) {
    //     setTimeout(() => resolve({username: "John Doe", balance: 10}), 1000);
    // });
}