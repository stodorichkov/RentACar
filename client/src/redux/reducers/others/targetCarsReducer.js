const targetCarsReducer = (state = "afjlf", action) => {
    switch(action.type) {
        case 'SEARCH':
            return action.payload;
        default:
            return state;
    }
};

export default targetCarsReducer;