import { Paper, Divider } from '@mui/material';
import Grid from '@mui/material/Unstable_Grid2';

import { useSelector } from 'react-redux';
import { useTheme } from '@mui/material/styles';

import CarCard from './CarCard';

const ShowCars = () => {
    const targetCars = useSelector((state) => state.targetCars)

    // style
    const theme = useTheme();

    return(
        <Grid xs={10} >
            <Paper 
                elevation={12} 
                sx={{
                    padding: '1rem', 
                    height: {xs: '55vh', sm:'55vh', md: '55vh', lg: '55vh', xl: '55vh'}
                }}
            >
                <Grid container spacing={2} justifyContent='center' >
                    <Grid xs={12}>
                        <Divider sx={{backgroundColor: theme.palette.menue.main}}/>
                    </Grid>
                    <Grid container  justifyContent='center' alignItems='center' spacing={2} sx={{overflow: 'auto', height: '300px'}}>
                        {targetCars.map(car => (<CarCard CAR/>))}
                    </Grid>
                </Grid>    
            </Paper>
        </Grid>
    )
}

export default ShowCars;