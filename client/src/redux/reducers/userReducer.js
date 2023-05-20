const userReducer = (state = null, action) => {
    if(action.type === 1) {
        return state + 1;
    }
    return state;
};

export default userReducer;