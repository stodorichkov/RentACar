const alertReducer = (state = null, action) => {
    switch(action.type) {
        case 'ALERT':
            return action.payload;
        default:
            return state;
    }
}

export default alertReducer;