import { Paper, Divider, Typography } from '@mui/material';
import Grid from '@mui/material/Unstable_Grid2';

import { useEffect, useState } from 'react';
import { useTheme } from '@emotion/react';

import axios from 'axios';

import CarCard from '../CarCard';

const ShowAllCars = () => {
    const theme = useTheme();
    const [cars, setCars] = useState([]);

    const getCars = async () => {
        try {
            const response = await axios.get('http://localhost:8086/car/all-unique');
            if (response.status === 200) {
                setCars(response.data)
            }
        }
        catch (error) {
            console.error('Error fetching data:', error);
        } 
    }

    useEffect(() => {
        getCars();
    }, [])

    return (
        <Grid container spacing={2} justifyContent='center' alignItems='center' direction='column' sx={{marginTop: '4vh'}}>
            <Grid xs={11.5} >
                <Paper 
                    elevation={12} 
                    sx={{
                        padding: '2rem', 
                    }}
                >
                    <Grid container spacing={1.5} justifyContent='center' >
                        <Grid xs={12}>
                            <Typography variant="h3" color="textPrimary" align="center" >All cars</Typography>
                        </Grid>
                        <Grid xs={12}>
                            <Divider sx={{backgroundColor: theme.palette.menu.main}}/>
                        </Grid>
                        <Grid xs={12}>
                            <Grid container justifyContent='space-evenly' sx={{overflow: 'auto', maxHeight: '70vh'}}>
                                {cars.map(car => (<CarCard key={car.id} car={car}/>))}
                            </Grid>
                        </Grid>
                    </Grid>    
                </Paper>
            </Grid>
        </Grid>
    );
}

export default ShowAllCars;