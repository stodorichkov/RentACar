import { Paper, Divider, Typography }  from '@mui/material';
import Grid from '@mui/material/Unstable_Grid2';

import { useTheme } from '@mui/material/styles';
import { useState } from 'react';

import AllCars from './AdminGrids/Cars/AllCars';
import AllUsers from './AdminGrids/Users/AllUsers';
import AllRentals from './AdminGrids/Rentals/AllRentals';

const Admin = () => {
    const theme = useTheme();
    const [expanded, setExpanded] = useState(null);

    const handleChangeExpand = (panel) => (event, isExpanded) => {
        if (isExpanded || expanded !== panel ) {
            setExpanded(panel);
        }
    };

    return(
        <Grid container spacing={2} justifyContent='center' direction='row' sx={{ height: "85vh", marginTop: '4vh',  overflow: 'auto'}}>
            <Grid xs={11} >
                <Paper elevation={12} sx={{padding: '3.5rem', height: "100%"}}>
                    <Grid container spacing={5} justifyContent="center">   
                        <Grid xs={12}>
                            <Typography variant="h3" color="textPrimary" align="center" >Admin panel</Typography>
                        </Grid>
                        <Grid xs={12}>
                            <Divider sx={{backgroundColor: theme.palette.menu.main}}/>
                        </Grid>
                        <Grid xs={12}>
                            <AllCars handleChangeExpand={handleChangeExpand('panel1')} expanded={expanded === 'panel1'}/>
                        </Grid>
                        <Grid xs={12}>
                            <AllUsers handleChangeExpand={handleChangeExpand('panel2')} expanded={expanded === 'panel2'}/>
                        </Grid>
                        <Grid xs={12}>
                            <AllRentals handleChangeExpand={handleChangeExpand('panel3')} expanded={expanded === 'panel3'}/>
                        </Grid>
                    </Grid> 
                </Paper>
            </Grid>
        </Grid>
    );
}

export default Admin;