import { Paper, Typography, Divider, Button, IconButton, Stack } from '@mui/material';
import CloseIcon from '@mui/icons-material/Close';
import Grid from '@mui/material/Unstable_Grid2';

import { useState, useEffect } from 'react';
import { useSelector} from 'react-redux';
import { useTheme } from '@mui/material/styles';

import axios from 'axios';
import dayjs from 'dayjs';


const Rent = (props) => {
    const pickUpDate = useSelector((state) => state.pickUpDate);
    const dropOffDate = useSelector((state) => state.dropOffDate);
    const theme = useTheme();
    const car = props.car;
    const user = useSelector((state) => state.user);

    const [cost, setCost] = useState(0.00.toFixed(2));

    axios.defaults.headers.common['Authorization'] = `Bearer ${user.token}`;

    const getCost = async () => {
        const content = {
            startDate: pickUpDate,
            endDate: dropOffDate
        }
        try {
            const response = await axios.get(`http://localhost:8086/rentals/${car.id}/showCost`, {params: content});
            if (response.status === 200) {
                setCost(response.data.toFixed(2))
            }
        }
        catch (error) {
            console.error('Error fetching data:', error);
        } 
    }

    useEffect(() => {
        getCost();
    }, [getCost]);

    const rentCar = async () => {
        const content = {
            startTime: pickUpDate,
            endTime: dropOffDate
        }
        try {
            const response = await axios.post(`http://localhost:8086/rentals/${car.id}/add`, content);
            if (response.status === 200) {
                console.log(response)
            }
        }
        catch (error) {
            console.error('Error fetching data:', error);
        } 
    }

    return (
        <Grid container justifyContent='center' sx={{marginTop: '10vh'}}>
            <Grid xs={7} xl={8}>
                <Paper elevation={12} sx={{padding: '2rem'}}>
                    <Stack
                        direction="row"
                        justifyContent="flex-end"
                    >
                        <IconButton size="large" onClick={props.handleClose}>
                            <CloseIcon fontSize="large" />
                        </IconButton>
                    </Stack>
                    <Grid container justifyContent="center" spacing={2}>
                        <Grid xs={12}>
                            <Typography variant="h4" color="textPrimary" align="center" >Rent Info</Typography>
                        </Grid>
                        <Grid xs={12}>
                            <Divider sx={{backgroundColor: theme.palette.menu.main}}/>
                        </Grid>
                        <Grid xs={12}>
                            <Stack
                                direction="row"
                                justifyContent="space-between"
                            >
                                <Typography variant="body1" color="textPrimary" >Pick up date: </Typography>
                                <Typography variant="h6" color="textPrimary" >{dayjs(pickUpDate).format("DD/MM/YYYY HH:mm")}</Typography>
                            </Stack>
                        </Grid>
                        <Grid xs={12}>
                            <Stack
                                direction="row"
                                justifyContent="space-between"
                            >
                                <Typography variant="body1" color="textPrimary" >Drop off date:</Typography>
                                <Typography variant="h6" color="textPrimary" >{dayjs(dropOffDate).format("DD/MM/YYYY HH:mm")}</Typography>
                            </Stack>
                        </Grid>
                        <Grid xs={12}>
                            <Stack
                                direction="row"
                                justifyContent="space-between"
                            >
                                <Typography variant="body1" color="textPrimary" >Car: </Typography>
                                <Typography variant="h6" color="textPrimary" >{car.makeModel}</Typography>
                            </Stack>
                        </Grid>
                        <Grid xs={12}>
                            <Stack
                                direction="row"
                                justifyContent="space-between"
                            >
                                <Typography variant="body1" color="textPrimary" >Price per day: </Typography>
                                <Typography variant="h6" color="textPrimary" >{parseFloat(car.pricePerDay).toFixed(2)} CC</Typography>
                            </Stack>
                        </Grid>
                        <Grid xs={12}>
                            <Divider sx={{backgroundColor: theme.palette.menu.main}}/>
                        </Grid>
                        <Grid xs={12}>
                            <Stack
                                direction="row"
                                justifyContent="flex-end"
                            >
                                <Typography variant="h6" color="textPrimary" >Total: {cost} CC</Typography>
                            </Stack>
                        </Grid>
                        <Grid xs={12}>
                            <Stack
                                direction="row"
                                justifyContent="flex-end"
                            >
                                <Button variant="contained" size="large" color="button_primary" onClick={rentCar}>
                                    Rent
                                </Button>
                            </Stack>
                        </Grid>
                    </Grid>
                </Paper>
            </Grid>
        </Grid>
    );
}

export default Rent;