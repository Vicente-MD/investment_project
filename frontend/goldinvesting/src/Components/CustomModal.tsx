import React, { useState } from 'react';
import { Modal, Select, MenuItem, Grid, Typography, SelectChangeEvent, Box, InputLabel, Input, InputAdornment, Button, createTheme } from '@mui/material';
import BasicDatePicker from './DateTimePickerViewRenderers';
import AutoComplete from './AutoComplete';
import { purple, yellow } from '@mui/material/colors';

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


  const [selectedLayout, setSelectedLayout] = useState<string>('Renda Fixa');

  const handleSelectChange = (event: SelectChangeEvent) => {
    setSelectedLayout(event.target.value as string);
  };

  const handleClose = () => {
    // Call onClose when the modal is closed
    onClose();
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
    <Modal
      open={open}
      onClose={handleClose}
      aria-labelledby="parent-modal-title"
      aria-describedby="parent-modal-description">
      <Box sx={{ ...style, width: 600 }}>
        <Grid container spacing={2} justifyContent="center" alignItems="center">
          <Grid item xs={12}>
            <Select value={selectedLayout} onChange={handleSelectChange}>
              <MenuItem value="Renda Fixa">Renda Fixa</MenuItem>
              <MenuItem value="Ações">Ações</MenuItem>
              <MenuItem value="Tesouro Direto">Tesouro Direto</MenuItem>
              <MenuItem value="Conta Corrente">Conta Corrente</MenuItem>
            </Select>
          </Grid>
          <Grid item xs={12}>
            {selectedLayout === 'Renda Fixa' && (
              <>
                <Grid container>
                  <Grid item xs={12}>
                    <Typography>This is the Renda Fixa</Typography>
                  </Grid>
                  <Grid item xs={12}>
                    <AutoComplete />
                  </Grid>
                  <Grid item xs={6} sx={{ marginY: 2 }}>
                    <InputLabel htmlFor="standard-adornment-amount">Total Investment</InputLabel>
                    <BasicDatePicker label='select Initial Date' />
                  </Grid>
                  <Grid item xs={6} sx={{ marginY: 2 }}>
                    <InputLabel htmlFor="standard-adornment-amount">Total Investment</InputLabel>
                    <BasicDatePicker label='select Final Date' />
                  </Grid>
                  <Grid item xs={6} sx={{ marginY: 2 }}>
                    <InputLabel htmlFor="standard-adornment-amount">Amount</InputLabel>
                    <Input
                      id="standard-adornment-amount"
                      startAdornment={<InputAdornment position="start">$</InputAdornment>}
                    />
                  </Grid>
                  <Grid item xs={6} sx={{ marginY: 2 }}>
                    <InputLabel htmlFor="standard-adornment-amount">Total Investment</InputLabel>
                    <Input
                      id="standard-adornment-amount"
                      startAdornment={<InputAdornment position="start">$</InputAdornment>}
                    />
                  </Grid>
                  <Grid item xs={12} display="flex" justifyContent="flex-end" sx={{ marginY: 2 }}>
                    <Button variant="contained" sx={{
                      color: theme.palette.getContrastText(yellow[500]),
                      backgroundColor: yellow[500],
                      '&:hover': {
                        backgroundColor: yellow[700],
                      }
                    }}>
                      Submit
                    </Button>
                  </Grid>
                </Grid>
              </>
            )}
            {selectedLayout === 'Ações' && (
              <Grid container>
                <Grid item xs={12}>
                  <Typography>This is the Ações</Typography>
                </Grid>
                <Grid item xs={6} sx={{ marginTop: 2 }}>
                  <Typography>broker</Typography>
                  <AutoComplete />
                </Grid>
                <Grid item xs={6} sx={{ marginTop: 2 }}>
                  <InputLabel htmlFor="standard-adornment-amount">Stock</InputLabel>
                  <AutoComplete />
                </Grid>
                <Grid item xs={6} sx={{ marginTop: 2 }}>
                  <InputLabel htmlFor="standard-adornment-amount">Price</InputLabel>
                  <Input
                    id="standard-adornment-amount"
                    startAdornment={<InputAdornment position="start">$</InputAdornment>}
                    sx={{
                      bottom: 0,
                      left: 0,
                      marginTop: 2,
                      padding: 1,
                    }}
                  />
                </Grid>
                <Grid item xs={6} sx={{ marginTop: 2 }}>
                  <InputLabel htmlFor="standard-adornment-amount">Initial Date</InputLabel>
                  <BasicDatePicker label='select Final Date' />
                </Grid>
                <Grid item xs={12} sx={{ marginTop: 2 }}>
                  <InputLabel htmlFor="standard-adornment-amount">Quantidade</InputLabel>
                  <Input
                    id="standard-adornment-amount"
                    startAdornment={<InputAdornment position="start"></InputAdornment>}
                    sx={{
                      bottom: 0,
                      left: 0,
                      marginTop: 2,
                      padding: 1,
                    }}
                  />
                </Grid>
                <Grid item xs={12} display="flex" justifyContent="flex-end" sx={{ marginY: 2 }}>
                  <Button variant="contained" sx={{
                    color: theme.palette.getContrastText(yellow[500]),
                    backgroundColor: yellow[500],
                    '&:hover': {
                      backgroundColor: yellow[700],
                    }
                  }}>
                    Submit
                  </Button>
                </Grid>
              </Grid>
            )}
            {selectedLayout === 'Tesouro Direto' && (
              <>
                <Grid container>
                  <Grid item xs={12}>
                    <Typography>This is the Tesouro Direto</Typography>
                  </Grid>
                  <Grid item xs={6} sx={{ marginTop: 2 }}>
                    <Typography>broker</Typography>
                    <AutoComplete />
                  </Grid>
                  <Grid item xs={6} sx={{ marginTop: 2 }}>
                    <InputLabel htmlFor="standard-adornment-amount">Stock</InputLabel>
                    <AutoComplete />
                  </Grid>
                  <Grid item xs={6} sx={{ marginY: 2 }}>
                    <InputLabel htmlFor="standard-adornment-amount">Initial Date</InputLabel>
                    <BasicDatePicker label='select Initial Date' />
                  </Grid>
                  <Grid item xs={6} sx={{ marginY: 2 }}>
                    <InputLabel htmlFor="standard-adornment-amount">Final Date</InputLabel>
                    <BasicDatePicker label='select Final Date' />
                  </Grid>
                  <Grid item xs={6} sx={{ marginY: 2 }}>
                    <InputLabel htmlFor="standard-adornment-amount">Amount</InputLabel>
                    <Input
                      id="standard-adornment-amount"
                      startAdornment={<InputAdornment position="start">$</InputAdornment>}
                    />
                  </Grid>
                  <Grid item xs={6} sx={{ marginY: 2 }}>
                    <InputLabel htmlFor="standard-adornment-amount">Total Investment</InputLabel>
                    <Input
                      id="standard-adornment-amount"
                      startAdornment={<InputAdornment position="start">$</InputAdornment>}
                    />
                  </Grid>
                  <Grid item xs={12} display="flex" justifyContent="flex-end" sx={{ marginY: 2 }}>
                    <Button variant="contained" sx={{
                      color: theme.palette.getContrastText(yellow[500]),
                      backgroundColor: yellow[500],
                      '&:hover': {
                        backgroundColor: yellow[700],
                      }
                    }}>
                      Submit
                    </Button>
                  </Grid>
                </Grid>
              </>
            )}
            {selectedLayout === 'Conta Corrente' && (
              <Grid container>
                <Grid item xs={12}>
                  <Typography>This is the Conta Corrente</Typography>
                </Grid>
                <Grid item xs={12}>
                  <Typography>broker</Typography>

                  <AutoComplete />
                </Grid>
                <Grid item xs={6} sx={{ marginY: 2 }}>
                  <InputLabel htmlFor="standard-adornment-amount">Initial Date</InputLabel>
                  <BasicDatePicker label='select Initial Date' />
                </Grid>
                <Grid item xs={6} sx={{ marginY: 2 }}>
                  <InputLabel htmlFor="standard-adornment-amount">Final Date</InputLabel>
                  <BasicDatePicker label='select Final Date' />
                </Grid>
                <Grid item xs={6} sx={{ marginY: 2 }}>
                  <InputLabel htmlFor="standard-adornment-amount">Initial Value</InputLabel>
                  <Input
                    id="standard-adornment-amount"
                    startAdornment={<InputAdornment position="start">$</InputAdornment>}
                  />
                </Grid>
                <Grid item xs={6} sx={{ marginY: 2 }}>
                  <InputLabel htmlFor="standard-adornment-amount">Yield Rate / year</InputLabel>
                  <Input
                    id="standard-adornment-amount"
                    startAdornment={<InputAdornment position="start">$</InputAdornment>}
                  />
                </Grid>
                <Grid item xs={12} display="flex" justifyContent="flex-end" sx={{ marginY: 2 }}>
                  <Button variant="contained" sx={{
                    color: theme.palette.getContrastText(yellow[500]),
                    backgroundColor: yellow[500],
                    '&:hover': {
                      backgroundColor: yellow[700],
                    }
                  }}>
                    Submit
                  </Button>
                </Grid>
              </Grid>

            )}
          </Grid>
        </Grid>
      </Box>
    </Modal>
  );
};

export default CustomModal;

