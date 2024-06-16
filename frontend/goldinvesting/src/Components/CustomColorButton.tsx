import React from 'react';
import Button, { ButtonProps } from '@mui/material/Button';
import { styled } from '@mui/system';
import { purple, yellow } from '@mui/material/colors';
import { ThemeProvider, createTheme } from '@mui/material/styles';

const theme = createTheme({
    palette: {
      primary: yellow,
      secondary: purple,
    },
});

const Style = {
    color: theme.palette.getContrastText(yellow[500]),
    backgroundColor: yellow[500],
    '&:hover': {
      backgroundColor: yellow[700],
    },
}

interface Props extends ButtonProps {
  // Add any additional props you want to pass to the Button component
}

const CustomColorButton: React.FC<Props> = (props) => {
  return (
    <ThemeProvider theme = {theme}>
        <Button
        sx={Style}
        fullWidth 
        variant="contained"
        type="submit"
        {...props} // Pass any props to the underlying Button component
        />
    </ThemeProvider>
  );
};

export default CustomColorButton;
