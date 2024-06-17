import * as React from 'react';
import Avatar from '@mui/material/Avatar';
import Button, { ButtonProps } from '@mui/material/Button';
import CssBaseline from '@mui/material/CssBaseline';
import TextField from '@mui/material/TextField';
import Grid from '@mui/material/Grid';
import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';
import Container from '@mui/material/Container';
import { createTheme, styled, ThemeProvider } from '@mui/material/styles';
import { yellow } from '@mui/material/colors';
import AccountCircleIcon from '@mui/icons-material/AccountCircle';
import CustomColorButton from '../../Components/CustomColorButton';
import ErrosValidacaoException from '../../app/service/errosValidacao';
import UsuarioService from '../../app/service/usuarioService';

interface Usuario {
  name: string;
  email: string;
  password: string;
}

const defaultTheme = createTheme();

export default function SignUp() {

  const [usuario, setUsuario] = React.useState<Usuario>({
    name: '',
    email: '',
    password: '',
  });
  const usuarioService = new UsuarioService();

  const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();

    try {
      usuarioService.validar(usuario); // Validate user data before sending

      const response = await usuarioService.cadastrarUsuario(usuario);
      console.log('User registered successfully:', response);
      // Handle successful registration (e.g., redirect to login page)
    } catch (error) {
      if (error instanceof ErrosValidacaoException) {
        console.error('Validation errors:', error);
        // Display validation errors to the user
      } else {
        console.error('Error registering user:', error);
        // Handle other errors
      }
    }
  }

  const ColorButton = styled(Button)<ButtonProps>(({ theme }) => ({
    color: theme.palette.getContrastText(yellow[500]),
    backgroundColor: yellow[500],
    '&:hover': {
      backgroundColor: yellow[700],
    },
  }));

  return (
    <ThemeProvider theme={defaultTheme}>
      <Container component="main" maxWidth="xs">
        <CssBaseline />
        <Box
          sx={{
            marginTop: 8,
            display: 'flex',
            flexDirection: 'column',
            alignItems: 'center',
          }}
        >
          <Avatar sx={{ m: 1, bgcolor: yellow[500] }}>
            <AccountCircleIcon />
          </Avatar>
          <Typography component="h1" variant="h5">
            Account
          </Typography>
          <Box component="form" noValidate onSubmit={handleSubmit} sx={{ mt: 3 }}>
            <Grid container spacing={2}>
              <Grid item xs={12}>
                <TextField
                  autoComplete="given-name"
                  name="firstName"
                  required
                  fullWidth
                  id="firstName"
                  label="First Name"
                  autoFocus
                  onChange={(e) => setUsuario({ ...usuario, name: e.target.value })}
                />
              </Grid>
              <Grid item xs={12}>
                <TextField
                  required
                  fullWidth
                  id="email"
                  label="Email Address"
                  name="email"
                  autoComplete="email"
                  onChange={(e) => setUsuario({ ...usuario, email: e.target.value })}
                />
              </Grid>
              <Grid item xs={12}>
                <TextField
                  name="password"
                  label="Password"
                  type="password"
                  id="password"
                  autoComplete="new-password"
                  required
                  fullWidth
                  onChange={(e) => setUsuario({ ...usuario, password: e.target.value })}
                />
              </Grid>
              <Grid item xs={12}>
                <TextField
                  name="confirmPassword"
                  label="Confirm Password"
                  type="password"
                  id="confirmPassword"
                  autoComplete="new-password"
                  required
                  fullWidth
                  onChange={(e) => setUsuario({ ...usuario, password: e.target.value })}
                />
              </Grid>
            </Grid>
            <CustomColorButton
              sx={{ mt: 3, mb: 2 }}
              type="submit"
            >
              Sign Up
            </CustomColorButton>
          </Box>
        </Box>
      </Container>
    </ThemeProvider>
  );
}
