# [AlphaBank](https://github.com/VctorMoraes/AlphaBank)

## Projeto
A aplicação tem como objetivo simular uma solicitação de empréstimo.

Para solicitar um empréstimo é nessários o prenchimento de alguns campos, como: Nome, Idade, Renda mensal e cidade. A partir desses dados, uma requição é feita a API e retorna a informação de se o empréstimo foi aprovado ou não.

O resultado da solicitação de empréstimo é armazenado em um banco de dados local (Room) para que quando o usuário abra a aplicação novamente ele ja seja enviado para a tela de empréstimo aprovado ou recusado.

## How To
AlphaBank é um aplicativo Android escrito em Kotlin. O projeto se encontra no seguinte repositório: https://github.com/VctorMoraes/AlphaBank

Para executar o aplicativo basta instalar a APK que estou enviando em um dispositivo ou emulador Android, para que não seja necessário a instalação de nenhuma ferramenta adicional. Se preferível, também é possível clonar o repositório e executar a aplicação no AndroidStudio.

## Arquitetura
* Hilt para injeção de dependências.
* Clean Architecture com MVVM.
* Retrofit e OkHttp para o consumo de APIs.
* Room como banco de dados local.

## [API](https://github.com/VctorMoraes/AplhaBankApi)

As requisições são feitas para uma API própria: https://github.com/VctorMoraes/AplhaBankApi

A API funciona de forma local, então é possível acessar a API quando a aplicação é executada em um emulador que está na mesma rede local que a API.

Caso não seja possível acessar a API, a aplicação Android retornará a seguinte resposta mockada:
`{
"status": "DENIED"
}`

### API Doc:

| Method   | URL       | Descrição                           |
| :---------- | :--------- | :---------------------------------- |
| `POST` | `/` | Salva usuário no banco de dados e retorna o resultado da solicitação de empréstimo |

#### Example body

```
{
  "name": "Victor Moraes",
  "age": 22
  "month_income": 1000.0,
  "city": "Campinas"
}
```

#### Example return
```
{
  "status": "DECLINED",
}
```

OR
```
{
  "status": "APPROVED",
  "max_amount": 10000.0
}
```

## Testes
* Testes unitários com JUnit4
* Mockk para mocks
* OkHttp MockWebServer para testes de API
* Kover para cobertura de testes

## Test Coverage
Atualmente 	21,5%.

Para verificar a cobertura de teste:
```bash
  ./gradlew koverHtmlReportDebug
```