import { combineReducers } from "@reduxjs/toolkit";
import newReducer from "./newReducer";

const rootReducer = combineReducers({
    new: newReducer
});

export default rootReducer;