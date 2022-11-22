## 1. What the Cron services does in linux? how to use it?

O Cron é um serviço do Linux que serve para agendar tarefas a serem executadas, como rotinas de backup a ou rotinas de sincronização. É utilizado para automatizar a manutenção de ambientes de software, agendando comandos ou scripts para serem executados.

É possível agendar uma tarefa com o Cron seguindo a sintaxe abaixo:

# ┌───────────── minute (0 - 59)

# │ ┌───────────── hour (0 - 23)

# │ │ ┌───────────── day of the month (1 - 31)

# │ │ │ ┌───────────── month (1 - 12)

# │ │ │ │ ┌───────────── day of the week (0 - 6) (Sunday to Saturday;

# │ │ │ │ │ 7 is also Sunday on some systems)

# │ │ │ │ │

# │ │ │ │ │

# \* \* \* \* \* <command to execute>

O comando abaixo agendaria uma tarefa que printaria “watoto” toda segunda-feira 13:30:

`30 13 * * 1 echo watoto`

## 2. What is SystemD? How to use it?

O systemd é um sistema de inicialização para Linux. Ele disponibiliza um conjunto de blocos básicos para sistemas Linux e provê um gerenciador de sistema e serviços. O systemd roda como PID 1 e inicializa os demais componentes do sistema. O systemd ganhou popularidade em 2015, quando o Ubuntu substituiu seu inicializador, o SystemV, pelo systemd.

O systemd permite a execução de alguns comandos:

`systemd-analyze blame`

    Lista todos os serviços inicializados durante a inicialização do sistema e o tempo que cada serviço demorou para ser inicializado completamente, possibilitando a detecção do culpado de uma inicialização mais demorado do sistema.

`systemctl <enable/disable> <x.service>`

    Configura um serviço para inicializar automaticamente com a sistema ou desativa uma inicialização automática.

`systemctl <start/restart/stop/reload/status> <x.service>`

    Gerenciamento básico dos sistemas; iniciar, parar, reiniciar, recarregar, listar status.

## 3. Why linux is so important to DevOps? Explain Why?

Com os anos de competição entre Windows e Linux, estabeleceu-se o Windows como o OS para quem quer jogar coisas no computador e o Linux, por se tratar de um OS customizável, leve e open-source, foi mais utilizado para a execução de programas e servidores.

A maior parte dos softwares que utilizamos para construir software foram pensados e desenvolvidos para rodar em Linux. Um exemplo claro é o Docker,que só roda no Windows através de alguma virtualização, como o WSL.

O Linux também é estável e confiável e a maior parte das máquinas dos provedores Cloud utilizam alguma distribuição do Linux como OS. O provisionamento em DevOps, a instalação de pacotes e configuração de arquivos, também é feito majoritariamente para Linux.

Assim, podemos ver que o Linux é o OS padrão no mundo de desenvolvimento de software.
