import { Grid, Paper, Typography, TextField, Divider, Button, Container, Select, MenuItem, InputLabel, FormControl } from '@mui/material';

const AddCarForm = () => {
    

    return (
        <>
            <Grid container sx={{backgroundSize: 'cover', background: 'linear-gradient(rgba(48,94,171, 0.7), rgba(115, 35, 101, 0.7))'}}>
                <Container maxWidth="sm" sx={{marginTop: "7.5rem", marginBottom: '1rem'}}>
                    <Paper elevation={12} sx={{padding: '3rem'}}>
                        <Grid container direction="column"  spacing={3}>
                            <Grid item xs={12} sm={6} md={6}>
                                <Typography variant="h3" color="textPrimary" align="center" >Add car</Typography>
                            </Grid>
                            <Grid item xs={12} sm={6} md={6}>
                                <Divider/>
                            </Grid>
                            <Grid item xs={12} sm={6} md={6}>
                                <TextField
                                    fullWidth
                                    label="Brand"
                                />
                            </Grid>
                            <Grid item xs={12} sm={6} md={6}>
                                <TextField
                                    fullWidth
                                    label="Model"
                                />
                            </Grid>
                            <Grid item xs={12} sm={6} md={6}>
                                <TextField
                                    fullWidth
                                    label="Registration plate"
                                />
                            </Grid>
                            <Grid item >
                                <Button variant="contained" size="large" color="button_secondary" fullWidth>
                                    Upload Image
                                </Button>
                            </Grid>
                            <Grid item xs={12} sm={6} md={6}>
                                <TextField
                                    fullWidth
                                    label="Price per day"
                                />
                            </Grid>
                            <Grid item xs={12} sm={6} md={6}>
                                <FormControl fullWidth>
                                    <InputLabel id="seats-label">Seats number</InputLabel>
                                    <Select
                                        labelId="seats-label"
                                        id="demo-simple-select"
                                        label="Seats number"
                                    >
                                        {Array.from({length: 9}, (_, i) => i + 2).map(el => <MenuItem value={el}>{el}</MenuItem>)}
                                    </Select>
                                </FormControl>
                            </Grid>
                            <Grid item xs={12} sm={6} md={6}>
                                <FormControl fullWidth>
                                    <InputLabel id="transmition-label">Transmition</InputLabel>
                                    <Select
                                        labelId="transmition-label"
                                        id="demo-simple-select"
                                        label="Transmition"
                                    >
                                        {Array.from({length: 2}, (_, i) => i).map(el => <MenuItem value={el}>{el}</MenuItem>)}
                                    </Select>
                                </FormControl>
                            </Grid>
                            <Grid item xs={12} sm={6} md={6}>
                                <FormControl fullWidth>
                                    <InputLabel id="engine-label">Engine type</InputLabel>
                                    <Select
                                        labelId="engine-label"
                                        id="demo-simple-select"
                                        label="Engine type"
                                    >
                                        {Array.from({length: 4}, (_, i) => i).map(el => <MenuItem value={el}>{el}</MenuItem>)}
                                    </Select>
                                </FormControl>
                            </Grid>
                            <Grid item xs={12} sm={6} md={6}>
                                <TextField
                                    fullWidth
                                    label="Fuel consumption"
                                />
                            </Grid>
                            <Grid item xs={12} sm={6} md={10} alignSelf={'center'}>
                                <Button variant="contained" size="large" color="button_primary">
                                    Add car
                                </Button>
                            </Grid>
                        </Grid>
                    </Paper>
                </Container>
            </Grid>
        </>
    );
}

export default AddCarForm;