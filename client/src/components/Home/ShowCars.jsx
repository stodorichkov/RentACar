import { Paper, Divider } from '@mui/material';
import Grid from '@mui/material/Unstable_Grid2';

import { useSelector } from 'react-redux';
import { useTheme } from '@mui/material/styles';

import CarCard from './CarCard';
import SortBar from './SortBar';

const ShowCars = () => {
    const targetCars = useSelector((state) => state.targetCars)

    // style
    const theme = useTheme();

    return(
        <Grid xs={11.5} >
            <Paper 
                elevation={12} 
                sx={{
                    padding: '2rem', 
                    height: '63vh'
                }}
            >
                <Grid container spacing={1.5} justifyContent='center' >
                    <Grid>
                        <SortBar/>
                    </Grid>
                    <Grid xs={12}>
                        <Divider sx={{backgroundColor: theme.palette.menu.main}}/>
                    </Grid>
                    <Grid xs={12}>
                        <Grid container justifyContent='space-evenly' sx={{overflow: 'auto', maxHeight: '47vh'}}>
                            {targetCars.map(car => (<CarCard key={car.id} car={car}/>))}
                        </Grid>
                    </Grid>
                </Grid>    
            </Paper>
        </Grid>
    )
}

export default ShowCars;