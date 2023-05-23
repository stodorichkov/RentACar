import { Paper, Divider, Typography }  from '@mui/material';
import Grid from '@mui/material/Unstable_Grid2';

import { useState } from 'react';
import { useTheme } from '@mui/material/styles';

import ActiveRentals from './ActiveRentals';
import RentalsHistory from './RentalsHistory';

const UserRentals = () => {
    const [expanded, setExpanded] = useState('panel1');
    const theme = useTheme();

    const handleChangeExpand = (panel) => (event, isExpanded) => {
        if (isExpanded || expanded !== panel) {
            setExpanded(panel);
        }
    };

    return (
        <Grid xl={8} lg={7} md={6} sm={7} xs={10} >
            <Paper elevation={12} sx={{padding: '3.5rem', height: '100%'}}>
                <Grid container spacing={2.5} justifyContent="center">   
                    <Grid xs={12}>
                        <Typography variant="h3" color="textPrimary" align="center" >Rentals</Typography>
                    </Grid>
                    <Grid xs={12}>
                        <Divider sx={{backgroundColor: theme.palette.menu.main}}/>
                    </Grid>
                    <Grid xs={12}>
                        <ActiveRentals handleChangeExpand={handleChangeExpand('panel1')} expanded={expanded === 'panel1'}/>
                    </Grid>
                    <Grid xs={12}>
                        <RentalsHistory handleChangeExpand={handleChangeExpand('panel2')} expanded={expanded === 'panel2'}/>
                    </Grid>
                </Grid> 
            </Paper>
        </Grid>
    );
}

export default UserRentals;