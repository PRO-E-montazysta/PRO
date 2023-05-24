import { Grid, Typography, Box, Button } from '@mui/material'
import TextField from '@mui/material/TextField'
import { styled, useTheme } from '@mui/material/styles'
import * as yup from 'yup'
import { useFormik } from 'formik'
import { forgotPassword } from '../../api/authentication.api'
import { useMutation } from 'react-query'
import { useState } from 'react'
import CustomTextField from './StyledTextField'


const validationSchema = yup.object({
    username: yup.string().required('Wpisz nazwę użytkownika'),
})

const ForgotForm = () => {
    const [successInfo, setSuccessInfo] = useState<string>()
    const [errorInfo, setErrorInfo] = useState<string>()
    const { data, isLoading, isError, error, mutate } = useMutation({
        mutationFn: forgotPassword,
        onSuccess() {
            setErrorInfo('')
            setSuccessInfo('Sprawdź skrzynkę e-mail w celu dokończenia zmiany hasła.')
        },
        onError(error: any) {
            setSuccessInfo('')
            if (error.response.status === 404) {
                setErrorInfo('Błędna nazwa użytkownika!')
            } else {
                setErrorInfo('Skontaktuj się z administratorem systemu!')
            }
        },
    })

    const formik = useFormik({
        initialValues: {
            username: '',
        },
        validationSchema: validationSchema,
        onSubmit: (values) => {
            return mutate({ username: values.username })
        },
    })

    const dispalySuccessInfo = () => {
        return (
            <Typography align="center" marginTop={'5px'}>
                {successInfo}
            </Typography>
        )
    }

    const dispalyErrorInfo = () => {
        return (
            <Typography color="#d32f2f" align="center" marginTop={'5px'}>
                {errorInfo}
            </Typography>
        )
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
                    Resetowanie hasła
                </Typography>
                <Box
                    component="form"
                    onSubmit={formik.handleSubmit}
                    noValidate
                    sx={{ autoComplete: 'off', maxWidth: 666, marginTop: '17px', width: '100%' }}
                >
                    <CustomTextField
                        className={'form-input'}
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
                    {successInfo && dispalySuccessInfo()}
                    {errorInfo && dispalyErrorInfo()}
                    <Grid spacing={2} container justifyContent="flex-end" marginBottom={'5px'} marginTop={'15px'}>
                        <Grid item>
                            <Button
                                id={'forgot'}
                                type="submit"
                                color="primary"
                                variant="contained"
                                disabled={isLoading}
                            >
                                Resetuj
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

export default ForgotForm
