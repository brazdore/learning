
# tema06 - Docker

## Preparação

- Instalar e configurar o [Docker](https://docs.docker.com/engine/install/).
- Clonar este repositório.

## Gerando a Imagem

- Na pasta raíz do projeto, execute o comando `docker build -t calc-3704 .`
- Sucesso: o comando `docker image ls` deve listar uma imagem com `REPOSITORY` igual a `calc-3704`

## Subindo o Container

- Na pasta raíz do projeto, execute o comando `docker run -d -p 8080:8080 calc-3704`
- Sucesso: o comando `docker container ls` deve listar um *container* com `IMAGE` igual a `calc-3704`

## Endpoints

Todos os endpoints do projeto devem estar disponíveis em `localhost:8080/tema06/calc`

	Exemplos: GET
	
> Adição (2 + 3)

`http://localhost:8080/tema06/calc?op=add&x=2&y=3`

> Subtração (2 - 3)

`http://localhost:8080/tema06/calc?op=sub&x=2&y=3`

> Multiplicação (2 * 3)

`http://localhost:8080/tema06/calc?op=mul&x=2&y=3`

> Divisão (2 / 3)

`http://localhost:8080/tema06/calc?op=div&x=2&y=3`

> Exponenciação (2 ^ 3)

`http://localhost:8080/tema06/calc?op=exp&x=2&y=3`

> Histórico

`http://localhost:8080/tema06/calc?op=history`

## Encerrando o Container

- Execute o comando `docker container ls` e copie o `CONTAINER ID` do *container* com `IMAGE` igual a `calc-3704`
- Execute o comando `docker stop ${CONTAINER ID}`