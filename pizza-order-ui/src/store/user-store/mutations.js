export function changeLoginFormVisibility (state,val) {
    state.loginPageVisability = val;
}

export function changeSignupFormVisibility (state,val) {
    state.signupPageVisability = val;
}

export function saveToken (state,val) {
    state.tokenInfo.access_token = val;
}

export function saveRefreshToken (state,val) {
    state.tokenInfo.refresh_token = val;
}

export function saveUsername (state,val) {
    state.userInfo.userName = val;
}

export function setPermisions (state,val) {
    state.userInfo.permisions = val;
}
