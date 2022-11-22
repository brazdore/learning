# tema04 -- Bash

## 1 - Calculadora

- Executar o comando `chmod +x calc.sh` para dar permissão para a execução do script
- A sintaxe do script é: `calc.sh <op> <x> <y>`

> - op é a operação (sum, sub, mul, div, exp)
> - x é o primeiro número
> - y é o segundo número

- Exemplos:
  - `./calc.sh sum 2 3` (2 + 3)
  - `./calc.sh sub 2 3` (2 - 3)
  - `./calc.sh mul 2 3` (2 \* 3)
  - `./calc.sh div 2 3` (2 / 3)
  - `./calc.sh exp 2 3` (2³)

## 2 - Zip

- Executar o comando `chmod +x zip.sh` para dar permissão para a execução do script
- Executar o comando `./zip.sh` a partir do diretório contendo o script
- Digitar o caminho do diretório a ser compactado
- O arquivo compactado deve estar em `/home/backup/data/$TODAY/`

## 3 - Variáveis

- Executar o comando `chmod +x var.sh` para dar permissão para a execução do script
- Executar o comando `./var.sh` a partir do diretório contendo o script
- O arquivo com as variáveis deve estar em `/home/backup/data/$TODAY/env_var.txt`
