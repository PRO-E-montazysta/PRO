import {
    Grid,
    Typography,
    Box,
    FormControlLabel,
    Checkbox,
    Button,
    InputAdornment,
    IconButton,
    OutlinedInput,
} from '@mui/material'
import TextField from '@mui/material/TextField'
import { styled, useTheme } from '@mui/material/styles'
import * as yup from 'yup'
import { useFormik } from 'formik'
import { useNavigate } from 'react-router-dom'
import { setToken } from '../../utils/token'
import { logIn } from '../../api/authentication.api'
import { useMutation } from 'react-query'
import { MouseEvent, useState } from 'react'
import './style.less'

import { Visibility, VisibilityOff } from '@mui/icons-material'

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
}))

const CustomCheckbox = styled(Checkbox)(({ theme }) => ({
    color: theme.palette.secondary.contrastText,
    '&.Mui-checked': {
        color: theme.palette.secondary.contrastText,
    },
}))

const validationSchema = yup.object({
    username: yup.string().required('Wpisz nazwę użytkownika'),
    password: yup.string().min(4, 'Hasło powinno składać się z minium 4 znaków ').required('Wpisz hasło'),
})

const LoginForm = () => {
    const theme = useTheme()
    const navigation = useNavigate()
    const { data, isLoading, isError, error, mutate } = useMutation({
        mutationFn: logIn,
        onSuccess(data) {
            setToken(data)
            navigation('/', { replace: true })
        },
        onError(error: any) {
            console.error(error)
        },
    })

    const formik = useFormik({
        initialValues: {
            username: '',
            password: 'password',
        },
        validationSchema: validationSchema,
        onSubmit: (values) => {
            return mutate({ username: values.username, password: values.password })
        },
    })

    const [showPassword, setShowPassword] = useState(false)

    const handleClickShowPassword = () => setShowPassword((show) => !show)

    const handleMouseDownPassword = (event: MouseEvent<HTMLButtonElement>) => {
        event.preventDefault()
    }

    const dispalyError = (error: any) => {
        if (error == 'AxiosError: Network Error') {
            return 'Skontaktuj się z administratorem systemu!'
        } else if (error.response.status === 400) {
            return 'Błędne dane logowania!'
        } else if (error.response.status === 402) {
            return 'Firma zawieszona, skontaktuj się z administratorem systemu!'
        } else if (error.response.status === 403) {
            return 'Skontaktuj się z administratorem firmy!'
        } else {
            return 'Skontaktuj się z administratorem systemu!'
        }
    }

    return (
        <>
            <Box
                bgcolor="secondary.main"
                color="secondary.contrastText"
                position="relative"
                mx="auto"
                borderRadius="15px"
                display="flex"
                flexDirection="column"
                alignItems="center"
                maxWidth={800}
                paddingTop={5}
                paddingBottom={0.5}
                bottom="50px"
                sx={{ boxShadow: '0px 20px 4px rgba(0, 0, 0, 0.25)' }}
            >
                <Typography component="h1" variant="h5">
                    Logowanie użytkownika
                </Typography>
                <Box
                    component="form"
                    onSubmit={formik.handleSubmit}
                    noValidate
                    sx={{ autoComplete: 'off', maxWidth: 666, marginTop: '17px', width: '100%' }}
                >
                    <CustomTextField
                        className={'login-form-input'}
                        fullWidth
                        margin="normal"
                        label="Nazwa użytkownika"
                        name="username"
                        id="username"
                        InputLabelProps={{
                            shrink: true,
                        }}
                        value={formik.values.username}
                        onChange={formik.handleChange}
                        error={formik.touched.username && Boolean(formik.errors.username)}
                        helperText={formik.touched.username && formik.errors.username}
                    />
                    <CustomTextField
                        className={'login-form-input'}
                        fullWidth
                        margin="normal"
                        label="Hasło"
                        name="password"
                        id="password"
                        type={showPassword ? 'text' : 'password'}
                        InputLabelProps={{
                            shrink: true,
                        }}
                        value={formik.values.password}
                        onChange={formik.handleChange}
                        error={formik.touched.password && Boolean(formik.errors.password)}
                        helperText={formik.touched.password && formik.errors.password}
                        InputProps={{
                            endAdornment: (
                                <InputAdornment position="end">
                                    <IconButton
                                        id={`showPassword`}
                                        sx={{
                                            color: theme.palette.secondary.contrastText,
                                        }}
                                        aria-label="toggle password visibility"
                                        onClick={handleClickShowPassword}
                                        onMouseDown={handleMouseDownPassword}
                                        edge="end"
                                    >
                                        {showPassword ? <VisibilityOff /> : <Visibility />}
                                    </IconButton>
                                </InputAdornment>
                            ),
                        }}
                    />
                    <Typography color="#d32f2f" align="center" marginTop={'5px'}>
                        {error && dispalyError(error)}
                    </Typography>
                    <FormControlLabel
                        sx={{ marginTop: '15px', marginBottom: '25px' }}
                        control={<CustomCheckbox value="remember" />}
                        label="Zapamiętaj moje dane logowania"
                        name="check"
                    />
                    <Grid spacing={2} container justifyContent="flex-end" marginBottom={'5px'}>
                        <Grid item>
                            <Button
                                id={'login-logIn'}
                                type="submit"
                                color="primary"
                                variant="contained"
                                disabled={isLoading}
                            >
                                Zaloguj się
                            </Button>
                        </Grid>
                        <Grid item>
                            <Button id={'login-reset'} color="secondary" variant="text" href="/forgot">
                                Resetuj hasło
                            </Button>
                        </Grid>
                    </Grid>
                </Box>
            </Box>
            <Box sx={{ m: '10px', mt: '1000px' }}>{isError ? JSON.stringify(error) : JSON.stringify(data)}</Box>
        </>
    )
}

export default LoginForm
