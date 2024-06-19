import React, { useState } from 'react';
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
import CircularProgress from '@mui/material/CircularProgress';
import { useNavigate } from 'react-router-dom';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import { useDispatch, useSelector } from 'react-redux';
import { AppDispatch, RootState } from '../../store/store';
import { registerUser } from '../../features/user/userSlice';

interface Usuario {
  name: string;
  email: string;
  password: string;
}

const defaultTheme = createTheme();

export default function SignUp() {
  const [usuario, setUsuario] = useState<Usuario>({
    name: '',
    email: '',
    password: '',
  });
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();
  const dispatch: AppDispatch = useDispatch();
  const registerError = useSelector((state: RootState) => state.user.error);

  const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    setLoading(true);

    try {
      // Dispatch registerUser thunk
      const resultAction = await dispatch(registerUser(usuario));
      if (registerUser.fulfilled.match(resultAction)) {
        toast.success('Usuário cadastrado com sucesso!');
        setTimeout(() => navigate('/home'), 2500);
      } else {
        toast.error('Erro ao cadastrar usuário. Tente novamente mais tarde.');
      }
    } catch (error) {
      toast.error('Erro ao cadastrar usuário. Tente novamente mais tarde.');
    } finally {
      setLoading(false);
    }
  };

  const ColorButton = styled(Button)<ButtonProps>(({ theme }) => ({
    color: theme.palette.getContrastText(yellow[500]),
    backgroundColor: yellow[500],
    '&:hover': {
      backgroundColor: yellow[700],
    },
  }));

  return (
    <ThemeProvider theme={defaultTheme}>
      <ToastContainer />
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
            Criar conta
          </Typography>
          <Box component="form" noValidate onSubmit={handleSubmit} sx={{ mt: 3 }}>
            <Grid container spacing={2}>
              <Grid item xs={12}>
                <TextField
                  autoComplete="given-name"
                  name="name"
                  required
                  fullWidth
                  id="name"
                  label="Nome"
                  autoFocus
                  onChange={(e) => setUsuario({ ...usuario, name: e.target.value })}
                />
              </Grid>
              <Grid item xs={12}>
                <TextField
                  required
                  fullWidth
                  id="email"
                  label="Email"
                  name="email"
                  autoComplete="email"
                  onChange={(e) => setUsuario({ ...usuario, email: e.target.value })}
                />
              </Grid>
              <Grid item xs={12}>
                <TextField
                  name="password"
                  label="Senha"
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
                  label="Confirme a senha"
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
              disabled={loading}
            >
              {loading ? <CircularProgress size={24} /> : 'Criar conta'}
            </CustomColorButton>
          </Box>
        </Box>
      </Container>
    </ThemeProvider>
  );
}
