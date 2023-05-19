import { Grid, Paper, Typography, TextField, Divider, Button, Container, Alert } from '@mui/material';
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
        <Container maxWidth="sm" sx={{marginTop: "8rem", marginBottom: "2rem"}}>
            <Paper elevation={12} sx={{padding: '2rem'}}>
                <Grid container direction="column"  spacing={2.5}>
                    <Grid item xs={12} sm={6} md={6}>
                        <Typography variant="h3" color="textPrimary" align="center" >Sign Up</Typography>
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
                        <TextField
                            fullWidth
                            label="Username"
                            value = {username}
                            onChange ={handleChangeUsername}
                        />
                    </Grid>
                    <Grid item xs={12} sm={6} md={6}>
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
                    <Grid item xs={12} sm={6} md={6}>
                        <TextField
                            fullWidth
                            label="Email"
                            value = {email}
                            onChange ={handleChangeEmail}
                        />
                    </Grid>
                    <Grid item xs={12} sm={6} md={6}>
                        <TextField
                            fullWidth
                            label="Phone number"
                            value = {phone}
                            onChange ={handleChangePhone}
                        />
                    </Grid>
                    <Grid item xs={12} sm={6} md={6}>
                        <TextField
                            fullWidth
                            label="Password"
                            type="password"
                            value = {password}
                            onChange ={handleChangePassword}
                        />
                    </Grid>
                    <Grid item xs={12} sm={6} md={6}>
                        <TextField
                            fullWidth
                            label="Confirm password"
                            type="password"
                            value = {confirmPassword}
                            onChange ={handleChangeConfirmPassword}
                        />
                    </Grid>
                    <Grid item xs={12} sm={6} md={6} alignSelf={'center'}>
                        <Button variant="contained" size="large" color="button_secondary" onClick={addUser}>
                            Sign Up
                        </Button>
                    </Grid>
                </Grid>
            </Paper>
        </Container>
    )
}

export default SignUpForm;
