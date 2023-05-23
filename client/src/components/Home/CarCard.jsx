import { Card, CardContent, CardMedia, Typography} from '@mui/material';
import Grid from '@mui/material/Unstable_Grid2';
import { useTheme, Button } from '@mui/material';

const CarCard = (props) => {
    const theme = useTheme();

    const {car} = props;

    return(
        <Grid xs={6}>
            <Card sx={{height: '250px', border: '1px solid', borderColor: theme.palette.button_secondary.main,}}>
                <Grid container spacing={1}>
                    <Grid xs={6.5} >
                        <CardMedia 
                            component="img" 
                            src={"https://www.automoblog.net/wp-content/uploads/2010/04/2010-dok-ing-xd-concept-front-3-21.jpg"} 
                            alt="Alt text" 
                            sx={{
                                height: "100%",
                                objectFit: 'cover',
                            }}
                        />
                    </Grid>
                    <Grid xs={5.5}>
                        <CardContent sx={{overflow: 'auto', maxHeight: '260px'}}>
                            <Grid container spacing={1} justifyContent="center">
                                <Grid xs={12}>
                                    <Typography variant="h6" component="div">
                                        {car.name}
                                    </Typography>
                                </Grid>
                                <Grid xs={12}>
                                    <Typography variant="body2" color="text.secondary">
                                        Engine Type: {car.engine}
                                    </Typography>
                                </Grid>
                                <Grid xs={12}>
                                    <Typography variant="body2" color="text.secondary">
                                        Gearbox: {car.gearbox}
                                    </Typography>
                                </Grid>
                                <Grid xs={12}>
                                    <Typography variant="body2" color="text.secondary">
                                        Fuel Consumption: {car.fueConsuption}
                                    </Typography>
                                </Grid>
                                <Grid xs={12}>
                                    <Typography variant="body2" color="text.secondary">
                                        Seats: {car.seats}
                                    </Typography>
                                </Grid>
                                <Grid xs={12}>
                                    <Typography variant="body2" color="text.secondary">
                                        Price per Day: {car.pricePerDay}
                                    </Typography>
                                </Grid>
                                <Grid xs={12}>
                                    <Button variant="contained" color="button_primary" sx={{ width: '100%' }}>
                                        Rent
                                    </Button>
                                </Grid>
                            </Grid>  
                        </CardContent>
                    </Grid>
                </Grid>
            </Card>
        </Grid>
    );
}

export default CarCard;