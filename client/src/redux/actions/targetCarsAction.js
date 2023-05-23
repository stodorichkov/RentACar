export const searchCars = (targetCars) => {
    return {
        type: 'SEARCH',
        payload: targetCars
    };
}