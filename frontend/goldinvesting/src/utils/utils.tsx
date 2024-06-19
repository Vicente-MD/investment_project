export const translateType = (type: string) => {
    switch (type) {
        case 'STOCK':
            return 'Ações';
        case 'FIXED_INCOME':
            return 'Renda Fixa';
        case 'CHECKING_ACCOUNT':
            return 'Conta Corrente';
        default:
            return type;
    }
}