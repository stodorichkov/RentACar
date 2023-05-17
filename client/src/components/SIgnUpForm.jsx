import { Grid, Paper, Typography, TextField, Divider, Button, Container } from '@mui/material';


const SignUpForm = () => {
    return (
        <>
            <Grid container sx={{backgroundSize: 'cover', background: 'linear-gradient(rgba(48,94,171, 0.7), rgba(115, 35, 101, 0.7)), url(authentication.jpg)', height: "86.6vh"}} justifyContent={"center"} alignItems={"baseline"}>
                <Container maxWidth="sm" sx={{marginTop: "1rem"}}>
                    <Paper elevation={12} sx={{padding: '2rem'}}>
                        <Grid container direction="column"  spacing={3.5}>
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
                                    label="Phone number"
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
                                    Sign Up
                                </Button>
                            </Grid>
                        </Grid>
                    </Paper>
                </Container>
            </Grid>
        </> 
    )
}

export default SignUpForm;