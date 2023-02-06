import { Grid, Typography, Box, FormControlLabel, Checkbox, Button } from '@mui/material';
import TextField from '@mui/material/TextField';
import { styled } from '@mui/material/styles';
import * as yup from 'yup';
import { useFormik } from 'formik';
import { useNavigate } from 'react-router-dom';
import { setToken } from '../../utils/token';
import { logIn } from '../../api/authentication';

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
}));

const CustomCheckbox = styled(Checkbox)(({ theme }) => ({
  color: theme.palette.secondary.contrastText,
  '&.Mui-checked': {
    color: theme.palette.secondary.contrastText,
  },
}));

const validationSchema = yup.object({
  // email: yup.string().email('Wprowadź poprawny email').required('Email jest wymagany'),
  password: yup.string().min(4, 'Hasło powinno składać się z minium 4 znaków ').required('Hasło jest wymagane'),
});

const LoginForm = () => {
  const navigation = useNavigate();
  const formik = useFormik({
    initialValues: {
      email: '',
      password: '',
    },
    validationSchema: validationSchema,
    onSubmit: (values) => {
      return handleSubmit(values.email, values.password);
    },
  });

  const handleSubmit = async (email: string, password: string) => {
    console.log({
      email,
      password,
    });
    if (!!email && !!password) {
      try {
        const response: any = await logIn({ username: email, password });
        setToken(response);
        navigation('/mock', { replace: true });
      } catch (e) {
        console.log({ e });
      }

    }
  };

  return (
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
        sx={{ autoComplete: 'off', maxWidth: 666, marginTop: '17px' }}
      >
        <CustomTextField
          fullWidth
          margin="normal"
          label="Email"
          name="email"
          value={formik.values.email}
          onChange={formik.handleChange}
          error={formik.touched.email && Boolean(formik.errors.email)}
          helperText={formik.touched.email && formik.errors.email}
        />
        <CustomTextField
          fullWidth
          margin="normal"
          label="Password"
          name="password"
          type="password"
          value={formik.values.password}
          onChange={formik.handleChange}
          error={formik.touched.password && Boolean(formik.errors.password)}
          helperText={formik.touched.password && formik.errors.password}
        />
        <FormControlLabel
          sx={{ marginTop: '37px' }}
          control={<CustomCheckbox value="remember" />}
          label="Zapamiętaj moje dane logowania"
          name="check"
        />

        <Grid spacing={2} container justifyContent="flex-end" sx={{ marginTop: '62px' }}>
          <Grid item>
            <Button type="submit" color="primary" variant="contained">
              Zaloguj się
            </Button>
          </Grid>
          <Grid item>
            <Button color="secondary" variant="text">
              Resetuj hasło
            </Button>
          </Grid>
        </Grid>
      </Box>
    </Box>
  );
};

export default LoginForm;
