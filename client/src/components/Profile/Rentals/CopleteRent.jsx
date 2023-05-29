import { Paper, Typography, Divider, Button, IconButton, Stack } from '@mui/material';
import CloseIcon from '@mui/icons-material/Close';
import Grid from '@mui/material/Unstable_Grid2';

import { useState, useEffect, useCallback } from 'react';
import { useSelector } from 'react-redux';
import { useTheme } from '@mui/material/styles';

import axios from 'axios';
import { useNavigate } from 'react-router-dom';

const CompleteRent = (props) => {
    const theme = useTheme();
    const user = useSelector((state) => state.user);
    const navigate = useNavigate();

    const rent = props.rent;

    const [summary, setSummary] = useState(null);

    axios.defaults.headers.common['Authorization'] = `Bearer ${user.token}`;

    const getSummary = useCallback(async () => {
        try {
            const response = await axios.get(`http://localhost:8086/rentals/${rent.id}/summary`);
            if (response.status === 200) {
                setSummary(response.data);
            }
        }
        catch (error) {
            console.error('Error fetching data:', error);
        } 
    }, [rent.id])

    useEffect(() => {
        getSummary();
    }, [getSummary]);

    const payRent = async () => {
        try {
            const response = await axios.post(`http://localhost:8086/rentals/${rent.id}/complete`);
            if (response.status === 200) {
                props.handleClose();
                navigate(0);
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
                            <Typography variant="h4" color="textPrimary" align="center" >Pay Rent</Typography>
                        </Grid>
                        <Grid xs={12}>
                            <Divider sx={{backgroundColor: theme.palette.menu.main}}/>
                        </Grid>
                            <Grid xs={12}>
                                <Stack
                                    direction="row"
                                    justifyContent="space-between"
                                >
                                    <Typography variant="body1" color="textPrimary" >Whithout discount: </Typography>
                                    <Typography variant="h6" color="textPrimary" >{summary ? parseFloat(summary.withoutDiscount).toFixed(2) : null} CC</Typography>
                                </Stack>
                            </Grid>
                            <Grid xs={12}>
                                <Stack
                                    direction="row"
                                    justifyContent="space-between"
                                >
                                    <Typography variant="body1" color="textPrimary" >Discount:</Typography>
                                    <Typography variant="h6" color="textPrimary" >{summary ? parseFloat(summary.discount).toFixed(2) : null} CC</Typography>
                                </Stack>
                            </Grid>
                            <Grid xs={12}>
                                <Stack
                                    direction="row"
                                    justifyContent="space-between"
                                >
                                    <Typography variant="body1" color="textPrimary" >Total: </Typography>
                                    <Typography variant="h6" color="textPrimary" >{summary ? parseFloat(summary.withDiscount).toFixed(2) : null} CC</Typography>
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
                                <Button variant="contained" size="large" color="button_primary" onClick={payRent}>
                                    Pay
                                </Button>
                            </Stack>
                        </Grid>
                    </Grid>
                </Paper>
            </Grid>
        </Grid>
    );
}

export default CompleteRent;