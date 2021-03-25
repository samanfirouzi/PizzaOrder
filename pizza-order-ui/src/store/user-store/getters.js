export function getAccessToken (state) {
    return state.tokenInfo.access_token
}
export function getRefreshToken (state) {
    return state.tokenInfo.refresh_token
}
export function isLoginPageVisible(state){
    return state.loginPageVisability
}
export function isSignupPageVisible(state){
    return state.signupPageVisability
}

export function getUserInfo(state){
    return state.userInfo.userName
}

export function getPermisions(state){
    return state.userInfo.permisions
}

