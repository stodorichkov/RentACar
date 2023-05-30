const sortReducer = (state = null, action) => {
    switch(action.type) {
        case 'SORT':
            return action.payload;
        default:
            return state;
    }
}
export default sortReducer;