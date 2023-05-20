import { Paper, Typography, TextField, Divider, Button, Alert } from '@mui/material';
import Grid from '@mui/material/Unstable_Grid2';
import { useState } from 'react';
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
        console.log(password)
        if (username === '' || password === '') {
            setAlert('The form must be completed!');
        }
        else {
            const content = {
                username: username,
                password: password
            };
            try {
                const response = await axios.post('http://localhost:8086/user/login', content);
                if (response.status === 200) {
                    console.log(response);
                    navigate('/');
                }  
            }
            catch (error) {
                setAlert(error.response.data)
            } 
        }
    }

    return (
        <Grid container justifyContent='center' sx={{marginTop: '2vw'}}>
            <Grid xl={3.5} lg={4} md={5} sm={7} xs={10}>
                <Paper elevation={12} sx={{padding: '3.5rem'}}>    
                    <Grid container spacing={3} justifyContent='center'>
                        <Grid xs={12}>
                            <Typography variant="h3" color="textPrimary" align="center" >Sign In</Typography>
                        </Grid>
                        <Grid xs={12}>
                            <Divider/>
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
                                value={username}
                                onChange={handleChangeUsername}
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
                        <Grid>
                            <Button variant="contained" size="large" color="button_primary" onClick={signInUser}>
                                Sign In
                            </Button>
                        </Grid>
                    </Grid>
                </Paper>
            </Grid>
        </Grid>
    )
}

export default SignInForm;