import { Box, Button, Grid, Input, InputAdornment, InputLabel, MenuItem, Modal, Select, SelectChangeEvent, createTheme } from '@mui/material';
import { purple, yellow } from '@mui/material/colors';
import React, { useState } from 'react';
import { createCheckingAccount, createFixedIncome, createStock, fetchBrokers, fetchStocksSymbols } from '../Services/api';
import AsyncAutoComplete from './AsyncAutoComplete';
import BasicDatePicker from './DateTimePickerViewRenderers';
import { useSelector } from 'react-redux';
import { RootState } from '../store/store';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

interface CustomModalProps {
  open: boolean;
  onClose: () => void;
}

const CustomModal: React.FC<CustomModalProps> = ({ open, onClose }) => {
  const theme = createTheme({
    palette: {
      primary: yellow,
      secondary: purple,
    },
  });
  const user = useSelector((state: RootState) => state.user.user.data);
  const [selectedLayout, setSelectedLayout] = useState<string>('Renda Fixa');

  type LayoutType = 'rendaFixa' | 'acoes' | 'contaCorrente';

  const [formData, setFormData] = useState<{
    rendaFixa: {
      broker: string;
      initialDate: Date | null;
      finalDate: Date | null;
      initialValue: string;
      yieldRate: string;
      paper: string;
    };
    acoes: {
      broker: string;
      stockSymbol: string;
      price: string;
      purchaseDate: Date | null;
      quantity: string;
    };
    contaCorrente: {
      broker: string;
      initialDate: Date | null;
      endDate: Date | null;
      initialValue: string;
      yieldRate: string;
      title: string;
    };
  }>({
    rendaFixa: {
      broker: '',
      initialDate: null,
      finalDate: null,
      initialValue: '',
      yieldRate: '',
      paper: '',
    },
    acoes: {
      broker: '',
      stockSymbol: '',
      price: '',
      purchaseDate: null,
      quantity: '',
    },
    contaCorrente: {
      broker: '',
      initialDate: null,
      endDate: null,
      initialValue: '',
      yieldRate: '',
      title: ''
    },
  });

  const resetForm = () => {
    setFormData({
      rendaFixa: {
        broker: '',
        initialDate: null,
        finalDate: null,
        initialValue: '',
        yieldRate: '',
        paper: '',
      },
      acoes: {
        broker: '',
        stockSymbol: '',
        price: '',
        purchaseDate: null,
        quantity: '',
      },
      contaCorrente: {
        broker: '',
        initialDate: null,
        endDate: null,
        initialValue: '',
        yieldRate: '',
        title: ''
      },
    });
  };

  const handleSelectChange = (event: SelectChangeEvent) => {
    setSelectedLayout(event.target.value as string);
  };

  const handleClose = () => {
    onClose();
  };

  const handleInputChange = (layout: LayoutType, field: string, value: any) => {
    setFormData({
      ...formData,
      [layout]: {
        ...formData[layout],
        [field]: value,
      },
    });
  };

  const handleSubmit = async (layout: string) => {
    try {
      switch (layout) {
        case 'rendaFixa':
          await createFixedIncome(user.id, formData.rendaFixa);
          toast.success('Renda Fixa adicionada com sucesso!');
          break;

        case 'acoes':
          await createStock(user.id, formData.acoes);
          toast.success('Ação adicionada com sucesso!');
          break;

        case 'contaCorrente':
          await createCheckingAccount(user.id, formData.contaCorrente);
          toast.success('Conta Corrente adicionada com sucesso!');
          break;

        default:
          break;
      }
      resetForm();
    } catch (error) {
      toast.error('Erro ao adicionar!');
    }
  };

  const style = {
    position: 'absolute' as 'absolute',
    top: '50%',
    left: '50%',
    transform: 'translate(-50%, -50%)',
    width: 600,
    bgcolor: 'background.paper',
    border: '2px solid #000',
    boxShadow: 24,
    pt: 2,
    px: 4,
    pb: 3,
  };

  return (
    <>
      <Modal
        open={open}
        onClose={handleClose}
        aria-labelledby="parent-modal-title"
        aria-describedby="parent-modal-description"
      >
        <Box sx={{ ...style, width: 600 }}>
          <Grid container spacing={2} justifyContent="center" alignItems="center">
            <Grid item xs={12}>
              <Select value={selectedLayout} onChange={handleSelectChange}>
                <MenuItem value="Renda Fixa">Renda Fixa (pré fixada)</MenuItem>
                <MenuItem value="Ações">Ações</MenuItem>
                <MenuItem value="Conta Corrente">Conta Corrente</MenuItem>
              </Select>
            </Grid>
            <Grid item xs={12}>
              {selectedLayout === 'Renda Fixa' && (
                <>
                  <Grid container>
                    <Grid item xs={6}>
                      <AsyncAutoComplete
                        valueKey="id"
                        labelKey="name"
                        labelText="Corretora"
                        fetchOptions={fetchBrokers}
                        onChange={(value) => handleInputChange('rendaFixa', 'broker', value)}
                      />
                    </Grid>
                    <Grid item xs={6} sx={{ marginY: 2 }}>
                      <InputLabel htmlFor="yieldRate">Papel</InputLabel>
                      <Input
                        id="paper"
                        value={formData.rendaFixa.paper}
                        onChange={(e) => handleInputChange('rendaFixa', 'paper', e.target.value)}
                      />
                    </Grid>
                    <Grid item xs={6} sx={{ marginY: 2 }}>
                      <BasicDatePicker
                        label="Data Inicial"
                        onChange={(value) => handleInputChange('rendaFixa', 'initialDate', value)}
                      />
                    </Grid>
                    <Grid item xs={6} sx={{ marginY: 2 }}>
                      <BasicDatePicker
                        label="Data final"
                        onChange={(value) => handleInputChange('rendaFixa', 'finalDate', value)}
                      />
                    </Grid>
                    <Grid item xs={6} sx={{ marginY: 2 }}>
                      <InputLabel htmlFor="initialValue">Valor</InputLabel>
                      <Input
                        id="initialValue"
                        startAdornment={<InputAdornment position="start">R$</InputAdornment>}
                        value={formData.rendaFixa.initialValue}
                        onChange={(e) => handleInputChange('rendaFixa', 'initialValue', e.target.value)}
                      />
                    </Grid>
                    <Grid item xs={6} sx={{ marginY: 2 }}>
                      <InputLabel htmlFor="yieldRate">Valorização final</InputLabel>
                      <Input
                        id="yieldRate"
                        endAdornment={<InputAdornment position="start">%</InputAdornment>}
                        value={formData.rendaFixa.yieldRate}
                        onChange={(e) => handleInputChange('rendaFixa', 'yieldRate', e.target.value)}
                      />
                    </Grid>
                    <Grid item xs={12} display="flex" justifyContent="flex-end" sx={{ marginY: 2 }}>
                      <Button
                        variant="contained"
                        sx={{
                          color: theme.palette.getContrastText(yellow[500]),
                          backgroundColor: yellow[500],
                          '&:hover': {
                            backgroundColor: yellow[700],
                          },
                        }}
                        onClick={() => handleSubmit('rendaFixa')}
                      >
                        Adicionar
                      </Button>
                    </Grid>
                  </Grid>
                </>
              )}
              {selectedLayout === 'Ações' && (
                <Grid container>
                  <Grid item xs={12}>
                    <AsyncAutoComplete
                      valueKey="id"
                      labelKey="name"
                      labelText="Corretora"
                      fetchOptions={fetchBrokers}
                      onChange={(value) => handleInputChange('acoes', 'broker', value)}
                    />
                  </Grid>
                  <Grid item xs={6} sx={{ marginTop: 2 }}>
                    <AsyncAutoComplete
                      valueKey="id"
                      labelKey="ticker"
                      labelText="Ativo"
                      fetchOptions={fetchStocksSymbols}
                      onChange={(value) => handleInputChange('acoes', 'stockSymbol', value)}
                    />
                  </Grid>
                  <Grid item xs={6} sx={{ marginTop: 2 }}>
                    <InputLabel htmlFor="price">Preço</InputLabel>
                    <Input
                      id="price"
                      startAdornment={<InputAdornment position="start">$</InputAdornment>}
                      value={formData.acoes.price}
                      onChange={(e) => handleInputChange('acoes', 'price', e.target.value)}
                    />
                  </Grid>
                  <Grid item xs={6} sx={{ marginTop: 2 }}>
                    <BasicDatePicker
                      label="Data da compra"
                      onChange={(value) => handleInputChange('acoes', 'purchaseDate', value)}
                    />
                  </Grid>
                  <Grid item xs={12} sx={{ marginTop: 2 }}>
                    <InputLabel htmlFor="quantity">Quantidade</InputLabel>
                    <Input
                      id="quantity"
                      value={formData.acoes.quantity}
                      onChange={(e) => handleInputChange('acoes', 'quantity', e.target.value)}
                    />
                  </Grid>
                  <Grid item xs={12} display="flex" justifyContent="flex-end" sx={{ marginY: 2 }}>
                    <Button
                      variant="contained"
                      sx={{
                        color: theme.palette.getContrastText(yellow[500]),
                        backgroundColor: yellow[500],
                        '&:hover': {
                          backgroundColor: yellow[700],
                        },
                      }}
                      onClick={() => handleSubmit('acoes')}
                    >
                      Adicionar
                    </Button>
                  </Grid>
                </Grid>
              )}
              {selectedLayout === 'Conta Corrente' && (
                <Grid container>
                  <Grid item xs={12}>
                    <AsyncAutoComplete
                      valueKey="id"
                      labelKey="name"
                      labelText="Corretora"
                      fetchOptions={fetchBrokers}
                      onChange={(value) => handleInputChange('contaCorrente', 'broker', value)}
                    />
                  </Grid>
                  <Grid item xs={6} sx={{ marginY: 2 }}>
                    <InputLabel htmlFor="title">Título</InputLabel>
                    <Input
                      id="title"
                      value={formData.contaCorrente.title}
                      onChange={(e) => handleInputChange('contaCorrente', 'title', e.target.value)}
                    />
                  </Grid>
                  <Grid item xs={6} sx={{ marginY: 2 }}>
                    <BasicDatePicker
                      label="Data inicial"
                      onChange={(value) => handleInputChange('contaCorrente', 'initialDate', value)}
                    />
                  </Grid>
                  <Grid item xs={6} sx={{ marginY: 2 }}>
                    <InputLabel htmlFor="initialValue">Valor Inicial</InputLabel>
                    <Input
                      id="initialValue"
                      startAdornment={<InputAdornment position="start">$</InputAdornment>}
                      value={formData.contaCorrente.initialValue}
                      onChange={(e) => handleInputChange('contaCorrente', 'initialValue', e.target.value)}
                    />
                  </Grid>
                  <Grid item xs={6} sx={{ marginY: 2 }}>
                    <InputLabel htmlFor="yieldRate">Taxa de valorização mensal</InputLabel>
                    <Input
                      id="yieldRate"
                      endAdornment={<InputAdornment position="start">%</InputAdornment>}
                      value={formData.contaCorrente.yieldRate}
                      onChange={(e) => handleInputChange('contaCorrente', 'yieldRate', e.target.value)}
                    />
                  </Grid>
                  <Grid item xs={12} display="flex" justifyContent="flex-end" sx={{ marginY: 2 }}>
                    <Button
                      variant="contained"
                      sx={{
                        color: theme.palette.getContrastText(yellow[500]),
                        backgroundColor: yellow[500],
                        '&:hover': {
                          backgroundColor: yellow[700],
                        },
                      }}
                      onClick={() => handleSubmit('contaCorrente')}
                    >
                      Adicionar
                    </Button>
                  </Grid>
                </Grid>
              )}
            </Grid>
          </Grid>
        </Box>
      </Modal>
      <ToastContainer />
    </>
  );
};

export default CustomModal;
