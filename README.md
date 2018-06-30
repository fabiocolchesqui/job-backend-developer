# Intelipost: Teste prático para Backend Developer

### Descrição do Desafio e resolução técnica

O projeto utiliza Java 8, Spring Boot, Spring MVC, H2 Database Engine, Hibernate.

A solução para realização de um sistema de login:

Foi utilizado Spring Security com autenticação por banco de dados (H2 em memória).
Para que fosse possível realizar testes foi descartado a utilização de criptografia na senha do usuário.

A solução, via aplicação, para melhorar o desempenho de conexão simultânea foi garantir que cada usuário tivesse apenas umas única sessão ativa, para isso, foi adicionado um SessionRegister que avalia se um usuário se encontra logado;
Também foi configurado no Spring Security as questões de gerenciamento de sessão de cada login efetuado e também o gerenciamento de cookies e JSESSIONID;

Foi adicionado ao container um timeout para inatividade;

Para o JPA foi utilizado Hibernate, porém, visando a melhoria da conexão seria necessário adicionar um pool de conexão e gerenciamento de cache. A sugestão seria utilizar HikariCP;

Também visando melhorias na performance da aplicação, seria necessário utilizar um load balance escalável após detectar overload. Cada ciclo de amostragem seria validado após alguns stress testes com JMeter para determinar gargalos e assim verificar a melhor abordagem. 

Outra sugestão seria também utilizar a aplicação container, Docker / Kubernetes.

### Banco de dados

Banco de dados H2 Database engine

Foi utilizado H2 em memória; Também foi adicionado ao projeto um script default que insere alguns usuários na tabela 'credential';
** Para realizar os testes, por favor, verifique os inserts disponíveis no arquivo data.sql na pasta Resources

### Frontend

Para apresentar uma solução de demostração de login/logout, foi criado um frontend com AngularJS que possibilita simular o login de um usuário. O login é feito por Spring Security com autenticação via banco de dados.
A classe Controller responsável por retornar o usuário da sessão também verifica se o usuário atual está logado. O sistema evita navegação de um UserAnonymous;

### Performance Frontend

Para apresentar melhoria nas requisições e callback no frontend, a aplicação utiliza 'compression / gzip';
Um sugestão seria uma abordagem loadOnDemand por requisição, e assim, evitar adições de scripts;
Outro ponto a considerar seria minificar os arquivos de frontview; 

### Execução

O projeto pode ser acessado em http://localhost:8080/ 
Para o H2, acessar: http://localhost:8080/h2-console

### Testes

Foi criado um teste que verifica o status de login do Spring Security;
Não foram realizandos testes de integração da Controller class;

### Resultado Final

O resultado final demostrou que a aplicação pode evoluir em questão de performance e segurança, adotando algumas novas medidas preventivas, estas seriam válidas para um resultado, porém, tratando-se de uma prova de conceito, o esperado foi validado.






