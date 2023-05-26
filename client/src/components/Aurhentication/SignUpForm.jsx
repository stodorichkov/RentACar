import { Paper, Typography, TextField, Divider, Button, Alert, FormControl, OutlinedInput, InputLabel, InputAdornment, IconButton, Link } from '@mui/material';
import Grid from '@mui/material/Unstable_Grid2';
import Visibility from '@mui/icons-material/Visibility';
import VisibilityOff from '@mui/icons-material/VisibilityOff';

import { useState, useEffect } from 'react';
import { useTheme } from '@mui/material/styles';
import { useNavigate } from "react-router-dom";
import { useDispatch, useSelector } from 'react-redux';

import axios from 'axios';

import { setAlert } from '../../redux/actions/alertActions';

const SignUpForm = () => {
    const [username, setUsername] = useState('');
    const [firstName, setFirstName] = useState('');
    const [lasttName, setLastName] = useState('');
    const [email, setEmail] = useState('');
    const [phone, setPhone] = useState('');
    const [age, setAge] = useState(18);
    const [password, setPassword] = useState('');
    const [showPass, setShowPass] = useState(false);
    const [confirmPassword, setConfirmPassword] = useState('');
    const [showConfPass, setShowConfPass] = useState(false);

    const alert = useSelector(state => state.alert);
    const dispatch = useDispatch();
    const navigate = useNavigate();
    const theme = useTheme();

    const handleChangeUsername = (event) => {
        setUsername(event.target.value);
    }
    const handleChangeFirstName = (event) => {
        setFirstName(event.target.value);
    }
    const handleChangeLastName = (event) => {
        setLastName(event.target.value);
    }
    const handleChangeEmail = (event) => {
        setEmail(event.target.value);
    }
    const handleChangePhone = (event) => {
        setPhone(event.target.value);
    }
    const handleChangeAge = (event) => {
        if(!parseInt(event.target.value) || parseInt(event.target.value) < 2) {
            setAge(18);
        }
        else if (parseInt(event.target.value) > 65) {
            setAge(65);
        }
        else {
            setAge(event.target.value);
        }
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
        const content = {
            username: username,
            email: email,
            fullName: `${firstName} ${lasttName}`,
            years: age,
            mobilePhone: phone,
            password: password,
            confirmPassword: confirmPassword
        };
        
        if(Object.values(content).some(value => value === '')) {
            dispatch(setAlert('The form must be completed!'));
        }
        else {
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
        <Grid container justifyContent='center' sx={{marginTop: '4vh'}}>
            <Grid xs={10} sm={7.5} md={6.5} lg={4.5} xl={3.7}>
                <Paper elevation={12} sx={{padding: '3em',overflow: 'auto', maxHeight: {xl:'94vh', lg: '85vh'}}}>
                    <Grid container spacing={3.5} justifyContent="center">
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
                        <Grid xs={6}>
                            <TextField
                                fullWidth
                                label="First name"
                                value = {firstName}
                                onChange ={handleChangeFirstName}
                            />
                        </Grid>
                        <Grid xs={6}>
                            <TextField
                                fullWidth
                                label="Last name"
                                value = {lasttName}
                                onChange ={handleChangeLastName}
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
                        <Grid xs={9}>
                            <TextField
                                fullWidth
                                label="Phone number"
                                value = {phone}
                                onChange ={handleChangePhone}
                            />
                        </Grid>
                        <Grid xs={3}>
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
                        <Grid xs={6}>
                            <FormControl variant="outlined" fullWidth>
                                <InputLabel htmlFor="password">Password</InputLabel>
                                <OutlinedInput
                                    id="password"
                                    type={showPass ? 'text' : 'password'}
                                    endAdornment={
                                        <InputAdornment position="end">
                                            <IconButton
                                            onClick={() => setShowPass(!showPass)}
                                            edge="end"
                                            >
                                            {showPass ? <VisibilityOff /> : <Visibility />}
                                            </IconButton>
                                        </InputAdornment>
                                    }
                                    label="Password"
                                    value = {password}
                                    onChange ={handleChangePassword}
                                />
                            </FormControl>
                        </Grid>
                        <Grid xs={6}>
                            <FormControl variant="outlined" fullWidth>
                                <InputLabel htmlFor="confPassword">Confirm password</InputLabel>
                                <OutlinedInput
                                    id="confPassword"
                                    type={showConfPass ? 'text' : 'password'}
                                    endAdornment={
                                        <InputAdornment position="end">
                                            <IconButton
                                            aria-label="toggle password visibility"
                                            onClick={() => setShowConfPass(!showConfPass)}
                                            edge="end"
                                            >
                                            {showConfPass ? <VisibilityOff /> : <Visibility />}
                                            </IconButton>
                                        </InputAdornment>
                                    }
                                    label="Confirm password"
                                    value = {confirmPassword}
                                    onChange ={handleChangeConfirmPassword}
                                />
                            </FormControl>
                        </Grid>
                        <Grid>
                            <Button variant="contained" size="large" color="button_secondary" onClick={addUser}>
                                Sign Up
                            </Button>
                        </Grid>
                        <Grid xs={12}>
                            <Divider sx={{backgroundColor: theme.palette.menu.main}}/>
                        </Grid>
                        <Grid xs={12}>
                            <Typography variant="body2" align="center">
                                Already have an account? <Link onClick={() => navigate('/signin')}>Sign In</Link>
                            </Typography>
                        </Grid>
                    </Grid>    
                </Paper>
            </Grid>
        </Grid>
    )
}

export default SignUpForm;
