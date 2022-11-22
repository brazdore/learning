## 1. Research about Terraform and Ansible. Explain the differences and when use one or another.

Tanto Ansible quanto Terraform são ferramentas utilizadas na engenharia DevOps. Ambas são ferramentas de automação que seguem o princípio de IaC.

O Ansible é uma ferramenta de gerenciamento de configurações, provisionamento e orquestração. Ansible é agentless e faz a configuração dos sistemas utilizando as ferramentas nativas das máquinas: SSH para sistemas UNIX e Windows Remote Management via PowerShell no Windows.

Para ser executado, o Ansible precisa de um **playbook** e um inventário de **hosts**. O playbook é composto por **roles** e as roles são compostas por **tasks**. Os hosts ficam em um arquivo de inventário, cuja localização padrão é /etc/ansible/hosts, e podem ser selecionados para a execução do Ansible. Em execução, o Ansible roda os módulos do playbook no host escolhido. Como gerenciador de configurações, o Ansible roda tanto em Cloud quanto em bare metal.

`PLAYBOOK: O arquivo principal onde definiremos as ROLES e os HOSTS; o que executar e onde executar.`

`ROLE: A ser chamado no PLAYBOOK; uma forma de organizar as TASKS por responsabilidade.`

`HOST: Parte do sistema ou máquina a ser gerenciada; como o Ansible pode gerenciar um sistema completo como vários nodos, o HOST é como escolhemos o nodo específico.`

`TASK: Contém procedimentos básicos a serem executados; como atualizar o OS ou instalar o Java.`

Por sua vez, o Terraform permite o gerenciamento de serviços Cloud através da construção, mudança e versionamento da infraestrutura. Não é uma ferramenta que gerencia software em máquinas, mas sim servidores e serviços Cloud. Para a execução do Terraform é necessário um arquivo de configuração `.tf` e um arquivo de estado `.tfstate`. Também é necessário um provider, como AWS, GCP ou Azure, entre outros. O Terraform armazena o estado de um sistema e é capaz de recuperá-lo em casos de falhas inesperadas. É uma ferramenta que garante um estado constante de um sistema.

O Ansible é processual na medida em que os playbooks são executados em sequência para configurar os sistemas. É possível utilizar o Ansible para provisionar uma infraestrutura Cloud, mas não é a melhor escolha para deployar infraestruturas muito grandes. Já o Terraform é uma ferramenta de automação de infraestrutura que interpreta as descrições de servidores, conexões e chaves em um modelo HCL e gera um plano de execução otimizado. Múltiplas execuções não alteram o estado se nenhuma mudança foi feita; caso mudanças sejam feitas, ocorre a sincronização com a infraestrutura.

Assim, Ansible o Terraform parecem ser ferramentas de automação com escopos diferentes; Ansible como um gerenciador de configurações de um sistema Cloud ou bare metal, Terraform como um orquestrador de configurações de insfraestrutura Cloud.

> Referências:

[Ansible: Tutorial - RedHat](https://www.redhat.com/en/topics/automation/learning-ansible-tutorial)
[Ansible: Benefits of Agentless - RedHat](https://www.ansible.com/hubfs/pdfs/Benefits-of-Agentless-WhitePaper.pdf)
[Ansible: Ansible 101 - Medium](https://medium.com/@wintonjkt/ansible-101-getting-started-1daaff872b64)
[Terraform: Terraform Documentation - HashiCorp](https://www.terraform.io/docs)
[Ansible & Terraform: Ansible vs Terraform - Intellipaat](https://intellipaat.com/blog/terraform-vs-ansible-difference/)
[Ansible & Terraform: Ansible vs Terraform - Cloudify](https://cloudify.co/blog/ansible-vs-terraform/)
