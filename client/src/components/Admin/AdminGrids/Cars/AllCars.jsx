import { Accordion, AccordionSummary, AccordionDetails, Typography, Stack, Button, Modal } from '@mui/material';
import { DataGrid } from '@mui/x-data-grid';

import { useTheme } from '@mui/material/styles';
import { useEffect, useReducer, useState } from 'react';
import { useSelector } from 'react-redux';

import AddCarForm from './AddCarForm';

import axios from 'axios';


const AllCars = (props) => {
    const user = useSelector((state) => state.user);
    const [open, setOpen] = useState(false);
    const [update, makeUpdate] = useReducer(x => x + 1, 0);
    const theme = useTheme();

    const[cars, setCars] = useState([]);

    const handleOpen = () => setOpen(true);
    const handleClose = () => setOpen(false);

    const curFormatter = new Intl.NumberFormat(undefined, {
        maximumFractionDigits: 2,
    });

    axios.defaults.headers.common['Authorization'] = `Bearer ${user.token}`;

    const getCars = async () => {
        try {
            const response = await axios.get('http://localhost:8086/car/all');
            if (response.status === 200) {
                setCars(response.data)
            }
        }
        catch (error) {
            console.error('Error fetching data:', error);
        } 
    }

    const removeCar = async (id) => {
        try {
            const response = await axios.delete(`http://localhost:8086/car/${id}/delete`);
            makeUpdate(); 
        }
        catch (error) {
            console.error('Error fetching data:', error);
        }
        
    }

    useEffect(() => {
        getCars();
    }, [update])

    const columns = [
        { 
            field: 'id', 
            headerName: 'ID', 
            width: 90,
            align: 'center',
            headerAlign: 'center' 
        },
        { 
            field: 'carName', 
            headerName: 'Name', 
            width: 300,
            align: 'center',
            headerAlign: 'center'  
        },
        { 
            field: 'condition', 
            headerName: 'Conditions', 
            width: 150,
            align: 'center',
            headerAlign: 'center'  
        },
        { 
            field: 'registrationPlate', 
            headerName: 'Registration plate', 
            width: 200,
            align: 'center',
            headerAlign: 'center'  
        },
        { 
            field: 'engine', 
            headerName: 'Engine', 
            width: 200,
            align: 'center',
            headerAlign: 'center'  
        },
        { 
            field: 'transmission', 
            headerName: 'Transmission', 
            width: 200,
            align: 'center',
            headerAlign: 'center'  
        },
        { 
            field: 'capacity', 
            headerName: 'Seats', 
            width: 200,
            type: 'number',
            align: 'center',
            headerAlign: 'center'  
        },
        { 
            field: 'fuelConsumption', 
            headerName: 'Fue consumption', 
            width: 200,
            align: 'center',
            headerAlign: 'center'  
        },
        { 
            field: 'pricePerDay', 
            headerName: 'Price per day', 
            width: 200, 
            type: 'number',
            align: 'center',
            headerAlign: 'center',
            valueFormatter: ({ value }) => curFormatter.format(value) + " CC",
        },
        { 
            field: 'actions', 
            headerName: 'Actions', 
            width: 250,
            headerAlign: 'center',
            renderCell: (params) => (
                <Stack direction="row"justifyContent="space-evenly" sx={{width: '100%'}}> 
                    <Button variant="contained" size="large" color="button_secondary">
                        Edit
                    </Button>
                    <Button variant="contained" size="large" color="button_primary" onClick={() => removeCar(params.id)}>
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
                <Stack direction="row" justifyContent="space-between" alignContent="center" sx={{width: '100%'}}> 
                    <Typography color='white' textAlign="center">Cars</Typography>
                    <Button variant="contained" size="large" color="button_primary" onClick={handleOpen}>
                        Add car
                    </Button>
                </Stack>
            </AccordionSummary>
            <AccordionDetails >
                <DataGrid 
                    columns={columns}
                    rows={cars}
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
            <Modal
                open={open}
            >
                <div
                    style={{ outline: 'none' }}
                >
                    <AddCarForm handleClose={handleClose} makeUpdate={makeUpdate}/>
                </div>
            </Modal>
        </Accordion>
    );
}

export default AllCars;
