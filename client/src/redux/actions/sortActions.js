export const sortAction = (sortType) => {
    return {
        type: 'SORT',
        payload: sortType
    };
}

export const orderAction = () => {
    return {
        type: 'ORDER'
    }
}