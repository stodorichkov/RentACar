import { Paper, Typography, TextField, Divider, Button, Select, MenuItem, InputLabel, FormControl, IconButton, Stack, Skeleton, Input, Alert } from '@mui/material';
import CloseIcon from '@mui/icons-material/Close';
import Grid from '@mui/material/Unstable_Grid2';

import { useEffect, useState, useCallback } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { useNavigate } from "react-router-dom";

import { setAlert } from '../../../../redux/actions/alertActions';
import { signOutAction } from '../../../../redux/actions/userActions';

import axios from 'axios';


const AddCarForm = (props)  => {
    const user = useSelector((state) => state.user);
    const alert = useSelector(state => state.alert);
    const dispatch = useDispatch();
    const navigate = useNavigate();
    const [enums, setEnums] = useState(null);

    const [brand, setBrand] = useState('');
    const [model, setModel] = useState('');
    const [plate, setPlate] = useState('');
    const [condition, setCondition] = useState('');
    const [price, setPrice] = useState('');
    const [img, setImg] = useState('');
    const [imgName, setImgName] = useState('');
    const [seats, setSeats] = useState(2);
    const [transmission, setTransmission] = useState('');
    const [engine, setEngine] = useState('');
    const [fuelConsumption, setFuelConsumption] = useState('');

    const handleChangeBrand = (event) => {
        setBrand(event.target.value);
    };
    const handleChangeModel = (event) => {
        setModel(event.target.value);
    };
    const handleChangePlate = (event) => {
        setPlate(event.target.value);
    }; 
    const handleChangeCondition = (event) => {
        setCondition(event.target.value);
    };
    const handleChangePrice = (event) => {
        setPrice(event.target.value);
    };
    const handleChangeImg = (event) => {
        if (event.target.files[0]) {
            const reader = new FileReader();
            reader.onloadend = () => {
                setImg(reader.result);
                setImgName(event.target.files[0].name);
            };
            reader.readAsDataURL(event.target.files[0]);
        } else {
            setImg('');
            setImgName('');
        }
    };
    const handleChangeSeats = (event) => {
        if(!parseInt(event.target.value) || parseInt(event.target.value) < 2) {
            setSeats(2);
        }
        else if (parseInt(event.target.value) > 10) {
            setSeats(10);
        }
        else {
            setSeats(event.target.value);
        }
        
    };
    const handleChangeTransmission = (event) => {
        setTransmission(event.target.value);
    };
    const handleChangeEngine = (event) => {
        setEngine(event.target.value);
    };
    const handleChangeFuelConsumption = (event) => {
        setFuelConsumption(event.target.value);
    };

    axios.defaults.headers.common['Authorization'] = `Bearer ${user.token}`;

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

    const addCar = async () => {
        const content = {
            make: brand,
            model: model,
            registrationPlate: plate,
            carCondition: condition,
            pricePerDay: parseFloat(price).toFixed(2),
            imageUrl: img,
            capacity: seats,
            transmissionEnum: transmission,
            engine: engine,
            fuelConsumption: fuelConsumption
        }
        
        if(Object.values(content).some(value => value === '')) {
            dispatch(setAlert('The form must be completed!'));
        }
        else if(isNaN(content.pricePerDay)) {
            dispatch(setAlert('Price must be number'));
        }
        else {
            try {
                axios.defaults.headers.common['Authorization'] = `Bearer ${user.token}`;
                const response = await axios.post('http://localhost:8086/car/add', content);
                if (response.status === 200) {
                    console.log("Succes")
                }
            }
            catch (error) {
                if(error.response.status ) {
                    props.handleClose();
                    dispatch(signOutAction());
                    navigate('/signin');
                }
                dispatch(setAlert(error.response.data));
            } 
        }
    }

    useEffect(() => {
        getEnums();
        dispatch(setAlert(null));
    }, [dispatch, getEnums])

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
                            <Typography variant="h3" color="textPrimary" align="center" >Add car</Typography>
                        </Grid>
                        <Grid xs={12}>
                            <Divider/>
                        </Grid>
                        {alert ? (
                            <Grid xs={12}>
                                <Alert severity="error" variant="filled">{alert}</Alert>
                            </Grid>
                        ) : null}
                        <Grid xs={7}>
                            <TextField
                                fullWidth
                                label="Brand"
                                value={brand}
                                onChange={handleChangeBrand}
                            />
                        </Grid>
                        <Grid xs={5}>
                            <TextField
                                fullWidth
                                label="Model"
                                value={model}
                                onChange={handleChangeModel}
                            />
                        </Grid>
                        <Grid xs={12}>
                            <TextField
                                fullWidth
                                label="Registration plate"
                                value={plate}
                                onChange={handleChangePlate}
                            />
                        </Grid>
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
                                value={price} 
                                onChange={handleChangePrice}
                            />
                        </Grid>
                        <Grid xs={12}>
                            <Input
                                type="file"
                                onChange={handleChangeImg}
                                inputProps={{
                                    accept: 'image/*',
                                    id: 'image-upload',
                                }}
                                sx={{ display: 'none' }}
                            />
                            <label htmlFor="image-upload">
                                <Button variant="contained" size="large" color="button_secondary" component="span" fullWidth sx={{ textTransform: 'none' }} >
                                    {img ? "Selected Image: " + imgName : "Upload Image"}
                                </Button>
                            </label>
                        </Grid>
                        <Grid xs={6}>
                            <TextField
                                fullWidth
                                type="number"
                                value={seats}
                                onChange={handleChangeSeats}
                                InputProps={{
                                    inputProps: { 
                                        max: 10, min: 2 
                                    }
                                }}
                                label="Seats number"
                            />
                        </Grid>
                        <Grid xs={6} >
                            {enums ? (
                                <FormControl fullWidth>
                                    <InputLabel id="transmition-label">Transmission</InputLabel>
                                    <Select
                                        labelId="transmission-label"
                                        label="Transmission"
                                        value={transmission} 
                                        onChange={handleChangeTransmission}
                                    >
                                        {enums.transmission.map((item, index) => (
                                            <MenuItem key={index} value={item}>{item}</MenuItem>
                                        ))}
                                    </Select>
                                </FormControl>
                            ) : (
                                <Skeleton variant="rectangular" height="100%"/>
                            )}
                        </Grid>
                        <Grid xs={6} >
                            {enums ? (
                                <FormControl fullWidth>
                                    <InputLabel id="engine-label">Engine</InputLabel>
                                    <Select
                                        labelId="engine-label"
                                        label="Engine"
                                        value={engine} 
                                        onChange={handleChangeEngine}
                                    >
                                        {enums.engine.map((item, index) => (
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
                                label="Fuel consumption"
                                value={fuelConsumption} 
                                onChange={handleChangeFuelConsumption}
                            />
                        </Grid>
                        <Grid>
                            <Button variant="contained" size="large" color="button_primary" onClick={addCar}>
                                Add car
                            </Button>
                        </Grid>
                    </Grid>
                </Paper>
            </Grid>
        </Grid>
    );
};

export default AddCarForm;