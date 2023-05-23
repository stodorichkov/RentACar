import { ToggleButton, ToggleButtonGroup, IconButton } from '@mui/material';
import Grid from '@mui/material/Unstable_Grid2';
import NorthIcon from '@mui/icons-material/North';
import SouthIcon from '@mui/icons-material/South';

const SortBar = () => {
    // style
    const theme = useTheme();
    const toggleButtonStyle = {
        marginRight: '8px',
        color: 'white',
        '&.Mui-selected': {
            color: 'white',
            backgroundColor: theme.palette.button_primary.main,
            '&:hover': {
                backgroundColor: theme.palette.menue.main,
            },
        },
        '&:not(.Mui-selected)': {
            backgroundColor: theme.palette.button_secondary.main,
            '&:hover': {
                backgroundColor: theme.palette.menue.main,
            }, 
        },
    }
    return(
        <Grid>
            <ToggleButtonGroup
                value={sort}
                exclusive
                onChange={handleChangeSort}
            >
                <ToggleButton value="name" sx={{...toggleButtonStyle}}>Name</ToggleButton>
                <ToggleButton value="engineType" sx={{...toggleButtonStyle}}>Engine type</ToggleButton>
                <ToggleButton value="gearbox" sx={{...toggleButtonStyle}}>Gearbox</ToggleButton>
                <ToggleButton value="fuelConsumption" sx={{...toggleButtonStyle}}>Fuel Consumption</ToggleButton>
                <ToggleButton value="seats" sx={{...toggleButtonStyle}}>Seats</ToggleButton>
                <ToggleButton value="pricePerDay" sx={{...toggleButtonStyle}}>Price per Day</ToggleButton>
            </ToggleButtonGroup>
            <IconButton color='menue' onClick={handleChangeOeder} size='large'>
                {order ? <NorthIcon/> : <SouthIcon/> }
            </IconButton>
        </Grid>
    )
}