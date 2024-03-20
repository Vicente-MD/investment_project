package br.com.goldinvesting.investment;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvestData implements Comparable<InvestData> {
    private int year;
    private int month;
    private BigDecimal dividend = BigDecimal.valueOf(0);
    private double price;

    public InvestData(int year, int month, double price) {
        this.year = year;
        this.month = month;
        this.price = price;
    }

    @Override
    public int compareTo(InvestData investData) {

        String ano1 = this.year + "";
        String mes1 = this.month + "";

        if (mes1.length() == 1) {
            mes1 = "0" + mes1;
        }

        String ano2 = investData.getYear() + "";
        String mes2 = investData.getMonth() + "";

        if (mes2.length() == 1) {
            mes2 = "0" + mes2;
        }

        String soma1 = ano1 + mes1;
        String soma2 = ano2 + mes2;

        int data1 = Integer.parseInt(soma1);
        int data2 = Integer.parseInt(soma2);

        if (data1 > data2) {
            return 1;
        }
        if (data1 < data2) {
            return -1;
        }
        return 0;
    }
}
