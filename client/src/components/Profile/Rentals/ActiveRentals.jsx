import { Accordion, AccordionSummary, AccordionDetails, Typography, Stack, Button } from '@mui/material';
import { DataGrid } from '@mui/x-data-grid';

import { useTheme } from '@mui/material/styles';
import { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';

import axios from 'axios';



const ActiveRentals = (props) => {
    const theme = useTheme();
    const user = useSelector((state) => state.user);

    const [allRentals, setAllRentals] = useState([]);

    const columns = [
        { 
            field: 'id', 
            headerName: 'ID', 
            width: 90,
            align: 'center',
            headerAlign: 'center' 
        },
        { 
            field: 'startDate', 
            headerName: 'Start date', 
            width: 200,
            align: 'center',
            headerAlign: 'center'  
        },
        { 
            field: 'endDate', 
            headerName: 'End date', 
            width: 200,
            align: 'center',
            headerAlign: 'center'  
        },
        { 
            field: 'carName', 
            headerName: 'Car', 
            width: 200,
            align: 'center',
            headerAlign: 'center', 
        },
        { 
            field: 'price', 
            headerName: 'Total price', 
            width: 150, 
            type: 'number',
            align: 'center',
            headerAlign: 'center',
            valueFormatter: ({ value }) => parseFloat(value).toFixed(2) + " CC",
        },
        { 
            field: 'status', 
            headerName: 'Status', 
            width: 150,
            headerAlign: 'center',
            align: 'center',
        },
        { 
            field: 'actions', 
            headerName: 'Actions', 
            width: 250,
            headerAlign: 'center',
            renderCell: (params) => (
                <Stack direction="row"justifyContent="space-evenly" sx={{width: '100%'}}> 
                    <Button variant="contained" size="large" color="button_secondary">
                        Reject
                    </Button>
                    <Button variant="contained" size="large" color="button_primary">
                        Finish
                    </Button>
                </Stack>
            ) 
        },
    ]

    axios.defaults.headers.common['Authorization'] = `Bearer ${user.token}`;

    const getActive = async () => {
        try {
            const response = await axios.get(`http://localhost:8086/rentals/active-history`);
            if (response.status === 200) {
                setAllRentals(response.data)
            }
        }
        catch (error) {
            console.error('Error fetching data:', error);
        } 
    }

    useEffect(() => {
        getActive();
    }, []);


    return(
        <Accordion expanded={props.expanded} onChange={props.handleChangeExpand}
            sx={{
                boxShadow: (theme) => theme.shadows[10],
            }}
        >
            <AccordionSummary
                aria-controls="panel1a-content"
                id="panel1a-header"
                sx={{
                    background: theme.palette.button_primary.main,
                    borderTopRightRadius: 8,
                    borderTopLeftRadius: 8
                }}
            >
                <Typography color='white' >Active Rentals</Typography>
            </AccordionSummary>
            <AccordionDetails >
                <DataGrid 
                    columns={columns}
                    rows={allRentals}
                    initialState={{
                        pagination: {
                            paginationModel: {
                                pageSize: 5,
                            },
                        },
                    }}
                    pageSizeOptions={[5]}
                    disableRowSelectionOnClick
                />
            </AccordionDetails> 
        </Accordion>
    );
}

export default ActiveRentals;