import { Accordion, AccordionSummary, AccordionDetails, Typography, Stack, Button } from '@mui/material';
import { DataGrid } from '@mui/x-data-grid';

import { useTheme } from '@mui/material/styles';
import { useCallback, useEffect, useReducer, useState } from 'react';
import { useSelector } from 'react-redux';

import axios from 'axios';

const AllRentals = (props) => {
    const user = useSelector((state) => state.user);
    const [update, makeUpdate] = useReducer(x => x + 1, 0);
    const theme = useTheme();

    const[rentals, setRentals] = useState([]);

    axios.defaults.headers.common['Authorization'] = `Bearer ${user.token}`;

    const getRentals = useCallback(async () => {
        try {
            const response = await axios.get('http://localhost:8086/rentals/all-admin');
            if (response.status === 200) {
                setRentals(response.data)
            }
        }
        catch (error) {
            console.error('Error fetching data:', error);
        } 
    }, []);

    useEffect(() => {
        getRentals();
    }, [update, getRentals])

    const cancelRental = async (id) => {
        try {
            const response = await axios.patch(`http://localhost:8086/rentals/${id}/change-status`);
            if (response.status === 200) {
                makeUpdate()
            }
        }
        catch (error) {
            console.error('Error fetching data:', error);
        } 
    }

    const columns = [
        { 
            field: 'id', 
            headerName: 'ID', 
            width: 90,
            align: 'center',
            headerAlign: 'center' 
        },
        { 
            field: 'username', 
            headerName: 'Username', 
            width: 200,
            align: 'center',
            headerAlign: 'center'  
        },
        { 
            field: 'carName', 
            headerName: 'Car', 
            width: 300,
            align: 'center',
            headerAlign: 'center'  
        },
        { 
            field: 'registrationPlate', 
            headerName: 'Registration Plate', 
            width: 200,
            align: 'center',
            headerAlign: 'center'  
        },
        { 
            field: 'totalPrice', 
            headerName: 'Price', 
            width: 200,
            align: 'center',
            headerAlign: 'center',
            valueFormatter: (params) => parseFloat(params.value).toFixed(2) + " CC",    
        },
        { 
            field: 'status', 
            headerName: 'Status', 
            width: 150,
            align: 'center',
            headerAlign: 'center'  
        },
        { 
            field: 'actions', 
            headerName: 'Actions', 
            width: 150,
            headerAlign: 'center',
            renderCell: (params) => (
                <Stack direction="row"justifyContent="space-evenly" sx={{width: '100%'}}> 
                    {params.row.status  === "Reserved" || params.row.status === "Active" ?
                        <Button variant="contained" size="large" color="button_secondary" onClick={() => cancelRental(params.row.id)}>
                            Cancel
                        </Button>
                    : null}
                </Stack>
            ) 
        },
    ];

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
                    background: theme.palette.menu.main,
                    borderTopRightRadius: 8,
                    borderTopLeftRadius: 8,
                    zIndex: 10
                }}
            >
                
                <Typography color='white' textAlign="center">Rentals</Typography>
            </AccordionSummary>
            <AccordionDetails >
                <DataGrid 
                    columns={columns}
                    rows={rentals}
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

export default AllRentals;