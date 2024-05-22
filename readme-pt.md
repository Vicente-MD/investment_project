---

# Implementação do GoldInvesting

Bem-vindo ao guia da aplicação GoldInvesting. Esta aplicação é dividida em duas partes principais: um backend em Java Spring Boot e um frontend em React.js. Utilizamos Docker e Docker Compose para containerizar a aplicação, simplificando seu desenvolvimento e implantação.

## Estrutura do Projeto

A aplicação está organizada em dois diretórios principais:

- **Backend:** Localizado em `backend/goldinvesting`, é uma aplicação Spring Boot executando em Java 21.
- **Frontend:** Localizado em `frontend/goldinvesting`, é uma aplicação React.js.

## Funcionalidades

A aplicação GoldInvesting oferece as seguintes funcionalidades principais:

1. **Cadastro e Login de Usuários**:
   - Rota de Cadastro: `POST /users`
   - Rota de Login: `POST /users/authenticate`
   - Rota de Obter Usuário por ID: `GET /users/{id}`
   - Rota de Listar Usuários: `GET /users`
   - Rota de Atualizar Usuário: `PUT /users`
   - Rota de Deletar Usuário: `DELETE /users/{id}`

2. **Criação de Corretoras**:
   - Rota de Criação de uma nova corretora: `POST /brokers`
   - Rota de Obter Corretoras por ID: `GET /brokers/{id}`
   - Rota de Listar Corretoras: `GET /brokers`
   - Rota de Deletar Corretoras: `DELETE /brokers/{id}`

3. **Adicionar Investimentos em Conta Corrente**:
   - Rota de Criação de Conta Corrente: `POST /checking-accounts`
   - Rota de Obter Conta Corrente por ID: `GET /checking-accounts/{id}`
   - Rota de Listar Contas Correntes: `GET /checking-accounts`
   - Rota de Deletar Conta Corrente: `DELETE /checking-accounts/{id}`
   - Rota de Concluir Conta Corrente: `PUT /checking-accounts/conclude/{id}`

## Arquitetura Hexagonal

O backend do projeto GoldInvesting segue a arquitetura hexagonal (Ports and Adapters), que facilita a separação de responsabilidades e a testabilidade do código. Essa abordagem permite que a lógica de negócios seja independente dos detalhes de implementação, como frameworks e bibliotecas externos. As portas (ports) definem interfaces que a lógica de negócios expõe, enquanto os adaptadores (adapters) implementam essas interfaces para interagir com o mundo exterior (ex.: APIs, banco de dados).

## Diagrama de Classes

A seguir está o diagrama de classes da aplicação GoldInvesting:

![Diagrama de Classes](file:///mnt/data/image.png)

Este diagrama ilustra a estrutura do sistema, incluindo as principais entidades, seus atributos e métodos, bem como as relações entre elas.

## Banco de Dados SQL

O banco de dados utilizado no projeto é o MySQL. Abaixo estão algumas das principais tabelas e seus relacionamentos:

- **Usuário:** Contém informações dos usuários, como nome, email e senha.
- **Carteira:** Armazena as carteiras dos usuários, que podem conter diversas transações e investimentos.
- **Transação:** Representa uma transação financeira, vinculada a uma carteira específica.
- **Conta Corrente:** Representa uma conta corrente onde os investimentos são adicionados.
- **Renda Fixa:** Armazena informações sobre investimentos de renda fixa.
- **Ativo:** Contém dados sobre os ativos, como ações e suas cotações.
Aqui está a versão atualizada do README em português, incluindo uma seção sobre os testes unitários realizados no backend:

## Banco de Dados SQL

O banco de dados utilizado no projeto é o MySQL. Abaixo estão algumas das principais tabelas e seus relacionamentos:

- **Usuário:** Contém informações dos usuários, como nome, email e senha.
- **Carteira:** Armazena as carteiras dos usuários, que podem conter diversas transações e investimentos.
- **Transação:** Representa uma transação financeira, vinculada a uma carteira específica.
- **Conta Corrente:** Representa uma conta corrente onde os investimentos são adicionados.
- **Renda Fixa:** Armazena informações sobre investimentos de renda fixa.
- **Ativo:** Contém dados sobre os ativos, como ações e suas cotações.

As operações no banco de dados são realizadas através de consultas SQL, utilizando o ORM do Spring Boot para facilitar a interação com o MySQL.

## Testes Unitários

Os testes unitários no backend do projeto GoldInvesting são realizados utilizando JUnit, um framework popular para testes em Java. Esses testes garantem que as diferentes partes do código funcionem conforme esperado, ajudando a identificar e corrigir erros durante o desenvolvimento. 

Os testes cobrem as seguintes áreas:

- **Serviços:** Testes das classes de serviço para verificar a lógica de negócios.
- **Controladores:** Testes dos endpoints da API para assegurar que as requisições e respostas estejam corretas.
- **Repositórios:** Testes das interações com o banco de dados, garantindo que as operações de CRUD funcionem corretamente.

Exemplo de teste unitário para o serviço de usuários:

```java
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import br.com.goldinvesting.application.dto.UserDTO;
import br.com.goldinvesting.application.ports.in.UserUseCase;
import br.com.goldinvesting.domain.model.User;
import br.com.goldinvesting.infrastructure.adapters.in.api.UserController;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

public class UserControllerTest {

    @Mock
    private UserUseCase userUseCase;

    @InjectMocks
    private UserController userController;

    @Test
    void testCreateUser() {
        MockitoAnnotations.openMocks(this);
        
        UserDTO userDTO = new UserDTO();
        userDTO.setName("John Doe");
        
        when(userUseCase.createUser(userDTO)).thenReturn(userDTO);
        
        ResponseEntity<UserDTO> response = userController.createUser(userDTO);
        
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("John Doe", response.getBody().getName());
    }
}
```

Estes testes são executados durante o ciclo de desenvolvimento para garantir que o código continue funcionando conforme novas funcionalidades são adicionadas ou alterações são feitas.

---
