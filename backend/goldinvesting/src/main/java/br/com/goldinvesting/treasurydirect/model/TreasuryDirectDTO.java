package br.com.goldinvesting.treasurydirect.model;

import br.com.goldinvesting.user.model.User;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TreasuryDirectDTO {
    private TreasuryDirect treasuryDirect;
    private User user;
}
