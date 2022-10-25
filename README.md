# bill-survey-api
Uma API para consulta de valores de juros e preço final de boletos vencidos. Desenvolvido utilizando Java com SpringBoot


## Sobre a API

Uma API que tem como objetivo retornar dados de um boleto de pagamento válido contendo: Sua Data de Pagamento, Data de Vencimento, Valor original da Multa, Valor com a Multa e seus respectivos juros.  O URL principal da API `/bill-survey-api/v1`.

## Features

Esta API fornece endpoints e ferramentas HTTP para:

* Registrar Pagamento: `POST//bill-survey-api/v1`

### Detalhes

`POST//bill-survey-api/v1`

Este endpoint é chamado para retornar informações sobre boletos somente do tipo NPC registrados na base de dados da Builders. Caso seja identificado um tipo diferente do esperado a API retornará o erro: 422. Para utilizar a api basta informar um código de barras válido e uma data de pagamento válida no padrão : YYYY-MM-DD

**Body:**

```json
{
  "bar_code": "34199800020104352008771020110004191070010000",
  "payment_date": "2022-10-30"
}
```

**Onde:**

`bar_code` - código de barras válido que possui registro na base de dados da Builders.

`payment_date` - data do pagamento do referido boleto.

Retornos possíveis:

* 201 - Created: Tudo ocorreu como esperado.
* 400 - Bad Request: A requisição não foi aceita, geralmente devido à falta de um parâmetro obrigatório ou JSON inválido.
* 404 - Not Found: Boleto não foi encontrado na Base.
* 422 – Unprocessable Entity: Se algum dos campos não for válido ou a data de pagamento for maior que a data atual.
* 500 - Server Errors: Erro interno.


**Retorno:**

```json
{
    "id": 1,
    "original_amount": 100.0,
    "amount": 102.1320,
    "due_date": "2022-10-26",
    "payment_date": "2022-10-30",
    "interest_amount_calculated": 0.1320,
    "fine_amount_calculated": 2.0000,
    "type": "NPC",
    "bar_code": "34199800020104352008771020110004191070010000"
}
```
 
**Onde:**

`original_amount` – O valor original do boleto sem alterações.

`amount` – O valor final do boleto com a aplicação de Juros e Multas.

`due_date` – A data de vencimento do Boleto.

`payment_date` – A data de pagamento do Boleto.

`interest_amount_calculated` – O valor do juros mensal.

`fine_amount_calculated` - O valor da multa.

`type` -Tipo do boleto.

`bar_code` -O código de barras.



### Tecnologias utilizadas

Este projeto foi desenvolvido utilizando:

* **Java 11 (Java Development Kit - JDK: 11.0.9)**
* **Spring Boot 2.7.4**
* **Maven**
* **JUnit 5**
* **Surfire**
* **Mysql 10.4.25-MariaDB**
* **Postman**
* **Heroku**


### Execução

Você precisa ter o **Mysql 10.4.25 ou superior** instalado em sua máquina para executar a API. Escolha o SGBD de sua escolha, crie uma conexão e rode o seguinte comando:

```sql
CREATE database bills_database;
```

Depois de criar o banco de dados da API, você precisa adicionar suas  **Credenciais de acesso ao banco**  `username` e `password` no arquivo `application.properties` em `src/main/resource`. As linhas que devem ser modificadas são as seguintes:

```properties
spring.datasource.username=
spring.datasource.password=
```


Quando a aplicação estiver rodando automaticamente ela irá criar as tabelas necessárias para utilização da API.

### Testes

* Para a fase de teste unitários, você pode executar:

```bash
mvn test
```

### Endereço Local

Por padrão, a API estará disponível em [http://localhost:8080/bill-survey-api/v1](http://localhost:8080//bill-survey-api/v1)

### Documentação

* Postman (development environment): [https://documenter.getpostman.com/view/21229006/2s84LPxXq7](https://documenter.getpostman.com/view/21229006/2s84LPxXq7)