import { Card, CardContent, CardMedia, Typography} from '@mui/material';
import Grid from '@mui/material/Unstable_Grid2';

import { useTheme, Button } from '@mui/material';
import { useSelector } from 'react-redux';
import { useNavigate } from 'react-router-dom';

const CarCard = (props) => {
    const theme = useTheme();
    const user = useSelector((state) => state.user);
    const navigate = useNavigate();
    const car = props.car;

    const rentCar = () => {
        if(!user) {
            navigate('/signin');
        }
    }
    


    return(
        <Grid xs={6}>
            <Card sx={{height: '275px', border: '1px solid', borderColor: theme.palette.button_secondary.main,}}>
                <Grid container spacing={1}>
                    <Grid xs={6.5} >
                        <CardMedia 
                            component="img" 
                            src={car.imageUrl} 
                            alt="Alt text" 
                            sx={{
                                height: "275px",
                                objectFit: 'cover',
                            }}
                        />
                    </Grid>
                    <Grid xs={5.5}>
                        <CardContent sx={{overflow: 'auto', height: '275px'}}>
                            <Grid container spacing={1.3}>
                                <Grid xs={12}>
                                    <Typography variant="h6" component="div">
                                        {car.make + " " + car.model}
                                    </Typography>
                                </Grid>
                                <Grid xs={12}>
                                    <Typography variant="body2" color="text.secondary">
                                        Engine Type: {car.engine}
                                    </Typography>
                                </Grid>
                                <Grid xs={12}>
                                    <Typography variant="body2" color="text.secondary">
                                        Gearbox: {car.transmissionEnum}
                                    </Typography>
                                </Grid>
                                <Grid xs={12}>
                                    <Typography variant="body2" color="text.secondary">
                                        Fuel Consumption: {car.fuelConsumption}
                                    </Typography>
                                </Grid>
                                <Grid xs={12}>
                                    <Typography variant="body2" color="text.secondary">
                                        Seats: {car.capacity}
                                    </Typography>
                                </Grid>
                                <Grid xs={12}>
                                    <Typography variant="body2" color="text.secondary">
                                        Price per Day: {parseFloat(car.pricePerDay).toFixed(2)} CC
                                    </Typography>
                                </Grid>
                                <Grid xs={12}>
                                    <Button variant="contained" color="button_primary" sx={{ width: '100%' }} onClick={rentCar}>
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