import { Paper, Typography, TextField, Divider, Button, Select, MenuItem, InputLabel, FormControl, IconButton, Stack, Skeleton, Alert, InputAdornment } from '@mui/material';
import CloseIcon from '@mui/icons-material/Close';
import Grid from '@mui/material/Unstable_Grid2';

import { useEffect, useState, useCallback } from 'react';
import { useSelector, useDispatch } from 'react-redux';

import { setAlert } from '../../../../redux/actions/alertActions';

import axios from 'axios';

const EditCar = (props) => {
    const [enums, setEnums] = useState(null);
    const alert = useSelector(state => state.alert);
    const dispatch = useDispatch();

    const [price, setPrice] = useState(props.car.pricePerDay ? props.car.pricePerDay.toFixed(2) : '');
    const [condition, setCondition] = useState(props.car.condition ? props.car.condition : '');

    const handleChangePrice = (event) => {
        setPrice(event.target.value);
    };
    const handleChangeCondition = (event) => {
        setCondition(event.target.value);
    };

    const getEnums = useCallback(async () => {
        try {
            const response = await axios.get('http://localhost:8086/car/enums');
            if (response.status === 200) {
                setEnums(response.data);
            }
        }
        catch (error) {
            console.error('Error fetching data:', error);
        } 
    }, [])

    useEffect(() => {
        getEnums();
        dispatch(setAlert(null));
    }, [dispatch, getEnums])


    const editCar = async () => {
        const content = {
            condition: condition,
            pricePerDay: parseFloat(price).toFixed(2),
        }
        
        if(Object.values(content).some(value => value === '')) {
            dispatch(setAlert('The form must be completed!'));
        }
        else if(isNaN(content.pricePerDay)) {
            dispatch(setAlert('Price must be number'));
        }
        else {
            try {
                const response = await axios.patch(`http://localhost:8086/car/${props.car.id}/edit`, content);
                if (response.status === 200) {
                    // console.log(response.data);
                    props.handleClose();
                    props.makeUpdate();
                }
            }
            catch (error) {
                dispatch(setAlert("Edit not make"));
            } 
        }
    }

    return (
        <Grid container justifyContent='center' sx={{marginTop: '5vh'}}>
            <Grid xs={7} xl={4}>
                <Paper elevation={12} sx={{padding: '3.5rem'}}>
                    <Stack
                        direction="row"
                        justifyContent="flex-end"
                    >
                        <IconButton size="large" onClick={props.handleClose}>
                            <CloseIcon fontSize="large" />
                        </IconButton>
                    </Stack>
                    <Grid container justifyContent="center" spacing={3}>
                        <Grid xs={12}>
                            <Typography variant="h3" color="textPrimary" align="center" >Edit car</Typography>
                        </Grid>
                        <Grid xs={12}>
                            <Divider/>
                        </Grid>
                        {alert ? (
                            <Grid xs={12}>
                                <Alert severity="error" variant="filled">{alert}</Alert>
                            </Grid>
                        ) : null}
                        <Grid xs={6} >
                            {enums ? (
                                <FormControl fullWidth>
                                    <InputLabel id="condsition-label">Condition</InputLabel>
                                    <Select
                                        labelId="condsition-label"
                                        label="Condition"
                                        value={condition} 
                                        onChange={handleChangeCondition}
                                    >
                                        {enums.condition.map((item, index) => (
                                            <MenuItem key={index} value={item}>{item}</MenuItem>
                                        ))}
                                    </Select>
                                </FormControl>
                            ) : (
                                <Skeleton variant="rectangular" height="100%"/>
                            )}
                        </Grid>
                        <Grid xs={6}>
                            <TextField
                                fullWidth
                                label="Price per day"
                                InputProps={{
                                    endAdornment: (
                                        <InputAdornment position='end'>
                                            CC
                                        </InputAdornment>
                                    ),
                                }}
                                value={price} 
                                onChange={handleChangePrice}
                            />
                        </Grid>
                        <Grid>
                            <Button variant="contained" size="large" color="button_primary" onClick={editCar}>
                                Edit
                            </Button>
                        </Grid>
                    </Grid>
                </Paper>
            </Grid>
        </Grid>
    );
}

export default EditCar;