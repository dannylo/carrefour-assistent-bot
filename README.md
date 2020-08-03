# Desafio: Tech Challange
Trata-se de uma aplica��o que controla um Assistente de atendimento integrado ao Telegram como um *chatbot*, a proposta � otimizar a comunica��o entre clientes e grupo Carrefour e representa um *tech challange* proposto pela DIO (*Digital Inovation One*).

### Caracter�sticas

- Utiliza Java 11 com* Spring Boot, Spring JPA, Spring Web Tools * H2 como banco de dados. 
- Integrado ao [RabbitMQ](https://www.rabbitmq.com/) para o tratamento de mensagens ass�ncronas.
- Integrado a API do [Telegram](https://web.telegram.org/) para controle de um *chatbot*.
- Integrado a API do [DialogFlow](https://cloud.google.com/dialogflow?&utm_source=google&utm_medium=cpc&utm_campaign=latam-BR-all-pt-dr-skws-all-all-trial-b-dr-1009133-LUAC0008677&utm_content=text-ad-none-none-DEV_c-CRE_434158362648-ADGP_SKWS+%7C+Multi+~+Machine+Learning+%7C+Dialogflow-KWID_43700053588659798-kwd-473320475507-userloc_1001662&utm_term=KW_%2Bdialogflow-ST_%2BDialogflow&gclid=Cj0KCQjw6575BRCQARIsAMp-ksM79Di_NtwZ1bMA90p11ZEQ20EhskYz5zqOII1FX7gLj4k0ps8j6LgaAgpEEALw_wcB&gclsrc=aw.ds) para lidar com processamento de linguagem natural.
- Por ser uma API REST �  uma solu��o interoper�vel. 

# Credenciais de integra��o

Por estar integrado a servi�os da Google e Telegram, � necess�rio gerar as credenciais e adicion�-las no arquivo de configura��o *application.properties*.



    telegram.token= [SECRET]
    telegram.botName= [SECRET]

O token do Telegram � gerado pela pr�pria aplica��o do Telegram quando um novo bot � gerado (com o *Bot Father*), desta forma, o nome e token gerado devem ser adicionados nos par�metros acima.



    dialogflow.projectId= [SECRET]
    dialogflow.languageCode= pt-BR
    dialogflow.settingsPath= [SECRET]

O DialogFlow precisa de credenciais pr�prias a serem geradas na plataforma da *Google Cloud*, como para qualquer outro servi�o da Google. L� voc� deve conseguir gerar as credenciais que no final resultar� em um arquivo de extens�o json que deve ser adicionado na pasta raiz do projeto, dentro desse mesmo arquivo voc� encontrar� os valores para os par�metros acima (tamb�m presentes no *application.properties*) que devem ser preenchidos de acordo com as credenciais geradas pela *Google Cloud Plataform*.

# Um aplica��o REST
A solu��o desenvolvida n�o � apenas um orquestrador com o Telegram e Dialog Flow, ela registra os atendimentos e avalia��es que podem ser acessadas e integradas a outros sistemas atrav�s de requisi��es HTTP (Arquitetura REST). As URLs disponibilizadas nesta vers�o s�o as seguintes:

- **/attendances/all : **Retorna todos os atendimentos registrados
- **/attendances/{ protocol }:** Retorna um atendimento registrado atrav�s de seu protocolo.
- **/evaluations**: Retorna todas as avalia��es de atendimentos registradas
- **/evaluations/average:**Retorna a m�dia aritm�tica de valor de satisfa��o registradas nas avalia��es.

###AMQP 

Outras aplica��es podem integrarem-se � nossa API tomando o controle do chatbot ap�s a triagem inicial feita pela intelig�ncia do DialogFlow. Essa comunica��o s� ser� poss�vel atrav�s de AMQP, que � um protocolo para mensagens ass�ncronas. A ferramenta utilizada para suportar essa comunica��o � o RabbitMQ. 

Duas filas devem ser utilizadas para a integra��o usando RabbitMQ e seus nomes s�o definidos no **application.properties**. O funionamento baseia-se em uma fila de mensagens para a API e uma fila de mensagens para o atendente (sistema de terceiro integrado � solu��o proposta), a implementa��o do sistema de ouvir a fila de mensagens para o atendente e escrever mensagens para a fila de mensagens para a API. A mensagem deve estar em um formato JSON como abaixo especificado.

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
		"dataType": "N�mero do pedido",
		"value": " 232112"
		}
	],
	"attendant": "FULANO",
	"messageDescription": "FINISHED"
}
```
A mensagem � constru�da pela solu��o quando a intelig�ncia coleta os dados e a envia para a fila correspondente. o campo messageDescription � utilizado para controlar o bot, no entanto uma mensagem com o valor ATTACHED deve ser enviada antes para informar a API que o controle do bot est� sob outra responsabilidade. Para finalizar a intera��o e fazer a solu��o chamar a avalia��o, a mensagem enviada deve ser FINISHED. 