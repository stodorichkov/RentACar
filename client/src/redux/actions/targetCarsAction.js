export const searchTargetCars = (targetCars) => {
    return {
        type: 'SEARCH',
        payload: targetCars
    };
}