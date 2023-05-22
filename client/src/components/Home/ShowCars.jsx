import { Paper, ToggleButton, ToggleButtonGroup, Divider, Button } from '@mui/material';
import Grid from '@mui/material/Unstable_Grid2';

import { useState } from 'react';
import { useTheme } from '@mui/material/styles';

import CarCard from './CarCard';

const ShowCars = () => {
    const [sort, setSort] = useState(null);
    const [order, setOrder] = useState(false);

    const handleChangeSort = (event, newSort) => {
        setSort(newSort);
    };

    const handleChangeOeder = (event) => {
        setOrder(!order);
    };

    const theme = useTheme();

    const toggleButtonStyle = {
        marginRight: '8px',
        color: 'white',
        '&.Mui-selected': {
            color: 'white',
            backgroundColor: theme.palette.button_primary.main,
            '&:hover': {
                backgroundColor: theme.palette.menue.main,
            },
        },
        '&:not(.Mui-selected)': {
            backgroundColor: theme.palette.button_secondary.main,
            '&:hover': {
                backgroundColor: theme.palette.menue.main,
            },
            
        },
        
    }

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
                    <Grid >
                        <ToggleButtonGroup
                            value={sort}
                            exclusive
                            onChange={handleChangeSort}
                        >
                            <ToggleButton value="name" sx={{...toggleButtonStyle}}>Name</ToggleButton>
                            <ToggleButton value="engineType" sx={{...toggleButtonStyle}}>Engine type</ToggleButton>
                            <ToggleButton value="gearbox" sx={{...toggleButtonStyle}}>Gearbox</ToggleButton>
                            <ToggleButton value="fuelConsumption" sx={{...toggleButtonStyle}}>Fuel Consumption</ToggleButton>
                            <ToggleButton value="seats" sx={{...toggleButtonStyle}}>Seats</ToggleButton>
                            <ToggleButton value="pricePerDay" sx={{...toggleButtonStyle}}>Price per Day</ToggleButton>
                        </ToggleButtonGroup>
                        <Button variant="contained" onClick={handleChangeOeder}>
                            {order ? 1:2}
                        </Button>
                    </Grid>
                    <Grid xs={12}>
                        <Divider sx={{backgroundColor: theme.palette.menue.main}}/>
                    </Grid>
                    <Grid container  justifyContent='center' alignItems='center' spacing={2} sx={{overflow: 'auto', height: '300px'}}>
                        <CarCard/>
                        <CarCard/>
                        <CarCard/>
                    </Grid>
                </Grid>    
            </Paper>
        </Grid>
    )
}

export default ShowCars;