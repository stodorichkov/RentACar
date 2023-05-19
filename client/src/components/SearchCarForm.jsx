import { Grid, Paper, Typography, Divider, Button, Container, Alert } from '@mui/material';

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
        <Container maxWidth="sm" sx={{marginTop: "8rem", marginBottom: '2rem'}}>
            <Paper elevation={12} sx={{padding: '3.5rem'}}>
                <LocalizationProvider dateAdapter={AdapterDayjs}>
                    <Grid container direction="column"  spacing={5}>
                        <Grid item xs={12} sm={6} md={6}>
                            <Typography variant="h3" color="textPrimary" align="center" >Search for car</Typography>
                        </Grid>
                        <Grid item xs={12} sm={6} md={6}>
                            <Divider/>
                        </Grid>
                        {alert ? (
                            <Grid item xs={12} sm={12} md={12}>
                                <Alert severity="error" variant="filled">{alert}</Alert>
                            </Grid>
                        ) : null}
                        <Grid item xs={12} sm={6} md={6}>
                            <DateTimePicker
                                label="Pick-up date"
                                slotProps={{ textField: { fullWidth: true } }}
                                format="DD/MM/YYYY HH:mm"
                                ampm={false}
                                minDate={dayjs()}
                                value={pickUpDate}
                                onChange={(newValue) => handleChangePickUpDate(newValue)}
                            />  
                        </Grid>
                        <Grid item xs={12} sm={6} md={6}>
                            <DateTimePicker
                                label="Drop-off date"
                                slotProps={{ textField: { fullWidth: true } }}
                                format="DD/MM/YYYY HH:mm"
                                ampm={false}
                                minDate={dayjs()}
                                value={dropOffDate}
                                onChange={(newValue) => handleChangeDropOffDate(newValue)}
                            />  
                        </Grid>
                        <Grid item xs={12} sm={6} md={6} alignSelf={'center'}>
                            <Button variant="contained" size="large" color="button_primary" onClick={searchCars}>
                                Search
                            </Button>
                        </Grid>
                    </Grid>
                </LocalizationProvider>
            </Paper>
        </Container> 
    )
}

export default SearchCarForm;