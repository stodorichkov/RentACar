import { Paper, TextField, Divider, Button, Avatar, InputAdornment, Stack }  from '@mui/material';
import Grid from '@mui/material/Unstable_Grid2';
import { useState } from 'react';
import axios from 'axios';

const UserInfo = () => {
    const [username, setUsername] = useState('');
    const [age, setAge] = useState('');
    const [email, setEmail] = useState('');
    const [phone, setPhone] = useState('');
    const [budget, setBudget] = useState(0.00);
    const [transfer, setTransfer] = useState(0.00);
    const [editMode, setEditMode] = useState(false);
    const [addMoneyMode, setAddMoneyMode] = useState(false);

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
    const handleChangeTransfer = (event) => {
        setTransfer(parseFloat(event.target.value));
    }
    const switchEditMode = () => {
        setEditMode(!editMode);
    }
    const switchAddMoneyMode = () => {
        setAddMoneyMode(!addMoneyMode);
        setTransfer(0.00);
    }

    const transferMoney = () => {
        setBudget(budget + transfer)
        switchAddMoneyMode()
        console.log(budget)
    }

    return(
        <Grid xl={3.5} lg={4} md={5} sm={7} xs={10}>
            <Paper elevation={12} sx={{padding: '3.5rem'}}>    
                <Grid container spacing={3} justifyContent="center">
                    <Grid>
                        <Avatar
                            sx={{
                                backgroundColor: "rgb(116, 34, 102)",
                                width: 100,
                                height: 100,
                            }}
                        >
                            {username[0]}
                        </Avatar>
                    </Grid>
                    <Grid xs={12}>
                        <Divider/>
                    </Grid>
                    <Grid xs={12}>
                        <TextField
                            fullWidth
                            InputProps={{
                                readOnly: !editMode,
                            }}
                            label="Username"
                            value={username}
                            onChange={handleChangeUsername}
                        />
                    </Grid>
                    <Grid xs={12}>
                        <TextField
                            fullWidth
                            type="number"
                            InputProps={{
                                readOnly: !editMode,
                                inputProps: { 
                                    max: 65, 
                                    min: 18,
                                        
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
                            InputProps={{
                                readOnly: !editMode,
                            }}
                            label="Email"
                            value = {email}
                            onChange ={handleChangeEmail}
                        />
                    </Grid>
                    <Grid xs={12}>
                        <TextField
                            fullWidth
                            InputProps={{
                                readOnly: !editMode,
                            }}
                            label="Phone number"
                            value = {phone}
                            onChange ={handleChangePhone}
                        />
                    </Grid>
                    <Grid >
                        {
                            !editMode ? (
                                <Button variant="contained" size="large" color="button_primary" onClick={switchEditMode}>
                                    Edit
                                </Button>
                            ) : (
                                <Stack spacing={4} direction="row">
                                    <Button variant="contained"  color="button_primary" size="large" >
                                        Save
                                    </Button>
                                    <Button variant="contained"  color="button_secondary" size="large" onClick={switchEditMode}>
                                        Cancel
                                    </Button>
                                </Stack>
                            )
                        }
                    </Grid>
                    <Grid xs={12}>
                        <TextField
                            fullWidth
                            label="Budget"
                            type="number"
                            value={parseFloat(budget).toFixed(2)}
                            InputProps={{
                                startAdornment: (
                                <InputAdornment position="start">
                                    CC
                                </InputAdornment>
                                ),
                                readOnly: true,
                            }}
                        />
                    </Grid>
                    <Grid >
                        {
                            !addMoneyMode ? (
                                <Button variant="contained" size="large" color="button_primary" onClick={switchAddMoneyMode}>
                                    Trnasfer money
                                </Button>
                            ) : (
                                <Stack spacing={2} direction="row">
                                    <TextField
                                        label="Transfer money"
                                        type="number"
                                        onChange={handleChangeTransfer }
                                        value={parseFloat(transfer).toFixed(2)}
                                        InputProps={{
                                            startAdornment: (
                                            <InputAdornment position="start">
                                                CC
                                            </InputAdornment>
                                            ),
                                            inputProps: { 
                                                min: 0.00,
                                                step: 0.01
                                            }
                                        }}
                                    />
                                    <Button variant="contained"  color="button_primary" size="large" onClick={transferMoney}>
                                        Transfer
                                    </Button>
                                    <Button variant="contained"  color="button_secondary" size="large" onClick={switchAddMoneyMode}>
                                        Cancel
                                    </Button>
                                </Stack>
                            )
                        }
                    </Grid>
                </Grid>
            </Paper>
        </Grid>
    );
}

export default UserInfo;