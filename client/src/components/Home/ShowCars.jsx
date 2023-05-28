import { Paper, Divider } from '@mui/material';
import Grid from '@mui/material/Unstable_Grid2';

import { useEffect, useState, useCallback } from 'react';
import { useSelector } from 'react-redux';
import { useTheme } from '@mui/material/styles';

import CarCard from './CarCard';
import SortBar from './SortBar';


const ShowCars = () => {
    const targetCars = useSelector((state) => state.targetCars)
    const sort = useSelector((state) => state.sort);
    const order = useSelector((state) => state.order);
    const theme = useTheme();

    const [sortedCars, setSortedCars] = useState(targetCars);

    const sortCars = useCallback(() => {
        if(sort) {
            setSortedCars((prevSortedCars) => {
                let sorted = [...prevSortedCars].sort((a, b) => {
                    const comparison = order ? 1 : -1;
                    if (a[sort] > b[sort]) {
                        return comparison;
                    } else if (a[sort] < b[sort]) {
                        return -comparison;
                    } else {
                        return 0;
                    }
                });
            
                return sorted;
            });
        }
        else {
            setSortedCars(targetCars);
        }
        
    }, [sort, order, targetCars]);

    useEffect(() => {
        sortCars();
    }, [sortCars])
    

    return(
        <Grid xs={11.5} >
            <Paper 
                elevation={12} 
                sx={{
                    padding: '2rem', 
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
                        <Grid container justifyContent='space-evenly' sx={{overflow: 'auto', height: '47vh'}}>
                            {sortedCars.map(car => (<CarCard key={car.id} car={car}/>))}
                        </Grid>
                    </Grid>
                </Grid>    
            </Paper>
        </Grid>
    )
}

export default ShowCars;