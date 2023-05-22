import { Paper, Typography, TextField, Divider, Button, Alert } from '@mui/material';
import Grid from '@mui/material/Unstable_Grid2';

import axios from 'axios';

import { useState } from 'react';
import { useNavigate } from "react-router-dom";
import { useDispatch } from 'react-redux';
import { useTheme } from '@mui/material/styles';

import { signInAction } from '../../redux/actions/userActions';

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
    const dispatch = useDispatch();
    const theme = useTheme();

    const signInUser = async () => {
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
                    // dispatch(signInAction("skfsj"));
                    navigate('/');
                }  
            }
            catch (error) {
                // setAlert(error.response.data)
                console.log(error)
            } 
        }
        // dispatch(signInAction("skfsj"));
        // navigate('/');
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
                            <Divider sx={{backgroundColor: theme.palette.menue.main}}/>
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