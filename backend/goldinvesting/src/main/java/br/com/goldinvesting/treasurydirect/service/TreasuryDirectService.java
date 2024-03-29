package br.com.goldinvesting.treasurydirect.service;

import java.util.List;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import br.com.goldinvesting.investmenttype.repository.InvestmentTypeRepository;
import br.com.goldinvesting.status.repository.StatusRepository;
import br.com.goldinvesting.transaction.model.FeignInvestment;
import br.com.goldinvesting.transaction.model.Transaction;
import br.com.goldinvesting.transaction.repository.TransactionRepository;
import br.com.goldinvesting.treasurydirect.model.TreasuryDirect;
import br.com.goldinvesting.treasurydirect.model.TreasuryDirectDTO;
import br.com.goldinvesting.treasurydirect.repository.TreasuryDirectRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TreasuryDirectService {
    private final TreasuryDirectRepository treasuryDirectRepository;
    private final TransactionRepository transactionRepository;
    private final InvestmentTypeRepository investmentTypeRepository;
    private final StatusRepository statusRepository;

    @Transactional
    public TreasuryDirect createTreasuryDirect(TreasuryDirectDTO treasuryDirectDTO) {
        var treasuryDirect = treasuryDirectRepository.save(treasuryDirectDTO.getTreasuryDirect());
        var investment = FeignInvestment.builder()
                .id(treasuryDirect.getId())
                .broker(treasuryDirect.getBroker())
                .issuer(treasuryDirect.getIssuer())
                .initialValue(treasuryDirect.getInitialValue())
                .initialDate(treasuryDirect.getInitialDate())
                .finalDate(treasuryDirect.getFinalDate())
                .title(treasuryDirect.getTitle())
                .yieldRate(treasuryDirect.getYieldRate()).build();
        var investmentType = investmentTypeRepository.findByInvestmentType("TREASURY_DIRECT").get();
        var status = statusRepository.findByStatus("ACTIVE").get();
        var wallet = treasuryDirectDTO.getUser().getWallet();
        var id = "" + investmentType.getId() + investment.getId();
        var transaction = new Transaction(Long.parseLong(id), investment, investmentType, wallet, status);
        transactionRepository.save(transaction);

        return treasuryDirect;
    }

    @Transactional
    public TreasuryDirect getTreasuryDirectById(long id) {
        var treasuryDirect = treasuryDirectRepository.findById(id);
        if (treasuryDirect.isPresent())
            return treasuryDirect.get();
        return null;
    }

    @Transactional
    public void deleteTreasuryDirect(long id) {
        treasuryDirectRepository.deleteById(id);
    }

    @Transactional
    public List<TreasuryDirect> getTreasuryDirects() {
        return treasuryDirectRepository.findAll();
    }
}
