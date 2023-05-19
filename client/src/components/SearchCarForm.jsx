import { Grid, Paper, Typography, Button, Alert, Divider } from '@mui/material';

import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import { DateTimePicker } from '@mui/x-date-pickers';

import dayjs from 'dayjs';

import { useState } from 'react';

const SearchCarForm = () => {
    const [pickUpDate, setPickUpDate] = useState(dayjs());
    const [dropOffDate, setDropOffDate] = useState(null);
    const [alert, setAlert] = useState('');

    const handleChangePickUpDate = (value) => {
        setPickUpDate(value);
    }

    const handleChangeDropOffDate = (value) => {
        setDropOffDate(value);
    }

    const searchCars = async () => {
        console.log(pickUpDate.format("YYYY-MM-DD hh:mm:ss"))
        console.log(pickUpDate.format("YYYY-MM-DD hh:mm:ss"))
    }

    return (
        <Grid container justifyContent="center" sx={{marginTop: '2vw'}}>
            <Grid item xs={11} md={11} xl={11}>
                <Paper elevation={12} sx={{padding: '1rem'}}>
                    <Grid container  justifyContent='center' alignContent={'center'} spacing={2}>
                        <Grid item xs={12}>
                            <Typography variant="h3" color="textPrimary" align="center" >Road is before you!</Typography>
                        </Grid>
                        <Grid item xs={10}>
                            <Divider/>
                        </Grid>
                        {alert ? (
                            <Grid item xs={12}>
                                <Alert severity="error" variant="filled">{alert}</Alert>
                            </Grid>
                        ) : null}
                        <Grid item xs={12} xl={3} md={4}>
                            <LocalizationProvider dateAdapter={AdapterDayjs}>
                            <DateTimePicker
                                label="Pick-up date"
                                slotProps={{ textField: { fullWidth: true } }}
                                format="DD/MM/YYYY HH:mm"
                                ampm={false}
                                minDate={dayjs()}
                                value={pickUpDate}
                                onChange={(newValue) => handleChangePickUpDate(newValue)}
                            />
                            </LocalizationProvider>  
                        </Grid>
                        <Grid item xs={12} xl={3} md={4}>
                            <LocalizationProvider dateAdapter={AdapterDayjs}>
                            <DateTimePicker
                                label="Drop-off date"
                                slotProps={{ textField: { fullWidth: true } }}
                                format="DD/MM/YYYY HH:mm"
                                ampm={false}
                                minDate={dayjs()}
                                value={dropOffDate}
                                onChange={(newValue) => handleChangeDropOffDate(newValue)}
                            />
                            </LocalizationProvider>   
                        </Grid>
                        <Grid item alignSelf={'center'}>
                            <Button variant="contained" size="large" color="button_primary" onClick={searchCars}>
                                Search
                            </Button>
                        </Grid>
                    </Grid>   
                </Paper>
            </Grid>
        </Grid> 
    )
}

export default SearchCarForm;