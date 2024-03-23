# hackaton-software-architecture

[Pos_tech_-_Hackaton_SOAT.pdf](https://github.com/juliapcosta97/hackaton-software-architecture/files/14643674/Pos_tech_-_Hackaton_SOAT.pdf)

### MVP Ponto Eletrônico 

#### Requisitos funcionais MVP
1. Autenticação de Usuário: O sistema deve permitir que os usuários
se autentiquem usando um nome de usuário ou matrícula e senha.
2. Registro de Ponto: O sistema deve permitir que os usuários registrem o horário de entrada, intervalos e saída do trabalho. Isso deve incluir a data e a hora exatas do registro. O usuário apenas registra o
evento, e o sistema obtém o horário do momento do registro.
3. Visualização de Registros: O sistema deve permitir que os usuários
visualizem seus registros de ponto. Isso deve incluir todos os detalhes,
como data, hora de entrada, intervalos e saída, e total de horas trabalhadas no dia.
4. Relatórios: O sistema deve ser capaz de gerar o espelho de ponto
mensal com base nos registros de ponto do mês fechado (anterior) e 
enviar esse relatório por e-mail ao solicitante. (Listagem das datas, batimentos de ponto e total de horas trabalhadas)
5. Segurança: O sistema deve garantir que os dados dos usuários sejam armazenados de forma segura e que a privacidade seja mantida.
6. Disponibilidade: O sistema deve estar disponível 24/7 para permitir
que os usuários registrem seu ponto a qualquer momento, e o tempo
de resposta dos serviços de marcação de ponto deve ser de até 5 segundos.

### Exemplos:

#### - Salvar Registro de Ponto

curl --location 'http://localhost:8080/point/register' \
--header 'Content-Type: application/json' \
--header 'Cookie: JSESSIONID=C19FB1413873565215FFED730B66D3EE' \
--data-raw '{
    "email": "test@email.com",
    "comments": "horario de entrada"
}'

![Captura de Tela 2024-03-23 às 19 32 20](https://github.com/juliapcosta97/hackaton-software-architecture/assets/15149920/3b2a24b8-e840-4373-af46-2812824310c5)

#### - Visualizar Registros

curl --location --request GET 'https://yh5mvo2tnd.execute-api.us-east-1.amazonaws.com/tech-challenge/point/records?email=test%40email.com&date=2024-03-23' \
--header 'Content-Type: application/json' \
--data-raw '{
    "email": "test@email.com",
    "comments": "horario de saida"
}'

![Captura de Tela 2024-03-23 às 19 33 14](https://github.com/juliapcosta97/hackaton-software-architecture/assets/15149920/e9623f12-4bf3-4e1a-a06e-00b523c2c512)

#### - Relatório Diário do Ponto Eletrônico

curl --location --request GET 'https://yh5mvo2tnd.execute-api.us-east-1.amazonaws.com/tech-challenge/point/report/daily?email=test%40email.com&date=2024-03-23' \
--header 'Content-Type: application/json' \
--data-raw '{
    "email": "test@email.com",
    "comments": "horario de saida"
}'

![Captura de Tela 2024-03-23 às 19 34 08](https://github.com/juliapcosta97/hackaton-software-architecture/assets/15149920/c52339cb-37e4-4ea5-aeba-3ac0b7c33749)

#### - Relatório Mensal e Espelho de Ponto

curl --location --request GET 'https://yh5mvo2tnd.execute-api.us-east-1.amazonaws.com/tech-challenge/point/report/monthly?email=test%40email.com&date=2024-03-23' \
--header 'Content-Type: application/json' \
--data-raw '{
    "email": "test@email.com",
    "comments": "horario de saida"
}'

![Captura de Tela 2024-03-23 às 19 34 48](https://github.com/juliapcosta97/hackaton-software-architecture/assets/15149920/5c69cdcf-4d7c-41c7-a57c-17341c8a8851)




