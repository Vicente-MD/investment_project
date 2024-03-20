package br.com.goldinvesting.investment;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.goldinvesting.checkingaccount.model.CheckingAccount;
import br.com.goldinvesting.checkingaccount.repository.CheckingAccountRepository;
import br.com.goldinvesting.fixedincome.model.FixedIncomeModel;
import br.com.goldinvesting.fixedincome.repository.FixedIncomeRepository;
import br.com.goldinvesting.stock.model.Stock;
import br.com.goldinvesting.stock.repository.StockRepository;
import br.com.goldinvesting.transaction.repository.TransactionRepository;
import br.com.goldinvesting.treasurydirect.repository.TreasuryDirectRepository;
import br.com.goldinvesting.wallet.model.Wallet;
import br.com.goldinvesting.wallet.repository.WalletRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/investment")
@RequiredArgsConstructor
public class InvestmentHistoricalDataController {
    private final TransactionRepository transactionRepository;
    private final DataTreatment dataTreatment;
    private final StockRepository stockRepository;
    private final FixedIncomeRepository fixedIncomeRepository;
    private final TreasuryDirectRepository treasuryDirectRepository;
    private final CheckingAccountRepository checkingAccountRepository;
    private final WalletRepository walletRepository;

    @GetMapping("/history")
    public ResponseEntity<?> getInvestmentHistory(@RequestBody Wallet wallet) {
        // pega as transações com esse id de carteira
        var transactions = transactionRepository.findByWallet(wallet);
        if (!transactions.isEmpty()) {
            // array que estará com o histórico de investimentos
            List<InvestData> history = new ArrayList<>();
            // para cada transação efetua um tipo de cálculo diferente, iterando no array
            // investData
            transactions.get().forEach(t -> {
                Long id = Long.parseLong(Long.toString(t.getId()).substring(1));
                if (t.getInvestmentType().getInvestmentType().equals("STOCK")) {
                    Stock stock = stockRepository.findById(id).get();
                    // entra no cálculo de ações, retornando um array disso
                    List<InvestData> temporary = new ArrayList<>();
                    temporary.addAll(history);
                    history.clear();
                    history.addAll(dataTreatment.stockHist(stock, temporary));
                } else if (t.getInvestmentType().getInvestmentType().equals("FIXED_INCOME")) {
                    FixedIncomeModel fixedIncome = fixedIncomeRepository.findById(id).get();
                    // entra no cálculo de renda fixa, retornando um array disso
                    List<InvestData> temporary = new ArrayList<>();
                    if (!history.isEmpty()) {
                        temporary.addAll(history);
                        history.clear();
                        history.addAll(dataTreatment.fixedIncomeHist(fixedIncome, temporary));
                    } else {
                        history.addAll(dataTreatment.fixedIncomeHist(fixedIncome, history));
                    }
                } else if (t.getInvestmentType().getInvestmentType().equals("TREASURY_DIRECT")) {
                    var treasuryDirect = treasuryDirectRepository.findById(id).get();
                    // entra no cálculo de tesouro direto, retornando um array disso
                    List<InvestData> temporary = new ArrayList<>();
                    temporary.addAll(history);
                    history.clear();
                    history.addAll(dataTreatment.treasuryDirectHist(treasuryDirect, temporary));
                } else if (t.getInvestmentType().getInvestmentType().equals("CHECKING_ACCOUNT")) {
                    var checAccount = checkingAccountRepository.findById(id).get();
                    // entra no cálculo de conta corrente, retornando um array disso
                    List<InvestData> temporary = new ArrayList<>();
                    temporary.addAll(history);
                    history.clear();
                    history.addAll(dataTreatment.checkingAccountHist(checAccount, temporary));
                }
            });

            return ResponseEntity.ok(history);
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/history/{idWallet}")
    public ResponseEntity<?> getInvestmentHistoryParam(@PathVariable long idWallet) {
        // pega as transações com esse id de carteira
        var wallet = walletRepository.findById(idWallet).get();
        var transactions = transactionRepository.findByWallet(wallet);
        if (!transactions.isEmpty()) {
            // array que estará com o histórico de investimentos
            List<InvestData> history = new ArrayList<>();
            // para cada transação efetua um tipo de cálculo diferente, iterando no array
            // investData
            transactions.get().forEach(t -> {
                Long id = Long.parseLong(Long.toString(t.getId()).substring(1));
                if (t.getInvestmentType().getInvestmentType().equals("STOCK")) {
                    Stock stock = stockRepository.findById(id).get();
                    // entra no cálculo de ações, retornando um array disso
                    List<InvestData> temporary = new ArrayList<>();
                    temporary.addAll(history);
                    history.clear();
                    history.addAll(dataTreatment.stockHist(stock, temporary));
                } else if (t.getInvestmentType().getInvestmentType().equals("FIXED_INCOME")) {
                    FixedIncomeModel fixedIncome = fixedIncomeRepository.findById(id).get();
                    // entra no cálculo de renda fixa, retornando um array disso
                    List<InvestData> temporary = new ArrayList<>();
                    if (!history.isEmpty()) {
                        temporary.addAll(history);
                        history.clear();
                        history.addAll(dataTreatment.fixedIncomeHist(fixedIncome, temporary));
                    } else {
                        history.addAll(dataTreatment.fixedIncomeHist(fixedIncome, history));
                    }
                } else if (t.getInvestmentType().getInvestmentType().equals("TREASURY_DIRECT")) {
                    var treasuryDirect = treasuryDirectRepository.findById(id).get();
                    // entra no cálculo de tesouro direto, retornando um array disso
                    List<InvestData> temporary = new ArrayList<>();
                    temporary.addAll(history);
                    history.clear();
                    history.addAll(dataTreatment.treasuryDirectHist(treasuryDirect, temporary));
                } else if (t.getInvestmentType().getInvestmentType().equals("CHECKING_ACCOUNT")) {
                    var checAccount = checkingAccountRepository.findById(id).get();
                    // entra no cálculo de conta corrente, retornando um array disso
                    List<InvestData> temporary = new ArrayList<>();
                    temporary.addAll(history);
                    history.clear();
                    history.addAll(dataTreatment.checkingAccountHist(checAccount, temporary));
                }
            });

            return ResponseEntity.ok(history);
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{idWallet}")
    public ResponseEntity<?> getAllInvestment(@PathVariable long idWallet) {
        // pega as transações com esse id de carteira
        var wallet = walletRepository.findById(idWallet).get();
        var transactions = transactionRepository.findByWallet(wallet);

        if (!transactions.isEmpty()) {
            List<Stock> stocks = new ArrayList<>();
            List<FixedIncomeModel> fixedIncomes = new ArrayList<>();
            List<CheckingAccount> checkingAccounts = new ArrayList<>();
            AllInvestmentData allInvestments = new AllInvestmentData(0, 0, 0);
            transactions.get().forEach(t -> {
                Long id = Long.parseLong(Long.toString(t.getId()).substring(1));
                double stockValue = 0.0;
                double fixedIncomeValue = 0.0;
                double checkingAccountValue = 0.0;
                if (t.getInvestmentType().getInvestmentType().equals("STOCK") && t.getStatus().getStatus().equals("ACTIVE")) {
                    Stock stock = stockRepository.findById(id).get();
                    var currentPrice = dataTreatment.currentStockValue(stock) * stock.getQuantity();
                    stockValue = currentPrice + allInvestments.getTotalStock();
                    allInvestments.setTotalStock(stockValue);
                    stock.setPrice(currentPrice);
                    stocks.add(stock);
                } else if (t.getInvestmentType().getInvestmentType().equals("FIXED_INCOME") && t.getStatus().getStatus().equals("ACTIVE")) {
                    FixedIncomeModel fixedIncome = fixedIncomeRepository.findById(id).get();
                    var currentPrice = dataTreatment.currentFixedIncomeValue(fixedIncome);
                    fixedIncomeValue = currentPrice
                            + allInvestments.getTotalFixedIncome();
                    fixedIncome.setInitialValue(currentPrice);
                    allInvestments.setTotalFixedIncome(fixedIncomeValue);
                    fixedIncomes.add(fixedIncome);
                } else if (t.getInvestmentType().getInvestmentType().equals("CHECKING_ACCOUNT") && t.getStatus().getStatus().equals("ACTIVE")) {
                    CheckingAccount checkingAccount = checkingAccountRepository.findById(id).get();
                    var currentPrice = dataTreatment.currentCheckingAccountValue(checkingAccount);
                    checkingAccountValue = currentPrice
                            + allInvestments.getTotalCheckingAccount();
                    allInvestments.setTotalCheckingAccount(checkingAccountValue);
                    checkingAccount.setInitialValue(currentPrice);
                    checkingAccounts.add(checkingAccount);
                }
            });

            allInvestments.setStock(stocks);
            allInvestments.setFixedIncome(fixedIncomes);
            allInvestments.setCheckingAccount(checkingAccounts);

            return ResponseEntity.ok(allInvestments);
        }
        return ResponseEntity.ok().build();
    }
}
