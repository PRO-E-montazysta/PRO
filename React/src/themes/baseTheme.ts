import { createTheme } from "@mui/material/styles";

export const theme = createTheme({
    palette: {
        primary: {
            main: '#282A3A',
            contrastText: '#FFFFFF',
        },
        secondary: {
            main: '#1A1C26',
            contrastText: '#FFFFFF',
        },
    },
    components: {
        MuiCssBaseline: {
            styleOverrides: {
                body: { backgroundColor: '#282A3A' },
            },
        },
        MuiButton: {
            styleOverrides: {
                root: {
                    color: '#FFFFFF',
                },
            },
        },
    },
});


