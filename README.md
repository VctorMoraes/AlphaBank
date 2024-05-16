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

## [API](https://github.com/VctorMoraes/AlphaBankApi)

As requisições são feitas para uma API própria: https://github.com/VctorMoraes/AlphaBankApi

A API funciona de forma local, então é possível acessar a API quando a aplicação é executada em um emulador que está na mesma rede local que a API.

Caso não seja possível acessar a API, a aplicação Android receberá a seguinte resposta mockada:
```
{
  "status": "DENIED"
}
```

### API Doc:

| Method   | URL       | Descrição                           |
| :---------- | :--------- | :---------------------------------- |
| `POST` | `/` | **Solicitação de empréstimo:** Salva usuário no banco de dados e retorna, de acordo com o `month_income`, o resultado da solicitação de emoréstimo   |

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
* Testes unitários com JUnit4 para os principais componentes: `LoanRequestViewModel` e `LoanRequestRepository`
* Mockk para mocks
* OkHttp MockWebServer para testes de API
* Kover para cobertura de testes

## Test Coverage
Atualmente 	21,5%, sendo eles em sua totalidade nos componentes: `LoanRequestViewModel` e `LoanRequestRepository`, além disso, Kover infelizmente não oferece cobertura de testes instrumentados.

Para verificar a cobertura de teste:
```bash
  ./gradlew koverHtmlReportDebug
```

![image](https://github.com/VctorMoraes/AlphaBank/assets/37370587/915aef59-61fb-487d-880d-319a432ac494)

#### LoanRequestViewModel
![image](https://github.com/VctorMoraes/AlphaBank/assets/37370587/de7da4c5-910f-4fde-816f-94ed83aa7a3e)

#### LoanRequestRepository
![image](https://github.com/VctorMoraes/AlphaBank/assets/37370587/3598ab45-66c5-40b0-b879-7e764c1889f9)



## Next Steps
* Aumentar a cobertura de testes unitários
* Criar testes instrumentados para verificação da lógica de erros
* Animar ícones nas telas de LoanApproved e LoanDenied
* Fazer com que o Room seja o single source of truth para a aplicação
