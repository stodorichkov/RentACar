import { AppBar, Toolbar, Button, Stack, Typography, Grid, Avatar}  from '@mui/material';
import { useNavigate } from "react-router-dom";

const Bar = () => {
	const navigate = useNavigate();
	return (
		<>
			<AppBar position="static" color="menue" sx={{padding: '0.5rem'}}>
				<Toolbar>
					<Grid container direction="row" justifyContent="space-between">
						<Button sx={{textTransform: "none"}} onClick={() => navigate('/')}>
							<Avatar alt="Remy Sharp" src="/logo.png" sx={{ width: 45, height: 45 }}/>
							<Typography variant="h4" color="white">
								odexio Cruisers
							</Typography>
						</Button>
						<Stack spacing={2} direction="row">
							<Button variant="contained"  color="button_primary" size="large" onClick={() => navigate('/signin')}>
								Sign In
							</Button>
							<Button variant="contained"  color="button_secondary" size="large" onClick={() => navigate('/signup')}>
								Sign Up
							</Button>
						</Stack>
					</Grid>
				</Toolbar>
			</AppBar>
		</>
	);
}

export default Bar;