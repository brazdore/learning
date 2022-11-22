
# tema08 -- RxNetty

## Preparação

> Repositório
- Clonar este repositório.

> Docker
- Instalar e configurar o [Docker](https://docs.docker.com/engine/install/).

## Gerando a Imagem & Executando o Container

- A partir da pasta raíz do projeto, execute o comando `bash run.sh start`
- Sucesso: o comando `sudo docker container ls` deve listar um novo _container_ com as seguintes configurações:

  -- `IMAGE: rxnetty-calc-8659`  `PORT: 8080:8080`

## Endpoints

- Todos os endpoints estão em: `http://localhost:8080/`

-- **Exemplos: GET**

> Health

`http://localhost:8080/health`

> Adição (2 + 3)

`http://localhost:8080/add?x=2&y=3`

> Subtração (2 - 3)

`http://localhost:8080/sub?x=2&y=3`

> Multiplicação ( 2 * 3)

`http://localhost:8080/mul?x=2&y=3`

> Divisão ( 2 / 3)

`http://localhost:8080/div?x=2&y=3`

> Exponenciação ( 2 ^ 3)

`http://localhost:8080/exp?x=2&y=3`

> Histórico

`http://localhost:8080/history`

-- **Exemplos: DELETE**

> Apagar Histórico

`localhost:8080/clear`

## Parando o Container

- A partir da pasta raíz do projeto, execute o comando `bash run.sh stop`
- Sucesso: o comando `sudo docker container ls` **NÃO** deve listar aquele container criado:

  -- `IMAGE: rxnetty-calc-8659`  `PORT: 8080:8080`

- Os containers parados podem ser encontrados através do comando `sudo docker container ls -a` e podem ser apagados com `sudo docker container rm ${CONTAINER ID}`

## Nota

- O comando `sudo docker kill $(docker ps -a -q)` do `run.sh stop` talvez precise ser executado como usuário *ROOT*.  Para isto, executar o comando `sudo su`. 