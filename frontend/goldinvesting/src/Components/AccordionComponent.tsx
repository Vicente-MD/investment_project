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
import { PieChart } from '@mui/x-charts/PieChart';
import IconButton from '@mui/material/IconButton';
import DeleteIcon from '@mui/icons-material/Delete';

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

  console.log(itemsByType)

  return (
    <div>
      {Object.entries(itemsByType).map(([type, items], index) => {
        // Transform the rows into the format expected by PieChart
        const pieData = items.map(item => ({ label: `${item.year}-${item.month}`, value: item.price }));
        return (
          <Accordion key={type}>
            <AccordionSummary
              expandIcon={<ExpandMoreIcon />}
              aria-controls={`panel${index}-content`}
              id={`panel${index}-header`}
            >
              <Typography>{type}</Typography>
            </AccordionSummary>
            <AccordionDetails>
              <PieChart
                series={[
                  {
                    data: pieData,
                    highlightScope: { faded: 'global', highlighted: 'item' },
                    faded: { innerRadius: 30, additionalRadius: -30, color: 'gray' },
                  },
                ]}
                height={200}
              />
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
