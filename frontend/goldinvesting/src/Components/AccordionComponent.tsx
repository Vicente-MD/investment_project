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

interface TableRowData {
  id: number;
  name: string;
  value: number;
}

interface AccordionItem {
  title: string;
  rows: TableRowData[];
}

interface AccordionComponentProps {
  items: AccordionItem[];
}

const AccordionComponent: React.FC<AccordionComponentProps> = ({ items }) => {
  return (
    <div>
      {items.map((item, index) => {
        // Transform the rows into the format expected by PieChart
        const pieData = item.rows.map(row => ({ label: row.name, value: row.value }));
        
        return (
          <Accordion key={index}>
            <AccordionSummary
              expandIcon={<ExpandMoreIcon />}
              aria-controls={`panel${index}-content`}
              id={`panel${index}-header`}
            >
              <Typography>{item.title}</Typography>
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
                      <TableCell>ID</TableCell>
                      <TableCell>Name</TableCell>
                      <TableCell>Value</TableCell>
                      <TableCell> </TableCell>
                      
                    </TableRow>
                  </TableHead>
                  <TableBody>
                    {item.rows.map((row) => (
                      <TableRow key={row.id}>
                        <TableCell>{row.id}</TableCell>
                        <TableCell>{row.name}</TableCell>
                        <TableCell>{row.value}</TableCell>
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
