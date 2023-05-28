import { Accordion, AccordionSummary, AccordionDetails, Typography, Stack, Button, Modal } from '@mui/material';
import { DataGrid } from '@mui/x-data-grid';

import { useTheme } from '@mui/material/styles';
import { useCallback, useEffect, useReducer, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { useNavigate } from 'react-router-dom';

import axios from 'axios';

import { signOutAction } from '../../../../redux/actions/userActions';

import AddCarForm from './AddCarForm';
import EditCar from './EditCar';

const AllCars = (props) => {
    const user = useSelector((state) => state.user);
    const [add, setAdd] = useState(false);
    const [edit, setEdit] = useState(false);
    const [car, setCar] = useState(null);
    const [update, makeUpdate] = useReducer(x => x + 1, 0);
    const theme = useTheme();
    const dispatch = useDispatch();
    const navigate = useNavigate();

    const[cars, setCars] = useState([]);

    const handleOpenAdd = () => setAdd(true);
    const handleCloseAdd = () => setAdd(false);
    const handleOpenEdit = (car) => {
        setCar(car);
        setEdit(true);

    }
    const handleCloseEdit = () => {
        setCar(null);
        setEdit(false);
    }

    axios.defaults.headers.common['Authorization'] = `Bearer ${user.token}`;

    const getCars = useCallback(async () => {
        try {
            const response = await axios.get('http://localhost:8086/car/all');
            if (response.status === 200) {
                setCars(response.data)
            }
        }
        catch (error) {
            console.error('Error fetching data:', error);
            dispatch(signOutAction());
            navigate("/");
        } 
    }, [dispatch, navigate]);

    const removeCar = async (id) => {
        try {
            await axios.delete(`http://localhost:8086/car/${id}/delete`);
            makeUpdate(); 
        }
        catch (error) {
            console.error('Error fetching data:', error);
        }
        
    }

    useEffect(() => {
        getCars();
    }, [update, getCars])

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
            valueFormatter: (params) => parseFloat(params.value).toFixed(2) + " CC",
        },
        { 
            field: 'actions', 
            headerName: 'Actions', 
            width: 250,
            headerAlign: 'center',
            renderCell: (params) => (
                <Stack direction="row"justifyContent="space-evenly" sx={{width: '100%'}}> 
                    <Button variant="contained" size="large" color="button_secondary" onClick={() => handleOpenEdit(params.row)}>
                        Edit
                    </Button>
                    <Button variant="contained" size="large" color="button_primary" onClick={() => removeCar(params.row.id)}>
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
                    <Button variant="contained" size="large" color="button_primary" onClick={handleOpenAdd}>
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
                open={add}
            >
                <div
                    style={{ outline: 'none' }}
                >
                    <AddCarForm handleClose={handleCloseAdd} makeUpdate={makeUpdate}/>
                </div>
            </Modal>
            <Modal
                open={edit}
            >
                <div
                    style={{ outline: 'none' }}
                >
                    <EditCar car={car} handleClose={handleCloseEdit} makeUpdate={makeUpdate}/>
                </div>
            </Modal>
        </Accordion>
    );
}

export default AllCars;
