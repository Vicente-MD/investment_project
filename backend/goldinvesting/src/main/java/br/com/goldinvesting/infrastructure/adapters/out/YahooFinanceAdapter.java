package br.com.goldinvesting.infrastructure.adapters.out;

import org.springframework.stereotype.Component;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Component
public class YahooFinanceAdapter {

    public Stock getStock(String symbol) throws IOException {
        return YahooFinance.get(symbol);
    }

    public Calendar stringToCalendar(String stringDate) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
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
