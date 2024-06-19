package br.com.goldinvesting.application.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.com.goldinvesting.application.dto.InvestmentDTO;
import br.com.goldinvesting.application.dto.InvestmentDataDTO;
import br.com.goldinvesting.application.ports.in.InvestmentUseCase;
import br.com.goldinvesting.application.ports.out.StockDataRepository;
import br.com.goldinvesting.application.ports.out.TransactionRepository;
import br.com.goldinvesting.domain.model.CheckingAccount;
import br.com.goldinvesting.domain.model.FixedIncome;
import br.com.goldinvesting.domain.model.InvestmentType;
import br.com.goldinvesting.domain.model.Status;
import br.com.goldinvesting.domain.model.Stock;
import br.com.goldinvesting.domain.model.StockData;
import br.com.goldinvesting.domain.model.Transaction;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InvestmentService implements InvestmentUseCase {

    private final TransactionRepository transactionRepository;
    private final StockDataRepository stockDataRepository;

    @Override
    public List<InvestmentDTO> getInvestments(Long userId) {
        List<Transaction> transactions = transactionRepository.findByUserId(userId);
        return transactions.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private InvestmentDTO toDTO(Transaction transaction) {
        InvestmentDTO dto = new InvestmentDTO();
        dto.setId(transaction.getId());
        dto.setInvestmentName(transaction.getInvestment().getInvestmentType().name());
        dto.setInvestmentType(transaction.getInvestment().getClass().getSimpleName());
        dto.setInvestmentId(transaction.getInvestment().getId());
        dto.setStatus(transaction.getStatus().name());
        dto.setInvestment(transaction.getInvestment());
        return dto;
    }

    @Override
    public List<InvestmentDataDTO> getStockHistory(Stock stock, List<InvestmentDataDTO> generalHistory,
            Transaction transaction) {
        List<StockData> stockHistory = stockDataRepository.findByStockSymbolId(stock.getStockSymbol().getId());

        List<InvestmentDataDTO> filteredStockHistory = stockHistory.stream()
                .filter(data -> !data.getDate().isBefore(stock.getPurchaseDate()))
                .map(data -> new InvestmentDataDTO(data.getDate().getYear(), data.getDate().getMonthValue(),
                        data.getValue() * stock.getQuantity(), 0, InvestmentType.STOCK, transaction.getId()))
                .collect(Collectors.toList());

        List<InvestmentDataDTO> histToReturn = new ArrayList<>(generalHistory);
        histToReturn.addAll(filteredStockHistory);
        histToReturn.sort(null);

        return organizeHist(histToReturn);
    }

    @Override
    public List<InvestmentDataDTO> getFixedIncomeHistory(FixedIncome fixedIncome,
            List<InvestmentDataDTO> generalHistory, Transaction transaction) {
        Calendar from = stringToCalendar(fixedIncome.getInitialDate().toString());
        Calendar to = stringToCalendar(fixedIncome.getFinalDate().toString());
        Calendar now = Calendar.getInstance();

        if (to.after(now)) {
            to = now;
        }

        int totalMonths = (to.get(Calendar.YEAR) * 12 + to.get(Calendar.MONTH))
                - (from.get(Calendar.YEAR) * 12 + from.get(Calendar.MONTH));

        double yieldRate = (fixedIncome.getYieldRate() / 100) + 1;

        double finalValue = fixedIncome.getInitialValue()
                * Math.pow(yieldRate, totalMonths / 12.0);
        double gainPerMonth = (finalValue - fixedIncome.getInitialValue()) / (totalMonths - 1);
        double value = fixedIncome.getInitialValue();

        List<InvestmentDataDTO> hist = new ArrayList<>();
        while (totalMonths > 0) {
            hist.add(new InvestmentDataDTO(from.get(Calendar.YEAR), from.get(Calendar.MONTH) + 1, value, 0,
                    InvestmentType.FIXED_INCOME, transaction.getId()));
            value += gainPerMonth;
            from.add(Calendar.MONTH, 1);
            totalMonths--;
        }

        List<InvestmentDataDTO> histToReturn = new ArrayList<>(generalHistory);
        histToReturn.addAll(hist);
        histToReturn.sort(null);
        return organizeHist(histToReturn);
    }

    @Override
    public List<InvestmentDataDTO> getCheckingAccountHistory(
            CheckingAccount checkingAccount,
            List<InvestmentDataDTO> generalHistory,
            Transaction transaction) {
        Calendar from = stringToCalendar(checkingAccount.getInitialDate().toString());
        Calendar now = Calendar.getInstance();

        int monthsForNow = (now.get(Calendar.YEAR) * 12 + now.get(Calendar.MONTH))
                - (from.get(Calendar.YEAR) * 12 + from.get(Calendar.MONTH));
        double value = checkingAccount.getInitialValue();

        List<InvestmentDataDTO> hist = new ArrayList<>();
        while (monthsForNow > 0) {
            hist.add(new InvestmentDataDTO(from.get(Calendar.YEAR), from.get(Calendar.MONTH) + 1, value, 0.0,
                    InvestmentType.CHECKING_ACCOUNT, transaction.getId()));
            value *= 1 + (checkingAccount.getYieldRate() / 100);
            from.add(Calendar.MONTH, 1);
            monthsForNow--;
        }

        List<InvestmentDataDTO> histToReturn = new ArrayList<>(generalHistory);
        histToReturn.addAll(hist);
        histToReturn.sort(null);
        return organizeHist(histToReturn);
    }

    @Override
    public List<InvestmentDataDTO> getAllInvestmentHistories(Long userId) {
        List<Transaction> transactions = transactionRepository.findByUserId(userId);
        List<InvestmentDataDTO> allHistories = new ArrayList<>();

        for (Transaction transaction : transactions) {
            if (!transaction.getStatus().name().equals(Status.ACTIVE.name())) {
                continue;
            } else if (transaction.getInvestment() instanceof Stock) {
                allHistories = getStockHistory((Stock) transaction.getInvestment(), allHistories, transaction);
            } else if (transaction.getInvestment() instanceof FixedIncome) {
                allHistories = getFixedIncomeHistory((FixedIncome) transaction.getInvestment(), allHistories,
                        transaction);
            } else if (transaction.getInvestment() instanceof CheckingAccount) {
                allHistories = getCheckingAccountHistory((CheckingAccount) transaction.getInvestment(), allHistories,
                        transaction);
            }
        }

        return allHistories;
    }

    private List<InvestmentDataDTO> organizeHist(List<InvestmentDataDTO> hist) {
        List<InvestmentDataDTO> result = new ArrayList<>();
        for (int i = 0; i < hist.size(); i++) {
            InvestmentDataDTO currentData = hist.get(i);
            result.add(currentData);
            for (int j = i + 1; j < hist.size(); j++) {
                InvestmentDataDTO nextData = hist.get(j);
                if (currentData.getYear() == nextData.getYear()
                        && currentData.getMonth() == nextData.getMonth()
                        && currentData.getInvestmentType().equals(nextData.getInvestmentType())) {
                    result.get(result.size() - 1)
                            .setPrice(result.get(result.size() - 1).getPrice() + nextData.getPrice());
                    hist.remove(j);
                    j--;
                }
            }
        }
        return result;
    }

    private Calendar stringToCalendar(String stringDate) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse(stringDate);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return calendar;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
