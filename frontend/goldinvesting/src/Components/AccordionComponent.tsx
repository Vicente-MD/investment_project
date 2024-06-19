import React from 'react';
import Accordion from '@mui/material/Accordion';
import AccordionSummary from '@mui/material/AccordionSummary';
import AccordionDetails from '@mui/material/AccordionDetails';
import Typography from '@mui/material/Typography';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';
import IconButton from '@mui/material/IconButton';
import DeleteIcon from '@mui/icons-material/Delete';
import { LineChart, Line, XAxis, YAxis, CartesianGrid, Tooltip, Legend, ResponsiveContainer } from 'recharts';
import { translateType } from '../utils/utils';

interface AccordionItem {
  year: string | number;
  month: string | number;
  price: number;
  dividend: number;
  investmentType: string;
}

interface AccordionComponentProps {
  items: AccordionItem[];
}

const AccordionComponent: React.FC<AccordionComponentProps> = ({ items }) => {

  const itemsByType = items.reduce((acc, item) => {
    if (!acc[item.investmentType]) {
      acc[item.investmentType] = [];
    }
    acc[item.investmentType].push(item);
    return acc;
  }, {} as Record<string, AccordionItem[]>);

  return (
    <div>
      {Object.entries(itemsByType).map(([type, items], index) => {
        const lineData = items.map(item => ({ date: `${item.month}/${item.year}`, price: item.price }));

        return (
          <Accordion key={type}>
            <AccordionSummary
              expandIcon={<ExpandMoreIcon />}
              aria-controls={`panel${index}-content`}
              id={`panel${index}-header`}
            >
              <Typography>{translateType(type)}</Typography>
            </AccordionSummary>
            <AccordionDetails>
              <ResponsiveContainer width="100%" height={300}>
                <LineChart data={lineData}>
                  <CartesianGrid strokeDasharray="3 3" />
                  <XAxis dataKey="date" />
                  <YAxis />
                  <Tooltip />
                  <Legend />
                  <Line type="monotone" dataKey="price" name="Preço" stroke="#8884d8" />
                </LineChart>
              </ResponsiveContainer>
              <TableContainer component={Paper}>
                <Table>
                  <TableHead>
                    <TableRow>
                      <TableCell>Ano</TableCell>
                      <TableCell>Mês</TableCell>
                      <TableCell>Preço</TableCell>
                    </TableRow>
                  </TableHead>
                  <TableBody>
                    {items.map((item, itemIndex) => (
                      <TableRow key={itemIndex}>
                        <TableCell>{item.year}</TableCell>
                        <TableCell>{item.month}</TableCell>
                        <TableCell>{item.price}</TableCell>
                        <TableCell sx={{ display: 'flex', justifyContent: 'flex-end' }}>
                          <IconButton color="error" aria-label="delete">
                            <DeleteIcon />
                          </IconButton>
                        </TableCell>
                      </TableRow>
                    ))}
                  </TableBody>
                </Table>
              </TableContainer>
            </AccordionDetails>
          </Accordion>
        );
      })}
    </div>
  );
};

export default AccordionComponent;
