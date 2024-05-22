import React from "react";
import { Route, Switch, HashRouter, Redirect } from "react-router-dom";
import { AuthConsumer } from "../main/ProvedorDeAutentificacao";
import login from "../Pages/login/Login";
import Home from "../Pages/home/Home";
import Account from "../Pages/account/account";
import News from "../Pages/news/News";
import AddInvestment from "../Pages/add-investment/AddInvestment";
import simulateInvestment from "../Pages/simulate_investment/SimulateInvestment";
import MyInvestments from "../Pages/investments/MyInvestments";
import Registration from "../Pages/registration/Registration";
import MainStocks from "../Pages/stocks/MainStocks";
import SI from "../Pages/simulate_investment/SimulateInvestment";

function RotaAutenticada({ component: Component, isAutenticado, ...props }) {
  return (
    <Route
      {...props}
      render={(componentProps) => {
        if (isAutenticado) return <Component {...componentProps} />;
        else return <Redirect to={{ pathname: "/login", state: { from: componentProps.location } }} />;
      }}
    />
  );
}

function Rotas(props) {
  return (
    <HashRouter>
      <Switch>
        <Route path="/login" component={login} />
        <Route path="/cadastro-usuarios" component={Registration} />
        <RotaAutenticada isAutenticado={props.isUsuarioAutenticado} exact path="/" component={Home} />
        <RotaAutenticada isAutenticado={props.isUsuarioAutenticado} path="/home" component={Home} />
        <RotaAutenticada isAutenticado={props.isUsuarioAutenticado} path="/meus-dados" component={Account} />
        <RotaAutenticada isAutenticado={props.isUsuarioAutenticado} path="/noticias" component={News} />
        <RotaAutenticada
          isAutenticado={props.isUsuarioAutenticado}
          path="/adicionar-investimento"
          component={AddInvestment}
        />
        <RotaAutenticada isAutenticado={props.isUsuarioAutenticado} path="/simular-investimento" component={SI} />
        <RotaAutenticada isAutenticado={props.isUsuarioAutenticado} path="/principais-acoes" component={MainStocks} />
        <RotaAutenticada
          isAutenticado={props.isUsuarioAutenticado}
          path="/meus-investimentos"
          component={MyInvestments}
        />
      </Switch>
    </HashRouter>
  );
}

export default () => <AuthConsumer>{(context) => <Rotas isUsuarioAutenticado={context.isAutenticado} />}</AuthConsumer>;
