import { Accordion, AccordionSummary, AccordionDetails, Typography, Stack, Button } from '@mui/material';
import { useTheme } from '@mui/material/styles';

import { DataGrid } from '@mui/x-data-grid';

const ActiveRentals = (props) => {
    const theme = useTheme();

    const curFormatter = new Intl.NumberFormat(undefined, {
        maximumFractionDigits: 2,
    });

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
            width: 150,
            align: 'center',
            headerAlign: 'center'  
        },
        { 
            field: 'endtDate', 
            headerName: 'End date', 
            width: 150,
            align: 'center',
            headerAlign: 'center'  
        },
        { 
            field: 'car', 
            headerName: 'Car', 
            width: 200,
            align: 'center',
            headerAlign: 'center', 
            renderCell: (params) => (
                <div>
                    {params.row.brand} {params.row.model}
                </div>
            ),
        },
        { 
            field: 'totalPrice', 
            headerName: 'Total price', 
            width: 150, 
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
                        Reject
                    </Button>
                    <Button variant="contained" size="large" color="button_primary">
                        Finish
                    </Button>
                </Stack>
            ) 
        },

    ]

    const rows = [
        {'id': 1, 'totalPrice': 122.33114, 'brand': 'Mercedes', 'model': 'A class'},
    ]

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
                    rows={rows}
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