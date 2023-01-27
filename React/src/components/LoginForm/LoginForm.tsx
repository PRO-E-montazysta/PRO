import { Grid, Typography, Box, FormControlLabel, Checkbox, Button } from '@mui/material';
import TextField from '@mui/material/TextField';
import { styled } from '@mui/material/styles';

import { logIn } from '../../api/Authentication';

const CustomeTextField = styled(TextField)(({ theme }) => ({
  border: '2px solid #96C0FB',
  borderRadius: '8px',
  label: { shrink: true, color: theme.palette.secondary.contrastText },
  input: { color: theme.palette.secondary.contrastText },
}));

const LoginForm = () => {
  const handleSubmit = async (event: any) => {
    event.preventDefault();
    const data = new FormData(event.currentTarget);
    const email = data.get('email')?.toString();
    const password = data.get('password')?.toString();
    console.log({ check: event.currentTarget });
    console.log({
      email,
      password,
      check: data.get('check'),
    });

    if (!!email && !!password) {
      const response = await logIn({ email, password });
      console.log({ response });
      //setToken, Link
    }
  };

  //   const handleSubmit = (event: any) => {
  //     // wysyłam dane do bazy otrzymuje odpowiedź
  //     //jeśli zła to wypadałoby to jakoś pokazać
  //     //await odpowiedź
  //   };
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
        onSubmit={handleSubmit}
        noValidate
        sx={{ maxWidth: 666, marginTop: '17px' }}
        autoComplete="off"
      >
        <CustomeTextField autoFocus fullWidth margin="normal" label="Email" name="email" required />
        <CustomeTextField
          autoFocus
          fullWidth
          margin="normal"
          label="Password"
          name="password"
          type="password"
          required
        />
        <FormControlLabel
          sx={{ marginTop: '37px' }}
          control={<Checkbox value="remember" color="primary" />}
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
