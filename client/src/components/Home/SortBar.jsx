import { ToggleButton, ToggleButtonGroup, IconButton } from '@mui/material';
import Grid from '@mui/material/Unstable_Grid2';
import NorthIcon from '@mui/icons-material/North';
import SouthIcon from '@mui/icons-material/South';

import { useTheme } from '@mui/material/styles';
import { useSelector, useDispatch } from 'react-redux';

import { sortAction } from '../../redux/actions/sortActions';
import { orderAction } from '../../redux/actions/sortActions';

const SortBar = () => {
    const sort = useSelector((state) => state.sort);
    const order = useSelector((state) => state.order);
    const dispatch = useDispatch();

    // const handleToggle = (event, newValue) => {
    //     setSelectedValue(newValue);
    // };

    // style
    const theme = useTheme();
    const toggleButtonStyle = {
        marginRight: '8px',
        color: 'white',
        '&.Mui-selected': {
            color: 'white',
            backgroundColor: theme.palette.button_primary.main,
            '&:hover': {
                backgroundColor: theme.palette.menu.main,
            },
        },
        '&:not(.Mui-selected)': {
            backgroundColor: theme.palette.button_secondary.main,
            '&:hover': {
                backgroundColor: theme.palette.menu.main,
            }, 
        },
    }

    return(
        <Grid>
            <ToggleButtonGroup
                value={sort}
                exclusive
                onChange={(event, newVal) => dispatch(sortAction(newVal))}
            >
                <ToggleButton value="name" sx={{...toggleButtonStyle}}>Name</ToggleButton>
                <ToggleButton value="engineType" sx={{...toggleButtonStyle}}>Engine type</ToggleButton>
                <ToggleButton value="gearbox" sx={{...toggleButtonStyle}}>Gearbox</ToggleButton>
                <ToggleButton value="fuelConsumption" sx={{...toggleButtonStyle}}>Fuel Consumption</ToggleButton>
                <ToggleButton value="seats" sx={{...toggleButtonStyle}}>Seats</ToggleButton>
                <ToggleButton value="pricePerDay" sx={{...toggleButtonStyle}}>Price per Day</ToggleButton>
            </ToggleButtonGroup>
            <IconButton color='menue' onClick={() => dispatch(orderAction())} size='large'>
                {order ? <NorthIcon/> : <SouthIcon/> }
            </IconButton>
        </Grid>
    )
}

export default SortBar;