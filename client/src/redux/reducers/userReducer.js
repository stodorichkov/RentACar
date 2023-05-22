const userReducer = (state = null, action) => {
    switch(action.type) {
        case 'SIGNOUT':
            return null;
        case 'SIGNIN':
            return action.payload
        default:
            return state;
    }
};

export default userReducer;