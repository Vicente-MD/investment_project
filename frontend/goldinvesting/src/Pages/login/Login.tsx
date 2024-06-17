import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
import { AppDispatch, RootState } from '../../store/store';
import { authenticateUser } from '../../features/actions/userActions';
import { TextField, Container, Typography, Box, Grid, Paper, styled, Link, AppBar, Toolbar } from '@mui/material';
import imagem1 from "../../shared/images/Carteira.png";
import CustomColorButton from '../../Components/CustomColorButton';
import "./login.css";

const Login: React.FC = () => {
  const dispatch: AppDispatch = useDispatch();
  const isAuthenticated = useSelector((state: RootState) => state.user.user !== null);
  const navigate = useNavigate();
  const [username, setUsername] = useState<string>('');
  const [password, setPassword] = useState<string>('');
  const [error, setError] = useState<string>('');

  useEffect(() => {
    if (isAuthenticated) {
      navigate('/home');
    }
  }, [isAuthenticated, navigate]);

  const handleLogin = () => {
    if (!username || !password) {
      setError("Preencha todos os campos");
      return;
    }

    const credenciais = { email: username, password };
    dispatch(authenticateUser(credenciais));
  };

  const Item = styled(Paper)(({ theme }) => ({
    logo: {
      maxWidth: 40,
      marginRight: '10px',
    },
  }));

  return (
    <Container>
      <Box sx={{ flexGrow: 1 }}>
        <Grid container spacing={16}>
          <Grid item xs={12} md={12}>
            <AppBar sx={{ bgcolor: 'black' }}>
              <Toolbar>
                <Item>Gold Investing(mudar)</Item>
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
                type="password"
                fullWidth
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                margin="normal"
              />
              {error && (
                <Typography color="error" variant="body2" gutterBottom>
                  {error}
                </Typography>
              )}
              <CustomColorButton
                sx={{ marginY: 2 }}
                onClick={handleLogin}
              >
                Entrar
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