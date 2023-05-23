import { combineReducers } from "@reduxjs/toolkit";
import userReducer from "../others/userReducer";
import targetCarsReducer from "../others/targetCarsReducer";
import alertReducer from "../others/alertReducer";


const rootReducer = combineReducers({
    user: userReducer,
    targetCars: targetCarsReducer,
    alert: alertReducer
});

export default rootReducer;