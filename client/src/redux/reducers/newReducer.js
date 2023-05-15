const newReducer = (state = 0, action) => {
    if(action.type === 1) {
        return state + 1;
    }
    return state;
};

export default newReducer;