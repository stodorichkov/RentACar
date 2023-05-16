import { createTheme } from '@mui/material/styles';

const theme = createTheme({
    palette: {
        background: {
          main: '#333'
        },
        button_primary: {
            main: "#305eab",
            contrastText: '#fff',
        },
        button_secondary: {
            main: '#732365',
            contrastText: '#fff',
        }
    }
});

export default theme;