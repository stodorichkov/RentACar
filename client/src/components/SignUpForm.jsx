import { Grid, Paper, Typography, TextField, Divider, Button, Alert } from '@mui/material';
import { useState } from 'react';
import axios from 'axios';
import { useNavigate } from "react-router-dom";

const SignUpForm = () => {
    const [username, setUsername] = useState('');
    const [age, setAge] = useState('');
    const [email, setEmail] = useState('');
    const [phone, setPhone] = useState('');
    const [password, setPassword] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');
    const [alert, setAlert] = useState('');

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

    const navigate = useNavigate();

    const addUser = async () => {
        const passwordRegex = /^(?=.*[A-Z])(?=.*[a-z])(?=.*\d)(?=.*[!@#$%^&*])[A-Za-z\d!@#$%^&*].{7,}$/;
        if (username === '' || age === '' || email === '' || phone === '' || password === '' || confirmPassword === '') {
            setAlert('The form must be completed!');
        }
        else if (!passwordRegex.test(password)) {
            setAlert('Password is not valid! It must containst min 8 charecters, one special and numbers');
        }
        else if (password !== confirmPassword) {
            setAlert('Password not confirmed!');
        }
        else {
            const content = {
                username: username,
                email: email,
                years: age,
                mobilePhone: phone,
                password: password
            };
            try {
                const response = await axios.post('http://localhost:8086/user/register', content);
                if (response.status === 200) {
                    console.log(response.data);
                    navigate('/signin');
                }
            }
            catch (error) {
                setAlert(error.response.data)
            } 
        }
    }

    return (
        <Grid container justifyContent="center" sx={{marginTop: '2vw'}}>
            <Grid item xs={12} md={5} xl={4}>
                <Paper elevation={12} sx={{padding: '3em', maxHeight: '84vh', overflow: 'auto'}}>
                    <Grid container spacing={4} justifyContent="center">
                        <Grid item xs={12}>
                            <Typography variant="h3" color="textPrimary" align="center" >Sign Up</Typography>
                        </Grid>
                        <Grid item xs={12}>
                            <Divider/>
                        </Grid>
                        {alert ? (
                            <Grid item xs={12}>
                                <Alert severity="error" variant="filled">{alert}</Alert>
                            </Grid>
                        ) : null}
                        <Grid item xs={12}>
                            <TextField
                                fullWidth
                                label="Username"
                                value = {username}
                                onChange ={handleChangeUsername}
                            />
                        </Grid>
                        <Grid item xs={12}>
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
                        <Grid item xs={12}>
                            <TextField
                                fullWidth
                                label="Email"
                                value = {email}
                                onChange ={handleChangeEmail}
                            />
                        </Grid>
                        <Grid item xs={12}>
                            <TextField
                                fullWidth
                                label="Phone number"
                                value = {phone}
                                onChange ={handleChangePhone}
                            />
                        </Grid>
                        <Grid item xs={12}>
                            <TextField
                                fullWidth
                                label="Password"
                                type="password"
                                value = {password}
                                onChange ={handleChangePassword}
                            />
                        </Grid>
                        <Grid item xs={12}>
                            <TextField
                                fullWidth
                                label="Confirm password"
                                type="password"
                                value = {confirmPassword}
                                onChange ={handleChangeConfirmPassword}
                            />
                        </Grid>
                        <Grid item >
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
