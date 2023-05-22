import { AppBar, Toolbar, Button, Stack, Typography, Avatar, Box }  from '@mui/material';

import { useNavigate } from "react-router-dom";
import { useDispatch, useSelector } from 'react-redux';
import { signOutAction } from '../redux/actions/userActions';

const Bar = () => {
	const navigate = useNavigate();
	
	const user = useSelector((state) => state.user);
	const dispatch = useDispatch();

	const signOutUser = () => {
		dispatch(signOutAction());
		navigate('/');
	}

	const renderButtons = () => {
		if(!user) {
			return(
				<>
					<Button variant="contained"  color="button_primary" size="large" onClick={() => navigate('/signin')}>
						Sign In
					</Button>
					<Button variant="contained"  color="button_secondary" size="large" onClick={() => navigate('/signup')}>
						Sign Up
					</Button>
				</>
			)
		}
		else {
			return(
				<>
					<Button sx={{textTransform: "none"}} onClick={() => navigate('/admin')}>
						<Typography variant="h6" color="white">
							Admin
						</Typography>
					</Button>
					<Button sx={{textTransform: "none"}} onClick={() => navigate('/profile')}>
						<Typography variant="h6" color="white">
							Profile
						</Typography>
					</Button>
					<Button variant="contained"  color="button_secondary" size="large" onClick={signOutUser}>
						Sign Out
					</Button>
				</>
			)
		}
	}

	return (
		<AppBar position="static" color="menue" sx={{padding: '0.5rem'}}>
			<Toolbar>
				<Box display="flex" justifyContent="space-between" width="100%">
					<Button sx={{textTransform: "none"}} onClick={() => navigate('/')}>
						<Avatar alt="Remy Sharp" src="/logo.png" sx={{ width: 45, height: 45 }}/>
						<Typography variant="h4" color="white">
							odexio Cruisers
						</Typography>
					</Button>
					<Stack spacing={3} direction="row">
						{renderButtons()}
					</Stack>
				</Box>
			</Toolbar>
		</AppBar>
	);
}

export default Bar;