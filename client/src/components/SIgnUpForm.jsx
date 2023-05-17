import { Grid, Paper, Typography, TextField, Divider, Button, } from '@mui/material';


const SignUpForm = () => {
    return (
        <>
            <Grid container sx={{backgroundSize: 'cover', background: 'linear-gradient(rgba(48,94,171, 0.7), rgba(115, 35, 101, 0.7)), url(authentication.jpg)', height: "100vh"}} justifyContent={"center"} alignItems={"baseline"}>
                <Paper elevation={12} sx={{padding: '3.5rem', width: '32rem', marginTop: "10rem"}}>
                    <Grid container direction="column"  spacing={5}>
                        <Grid item xs={12} sm={6} md={6}>
                            <Typography variant="h3" color="textPrimary" align="center" >Sign Up</Typography>
                        </Grid>
                        <Grid item xs={12} sm={6} md={6}>
                            <Divider/>
                        </Grid>
                        <Grid item xs={12} sm={6} md={6}>
                            <TextField
                                fullWidth
                                label="Username"
                            />
                        </Grid>
                        <Grid item xs={12} sm={6} md={6}>
                            <TextField
                                fullWidth
                                label="Email"
                            />
                        </Grid>
                        <Grid item xs={12} sm={6} md={6}>
                            <TextField
                                fullWidth
                                label="Password"
                                type="password"
                            />
                        </Grid>
                        <Grid item xs={12} sm={6} md={6} alignSelf={'center'}>
                            <Button variant="contained" size="large">
                                    Sign In
                            </Button>
                        </Grid>
                    </Grid>
                </Paper>
            </Grid>
        </> 
    )
}

export default SignUpForm;