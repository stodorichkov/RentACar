import SearchCarForm from "./SearchCarForm";
import ShowCars from "./ShowCars";

import Grid from '@mui/material/Unstable_Grid2';


const Home = () => {
    return (
        <Grid container spacing={2} justifyContent='center' alignItems='center' direction='column' sx={{marginTop: '2vw'}}>
            <SearchCarForm/>
            <ShowCars/>
        </Grid>
    );
}

export default Home;