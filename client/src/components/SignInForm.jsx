import { Grid, Paper, Typography, TextField, Divider, Button, Container, Alert } from '@mui/material';
import { useState } from 'react';
import { SHA256 } from 'crypto-js';
import axios from 'axios';
import { useNavigate } from "react-router-dom";

const SignInForm = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [alert, setAlert] = useState('');

    const handleChangeUsername = (event) => {
        setUsername(event.target.value);
    }
    const handleChangePassword = (event) => {
        setPassword(event.target.value);
    }

    const navigate = useNavigate();

    const signInUser = async () => {
        if (username === '' || password === '') {
            setAlert('The form must be completed!');
        }
        else {
            const content = {
                username: username,
                password: SHA256(password).toString()
            };
            try {
                const response = await axios.post('http://localhost:8086/user/login', content);
                if (response.status === 200) {
                    console.log(response);
                    navigate('/');
                }  
            }
            catch (error) {
                // setAlert(error.response.data)
                console.log(error)
            } 
        }
    }

    return (
        <>
            <Grid container sx={{backgroundSize: 'cover', background: 'linear-gradient(rgba(48,94,171, 0.7), rgba(115, 35, 101, 0.7))', height: "100vh"}}>
                <Container maxWidth="sm" sx={{marginTop: "7.5rem", marginBottom: '1rem'}}>
                    <Paper elevation={12} sx={{padding: '3.5rem'}}>
                        <Grid container direction="column"  spacing={5}>
                            <Grid item xs={12} sm={6} md={6}>
                                <Typography variant="h3" color="textPrimary" align="center" >Sign In</Typography>
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
                                    label="Password"
                                    type="password"
                                    value = {password}
                                    onChange ={handleChangePassword}
                                />
                            </Grid>
                            <Grid item xs={12} sm={6} md={6} alignSelf={'center'}>
                                <Button variant="contained" size="large" color="button_primary" onClick={signInUser}>
                                    Sign In
                                </Button>
                            </Grid>
                        </Grid>
                    </Paper>
                </Container>
            </Grid>
        </> 
    )
}

export default SignInForm;