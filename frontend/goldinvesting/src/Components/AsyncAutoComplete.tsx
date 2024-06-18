import { Autocomplete, TextField } from '@mui/material';
import React, { useState, useEffect, SyntheticEvent } from 'react';

type Props = {
  fetchOptions: (input: string) => Promise<Array<any>>;
  labelKey: string;
  valueKey: string;
  labelText: string;
  onChange: (value: any) => void;
};

function AsyncAutoComplete({ fetchOptions, labelKey, valueKey, labelText, onChange }: Props) {
  const [options, setOptions] = useState<Array<any>>([]);
  const [inputValue, setInputValue] = useState('');

  useEffect(() => {
    if (inputValue === '') {
      setOptions([]);
      return;
    }

    const fetchData = async () => {
      try {
        const newOptions = await fetchOptions(inputValue);
        setOptions(newOptions);
      } catch (error) {
        console.error('Error fetching options:', error);
      }
    };

    fetchData();
  }, [inputValue, fetchOptions]);

  return (
    <Autocomplete
      disablePortal
      id="combo-box-async"
      options={options}
      getOptionLabel={(option) => option[labelKey]}
      sx={{ width: 250, alignItems: 'center', alignContent: 'center' }}
      onInputChange={(event: SyntheticEvent, newInputValue: string) => setInputValue(newInputValue)}
      onChange={(event, value) => onChange(value)}
      renderInput={(params) => <TextField {...params} label={labelText} />}
    />
  );
}

export default AsyncAutoComplete;
