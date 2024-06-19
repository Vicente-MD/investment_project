// Login.tsx
import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
import { AppDispatch, RootState } from '../../store/store';
import { authenticateUser } from '../../Features/user/userSlice'; // Updated import
import {
  TextField, Container, Typography, Box, Grid, Paper, styled, Link, AppBar, Toolbar, CircularProgress, IconButton, InputAdornment
} from '@mui/material';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import Visibility from '@mui/icons-material/Visibility';
import VisibilityOff from '@mui/icons-material/VisibilityOff';
import imagem1 from "../../Shared/images/Carteira.png";
import CustomColorButton from '../../Components/CustomColorButton';
import "./login.css";
import Logo from '../../Shared/images/logo.png';

const Login: React.FC = () => {
  const dispatch: AppDispatch = useDispatch();
  const isAuthenticated = useSelector((state: RootState) => state.user.isAuthenticated);
  const loginError = useSelector((state: RootState) => state.user.error);
  const navigate = useNavigate();
  const [username, setUsername] = useState<string>('');
  const [password, setPassword] = useState<string>('');
  const [error, setError] = useState<string>('');
  const [loading, setLoading] = useState<boolean>(false);
  const [showPassword, setShowPassword] = useState<boolean>(false);

  useEffect(() => {
    if (isAuthenticated) {
      setLoading(false);
      navigate('/home');
    }
  }, [isAuthenticated, navigate]);

  useEffect(() => {
    if (loginError) {
      toast.error(loginError);
      setLoading(false);
    }
  }, [loginError]);

  const handleLogin = () => {
    if (!username || !password) {
      setError("Preencha todos os campos");
      return;
    }

    setLoading(true);
    const credenciais = { email: username, password };
    dispatch(authenticateUser(credenciais));
  };

  const handleTogglePasswordVisibility = () => {
    setShowPassword(!showPassword);
  };

  const Item = styled(Paper)(({ theme }) => ({
    logo: {
      maxWidth: 40,
      marginRight: '10px',
    },
  }));

  return (
    <Container>
      <ToastContainer />
      <Box sx={{ flexGrow: 1 }}>
        <Grid container spacing={16}>
          <Grid item xs={12} md={12}>
            <AppBar sx={{ bgcolor: 'black' }}>
              <Toolbar>
                <Box>
                  <img
                    src={Logo}
                    alt="logo"
                    style={{
                      backgroundColor: 'black', 
                      maxWidth: '130px',
                      height: 'auto',
                    }}
                  />
                </Box>
              </Toolbar>
            </AppBar>
          </Grid>
          <Grid item xs={12} md={8}>
            <Box display="flex" flexDirection="column" alignItems="center">
              <img src={imagem1} className="logo-login" alt="logo" />
              <Typography variant="h4" gutterBottom>
                A melhor plataforma para acompanhar seus investimentos...
              </Typography>
              <Typography variant="h5" gutterBottom>
                Notícias, Cotações, Simulações, Rentabilidade, e muito mais...
              </Typography>
            </Box>
          </Grid>
          <Grid item xs={12} md={4}>
            <Box display="flex" flexDirection="column" alignItems="center">
              <Typography variant="h4" gutterBottom>
                Login
              </Typography>
              <TextField
                label="E-mail"
                variant="outlined"
                fullWidth
                value={username}
                onChange={(e) => setUsername(e.target.value)}
                margin="normal"
              />
              <TextField
                label="Senha"
                variant="outlined"
                type={showPassword ? "text" : "password"}
                fullWidth
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                margin="normal"
                InputProps={{
                  endAdornment: (
                    <InputAdornment position="end">
                      <IconButton
                        aria-label="toggle password visibility"
                        onClick={handleTogglePasswordVisibility}
                        edge="end"
                      >
                        {showPassword ? <VisibilityOff /> : <Visibility />}
                      </IconButton>
                    </InputAdornment>
                  ),
                }}
              />
              {error && (
                <Typography color="error" variant="body2" gutterBottom>
                  {error}
                </Typography>
              )}
              <CustomColorButton
                sx={{ marginY: 2 }}
                onClick={handleLogin}
                disabled={loading}
              >
                {loading ? <CircularProgress size={24} /> : 'Entrar'}
              </CustomColorButton>
              <Link href="#" variant="body2" style={{ marginBottom: '16px' }}>
                Esqueceu a senha?
              </Link>
              <Link href="/registration" variant="body2">
                Não tem conta? Registrar agora
              </Link>
            </Box>
          </Grid>
        </Grid>
      </Box>
    </Container>
  );
};

export default Login;
