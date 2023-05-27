import { TextField, styled } from '@mui/material'

const CustomTextField = styled(TextField)(({ theme }) => ({
    label: { shrink: true, color: theme.palette.secondary.contrastText },
    input: { color: theme.palette.secondary.contrastText },
    '& label.Mui-focused': {
        color: theme.palette.secondary.contrastText,
    },
    '& .MuiOutlinedInput-root': {
        '& fieldset': {
            borderColor: '#96C0FB',
        },
        '&:hover fieldset': {
            borderColor: theme.palette.secondary.contrastText,
        },
    },
    '&.Mui-focused fieldset': {
        borderColor: 'green',
    },
    '& input:-webkit-autofill, input:-webkit-autofill:hover, input:-webkit-autofill:focus': {
        '-webkit-text-fill-color': 'white',
        '-webkit-box-shadow': '0 0 0px 1000px #1A1C26 inset',
    },
}))

// .form-input-dark input:-webkit-autofill,
// .form-input-dark input:-webkit-autofill:hover,
// .form-input-dark input:-webkit-autofill:focus {
//     -webkit-text-fill-color: white;
//     -webkit-box-shadow: 0 0 0px 1000px #1A1C26 inset;
// }
export default CustomTextField
