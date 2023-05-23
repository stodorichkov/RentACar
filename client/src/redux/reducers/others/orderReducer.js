const orderReducer = (state = true, action) => {
    switch(action.type) {
        case 'ORDER':
            return !state;
        default:
            return state;
    }
}
export default orderReducer;