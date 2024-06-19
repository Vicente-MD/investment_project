class ErrosValidacaoException extends Error {
  errors: string[];

  constructor(errors: string[]) {
    super("Validation error");
    this.errors = errors;
  }
}

export default ErrosValidacaoException;
