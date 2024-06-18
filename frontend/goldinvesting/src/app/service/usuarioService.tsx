import Api from "../api";
import ErrosValidacaoException from "../service/errosValidacao";

interface Credenciais {
  email: string;
  password: string;
}

interface Usuario {
  name: string;
  email: string;
  password: string;
}

class UsuarioService extends Api {
  constructor() {
    super("/api");
  }

  autenticar(credenciais: Credenciais): Promise<any> {
    return this.post("/users/authenticate", credenciais);
  }

  cadastrarUsuario(usuario: Usuario): Promise<any> {
    return this.post("/users", usuario);
  }

  historicoDaCarteira(idCarteira: string): Promise<any> {
    return this.get("/investment/history/" + idCarteira);
  }

  listarCarteira(idCarteira: string): Promise<any> {
    return this.get("/investment/" + idCarteira);
  }

  concluirInvestimentoAcao(idInvest: string): Promise<any> {
    return this.get("/stock/conclude/" + idInvest);
  }

  concluirInvestimentoContaCorrente(idInvest: string): Promise<any> {
    return this.get("/checkingAccount/conclude/" + idInvest);
  }

  atualizarUsuario(usuario: Usuario): Promise<any> {
    return this.put("/user/", usuario);
  }

  validar(usuario: Usuario): void {
    const erros: string[] = [];
    if (!usuario.name) erros.push("O campo Nome é obrigatório.");
    if (!usuario.email) erros.push("O Campo Email é obrigatório.");
    else if (!usuario.email.match(/^[a-z0-9.]+@[a-z0-9]+\.[a-z]/)) erros.push("Informe um Email válido.");
    if (!usuario.password) erros.push("Digite a Senha");
    if (erros.length > 0) throw new ErrosValidacaoException(erros);
  }
}

export default UsuarioService;
