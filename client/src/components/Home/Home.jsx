import SearchCarForm from "./SearchCarForm";
import ShowCars from "./ShowCars";

import Grid from '@mui/material/Unstable_Grid2';

import { useSelector } from 'react-redux';


const Home = () => {
    const targetCars = useSelector((state) => state.targetCars)
    
    return (
        <Grid container spacing={2} justifyContent='center' alignItems='center' direction='column' sx={{marginTop: '2vw'}}>
            <SearchCarForm/>
            { targetCars ? (
                <ShowCars/>
            ) : null }
            
        </Grid>
    );
}

export default Home;