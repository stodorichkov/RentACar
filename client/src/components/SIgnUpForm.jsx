import { Grid, Paper, Typography, TextField, Divider, Button, Container, Alert } from '@mui/material';
import { useState } from 'react';
import { hashString } from 'react-hash-string';
import axios from 'axios';


const SignUpForm = () => {
    const [username, SetUsername] = useState('');
    const [age, SetAge] = useState('');
    const [email, SetEmail] = useState('');
    const [phone, SetPhone] = useState('');
    const [password, SetPassword] = useState('');
    const [confirmPassword, SetConfirmPassword] = useState('');
    const [alert, setAlert] = useState('')

    const handleChangeUsername = (event) => {
        SetUsername(event.target.value)
    }
    const handleChangeAge = (event) => {
        SetAge(event.target.value)
    }
    const handleChangeEmail = (event) => {
        SetEmail(event.target.value)
    }
    const handleChangePhone = (event) => {
        SetPhone(event.target.value)
    }
    const handleChangePassword = (event) => {
        SetPassword(event.target.value)
    }
    const handleChangeConfirmPassword = (event) => {
        SetConfirmPassword(event.target.value)
    }

    const addUser = async () => {
        if (username === '' || age === '' || email === '' || phone === '' || password === '') {
            setAlert('The form must be completed!')
        }
        else {
            const content = {
                username: username,
                email: email,
                phone: phone,
                password: hashString(password)
            }
            const response = await axios.post('http://localhost:8086/user/register', content)
            if (response.data !== "Registration was successful") {
                setAlert(response.data)
            }
        }
    }

    return (
        <>
            <Grid container sx={{backgroundSize: 'cover', background: 'linear-gradient(rgba(48,94,171, 0.7), rgba(115, 35, 101, 0.7))'}}>
                <Container maxWidth="sm" sx={{marginTop: "7.5rem", marginBottom: '1rem'}}>
                    <Paper elevation={12} sx={{padding: '2rem'}}>
                        <Grid container direction="column"  spacing={3.5}>
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
                                <Button variant="contained" size="large" color="secondary" onClick={addUser}>
                                    Sign Up
                                </Button>
                            </Grid>
                        </Grid>
                    </Paper>
                </Container>
            </Grid>
        </> 
    )
}

export default SignUpForm;