# ilegra - Desafio Técnico JTs - Data Analysis

## Requisitos

- JDK 11 ou maior

## Aplicação

- Esta aplicação monitora um diretório `(/data/in/)` por novos arquivos `.dat`
- Ao detectar um novo arquivo, o programa lê e salva em memória objetos com os valores descritos no arquivo
- Após salvar em memória, o programa manipula e escreve dados em um novo arquivo no diretório `(/data/out/)`

## Configuração

- Em `data-analysis/src/main/resources/application.properties` é necessário alterar o `BASE_DIR` com o caminho ao diretório base `(/data/)` a ser analizado
- Também é possível alterar a propriedade `SIZE_LIMIT` que define o tamanho máximo de um arquivo a ser lido; qualquer arquivo maior que o limite será ignorado\*
- `%HOME%` no Windows: `/Users/user/data/`
- `%HOME%` no Linux: `/home/user/data/`
- Não é necessário criar este diretório, o programa criará juntamente com as subpastas `in/` e `out/` caso ainda não exista

## Execução

- Após alterar as propriedades, basta executar o comando `./run.sh` a partir da pasta `scripts`\*
- Para encerrar a aplicação basta um `CTRL + C` no terminal
- É possível limpar o `JAR` buildado com o comando `./clean.sh` a partir da pasta `scripts`

## Formato de entrada

- Esta aplicação suporta três formatos de dados:

1. `001çCPFçNameçSalary` i.e. `001ç1234567891234çDiegoç50000`
2. `002çCNPJçNameçBusiness Area` i.e. `002ç2345675434544345çJose da SilvaçRural`
3. `003çSale IDç[Item ID-Item Quantity-Item Price]çSalesman name` i.e. `003ç08ç[1-34-10,2-33-1.50,3-40-0.10]çRenato`

- Esta aplicação suporta entradas na mesma linha, desde que cada entrada esteja separada por um e apenas um espaço i.e. `001ç1234567891234çDiegoç50000 003ç10ç[1-10-100,2-30-2.50,3-40-3.10]çDiego`

## Formato de saída

- O formato de saída dos dados é:

1. Quantidade de `Customer` no arquivo de entrada
2. Quantidade de `Salesman` no arquivo de entrada
3. Valor da maior venda:Lista com o ID da(s) maior(es) venda(s)
4. Valor gerado pelo pior vendedor:Lista com o ID do(s) pior(es) vendedor(es)

## Exemplo

- Um arquivo chamado `test.dat` com os seguintes dados:

`001ç1234567891234çDiegoç50000 001ç3245678865434çRenatoç40000.99 002ç2345675434544345çJose da SilvaçRural 002ç2345675433444345çEduardoPereiraçRural 003ç10ç[1-10-100,2-30-2.50,3-40-3.10]çDiego 003ç08ç[1-34-10,2-33-1.50,3-40-0.10]çRenato 003ç09ç[1-34-10,2-33-1.50,3-40-0.10]çKiara`

- Resultaria em um arquivo chamado `test.done.dat` com os seguintes dados:

- `2`
- `2`
- `1199.00:[10]`
- `393.50:[Kiara, Renato]`

## Hipotéticas futuras implementações

- A aplicação não suporta nomes de áreas de negócios `(Business Area)` com `ç`
- Nomes de `Business Area` com `ç` estão sendo considerados como parte do nome do `Customer`; para arrumar isto, seria necessário alterar a lógica implementada na classe `DeserializationService`

- De alguma forma configurar o `application.properties` para receber o caminho até o diretório base como um argumento na hora de executar o `JAR` i.e. algo como `java -jar data-analysis-app.jar --BASE_DIR=/home/user/data/`

- Implementar algum tipo de tolerância a entradas inválidas, i.e. tratar a Exception lançada quando ler uma linha como `001ç1234567891234çDiegoçCinco Mil` ou `003ç08ç{lista}çRenato`
## Notas

- A propriedade `SIZE_LIMIT` em `data-analysis/src/main/resources/application.properties` existe porque é possível que falte memória para a JVM armazenar os dados lidos; com as configurações padrões da JVM em uma máquina adequada é possível ler e armazenar dados de arquivos de aproximadamente 500 MBs

- No Linux, pode ser necessário executar o comando `sudo chmod +x run.sh` e `sudo chmod +x clean.sh` para tornar o script executável

- Uma entrada de `Sale` identifica o vendedor pelo nome em vez de CPF; se tivermos dois vendedores distintos com o mesmo nome, o programa vai agrupá-los como se fosse o mesmo; para resolver isto seria necessário mudar o formato do dado de `Sale`