export const dropOffDateReducer= (state = null, action) => {
    switch(action.type) {
        case 'SET_DROP_OFF':
            return action.payload;
        default:
            return state;
    }
}