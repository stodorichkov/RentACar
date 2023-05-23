import { Paper, Typography, TextField, Divider, Button, Alert } from '@mui/material';
import Grid from '@mui/material/Unstable_Grid2';

import { useState, useEffect } from 'react';
import { useTheme } from '@mui/material/styles';
import { useNavigate } from "react-router-dom";
import { useDispatch, useSelector } from 'react-redux';

import axios from 'axios';

import { setAlert } from '../../redux/actions/alertActions';

const SignUpForm = () => {
    const [username, setUsername] = useState('');
    const [age, setAge] = useState('');
    const [email, setEmail] = useState('');
    const [phone, setPhone] = useState('');
    const [password, setPassword] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');
    const alert = useSelector(state => state.alert);
    const dispatch = useDispatch();
    const navigate = useNavigate();
    const theme = useTheme();

    const handleChangeUsername = (event) => {
        setUsername(event.target.value);
    }
    const handleChangeAge = (event) => {
        setAge(event.target.value);
    }
    const handleChangeEmail = (event) => {
        setEmail(event.target.value);
    }
    const handleChangePhone = (event) => {
        setPhone(event.target.value);
    }
    const handleChangePassword = (event) => {
        setPassword(event.target.value);
    }
    const handleChangeConfirmPassword = (event) => {
        setConfirmPassword(event.target.value);
    }

    useEffect(() => {
        dispatch(setAlert(null));
    }, [dispatch]);

    const addUser = async () => {
        if (username === '' || age === '' || email === '' || phone === '' || password === '' || confirmPassword === '') {
            dispatch(setAlert('The form must be completed!'));
        }
        else {
            const content = {
                username: username,
                email: email,
                years: age,
                mobilePhone: phone,
                password: password,
                confirmPassword: confirmPassword
            };

            try {
                const response = await axios.post('http://localhost:8086/user/register', content);
                if (response.status === 200) {
                    navigate('/signin');
                }
            }
            catch (error) {
                dispatch(setAlert(error.response.data));
            } 
        }
    }

    return (
        <Grid container justifyContent='center' sx={{marginTop: '2vw' }}>
            <Grid xl={3.5} lg={4} md={5} sm={7} xs={10}>
                <Paper elevation={12} sx={{padding: '3em', overflow: 'auto', maxHeight: {xs: '80vh', sm:'80vh', md: '80vh', lg: '85vh', xl: '80vh'}}}>
                    <Grid container spacing={4} justifyContent="center">
                        <Grid xs={12}>
                            <Typography variant="h3" color="textPrimary" align="center" >Sign Up</Typography>
                        </Grid>
                        <Grid xs={12}>
                            <Divider sx={{backgroundColor: theme.palette.menu.main}}/>
                        </Grid>
                        {alert ? (
                            <Grid xs={12}>
                                <Alert severity="error" variant="filled">{alert}</Alert>
                            </Grid>
                        ) : null}
                        <Grid xs={12}>
                            <TextField
                                fullWidth
                                label="Username"
                                value = {username}
                                onChange ={handleChangeUsername}
                            />
                        </Grid>
                        <Grid xs={12}>
                            <TextField
                                fullWidth
                                type="number"
                                InputProps={{
                                    inputProps: { 
                                        max: 65, min: 18 
                                    }
                                }}
                                label="Age"
                                value = {age}
                                onChange ={handleChangeAge}
                            />
                        </Grid>
                        <Grid xs={12}>
                            <TextField
                                fullWidth
                                label="Email"
                                value = {email}
                                onChange ={handleChangeEmail}
                            />
                        </Grid>
                        <Grid xs={12}>
                            <TextField
                                fullWidth
                                label="Phone number"
                                value = {phone}
                                onChange ={handleChangePhone}
                            />
                        </Grid>
                        <Grid xs={12}>
                            <TextField
                                fullWidth
                                label="Password"
                                type="password"
                                value = {password}
                                onChange ={handleChangePassword}
                            />
                        </Grid>
                        <Grid xs={12}>
                            <TextField
                                fullWidth
                                label="Confirm password"
                                type="password"
                                value = {confirmPassword}
                                onChange ={handleChangeConfirmPassword}
                            />
                        </Grid>
                        <Grid>
                            <Button variant="contained" size="large" color="button_secondary" onClick={addUser}>
                                Sign Up
                            </Button>
                        </Grid>
                    </Grid>    
                </Paper>
            </Grid>
        </Grid>
    )
}

export default SignUpForm;
