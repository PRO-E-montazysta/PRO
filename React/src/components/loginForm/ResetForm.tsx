import { Grid, Typography, Box, Button, InputAdornment, IconButton } from '@mui/material'
import TextField from '@mui/material/TextField'
import { styled, useTheme } from '@mui/material/styles'
import * as yup from 'yup'
import { useFormik } from 'formik'
import { useNavigate, useSearchParams } from 'react-router-dom'
import { resetPassword } from '../../api/authentication.api'
import { useMutation } from 'react-query'
import { MouseEvent, useState } from 'react'


import { Visibility, VisibilityOff } from '@mui/icons-material'
import CustomTextField from './StyledTextField'


const validationSchema = yup.object({
    password: yup.string().min(4, 'Hasło powinno składać się z minium 4 znaków ').required('Wpisz hasło'),
    confirmPassword: yup
        .string()
        .min(4, 'Hasło powinno składać się z minium 4 znaków')
        .oneOf([yup.ref('password'), null], 'Hasła różnią się')
        .required('Wpisz hasło'),
})

const ResetForm = () => {
    const theme = useTheme()
    const navigation = useNavigate()
    const [errorInfo, setErrorInfo] = useState<string>()
    const [searchParams, setSearchParams] = useSearchParams()
    const { data, isLoading, isError, error, mutate } = useMutation({
        mutationFn: resetPassword,
        onSuccess() {
            navigation('/login')
        },
        onError() {
            setErrorInfo('Skontaktuj się z administratorem systemu!')
        },
    })

    const formik = useFormik({
        initialValues: {
            password: '',
            confirmPassword: '',
        },
        validationSchema: validationSchema,
        onSubmit: (values) => {
            return mutate({ resetPasswordToken: String(searchParams.get('token')), password: values.password })
        },
    })

    const dispalyErrorInfo = () => {
        return (
            <Typography color="#d32f2f" align="center" marginTop={'5px'}>
                {errorInfo}
            </Typography>
        )
    }

    const [showPassword, setShowPassword] = useState(false)

    const handleClickShowPassword = () => setShowPassword((show) => !show)

    const handleMouseDownPassword = (event: MouseEvent<HTMLButtonElement>) => {
        event.preventDefault()
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
                    Zmiana hasła
                </Typography>
                <Box
                    component="form"
                    onSubmit={formik.handleSubmit}
                    noValidate
                    sx={{ autoComplete: 'off', maxWidth: 666, marginTop: '17px', width: '100%' }}
                >
                    <CustomTextField
                        className={'form-input-dark'}
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
                    <CustomTextField
                        className={'form-input-dark'}
                        fullWidth
                        margin="normal"
                        label="Potwierdź hasło"
                        name="confirmPassword"
                        id="confirmPassword"
                        type={showPassword ? 'text' : 'password'}
                        InputLabelProps={{
                            shrink: true,
                        }}
                        onChange={formik.handleChange}
                        error={formik.touched.confirmPassword && Boolean(formik.errors.confirmPassword)}
                        helperText={formik.touched.confirmPassword && formik.errors.confirmPassword}
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
                    {errorInfo && dispalyErrorInfo()}
                    <Grid spacing={2} container justifyContent="flex-end" marginBottom={'5px'}>
                        <Grid item>
                            <Button id={'reset'} type="submit" color="primary" variant="contained" disabled={isLoading}>
                                Zmień hasło
                            </Button>
                        </Grid>
                        <Grid item>
                            <Button id={'login'} color="secondary" variant="text" href="/login">
                                Logowanie
                            </Button>
                        </Grid>
                    </Grid>
                </Box>
            </Box>
        </>
    )
}

export default ResetForm
