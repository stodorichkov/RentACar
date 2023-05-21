import { Paper, Typography, Button, Divider, Stack } from '@mui/material';
import Grid from '@mui/material/Unstable_Grid2';

import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import { DateTimePicker } from '@mui/x-date-pickers';

import dayjs from 'dayjs';

import { useState } from 'react';

const SearchCarForm = () => {
    const [pickUpDate, setPickUpDate] = useState(dayjs());
    const [dropOffDate, setDropOffDate] = useState(null);

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
        <Grid container justifyContent='center' sx={{marginTop: '2vw'}}>
            <Grid xs={10}>
                <Paper elevation={12} sx={{padding: '1rem'}}>
                    <Grid container  justifyContent='center' alignItems='center' spacing={2}>
                        <Grid xs={12}>
                            <Typography variant="h3" color="textPrimary" align="center" >Road is before you!</Typography>
                        </Grid>
                        <Grid xs={10}>
                            <Divider/>
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
                                        minDate={dayjs()}
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
        </Grid>
    )
}

export default SearchCarForm;