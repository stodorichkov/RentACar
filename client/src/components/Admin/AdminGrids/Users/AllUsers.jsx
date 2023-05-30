import { Accordion, AccordionSummary, AccordionDetails, Typography, Stack, Button } from '@mui/material';
import { DataGrid } from '@mui/x-data-grid';

import { useTheme } from '@mui/material/styles';
import { useCallback, useEffect, useReducer, useState } from 'react';
import { useSelector } from 'react-redux';

import axios from 'axios';

const AllUsers = (props) => {
    const user = useSelector((state) => state.user);
    const [update, makeUpdate] = useReducer(x => x + 1, 0);
    const theme = useTheme();

    const[users, setUsers] = useState([]);

    axios.defaults.headers.common['Authorization'] = `Bearer ${user.token}`;

    const getUsers = useCallback(async () => {
        try {
            const response = await axios.get('http://localhost:8086/user/all');
            if (response.status === 200) {
                setUsers(response.data)
            }
        }
        catch (error) {
            console.error('Error fetching data:', error);
        } 
    }, []);

    useEffect(() => {
        getUsers();
    }, [update, getUsers])

    const removeUser = async (id) => {
        try {
            await axios.delete(`http://localhost:8086/user/${id}/delete`);
            makeUpdate(); 
        }
        catch (error) {
            console.error('Error fetching data:', error);
        }  
    }

    const setAdmin = async (id) => {
        try {
            await axios.patch(`http://localhost:8086/user/${id}/set-admin`);
            makeUpdate(); 
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
            field: 'fullName', 
            headerName: 'Name', 
            width: 300,
            align: 'center',
            headerAlign: 'center'  
        },
        { 
            field: 'email', 
            headerName: 'Email', 
            width: 300,
            align: 'center',
            headerAlign: 'center'  
        },
        { 
            field: 'mobilePhone', 
            headerName: 'Phone', 
            width: 200,
            align: 'center',
            headerAlign: 'center'  
        },
        { 
            field: 'years', 
            headerName: 'Age', 
            width: 90,
            align: 'center',
            headerAlign: 'center'  
        },
        { 
            field: 'score', 
            headerName: 'Score', 
            width: 200,
            type: 'number',
            align: 'center',
            headerAlign: 'center'  
        },
        { 
            field: 'budget', 
            headerName: 'Budget', 
            width: 200,
            align: 'center',
            headerAlign: 'center',
            valueFormatter: (params) => parseFloat(params.value).toFixed(2) + " CC",  
        },
        { 
            field: 'actions', 
            headerName: 'Actions', 
            width: 300,
            headerAlign: 'center',
            renderCell: (params) => (
                <Stack direction="row"justifyContent="space-evenly" sx={{width: '100%'}}> 
                    {!params.row.admin ?
                        <Button variant="contained" size="large" color="button_secondary" onClick={() => setAdmin(params.row.id)}>
                            Set Admin
                        </Button>
                    : null}
                    <Button variant="contained" size="large" color="button_primary" onClick={() => removeUser(params.row.id)}>
                        Remove 
                    </Button>
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
                
                <Typography color='white' textAlign="center">Users</Typography>
            </AccordionSummary>
            <AccordionDetails >
                <DataGrid 
                    columns={columns}
                    rows={users}
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

export default AllUsers