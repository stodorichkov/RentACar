import { Paper, Divider, Typography }  from '@mui/material';
import Grid from '@mui/material/Unstable_Grid2';

import { useTheme } from '@mui/material/styles';


const Admin = () => {
    const theme = useTheme();

    return(
        <Grid container spacing={2} justifyContent='center' direction='row' sx={{ maxHeight: "86vh",marginTop: '2vh', marginBottom: '2vh', overflow: 'auto'}}>
            <Grid xs={11} >
                <Paper elevation={12} sx={{padding: '3.5rem', height: "100%"}}>
                    <Grid container spacing={2.5} justifyContent="center">   
                        <Grid xs={12}>
                            <Typography variant="h3" color="textPrimary" align="center" >Admin panel</Typography>
                        </Grid>
                        <Grid xs={12}>
                            <Divider sx={{backgroundColor: theme.palette.menu.main}}/>
                        </Grid>
                        <Grid xs={12}>
                            
                        </Grid>
                        <Grid xs={12}>
                            
                        </Grid>
                    </Grid> 
                </Paper>
            </Grid>
        </Grid>
    );
}

export default Admin;