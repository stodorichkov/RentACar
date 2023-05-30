import { Paper, Divider, Typography, Stack, Select, MenuItem }  from '@mui/material';
import Grid from '@mui/material/Unstable_Grid2';

import { useTheme } from '@mui/material/styles';
import { useCallback, useEffect, useState } from 'react';
import { useSelector } from 'react-redux';

import dayjs from 'dayjs';
import axios from 'axios';

import AllCars from './AdminGrids/Cars/AllCars';
import AllUsers from './AdminGrids/Users/AllUsers';
import AllRentals from './AdminGrids/Rentals/AllRentals';

const Admin = () => {
    const theme = useTheme();
    const user = useSelector((state) => state.user);
    const [expanded, setExpanded] = useState(null);
    const [selectedMonth, setSelectedMonth] = useState(dayjs().month());
    const [selectedYear, setSelectedYear] = useState(dayjs().year());
    const [turnover, setTurnover] = useState(0);

    const monthNames = [
        'January', 'February', 'March', 'April', 'May', 'June',
        'July', 'August', 'September', 'October', 'November', 'December'
    ];
    const years = [2022, 2023, 2024, 2025]

    const handleChangeExpand = (panel) => (event, isExpanded) => {
        if (isExpanded || expanded !== panel ) {
            setExpanded(panel);
        }
    };
    const handleMonthChange = (event) => {
        setSelectedMonth(event.target.value);
    };
    const handleYearChange = (event) => {
        setSelectedYear(event.target.value)
    }

    axios.defaults.headers.common['Authorization'] = `Bearer ${user.token}`;

    const getTurnover = useCallback(async () => {
        try {
            const response = await axios.get(`http://localhost:8086/rentals/${selectedMonth + 1}/${selectedYear}/total`);
            if (response.status === 200) {
                setTurnover(response.data)
            }
        }
        catch (error) {
            console.error('Error fetching data:', error);
        } 
    }, [selectedMonth, selectedYear]);

    useEffect(() => {
        getTurnover();
    }, [getTurnover]);

    return(
        <Grid container spacing={2} justifyContent='center' direction='row' sx={{ height: "85vh", marginTop: '4vh',  overflow: 'auto'}}>
            <Grid xs={11} >
                <Paper elevation={12} sx={{padding: '3.5rem', height: "100%"}}>
                    <Grid container spacing={5} justifyContent="center">   
                        <Grid xs={12}>
                            <Typography variant="h3" color="textPrimary" align="center" >Admin panel</Typography>
                        </Grid>
                        <Grid xs={12}>
                            <Divider sx={{backgroundColor: theme.palette.menu.main}}/>
                        </Grid>
                        <Grid xs={12}>
                            <Stack justifyContent="space-between" direction="row">
                                <Stack spacing={2} direction="row">
                                    <Typography variant="h4" color="initial">
                                        Turnover for
                                    </Typography>
                                    <Select value={selectedMonth} onChange={handleMonthChange} sx={{width: 200}}>
                                        {monthNames.map((month, index) => (
                                            <MenuItem key={month} value={index}>{month}</MenuItem>
                                        ))}
                                    </Select>
                                    <Select value={selectedYear} onChange={handleYearChange} sx={{width: 200}}>
                                        {years.map((year) => (
                                            <MenuItem key={year} value={year}>{year}</MenuItem>
                                        ))}
                                    </Select>
                                    <Typography variant="h4" color="initial">
                                        is:
                                    </Typography>
                                </Stack>
                                <Typography variant="h4" color="initial">
                                   {parseFloat(turnover ? turnover : 0).toFixed(2)} CC
                                </Typography>
                            </Stack> 
                        </Grid>
                        <Grid xs={12}>
                            <AllCars handleChangeExpand={handleChangeExpand('panel1')} expanded={expanded === 'panel1'}/>
                        </Grid>
                        <Grid xs={12}>
                            <AllUsers handleChangeExpand={handleChangeExpand('panel2')} expanded={expanded === 'panel2'}/>
                        </Grid>
                        <Grid xs={12}>
                            <AllRentals handleChangeExpand={handleChangeExpand('panel3')} expanded={expanded === 'panel3'}/>
                        </Grid>
                    </Grid> 
                </Paper>
            </Grid>
        </Grid>
    );
}

export default Admin;