import { Paper, TextField, Divider, Button, Avatar, InputAdornment, Stack, Rating }  from '@mui/material';
import Grid from '@mui/material/Unstable_Grid2';

import { useEffect, useState } from 'react';
import { useTheme } from '@mui/material/styles';
import { useSelector } from 'react-redux';

import axios from 'axios';

const UserInfo = () => {
    const user = useSelector((state) => state.user);
    const theme = useTheme();

    const [score, setScore] = useState(0);
    const [username, setUsername] = useState('');
    const [firstName, setFirstName] = useState('');
    const [lastName, setLastName] = useState('');
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
    const handleChangeFirstName = (event) => {
        setFirstName(event.target.value);
    }
    const handleChangeLastName = (event) => {
        setLastName(event.target.value);
    }
    const handleChangeAge = (event) => {
        if(!parseInt(event.target.value) || parseInt(event.target.value) < 18) {
            setAge(18);
        }
        else if (parseInt(event.target.value) > 65) {
            setAge(65);
        }
        else {
            setAge(event.target.value);
        }
    }
    const handleChangeEmail = (event) => {
        setEmail(event.target.value);
    }
    const handleChangePhone = (event) => {
        setPhone(event.target.value);
    }
    const handleChangeTransfer = (event) => {
        const newTransfer = parseFloat(event.target.value)
        if(isNaN(newTransfer) || newTransfer < 0) {
            setTransfer("0.00");
        }
        else {
            setTransfer(event.target.value);
        }
        
    }
    const switchEditMode = () => {
        setEditMode(!editMode);
    }
    const switchAddMoneyMode = () => {
        setAddMoneyMode(!addMoneyMode);
        setTransfer(0.00);
    }

    axios.defaults.headers.common['Authorization'] = `Bearer ${user.token}`;

    const transferMoney = async() => {
        try {
            const response = await axios.put('http://localhost:8086/user/add-money', {money: transfer});
            if (response.status === 200) {
                console.log(response.data)
                setBudget(budget + parseFloat(transfer))
                switchAddMoneyMode()
            }
        }
        catch (error) {
            console.error('Error fetching data:', error);
        } 
    }

    const getUserInfo = async () => {
        try {
            const response = await axios.get('http://localhost:8086/user/profile');
            if (response.status === 200) {
                console.log(response.data)
                const data = response.data;
                setScore(data.score ? data.score : 0)
                setUsername(data.username ? data.username : '');
                setFirstName(data.fullName ? data.fullName.split(' ')[0] : '')
                setLastName(data.fullName ? data.fullName.split(' ')[1] : '')
                setAge(data.age ? data.age : 18);
                setEmail(data.email ? data.email : '');
                setPhone(data.mobilePhone ? data.mobilePhone : '');
                setBudget(data.budget ? data.budget : 0.00);
            }
        }
        catch (error) {
            console.error('Error fetching data:', error);
        } 
    }

    useEffect(() => {
        getUserInfo();
    }, [])


    return(
        <Grid xl={3.5} lg={4} md={5} sm={7} xs={10}>
            <Paper elevation={12} sx={{padding: '3.5rem'}}>    
                <Grid container spacing={3} direction="column" alignItems="center">
                    <Grid>
                        <Avatar
                            sx={{
                                backgroundColor: "rgb(116, 34, 102)",
                                width: 100,
                                height: 100,
                            }}
                        >
                            {`${firstName[0]}${lastName[0]}`}
                        </Avatar>
                    </Grid>
                    <Grid >
                        <Rating value={score / 1.5 * 5} precision={0.5} readOnly />
                    </Grid>
                    <Grid xs={12}>
                        <Divider sx={{backgroundColor: theme.palette.menu.main}}/>
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
                    <Grid container>
                        <Grid xs={6}>
                            <TextField
                                fullWidth
                                InputProps={{
                                    readOnly: !editMode,
                                }}
                                label="First name"
                                value = {firstName}
                                onChange ={handleChangeFirstName}
                            />
                        </Grid>
                        <Grid xs={6}>
                            <TextField
                                fullWidth
                                InputProps={{
                                    readOnly: !editMode,
                                }}
                                label="Last name"
                                value = {lastName}
                                onChange ={handleChangeLastName}
                            />
                        </Grid>
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
                    <Grid xs={12} container>
                    <Grid xs={9}>
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
                    <Grid xs={3}>
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
                    </Grid>
                    <Grid>
                        {
                            !editMode ? (
                                <Button variant="contained" size="large" color="button_primary" onClick={switchEditMode} >
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
                        {
                            !addMoneyMode ? (
                                <Stack direction="row" spacing={2} justifyContent="space-between">
                                    <TextField
                                        label="Budeget"
                                        InputProps={{
                                            readOnly: true,
                                            endAdornment: (
                                                <InputAdornment position='end'>
                                                    CC
                                                </InputAdornment>
                                            ),
                                        }}
                                        value={parseFloat(budget).toFixed(2)}
                                    />
                                    <Button variant="contained" size="large" color="button_primary" onClick={switchAddMoneyMode}>
                                        Trnasfer
                                    </Button>
                                </Stack>
                            ) : (
                                <Stack direction="row" spacing={2} justifyContent="space-between">
                                    <TextField
                                        label="Transfer money"
                                        onChange={handleChangeTransfer}
                                        value={transfer}
                                        InputProps={{
                                            endAdornment: (
                                                <InputAdornment position='end'>
                                                    CC
                                                </InputAdornment>
                                            ),
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
                    <Grid >
                        
                    </Grid>
                </Grid>
            </Paper>
        </Grid>
    );
}

export default UserInfo;