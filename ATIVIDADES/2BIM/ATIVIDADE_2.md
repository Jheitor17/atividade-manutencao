# Atividade 2 – Triagem, Relatório Profissional e Ciclo de Vida do Bug

Esta atividade foca na evolução do processo de relato e tratamento de bugs, com triagem formal, classificação de severidade e prioridade, e simulação do ciclo de vida até a correção.

## Objetivo

Registrar bugs com documentação profissional, revisar cada issue como parte de uma triagem técnica e avançar o card no fluxo do GitHub Projects até a simulação da correção.

## Etapa 1: Bug Report Profissional

Documentar **5 bugs adicionais** utilizando um relatório completo, com os campos abaixo:

- Título claro e objetivo
- Descrição detalhada
- Passos para reproduzir
- Comportamento esperado
- Comportamento observado
- Ambiente de execução
- Evidências da ocorrência do bug, como logs, screenshots e saídas da aplicação

O relatório deve evidenciar o problema em execução, e não apenas apontar o trecho de código suspeito.

## Etapa 2: Triagem Técnica

Revisar os bugs já cadastrados e simular a etapa de triagem no GitHub Projects.

### 2.1 Classificação do tipo de falha

Para cada issue, identificar se o caso deve ser tratado como:

- `Bug`: erro que já chegou ao usuário e exige manutenção corretiva
- `Defect`: erro identificado internamente pelo time, antes de chegar ao usuário

### 2.2 Rejeição de bugs inválidos

Antes da classificação, verificar se alguma issue representa um falso bug. Quando aplicável, encerrar a issue com uma das etiquetas abaixo:

- `duplicate`: já foi reportado anteriormente
- `invalid`: trata-se de pedido de funcionalidade ou suporte, não de erro
- `worksforme`: não foi possível reproduzir o problema
- `wontfix`: o custo de correção não compensa o impacto

### 2.3 Classificação de severidade

Para os bugs confirmados, atribuir uma severidade conforme a classificação oficial:

- `Catastrophic`: inviabiliza o uso de parte relevante do sistema e não possui rota de contorno
- `Serious`: funcionalidade importante parou, sem rota de contorno
- `Normal`: há impacto funcional, mas existe rota de contorno
- `Trivial`: impacto baixo ou apenas cosmético

### 2.4 Classificação de prioridade

Cada bug confirmado também deve receber uma prioridade de correção:

- `Baixa`
- `Média`
- `Alta`

## Etapa 3: Ciclo de Vida e Correção

Após a triagem, deve ser escolhido um bug que ainda não tenha sido corrigido anteriormente para aplicação da correção real no sistema.

### 3.1 Fluxo no GitHub Projects

- Atribuir a issue ao responsável
- Mover o card de `To Do (Confirmed)` para `In Progress (Em Correção)`
- Registrar o avanço da correção no próprio card ou na issue

### 3.2 Mensagem de commit

Ao final, registrar a mensagem de commit utilizada na correção, seguindo o padrão **Conventional Commits**.

A mensagem deve obedecer a estas regras:

- O tipo deve ser obrigatoriamente `fix`
- A descrição deve começar com um verbo no presente
- Opcionalmente, a mensagem pode conter referência automática para fechamento da issue, como `fixes #12`

Exemplo:

```text
fix(nome-do-modulo): corrige comportamento do componente X ao carregar a página
```

## Formato de Entrega

### Repositório da Entrega

1. A entrega deve ser feita em um repositório público no GitHub.
2. O repositório deve ser um fork do repositório da disciplina.
3. O project e as issues devem estar visíveis no repositório.

### Evidências

A entrega deve incluir evidências da execução, da triagem e da correção aplicada, como:

- issues criadas e classificadas
- labels de severidade e prioridade
- movimentação dos cards no project
- comentários com a mensagem de commit da correção
- logs, screenshots ou outros registros da ocorrência dos bugs

### Envio por E-mail

Após publicar o repositório no GitHub, enviar o link por e-mail para:

**joao.vsantos@unicesumar.edu.br**

O e-mail deve seguir este formato:

**Título do e-mail:**

```text
Atividade 2 Manutenção ESOFT5S 2BIM
```

**Corpo do e-mail:**

```text
Integrantes:

Nome completo de cada integrante da equipe
Usuário do GitHub de cada integrante

Repositório: https://github.com/seu-usuario/seu-fork
Project: https://github.com/seu-usuario/seu-fork/projects/XX
```

## Prazo de Entrega

**Data**: 07/05/2026  
**Horário**: até 19h

## Checklist de Validação

Antes de entregar, validar:

- [ ] 5 bugs documentados com bug report completo
- [ ] Evidências coletadas para cada bug
- [ ] Issues triadas com severidade e prioridade
- [ ] Bugs inválidos rejeitados com label adequada, quando aplicável
- [ ] Cards movidos no GitHub Projects até o estágio de correção
- [ ] Mensagem de commit registrada no padrão Conventional Commits
- [ ] Repositório público e fork da disciplina
- [ ] E-mail preparado com link do repositório e project
