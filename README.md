# Desafio: Tech Challange
Trata-se de uma aplicação que controla um Assistente de atendimento integrado ao Telegram como um *chatbot*, a proposta é otimizar a comunicação entre clientes e grupo Carrefour e representa um *tech challange* proposto pela DIO (*Digital Inovation One*).

### Características

- Utiliza Java 11 com* Spring Boot, Spring JPA, Spring Web Tools * H2 como banco de dados. 
- Integrado ao [RabbitMQ](https://www.rabbitmq.com/) para o tratamento de mensagens assíncronas.
- Integrado a API do [Telegram](https://web.telegram.org/) para controle de um *chatbot*.
- Integrado a API do [DialogFlow](https://cloud.google.com/dialogflow?&utm_source=google&utm_medium=cpc&utm_campaign=latam-BR-all-pt-dr-skws-all-all-trial-b-dr-1009133-LUAC0008677&utm_content=text-ad-none-none-DEV_c-CRE_434158362648-ADGP_SKWS+%7C+Multi+~+Machine+Learning+%7C+Dialogflow-KWID_43700053588659798-kwd-473320475507-userloc_1001662&utm_term=KW_%2Bdialogflow-ST_%2BDialogflow&gclid=Cj0KCQjw6575BRCQARIsAMp-ksM79Di_NtwZ1bMA90p11ZEQ20EhskYz5zqOII1FX7gLj4k0ps8j6LgaAgpEEALw_wcB&gclsrc=aw.ds) para lidar com processamento de linguagem natural.
- Por ser uma API REST é  uma solução interoperável. 

# Credenciais de integração

Por estar integrado a serviços da Google e Telegram, é necessário gerar as credenciais e adicioná-las no arquivo de configuração *application.properties*.



    telegram.token= [SECRET]
    telegram.botName= [SECRET]

O token do Telegram é gerado pela própria aplicação do Telegram quando um novo bot é gerado (com o *Bot Father*), desta forma, o nome e token gerado devem ser adicionados nos parâmetros acima.



    dialogflow.projectId= [SECRET]
    dialogflow.languageCode= pt-BR
    dialogflow.settingsPath= [SECRET]

O DialogFlow precisa de credenciais próprias a serem geradas na plataforma da *Google Cloud*, como para qualquer outro serviço da Google. Lá você deve conseguir gerar as credenciais que no final resultará em um arquivo de extensão json que deve ser adicionado na pasta raiz do projeto, dentro desse mesmo arquivo você encontrará os valores para os parâmetros acima (também presentes no *application.properties*) que devem ser preenchidos de acordo com as credenciais geradas pela *Google Cloud Plataform*.

# Um aplicação REST
A solução desenvolvida não é apenas um orquestrador com o Telegram e Dialog Flow, ela registra os atendimentos e avaliações que podem ser acessadas e integradas a outros sistemas através de requisições HTTP (Arquitetura REST). As URLs disponibilizadas nesta versão são as seguintes:

- **/attendances/all : **Retorna todos os atendimentos registrados
- **/attendances/{ protocol }:** Retorna um atendimento registrado através de seu protocolo.
- **/evaluations**: Retorna todas as avaliações de atendimentos registradas
- **/evaluations/average:**Retorna a média aritmética de valor de satisfação registradas nas avaliações.

###AMQP 

Outras aplicações podem integrarem-se à nossa API tomando o controle do chatbot após a triagem inicial feita pela inteligência do DialogFlow. Essa comunicação só será possível através de AMQP, que é um protocolo para mensagens assíncronas. A ferramenta utilizada para suportar essa comunicação é o RabbitMQ. 

Duas filas devem ser utilizadas para a integração usando RabbitMQ e seus nomes são definidos no **application.properties**. O funionamento baseia-se em uma fila de mensagens para a API e uma fila de mensagens para o atendente (sistema de terceiro integrado à solução proposta), a implementação do sistema de ouvir a fila de mensagens para o atendente e escrever mensagens para a fila de mensagens para a API. A mensagem deve estar em um formato JSON como abaixo especificado.

```json
{
	"id": 3,
	"protocolAttendance": "202030425",
	"descriptionSituation": "Ocorreu um problema na minha compra",
	"chatIdTelegram": 1024861053,
	"datas": [
		{
		"dataType": "CPF",
		"value": "473874834"
		},
		{
		"dataType": "Nome Completo",
		"value": " DANNYLO JOHNATHAN"
		},
		{
		"dataType": "Número do pedido",
		"value": " 232112"
		}
	],
	"attendant": "FULANO",
	"messageDescription": "FINISHED"
}
```
A mensagem é construída pela solução quando a inteligência coleta os dados e a envia para a fila correspondente. o campo messageDescription é utilizado para controlar o bot, no entanto uma mensagem com o valor ATTACHED deve ser enviada antes para informar a API que o controle do bot está sob outra responsabilidade. Para finalizar a interação e fazer a solução chamar a avaliação, a mensagem enviada deve ser FINISHED. 