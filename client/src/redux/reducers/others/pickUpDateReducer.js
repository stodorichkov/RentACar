export const pickUpDateReducer = (state = null, action) => {
    switch(action.type) {
        case 'SET_PICK_UP':
            return action.payload;
        default:
            return state;
    }
}