# tema13--AWS

## Informações

- Para a execução deste projeto pressupõe-se uma configuração igual a da `ilegra-poc`

## Requisitos

- Conta na AWS
- Packer
- Terraform

## Preparação

- Instalar o [Packer](https://learn.hashicorp.com/tutorials/packer/get-started-install-cli)
- Instalar o [Terraform](https://learn.hashicorp.com/tutorials/terraform/install-cli)

## AWS: Key Pairs

- No console da AWS, navegue até `EC2 > Network & Security > Key Pairs`
- Certifique-se que a região selecionada é `us-east-1`
- Crie um novo `Key Pair`
- Este `Key Pair Name` será utilizado na próxima etapa

## Execução

- O script a ser executado deve receber três argumentos:
  - Access Key ID
  - Secret Access Key
  - Key Pair Name
- No diretório raíz do projeto, execute o comando `scripts/start.sh $Access_Key_ID $Secret_Access_Key $Key_Pair_Name`
- Por exemplo: `scripts/start.sh AJYX8G24ZRTQKLL22BOB JZBw-FFyc7jnRT1lp7DhcP9GcyuyuM2lc23afcJU kp-teste-nenechi`
- O script deve executar o arquivo `packer.json` e criar uma AMI com as configurações especificadas
- Depois deve inicializar o Terraform, executar o `main.tf` e criar toda a infraestrutura especificada

## Acesso

- É possível acessar nossa aplicação através do `DNS name` do Load Balancer criado
- Basta navegar até `EC2 > Load Balancing > Load Balancers` e selecionar `go-calculator-elb`
- Na descrição, copie o endereço do `DNS name`
- Todos os endpoints abaixo seguirão o formato `http://$DNSNAME:8080`
- Por exemplo: `http://go-calculator-elb-8418248920.us-east-1.elb.amazonaws.com:8080`

## Endpoints

> **GET**

- `http://$DNSNAME:8080/calc/<op>/<x>/<y>`
  - Retorna o resultado da operação entre X e Y
- `http://$DNSNAME:8080/calc/history`
  - Retorna o histórico de operações
- `http://$DNSNAME:8080/calc/clear`
  - Limpa o histórico de operações
- `http://$DNSNAME:8080/headers`
  - Retorna os headers da requisição

> **Exemplos:**

- `http://$DNSNAME:8080/calc/sum/2/3`
  - 2 + 3
- `http://$DNSNAME:8080/calc/sub/10/2`
  - 10 - 2
- `http://$DNSNAME:8080/calc/mul/8/8`
  - 8 \* 8
- `http://$DNSNAME:8080/calc/div/5/2.5`
  - 5 / 2.5
- `http://$DNSNAME:8080/calc/exp/2/8`
  - 2 ^ 8

## Testes

- É possível testar os endpoints executando o comando `scripts/smoke-test-all.sh $DNSNAME` a partir da pasta raíz do projeto

- O script deve criar uma nova pasta `test-output` com os resultados dos testes

- Por exemplo: `scripts/smoke-test-all.sh http://go-calculator-elb-8418248920.us-east-1.elb.amazonaws.com`

## Notas

- O Terraform parece criar uma instância desacoplada do meu ASG. O resultado é um ASG com apenas uma instância e um ELB com duas instâncias. O desejado seria um ASG de limite 1 com uma instância e um ELB com esta mesma instância. Não tenho certeza dos motivos, mas não parece gerar nenhum problema já que ambas as instâncias aparecem no ELB e são acessíveis via DNS.

- Naturalmente, cada máquina estará rodando o serviço em Go independentemente e terão históricos desacoplados.
