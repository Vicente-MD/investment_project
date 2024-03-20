package br.com.goldinvesting.investment;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import br.com.goldinvesting.checkingaccount.model.CheckingAccount;
import br.com.goldinvesting.fixedincome.model.FixedIncomeModel;
import br.com.goldinvesting.treasurydirect.model.TreasuryDirect;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes2.HistoricalDividend;

@Component
public class DataTreatment {

    public Stock findStock(br.com.goldinvesting.stock.model.Stock stockGiven) {
        try {
            return YahooFinance.get(stockGiven.getStock().getId());
        } catch (IOException e) {
            return null;
        }
    }

    public List<InvestData> stockHist(br.com.goldinvesting.stock.model.Stock stockGiven,
                                      List<InvestData> generalHistory) {
        try {
            // pega o stock do yahoo
            var yahooStock = this.findStock(stockGiven);
            Calendar to = Calendar.getInstance();
            to.setTime(new Date());
            to.add(Calendar.MONTH, -1);
            // pega o histórico da ação
            List<HistoricalQuote> stockList = yahooStock.getHistory(this.stringToCalendar(stockGiven.getInitialDate()),
                    to);
            // pega o histórico dos dividendos da ação
            List<HistoricalDividend> dividendList = yahooStock
                    .getDividendHistory(this.stringToCalendar(stockGiven.getInitialDate()), to);

            List<InvestData> stocks = new ArrayList<InvestData>();

            for (HistoricalQuote stockHist : stockList) {
                InvestData stock = new InvestData();
                stock.setPrice(stockHist.getOpen().doubleValue() * stockGiven.getQuantity());
                stock.setMonth(stockHist.getDate().get(Calendar.MONTH) + 1);
                stock.setYear(stockHist.getDate().get(Calendar.YEAR));
                stocks.add(stock);
            }
            for (HistoricalDividend dividendHist : dividendList) {
                for (InvestData data : stocks) {
                    if (data.getMonth() == dividendHist.getDate().get(Calendar.MONTH)
                            && data.getYear() == dividendHist.getDate().get(Calendar.YEAR)) {
                        data.setDividend(dividendHist.getAdjDividend());
                    }
                }
            }

            stocks.get(0).setPrice(BigDecimal.valueOf(stockGiven.getPrice()).doubleValue() * stockGiven.getQuantity());

            List<InvestData> histToReturn = new ArrayList<>();
            histToReturn.addAll(generalHistory);
            histToReturn.addAll(stocks);
            Collections.sort(histToReturn);
            return this.organizeHist(histToReturn);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<InvestData> fixedIncomeHist(FixedIncomeModel fixedIncome, List<InvestData> generalHistory) {
        Calendar from = this.stringToCalendar(fixedIncome.getInitialDate());
        Calendar to = this.stringToCalendar(fixedIncome.getFinalDate());
        Calendar now = Calendar.getInstance();

        int totalMonths = (to.get(Calendar.YEAR) * 12 + to.get(Calendar.MONTH))
                - (from.get(Calendar.YEAR) * 12 + from.get(Calendar.MONTH));
        int monthsForNow = (now.get(Calendar.YEAR) * 12 + now.get(Calendar.MONTH))
                - (from.get(Calendar.YEAR) * 12 + from.get(Calendar.MONTH));

        var finalValue = fixedIncome.getInitialValue() * fixedIncome.getYieldRate();

        var gainPerMonth = (finalValue - fixedIncome.getInitialValue()) / (totalMonths - 1);

        var value = fixedIncome.getInitialValue();

        List<InvestData> hist = new ArrayList<>();

        List<InvestData> histToReturn = new ArrayList<>();

        if (totalMonths > monthsForNow) {
            while (monthsForNow > 0) {
                hist.add(new InvestData(from.get(Calendar.YEAR), from.get(Calendar.MONTH) + 1, value));
                value += gainPerMonth;
                from.add(Calendar.MONTH, 1);
                monthsForNow--;
            }
            histToReturn.addAll(hist);
            if (!generalHistory.isEmpty()) {
                histToReturn.addAll(generalHistory);
            }
            Collections.sort(histToReturn);
            return this.organizeHist(histToReturn);
        } else {
            while (totalMonths > 0) {
                hist.add(new InvestData(from.get(Calendar.YEAR), from.get(Calendar.MONTH) + 1, value));
                value += gainPerMonth;
                from.add(Calendar.MONTH, 1);
                totalMonths--;
            }
            histToReturn.addAll(hist);
            if (!generalHistory.isEmpty()) {
                histToReturn.addAll(generalHistory);
            }
            Collections.sort(histToReturn);
            return this.organizeHist(histToReturn);
        }
    }

    public List<InvestData> treasuryDirectHist(TreasuryDirect treasuryDirect, List<InvestData> generalHistory) {
        Calendar from = this.stringToCalendar(treasuryDirect.getInitialDate());
        Calendar to = this.stringToCalendar(treasuryDirect.getFinalDate());
        Calendar now = Calendar.getInstance();

        int totalMonths = (to.get(Calendar.YEAR) * 12 + to.get(Calendar.MONTH))
                - (from.get(Calendar.YEAR) * 12 + from.get(Calendar.MONTH));
        int monthsForNow = (now.get(Calendar.YEAR) * 12 + now.get(Calendar.MONTH))
                - (from.get(Calendar.YEAR) * 12 + from.get(Calendar.MONTH));

        var finalValue = treasuryDirect.getInitialValue() * treasuryDirect.getYieldRate();

        var gainPerMonth = (finalValue - treasuryDirect.getInitialValue()) / (totalMonths - 1);

        var value = treasuryDirect.getInitialValue();

        List<InvestData> hist = new ArrayList<>();

        List<InvestData> histToReturn = new ArrayList<>();

        if (totalMonths > monthsForNow) {
            while (monthsForNow > 0) {
                hist.add(new InvestData(from.get(Calendar.YEAR), from.get(Calendar.MONTH) + 1, value));
                value += gainPerMonth;
                from.add(Calendar.MONTH, 1);
                monthsForNow--;
            }
            histToReturn.addAll(generalHistory);
            histToReturn.addAll(hist);
            Collections.sort(histToReturn);
            return this.organizeHist(histToReturn);
        } else {
            while (totalMonths > 0) {
                hist.add(new InvestData(from.get(Calendar.YEAR), from.get(Calendar.MONTH) + 1, value));
                value += gainPerMonth;
                from.add(Calendar.MONTH, 1);
                totalMonths--;
            }
            histToReturn.addAll(generalHistory);
            histToReturn.addAll(hist);
            Collections.sort(histToReturn);
            return this.organizeHist(histToReturn);
        }
    }

    public List<InvestData> checkingAccountHist(CheckingAccount checkingAccount, List<InvestData> generalHistory) {
        Calendar from = this.stringToCalendar(checkingAccount.getInitialDate());
        Calendar now = Calendar.getInstance();

        int monthsForNow = (now.get(Calendar.YEAR) * 12 + now.get(Calendar.MONTH))
                - (from.get(Calendar.YEAR) * 12 + from.get(Calendar.MONTH));

        var value = checkingAccount.getInitialValue();

        List<InvestData> hist = new ArrayList<>();

        while (monthsForNow > 0) {
            hist.add(new InvestData(from.get(Calendar.YEAR), from.get(Calendar.MONTH) + 1, value));
            value *= checkingAccount.getYieldRate();
            from.add(Calendar.MONTH, 1);
            monthsForNow--;
        }

        List<InvestData> histToReturn = new ArrayList<>();
        histToReturn.addAll(generalHistory);
        histToReturn.addAll(hist);
        Collections.sort(histToReturn);

        this.organizeHist(histToReturn);

        return histToReturn;
    }

    public double currentStockValue(br.com.goldinvesting.stock.model.Stock stockGiven) {
        try {
            var yahooStock = this.findStock(stockGiven);
            var currentValue = yahooStock.getQuote().getPrice();
            return currentValue.doubleValue();
        } catch (Exception e) {
            e.printStackTrace();
            return 0.0;
        }
    }

    public double currentFixedIncomeValue(FixedIncomeModel fixedIncome) {
        Calendar from = this.stringToCalendar(fixedIncome.getInitialDate());
        Calendar to = this.stringToCalendar(fixedIncome.getFinalDate());
        Calendar now = Calendar.getInstance();

        int totalMonths = (to.get(Calendar.YEAR) * 12 + to.get(Calendar.MONTH))
                - (from.get(Calendar.YEAR) * 12 + from.get(Calendar.MONTH));
        int monthsForNow = (now.get(Calendar.YEAR) * 12 + now.get(Calendar.MONTH))
                - (from.get(Calendar.YEAR) * 12 + from.get(Calendar.MONTH));

        var finalValue = fixedIncome.getInitialValue() * fixedIncome.getYieldRate();
        var gainPerMonth = (finalValue - fixedIncome.getInitialValue()) / (totalMonths - 1);
        var value = fixedIncome.getInitialValue();

        if (totalMonths > monthsForNow) {
            return monthsForNow * gainPerMonth * value;
        } else {
            return finalValue;
        }
    }

    public double currentCheckingAccountValue(CheckingAccount checkingAccount) {
        Calendar from = this.stringToCalendar(checkingAccount.getInitialDate());
        Calendar now = Calendar.getInstance();

        int monthsForNow = (now.get(Calendar.YEAR) * 12 + now.get(Calendar.MONTH))
                - (from.get(Calendar.YEAR) * 12 + from.get(Calendar.MONTH));

        var value = checkingAccount.getInitialValue();

        System.out.println("\n\n\t" + checkingAccount.getYieldRate() + "\n\n");

        int i = 0;

        while (monthsForNow > i) {
            checkingAccount.setInitialValue(checkingAccount.getYieldRate() * checkingAccount.getInitialValue());
            i += 1;
        }

        return checkingAccount.getInitialValue();
    }

    public Calendar stringToCalendar(String stringDate) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String dateInString = stringDate;
            Date date;
            date = sdf.parse(dateInString);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return calendar;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<InvestData> organizeHist(List<InvestData> hist) {

        List<InvestData> result = new ArrayList<>();

        for (int i = 0; i < hist.size(); i++) {
            result.add(hist.get(i));
            for (int j = i + 1; j < hist.size(); j++) {
                if (Integer.parseInt(hist.get(i).getYear() + "" + hist.get(i).getMonth()) == Integer
                        .parseInt(hist.get(j).getYear()
                                + "" + hist.get(j).getMonth())) {
                    result.get(i).setPrice(hist.get(i).getPrice() + hist.get(j).getPrice());
                    hist.remove(hist.get(j));
                }
            }
        }

        return hist;
    }
}
