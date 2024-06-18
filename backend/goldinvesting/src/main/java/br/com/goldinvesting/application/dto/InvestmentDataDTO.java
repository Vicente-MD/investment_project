package br.com.goldinvesting.application.dto;

import br.com.goldinvesting.domain.model.InvestmentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvestmentDataDTO implements Comparable<InvestmentDataDTO> {
    private int year;
    private int month;
    private double price;
    private double dividend;
    private InvestmentType investmentType;
    private long id;
    // private StockDTO stock = null;
    // private FixedIncomeDTO fixedIncome = null;
    // private CheckingAccountDTO checkingAccountDTO = null;

    @Override
    public int compareTo(InvestmentDataDTO o) {
        return (this.year * 100 + this.month) - (o.year * 100 + o.month);
    }
}
