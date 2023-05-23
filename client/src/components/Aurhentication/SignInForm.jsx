import { Paper, Typography, TextField, Divider, Button, Alert } from '@mui/material';
import Grid from '@mui/material/Unstable_Grid2';

import { useEffect, useState } from 'react';
import { useNavigate } from "react-router-dom";
import { useDispatch, useSelector } from 'react-redux';
import { useTheme } from '@mui/material/styles';

import axios from 'axios';

import { signInAction } from '../../redux/actions/userActions';
import { setAlert } from '../../redux/actions/alertActions';

const SignInForm = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const alert = useSelector(state => state.alert);
    const navigate = useNavigate();
    const dispatch = useDispatch();
    const theme = useTheme();

    const handleChangeUsername = (event) => {
        setUsername(event.target.value);
    }
    const handleChangePassword = (event) => {
        setPassword(event.target.value);
    }

    useEffect(() => {
        dispatch(setAlert(null));
    }, [dispatch]);

    const signInUser = async () => {
        if (username === '' || password === '') {
            dispatch(setAlert('The form must be completed!'));
        }
        else {
            const content = {
                username: username,
                password: password
            };

            try {
                const response = await axios.post('http://localhost:8086/user/login', content);
                if (response.status === 200) {
                    dispatch(signInAction(response.data));
                    navigate('/');
                }  
            }
            catch (error) {
                dispatch(setAlert('Wrong username or password'));
            } 
        }
    }

    return (
        <Grid container justifyContent='center' sx={{marginTop: '2vw'}}>
            <Grid xs={10} sm={7.5} md={6.5} lg={4.5} xl={3.5}>
                <Paper elevation={12} sx={{padding: '3.5rem'}}>    
                    <Grid container spacing={3} justifyContent='center'>
                        <Grid xs={12}>
                            <Typography variant="h3" color="textPrimary" align="center" >Sign In</Typography>
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