import { Grid, Paper, Typography, TextField, Divider, Button, Container } from '@mui/material';

const SignInForm = () => {
    return (
        <>
            <Grid container sx={{backgroundSize: 'cover', background: 'linear-gradient(rgba(48,94,171, 0.7), rgba(115, 35, 101, 0.7))', height: "100vh"}}>
                <Container maxWidth="sm" sx={{marginTop: "7.5rem", marginBottom: '1rem'}}>
                    <Paper elevation={12} sx={{padding: '3.5rem'}}>
                        <Grid container direction="column"  spacing={5}>
                            <Grid item xs={12} sm={6} md={6}>
                                <Typography variant="h3" color="textPrimary" align="center" >Sign In</Typography>
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
                                    label="Password"
                                    type="password"
                                />
                            </Grid>
                            <Grid item xs={12} sm={6} md={6} alignSelf={'center'}>
                                <Button variant="contained" size="large" color="button_primary">
                                    Sign In
                                </Button>
                            </Grid>
                        </Grid>
                    </Paper>
                </Container>
            </Grid>
        </> 
    )
}

export default SignInForm;