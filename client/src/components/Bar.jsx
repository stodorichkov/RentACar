import { AppBar, Toolbar, Button, Stack, Typography, Grid, Avatar, IconButton }  from '@mui/material';

const Bar = () => {
	return (
		<>
			<AppBar position="fixed" color="background">
				<Toolbar>
					<Grid container direction="row" justifyContent="space-between">
						<Button>
							<Avatar alt="Remy Sharp" src="logo.png" />
							<Typography variant="h4" color="white" sx={{textTransform: "none"}}>
								odexio Cruisers
							</Typography>
						</Button>
						
						<Stack spacing={2} direction="row">
							<Button variant="contained"  color="button_primary" size="large">
								Sign In
							</Button>
							<Button variant="contained"  color="button_secondary" size="large">
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