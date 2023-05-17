import { AppBar, Toolbar, Button, Stack, Typography, Grid, Avatar}  from '@mui/material';

const Bar = () => {
	return (
		<>
			<AppBar position="absolute" color="background" sx={{padding: '1rem'}}>
				<Toolbar>
					<Grid container direction="row" justifyContent="space-between">
						<Button sx={{textTransform: "none"}} href="/">
							<Avatar alt="Remy Sharp" src="logo.png" sx={{ width: 45, height: 45 }}/>
							<Typography variant="h4" color="white">
								odexio Cruisers
							</Typography>
						</Button>
						
						<Stack spacing={2} direction="row">
							<Button variant="contained"  color="button_primary" size="large" href="/signin">
								Sign In
							</Button>
							<Button variant="contained"  color="button_secondary" size="large" href="/signup">
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