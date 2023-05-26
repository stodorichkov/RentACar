import { Paper, Typography, TextField, Divider, Button, Alert, Link, FormControl, InputLabel, OutlinedInput, InputAdornment, IconButton } from '@mui/material';
import Grid from '@mui/material/Unstable_Grid2';
import Visibility from '@mui/icons-material/Visibility';
import VisibilityOff from '@mui/icons-material/VisibilityOff';

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
    const [showPass, setShowPass] = useState(false);

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
        const content = {
            username: username,
            password: password
        };
        
        if(Object.values(content).some(value => value === '')) {
            dispatch(setAlert('The form must be completed!'));
        }
        else {
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
        <Grid container justifyContent='center' sx={{marginTop: '4vh'}}>
            <Grid xs={10} sm={7.5} md={6.5} lg={4.5} xl={3.7}>
                <Paper elevation={12} sx={{padding: '3.5rem'}}>    
                    <Grid container spacing={4} justifyContent='center'>
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
                        <Grid>
                            <Button variant="contained" size="large" color="button_primary" onClick={signInUser}>
                                Sign In
                            </Button>
                        </Grid>
                        <Grid xs={12}>
                            <Divider sx={{backgroundColor: theme.palette.menu.main}}/>
                        </Grid>
                        <Grid xs={12}>
                            <Typography variant="body2" align="center">
                                Don't have an account?  <Link onClick={() => navigate('/signup')}>Sign Up</Link>
                            </Typography>
                        </Grid>
                    </Grid>
                </Paper>
            </Grid>
        </Grid>
    )
}

export default SignInForm;