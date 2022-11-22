## Why SOA/Microservices is important for DevOps Engineering?

SOA (Service-Oriented Architecture) é um padrão de projeto de desenvolvimento de software de baixo acoplamento onde todas as funcionalidades devem ser compartilhados na forma de serviços consumíveis por outras aplicações ou serviços através de protocolos de comunicação pela rede. Microservices é um tipo de SOA com uma granularidade menor e isolamento de componentes.

Software construído utilizando SOA/Microservices produz diversos benefícios desejáveis a DevOps:

    1. Implementação: Oferece agilidade na hora de construir, testar, e implementar o código; CD/CI elevado.

    2. Confiabilidade: Caso um serviço falhe, outros deveriam continuar funcionando sem problemas, assim, há um isolamento de falhar ou erros, possibilitando uma recuperação mais fácil.

    3. Escalabilidade: Os serviços são todos independentes, com um baixo nível de acoplamento, podendo assim escalar individualmente sem impactar outros serviços.

    4. Flexibilidade: Os serviços podem comunicar-se entre si independentemente de tecnologias utilizadas, oferecendo aos desenvolvedores uma maior flexibilidade nas escolhas das tecnologias.

Assim, a arquitetura SOA amplifica o potencial de DevOps. Devido a esta cultura comum, DevOps e SOA se complementam.

## If I run my software in containers in bare-metal could I consider my architecture cloud-native? Why?

Todo o software precisa considerar a infraestrutura em que rodará; desde o OS às capacidades de memória, armazenamento e CPU. Criar um software pensando em rodá-lo em Bare Metal é extremamente diferente que pensar em rodá-lo na Cloud, já que o Bare Metal está limitado à física das coisas, i.e., está limitado às capacidades das peças, ao tempo de envio de uma nova peça comprada, ao tempo de instalação, ao tempo de manutenção, entre outros.

Mesmo que tenhamos um Bare Metal bem construído, com peças boas e manutenções frequentes, a virtualização ou conteinerização do software localmente nestas máquinas não implica considerar esta aplicação Cloud ou Cloud Native; primeiro que não está rodando na Cloud, segundo que não foi desenhada para rodar nativamente na Cloud.

Há uma diferença nas considerações que devem ser feitas durante a construção ou a arquitetura de um software; se vamos construir uma aplicação que rodará em Bare Metal, devemos levar em consideração aqueles problemas descritos acima. Encontramos outros problemas quando pensamos em rodar algo na Cloud, então a solução pensada para problema X não funcionará efetivamente para o problema Y. Por exemplo, um problema de se utilizar software em Cloud é que, caso não seja bem desenhado, consumirá mais e mais recursos sem necessidade, gerando custos e talvez prejuízo para a empresa, o que não aconteceria em Bare Metal restrito pelas capacidades das peças instaladas.

Assim, é possível rodar um software pensado para Bare Metal como Cloud “As-IS”, i.e., rodar em Cloud com uma arquitetura pensada em Bare Metal, mas não será eficiente.

Enfim: não. Não podemos considerar um software rodando em containers Bare Metal como Cloud Native.

## Explain how the circuit-breaker pattern works.

O Circuit Breaker é um design pattern que busca solucionar problemas que podem acontecer quando utilizamos chamadas remotas entre serviços. Se não tratados, muitas chamadas a um serviço que não está funcionando pode continuar a consumir recursos, que por sua vez pode levar a falhas em cascata por todo o projeto. O objetivo do Circuit Breaker é prevenir este tipo de falha catastrófica em cascata.

A ideia é envelopar a lógica de chamada de uma função em um objeto Circuit Breaker que monitora possíveis falhas. Caso estas falhas passem de um limite estabelecido, o Circuit Breaker é ativado e todas as chamadas subsequentes desta função retornam um erro sem chamar a função.

É um pattern muito importante quando trabalhamos com SOA, já que a toda hora os serviços estarão comunicando-se via chamadas remotas.
