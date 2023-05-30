import { Card, CardContent, CardMedia, Typography, Button, Modal } from '@mui/material';
import Grid from '@mui/material/Unstable_Grid2';

import { useTheme } from '@mui/material';
import { useSelector, useDispatch } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import { useState } from 'react';

import Rent from './Home/Rent';
import { setAlert } from '../redux/actions/alertActions';

const CarCard = (props) => {
    const theme = useTheme();
    const user = useSelector((state) => state.user);
    const navigate = useNavigate();
    const dispatch = useDispatch();
    const car = props.car;

    const [open, setOpen] = useState(false);

    const handleRent = () => {
        if(!user) {
            navigate('/signin');
        }
        else {
            dispatch(setAlert(null));
            setOpen(true);
        }
    }
    const handleClose = () => setOpen(false);

    return(
        <Grid xs={6}>
            <Card sx={{height: '275px', border: '1px solid', borderColor: theme.palette.button_secondary.main,}}>
                <Grid container spacing={1}>
                    <Grid xs={6.5} >
                        <CardMedia 
                            component="img" 
                            src={`data:image/jpeg;base64,${car.imageUrl}`} 
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
                                        {car.makeModel}
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
                                {props.isRent ?
                                    <Grid xs={12}>
                                        <Button variant="contained" color="button_primary" sx={{ width: '100%' }} onClick={handleRent}>
                                            Rent
                                        </Button>
                                    </Grid>
                                : null}
                            </Grid>  
                        </CardContent>
                    </Grid>
                </Grid>
            </Card>
            <Modal
                open={open}
            >
                <div
                    style={{ outline: 'none' }}
                >
                    <Rent handleClose={handleClose} car={car}/>
                </div>
            </Modal>
        </Grid>
    );
}

export default CarCard;