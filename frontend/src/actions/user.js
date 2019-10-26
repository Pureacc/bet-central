export const AUTHENTICATE = "AUTHENTICATE";
export const LOG_OUT = "LOG_OUT";

export function authenticate() {
    return {
        type: AUTHENTICATE,
        payload: {user: "John Doe"}
    }
}

export function logOut() {
    return {
        type: LOG_OUT,
    }
}