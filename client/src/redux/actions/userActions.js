export const signOutAction = () => {
    return {
        type: 'SIGNOUT'
    };
} 

export const signInAction = (user) => {
    return {
        type: 'SIGNIN',
        payload: user
    };
}