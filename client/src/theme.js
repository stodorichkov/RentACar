import { createTheme } from '@mui/material/styles';

const theme = createTheme({
    palette: {
        menue: {
          main: '#333'
        },
        button_primary: {
            main: "rgb(47, 93, 170)",
            contrastText: '#fff',
        },
        button_secondary: {
            main: 'rgb(116, 34, 102)',
            contrastText: '#fff',
        },
    },
    overrides: {
        MuiCssBaseline: {
          '@global': {
            body: {
              background: 'linear-gradient(94deg, rgba(47, 93, 170, 0.96) 0%, rgba(116, 34, 102, 0.96) 100%)',
            },
          },
        },
      },
});

export default theme;