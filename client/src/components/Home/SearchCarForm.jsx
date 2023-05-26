import { Paper, Typography, Button, Divider, Stack } from '@mui/material';
import Grid from '@mui/material/Unstable_Grid2';

import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import { DateTimePicker } from '@mui/x-date-pickers';

import dayjs from 'dayjs';
import axios from 'axios';

import { useState, useEffect } from 'react';
import { useTheme } from '@mui/material/styles';
import { useDispatch } from 'react-redux';

import { searchTargetCars } from '../../redux/actions/targetCarsAction';
import { sortAction } from '../../redux/actions/sortActions';

const SearchCarForm = () => {
    const [pickUpDate, setPickUpDate] = useState(dayjs());
    const [dropOffDate, setDropOffDate] = useState(pickUpDate.add(1, 'day'));

    const dispatch = useDispatch();

    const handleChangePickUpDate = (value) => {
        setPickUpDate(value);
        setDropOffDate(dayjs(value).add(1, 'day'));
    }

    const handleChangeDropOffDate = (value) => {
        setDropOffDate(value);
    }

    const searchCars = async () => {
        const content = {
            startDate: pickUpDate.format("YYYY-MM-DD hh:mm:ss"),
            endDate: dropOffDate.format("YYYY-MM-DD hh:mm:ss")
        }
        try {
            const response = await axios.get('http://localhost:8086/rentals/all-unique-available', content);
            if (response.status === 200) {
                dispatch(searchTargetCars(response.data))
            }
        }
        catch (error) {
            console.error('Error fetching data:', error);
        } 
    }

    useEffect(() => {
        dispatch(searchTargetCars(null));
        dispatch(sortAction(null));
    }, [dispatch]);

    const theme = useTheme();

    return (
        <Grid xs={11.5}>
            <Paper elevation={12} sx={{padding: '1rem'}}>
                <Grid container  justifyContent='center' alignItems='center' spacing={2}>
                    <Grid xs={12}>
                        <Typography variant="h3" color="textPrimary" align="center" >Road is before you!</Typography>
                    </Grid>
                    <Grid xs={10}>
                        <Divider sx={{backgroundColor: theme.palette.menu.main}}/>
                    </Grid>
                    <Grid xs={12} >
                        <Stack spacing={2} direction="row" justifyContent="center">
                            <LocalizationProvider dateAdapter={AdapterDayjs}>
                                <DateTimePicker
                                    label="Pick-up date"
                                    format="DD/MM/YYYY HH:mm"
                                    ampm={false}
                                    minDate={dayjs()}
                                    value={pickUpDate}
                                    onChange={(newValue) => handleChangePickUpDate(newValue)}
                                />
                            </LocalizationProvider>  
                            <LocalizationProvider dateAdapter={AdapterDayjs}>
                                <DateTimePicker
                                    label="Drop-off date"
                                    format="DD/MM/YYYY HH:mm"
                                    ampm={false}
                                    minDate={pickUpDate.add(1, 'day')}
                                    value={dropOffDate}
                                    onChange={(newValue) => handleChangeDropOffDate(newValue)}
                                />
                            </LocalizationProvider>   
                            <Button variant="contained" size="large" color="button_primary" onClick={searchCars}>
                                Search
                            </Button>
                        </Stack>
                    </Grid>
                </Grid>
            </Paper>
        </Grid>
    )
}

export default SearchCarForm;