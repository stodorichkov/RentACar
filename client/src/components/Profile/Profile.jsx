import Grid from '@mui/material/Unstable_Grid2';
import UserInfo from './UserInfo';
import UserRentals from './Rentals/UserRentals';

const Profile = () => {
    return(
        <>
        <Grid container spacing={2} justifyContent='center' direction='row' sx={{marginTop: '2vw'}}>
            <UserInfo/>
            <UserRentals/>
        </Grid>
        </>
    );
}

export default Profile;