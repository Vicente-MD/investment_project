import React from 'react';
import {
  Accordion,
  AccordionDetails,
  AccordionSummary,
  IconButton,
  Paper,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Typography,
} from '@mui/material';
import {
  CheckCircleOutline,
  Delete as DeleteIcon,
  ExpandMore as ExpandMoreIcon,
} from '@mui/icons-material';
import {
  CartesianGrid,
  Legend,
  Line,
  LineChart,
  ResponsiveContainer,
  Tooltip,
  XAxis,
  YAxis,
} from 'recharts';
import { toast } from 'react-toastify';
import { concludeCheckingAccount, deleteFixedIncome, sellStock } from '../Services/api';
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
  checkingAccounts: any[];
  fixedIncomes: any[];
  stocks: any[];
}

const AccordionComponent: React.FC<AccordionComponentProps> = ({
  items,
  stocks,
  checkingAccounts,
  fixedIncomes,
}) => {
  const groupedItems = items.reduce((acc, item) => {
    const { investmentType } = item;
    if (!acc[investmentType]) {
      acc[investmentType] = [];
    }
    acc[investmentType].push(item);
    return acc;
  }, {} as Record<string, AccordionItem[]>);

  const handleSellStock = async (stock: any) => {
    try {
      await sellStock(stock.id);
      toast.success('Ação vendida com sucesso');
    } catch {
      toast.error('Erro ao vender ação');
    }
  };

  const handleDeletionFixedIncome = async (fixedIncome: any) => {
    try {
      await deleteFixedIncome(fixedIncome.id);
      toast.success('Renda fixa deletada com sucesso');
    } catch {
      toast.error('Erro ao deletar renda fixa');
    }
  };

  const handleConcludeCheckingAccount = async (checkingAccount: any) => {
    try {
      await concludeCheckingAccount(checkingAccount.id);
      toast.success('Conta corrente concluída com sucesso');
    } catch {
      toast.error('Erro ao concluir conta corrente');
    }
  };

  const renderAccordion = (type: string, items: AccordionItem[]) => {
    const lineData = items.map((item) => ({
      date: `${item.month}/${item.year}`,
      price: item.price,
    }));

    return (
      <Accordion key={type}>
        <AccordionSummary
          expandIcon={<ExpandMoreIcon />}
          aria-controls={`${type}-content`}
          id={`${type}-header`}
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
              {type === 'STOCK' && (
                <>
                  <TableHead>
                    <TableRow>
                      <TableCell>Ativo</TableCell>
                      <TableCell>Preço inicial</TableCell>
                      <TableCell>Data de compra</TableCell>
                      <TableCell>Corretora</TableCell>
                      <TableCell>Ações</TableCell>
                    </TableRow>
                  </TableHead>
                  <TableBody>
                    {stocks.map((stock, index) => (
                      <TableRow key={index}>
                        <TableCell>{stock.stockSymbol.ticker}</TableCell>
                        <TableCell>{stock.purchasePrice}</TableCell>
                        <TableCell>{stock.purchaseDate}</TableCell>
                        <TableCell>{stock.broker.name}</TableCell>
                        <TableCell>
                          <IconButton
                            aria-label="Marcar como concluída"
                            onClick={() => handleSellStock(stock)}
                          >
                            <CheckCircleOutline />
                          </IconButton>
                        </TableCell>
                      </TableRow>
                    ))}
                  </TableBody>
                </>
              )}
              {type === 'FIXED_INCOME' && (
                <>
                  <TableHead>
                    <TableRow>
                      <TableCell>Papel</TableCell>
                      <TableCell>Valor inicial</TableCell>
                      <TableCell>Data inicial</TableCell>
                      <TableCell>Data final</TableCell>
                      <TableCell>Valorização final</TableCell>
                      <TableCell>Ações</TableCell>
                    </TableRow>
                  </TableHead>
                  <TableBody>
                    {fixedIncomes.map((fixedIncome, index) => (
                      <TableRow key={index}>
                        <TableCell>{fixedIncome.paper}</TableCell>
                        <TableCell>R${fixedIncome.initialValue}</TableCell>
                        <TableCell>{fixedIncome.initialDate}</TableCell>
                        <TableCell>{fixedIncome.finalDate}</TableCell>
                        <TableCell>{fixedIncome.yieldRate}%</TableCell>
                        <TableCell>
                          <IconButton
                            aria-label="delete"
                            onClick={() => handleDeletionFixedIncome(fixedIncome)}
                          >
                            <DeleteIcon />
                          </IconButton>
                        </TableCell>
                      </TableRow>
                    ))}
                  </TableBody>
                </>
              )}
              {type === 'CHECKING_ACCOUNT' && (
                <>
                  <TableHead>
                    <TableRow>
                      <TableCell>Título</TableCell>
                      <TableCell>Valor inicial</TableCell>
                      <TableCell>Data inicial</TableCell>
                      <TableCell>Valorização final</TableCell>
                      <TableCell>Ações</TableCell>
                    </TableRow>
                  </TableHead>
                  <TableBody>
                    {checkingAccounts.map((checkingAccount, index) => (
                      <TableRow key={index}>
                        <TableCell>{checkingAccount.title}</TableCell>
                        <TableCell>R${checkingAccount.initialValue}</TableCell>
                        <TableCell>{checkingAccount.initialDate}</TableCell>
                        <TableCell>{checkingAccount.yieldRate}%</TableCell>
                        <TableCell>
                          <IconButton
                            aria-label="Marcar como concluído"
                            onClick={() => handleConcludeCheckingAccount(checkingAccount)}
                          >
                            <CheckCircleOutline />
                          </IconButton>
                        </TableCell>
                      </TableRow>
                    ))}
                  </TableBody>
                </>
              )}
            </Table>
          </TableContainer>
        </AccordionDetails>
      </Accordion>
    );
  };

  return (
    <div>
      {Object.keys(groupedItems).map((type) => renderAccordion(type, groupedItems[type]))}
    </div>
  );
};

export default AccordionComponent;
