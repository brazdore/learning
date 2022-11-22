
# tema05--Python

## Requisitos

- Python 3 
- Conta no Slack
- Clonar este repositório

## Preparação - Instalação

-   Instalar e configurar o  [Python](https://www.python.org/downloads/)
-   Clone este repositório remoto

## Preparação - Configuração

> API Slack

-  Acesse https://api.slack.com/ e logue em uma conta no Slack ou crie uma nova
- Clique em `Create an app` > `From scratch`
- Dê um nome para o aplicativo e selecione o `workspace` onde será inserido
- No lado esquerdo da tela, clique em `OAuth & Permissions` em `Features`
- Procure a seção de `Scopes` > `Bot Token Scopes`
- Clique em `Add an OAuth Scope` e selecione a opção `chat:write`
- No topo da página em `OAuth Tokens for Your Workspace` clique em `Install to Workspace` e `Permitir`
-  Copie o código gerado em `OAuth Tokens for Your Workspace` > `Bot User OAuth Token`

> ID do Canal
- Precisamos também do ID do canal onde o aplicativo mandará as mensagens
- O ID pode ser encontrado clicando com o botão direito e selecionando `Open channel details`
- Copie o ID do canal

> service.py

- É necessário alterar duas variáveis em `service.py`
	- `BOT_USER_OAUTH_TOKEN` deve ser substituído pelo código gerado na API do Slack
	- `CHANNEL_ID` deve ser substituído pelo ID do canal

> Módulos Python
- É necessário instalar os módulos para rodar o aplicativo. Execute os comandos:
  - `pip install flask`
  - `pip install slack_sdk`

## Executando a aplicação:

-   Na pasta raíz do projeto, execute o comando `python app.py`

## Endpoints

> **GET**

-   `http://localhost:8080/conf/env`
	  - Retorna todas as variáveis de ambiente da máquina local
	  
-   `http://localhost:8080/conf/software`
	- Envia ao canal do Slack todos os processos em execução
	
-   `http://localhost:8080/conf/env/{key}/{value}`
	- Escreve no arquivo `bashrc` um novo `export`, que cria uma nova variável de ambiente com a chave e o valor passados no endpoint

> **Exemplo de nova variável de ambiente:**
-   `http://localhost:8080/conf/env/squirrel/AYUNDA_RISU`
	- Cria uma variável de ambiente `SQUIRREL` com o valor `AYUNDA_RISU`
	- Equivalente a export `SQUIRREL=AYUNDA_RISU`

## Notas

- A função `create_new_env_var` em `service.py` dá um append no arquivo `bashrc`; o conteúdo do append é um `export {KEY}={VALUE}`. Aparentemente um simples `export` não funcionaria para criar uma nova variável local porque: [Link do Stack Overflow](https://stackoverflow.com/questions/1506010/how-to-use-export-with-python-on-linux)

> You can't change your shell's environment from a child process (such
> as Python), it's just not possible.

- A solução seria escrever diretamente no `bashrc`, como explicado pelo *rantanplan* no [Stack Overflow](https://stackoverflow.com/questions/17657686/is-it-possible-to-set-an-environment-variable-from-python-permanently).

- O problema é que uma variável escrita desta forma não é removida com `unset`, apenas editando o arquivo `bashrc` e removendo os comandos de variáveis indesejadas.

- Isto pode ser feito executando o comando `nano ~/.bashrc` e apagando as entradas indesejadas no final do arquivo. Após, basta salvar com `CTRL+O` e sair com `CTRL+X`.
	- Não é necessário usar o nano; é possível utilizar outros editores.