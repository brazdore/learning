## 1. IF I'm doing deploy automation? I'm doing devops? Explain why is true or false?

DevOps surge por volta de 2007 com o objetivo de ser uma alterativa ao pensamento padrão à época das empresas de software: orientados ao custo, com foco no curto prazo e empregando soluções baratas. Este pensamento resultava em um produto final sem qualidade que no futuro geraria gargalos, bottlenecks e outages.

Assim, podemos dizer que DevOps é uma cultura ou uma prática que consiste em formas de pensar, escrever e operar software a fim de alcançar alto desempenho e qualidade para obtenção de retornos de negócio.

Para trabalhar DevOps de maneira efetiva é necessário que haja um ambiente cultural propício. Um dos modelos que auxiliam a implementação desse ambiente cultural é o CALMS: Culture, Automation, Lean, Measurement, Sharing.

Podemos perceber que a automação é um requisito para a prática efetiva de DevOps, mas não satisfaz completamente o conceito. Isto é, automação é apenas uma parte da cultura DevOps.

Portanto, fazer automação não significa fazer DevOps, mas não é possível fazer DevOps sem automação.

## 2. How do we improve the reliability of a system?(Tip: Look for SRE)

SRE (Site Reliability Engineering) é uma abordagem que utiliza software como uma ferramenta para gerenciar sistemas, solucionar problemas e automatizar processos. Tarefas que outrora eram realizadas manualmente e estavam sujeitas ao erro humano, agora, com SRE, são realizadas via software. O grande objetivo do SRE é garantir a confiabilidade das operações.

A grande vantagem é a confiabilidade que a gerência via software proporciona a um projeto. O erro humano é a causa de TODOS os problemas concebíveis na sociedade, e isto não é diferente no desenvolvimento de software. Mais processos automatizados significa menos possibilidade de erro humano, que por sua vez implica numa maior confiabilidade e qualidade do produto final.

A prática do SRE também implica numa maior escalabilidade do projeto, já que as bases do projeto tendem a conter menos erros e a qualidade de verificação se mantém constante durante todo o ciclo de vida de um projeto.

## 3. Reaserch about Postmortem and list the basic elements of a post-mortem analysis/report.

O Postmortem, no contexto de desenvolvimento de software, é um relatório contendo informações de algum incidente ocorrido, geralmente grave.

Por mais grave que seja o erro, o Postmortem adota a cultura do blameless/shameless, i.e., não buscar culpados ou apontar dedos para indivíduos que possam ter causado o problema. É abstraído do incidente qualquer personalização e ele é descrito objetivamente. Podemos pensar como um mecânismo que garante uma análise fria do incidente, já se um incidente grave acontece por erro de apenas uma pessoa, o processo como todo não parece estar bem pensado.

Existem muitas formas de escrever este relatório, mas algumas informações parecem essenciais:

    1. Data: quando o incidente aconteceu.

    2. Status: qual o status atual do incidente; foi resolvido?

    3. Resumo: visão geral do incidente.

    4. Impacto: impactos gerados pelo incidente; algo ficou fora do ar? Dados foram perdidos? Impacto financeiro?

    5. Causas: o que causou o incidente; por quê o site saiu do ar? Por quê o DB não foi recuperado? Por quê o funcionário fez X ou Y?

    6. Resolução: como o incidente foi resolvido; de vez ou solução provisória?

    7. Detecção: como o incidente foi detectado; alguém detectou que o site caiu? Alguém não conseguiu usar um serviço?

    8. Ações: o que foi feito para resolver o incidente; mudança no código? Rollback para outra versão?

    9. Timeline: linha do tempo dos acontecimentos do incidente contendo o gatilho, as causas, a detecção e as ações para resolução.

    10. Aprendizado: lições aprendidas com o incidente; o que melhorar? É necessário rever processos? É necessário mudar a infraestrutura?
