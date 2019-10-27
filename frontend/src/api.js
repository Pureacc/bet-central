import Axios from "axios";

export function authenticate(username, password) {
    // return Axios.get(`/api/user`, {params: {userId: userId}});
    return new Promise(function(resolve, reject) {
        setTimeout(() => resolve({username: "John Doe", balance: 10}), 1000);
    });
}