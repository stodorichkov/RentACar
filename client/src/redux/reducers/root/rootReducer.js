import { combineReducers } from "@reduxjs/toolkit";

import userReducer from "../others/userReducer";
import targetCarsReducer from "../others/targetCarsReducer";
import alertReducer from "../others/alertReducer";
import orderReducer from "../others/orderReducer";
import sortReducer from "../others/sortReducer";


const rootReducer = combineReducers({
    user: userReducer,
    targetCars: targetCarsReducer,
    alert: alertReducer,
    order: orderReducer,
    sort: sortReducer
});

export default rootReducer;