# Atividade 1 – Análise de Código e Manutenção Preventiva

Esta atividade foca em manutenção preventiva: entender o sistema legado, classificar problemas de manutenibilidade e refatorar sem alterar o comportamento funcional.

## Objetivos

1. Identificar pelo menos 10 problemas de manutenibilidade.
2. Classificar cada problema com base nos conceitos estudados.
3. Aplicar pelo menos 5 refatorações no código.
4. Garantir que o comportamento externo do sistema permaneça o mesmo.

## Escopo Técnico

- Linguagem: Java (sem frameworks)
- Operação: CLI
- Código-fonte principal em [../../library-maintenance-lab/src](../../library-maintenance-lab/src)

## Code Smells Identificados no Projeto

| Arquivo | Classe/Método | Code Smell | Descrição | Possível Refatoração |
| --- | --- | --- | --- | --- |
| [../../library-maintenance-lab/src/LibrarySystem.java](../../library-maintenance-lab/src/LibrarySystem.java) | [LibrarySystem (classe)](../../library-maintenance-lab/src/LibrarySystem.java#L4) | God Class | Centraliza menu, orquestração, entrada de dados, debug, logs e cenário de demonstração na mesma classe. | Separar em camadas (controller/serviço) e dividir casos de uso por módulo. |
| [../../library-maintenance-lab/src/LibrarySystem.java](../../library-maintenance-lab/src/LibrarySystem.java) | [startCli()](../../library-maintenance-lab/src/LibrarySystem.java#L23) | Mixed Responsibilities | Loop principal mistura interface de usuário, regras de execução e tratamento de erros genérico. | Extrair dispatcher de comandos e separar UI de aplicação. |
| [../../library-maintenance-lab/src/LibrarySystem.java](../../library-maintenance-lab/src/LibrarySystem.java) | [handleDebugArea()](../../library-maintenance-lab/src/LibrarySystem.java#L225) | Long Method | Método muito extenso para uma ação de menu, com muitos caminhos de execução. | Extract Method por ação de debug. |
| [../../library-maintenance-lab/src/LibrarySystem.java](../../library-maintenance-lab/src/LibrarySystem.java) | [handleDebugArea()](../../library-maintenance-lab/src/LibrarySystem.java#L225) | Deep Nesting | Cadeia de if/else em vários níveis dificulta leitura e alteração segura. | Substituir por early return ou mapa de comandos. |
| [../../library-maintenance-lab/src/LibrarySystem.java](../../library-maintenance-lab/src/LibrarySystem.java) | [campos com new](../../library-maintenance-lab/src/LibrarySystem.java#L9) | Tight Coupling / No DI | Dependências criadas diretamente com new BookManager, new UserManager, new LoanManager etc. | Injeção por construtor e interfaces para desacoplamento. |
| [../../library-maintenance-lab/src/LibrarySystem.java](../../library-maintenance-lab/src/LibrarySystem.java) | [handleRegisterBook()](../../library-maintenance-lab/src/LibrarySystem.java#L81) | Duplicate Code | Validação e normalização repetidas também em BookManager.registerBook. | Consolidar validação em um único ponto. |
| [../../library-maintenance-lab/src/LibrarySystem.java](../../library-maintenance-lab/src/LibrarySystem.java) | [handleBorrowBook() / handleReturnBook()](../../library-maintenance-lab/src/LibrarySystem.java#L141) | Primitive Obsession | Fluxos de negócio dirigidos por strings e inteiros mágicos (channel, policyCode, forceFlag). | Criar enums/objetos de valor para parâmetros de domínio. |
| [../../library-maintenance-lab/src/LoanManager.java](../../library-maintenance-lab/src/LoanManager.java) | [borrowBook(...)](../../library-maintenance-lab/src/LoanManager.java#L14) | Long Method | Método concentra validação de usuário/livro, criação de empréstimo, atualização de estado e notificação. | Quebrar em validar, criarEmpréstimo, atualizarEstoque e notificar. |
| [../../library-maintenance-lab/src/LoanManager.java](../../library-maintenance-lab/src/LoanManager.java) | [borrowBook(...)](../../library-maintenance-lab/src/LoanManager.java#L14) | Long Parameter List | Assinatura com 8 parâmetros primitivos aumenta risco de chamada incorreta. | Introduzir BorrowRequest com campos nomeados. |
| [../../library-maintenance-lab/src/LoanManager.java](../../library-maintenance-lab/src/LoanManager.java) | [borrowBook(...)](../../library-maintenance-lab/src/LoanManager.java#L20) | Deep Nesting | Múltiplos ifs encadeados para validações e regras operacionais. | Guard clauses para reduzir aninhamento. |
| [../../library-maintenance-lab/src/LoanManager.java](../../library-maintenance-lab/src/LoanManager.java) | [returnBook(...)](../../library-maintenance-lab/src/LoanManager.java#L90) | Inconsistent Error Handling | Quando loan é nulo, retorna silenciosamente (workaround); em outros casos lança RuntimeException. | Definir política única de erro por operação. |
| [../../library-maintenance-lab/src/LoanManager.java](../../library-maintenance-lab/src/LoanManager.java) | [calculateFineLegacy(...)](../../library-maintenance-lab/src/LoanManager.java#L144) | Primitive Obsession | Datas e regras representadas por String/int sem tipos de domínio. | Usar tipo de data e enum de política de multa. |
| [../../library-maintenance-lab/src/LoanManager.java](../../library-maintenance-lab/src/LoanManager.java) | [campo notificationService](../../library-maintenance-lab/src/LoanManager.java#L9) | Tight Coupling | Dependência concreta instanciada internamente. | Injeção de dependência e contrato de notificação. |
| [../../library-maintenance-lab/src/ReportGenerator.java](../../library-maintenance-lab/src/ReportGenerator.java) | [generateSimpleReport(...)](../../library-maintenance-lab/src/ReportGenerator.java#L9) | Mixed Responsibilities | Método monta texto, consulta base global e calcula estatísticas ao mesmo tempo. | Separar cálculo do relatório da formatação. |
| [../../library-maintenance-lab/src/ReportGenerator.java](../../library-maintenance-lab/src/ReportGenerator.java) | [totalLoans](../../library-maintenance-lab/src/ReportGenerator.java#L24) | Magic Numbers / Regra obscura | totalLoans usa ajuste fixo (+1), sem contexto de negócio. | Remover ajuste mágico e cobrir com testes. |
| [../../library-maintenance-lab/src/ReportGenerator.java](../../library-maintenance-lab/src/ReportGenerator.java) | [closedLoans](../../library-maintenance-lab/src/ReportGenerator.java#L33) | Calculation Bug / Smell de lógica frágil | closedLoans incrementa mesmo para empréstimo aberto. | Reorganizar cálculo com regras explícitas e testes. |
| [../../library-maintenance-lab/src/BookManager.java](../../library-maintenance-lab/src/BookManager.java) | [registerBook(...)](../../library-maintenance-lab/src/BookManager.java#L10) | Long Parameter List | Muitos parâmetros tornam manutenção e evolução mais caras. | Introduzir DTO/entidade Book e construtor com validação. |
| [../../library-maintenance-lab/src/BookManager.java](../../library-maintenance-lab/src/BookManager.java) | [registerBook(...)](../../library-maintenance-lab/src/BookManager.java#L17) | Validation Smell | Fluxo aceita título em branco por workaround legado, contrariando regra de negócio. | Centralizar validação e falhar com mensagem consistente. |
| [../../library-maintenance-lab/src/BookManager.java](../../library-maintenance-lab/src/BookManager.java) | [listBooksSimple()](../../library-maintenance-lab/src/BookManager.java#L51) | Edge Case mal tratado | Acesso a temp.get(0) quando lista vazia pode causar falha. | Fazer early return com mensagem de sem registros. |
| [../../library-maintenance-lab/src/BookManager.java](../../library-maintenance-lab/src/BookManager.java) | [updateAvailableWithLegacyRule(...)](../../library-maintenance-lab/src/BookManager.java#L75) | Long Parameter List / Mixed Responsibilities | Mistura atualização de estado, regras por opCode e logging com vários parâmetros de controle. | Separar por operações de estoque e reduzir parâmetros. |
| [../../library-maintenance-lab/src/UserManager.java](../../library-maintenance-lab/src/UserManager.java) | [registerUser(...)](../../library-maintenance-lab/src/UserManager.java#L5) | Long Parameter List | Cadastro recebe 7 parâmetros primitivos/strings sem objeto de contexto. | Criar UserData e validação centralizada. |
| [../../library-maintenance-lab/src/UserManager.java](../../library-maintenance-lab/src/UserManager.java) | [validateUserData(...)](../../library-maintenance-lab/src/UserManager.java#L102) | Duplicate Code | Regras de validação semelhantes às usadas no fluxo de cadastro. | Reutilizar único validador de usuário. |
| [../../library-maintenance-lab/src/LegacyDatabase.java](../../library-maintenance-lab/src/LegacyDatabase.java) | [classe e campos estaticos](../../library-maintenance-lab/src/LegacyDatabase.java#L6) | Hidden Dependencies | Estado global compartilhado por todo o sistema torna testes dependentes de ordem de execução. | Encapsular estado em repositórios com ciclo de vida controlado. |
| [../../library-maintenance-lab/src/LegacyDatabase.java](../../library-maintenance-lab/src/LegacyDatabase.java) | [getBooks/getUsers/getLoans](../../library-maintenance-lab/src/LegacyDatabase.java#L128) | Breaking Encapsulation | Estruturas internas mutáveis são expostas diretamente para qualquer classe. | Retornar cópias defensivas ou coleções imutáveis. |
| [../../library-maintenance-lab/src/LegacyDatabase.java](../../library-maintenance-lab/src/LegacyDatabase.java) | [unsafeUpdateBookField/unsafeUpdateUserField](../../library-maintenance-lab/src/LegacyDatabase.java#L144) | Encapsulation Violation | Atualizações dinâmicas por nome de campo ignoram validação e regras de negócio. | Criar métodos explícitos de atualização por caso de uso. |
| [../../library-maintenance-lab/src/LegacyDatabase.java](../../library-maintenance-lab/src/LegacyDatabase.java) | [countOpenLoansByBook()](../../library-maintenance-lab/src/LegacyDatabase.java#L181) | Poor Naming / Lógica inconsistente | Nome sugere filtro por livro, mas o filtro usa userId. | Corrigir filtro e alinhar nome/implementação. |
| [../../library-maintenance-lab/src/LegacyDatabase.java](../../library-maintenance-lab/src/LegacyDatabase.java) | [clearLogsIfTooBig()](../../library-maintenance-lab/src/LegacyDatabase.java#L200) | Magic Numbers | Limites 500 e 400 são números mágicos sem semântica explícita. | Extrair constantes nomeadas e documentar política. |
| [../../library-maintenance-lab/src/DataUtil.java](../../library-maintenance-lab/src/DataUtil.java) | [classe utilitaria estatica](../../library-maintenance-lab/src/DataUtil.java#L5) | Utility Class Overuse | Muitos métodos globais estáticos e estado compartilhado (scanner/retryCounter). | Reduzir estado global e separar utilitários por responsabilidade. |
| [../../library-maintenance-lab/src/DataUtil.java](../../library-maintenance-lab/src/DataUtil.java) | [datePlusDaysApprox(...)](../../library-maintenance-lab/src/DataUtil.java#L169) | Primitive Obsession / Regra frágil | Cálculo de data por concatenação de string. | Usar API de data e regra de prazo real. |
| [../../library-maintenance-lab/src/Main.java](../../library-maintenance-lab/src/Main.java) | [main(...)](../../library-maintenance-lab/src/Main.java#L3) | Feature Envy / Coordination Leakage | Main conhece detalhes de modos e aciona componentes internos diretamente. | Encapsular modos de execução em serviço de aplicação. |

## Hints (Guia de Exploração)

Hint 1  
Observe [LoanManager.borrowBook](../../library-maintenance-lab/src/LoanManager.java#L14) em [src/LoanManager.java](../../library-maintenance-lab/src/LoanManager.java).  
Ele mistura validação, regra de negócio e manipulação de estado em um único fluxo.

Hint 2  
Observe [LoanManager.returnBook](../../library-maintenance-lab/src/LoanManager.java#L90) em [src/LoanManager.java](../../library-maintenance-lab/src/LoanManager.java).  
Compare os caminhos de erro e note inconsistências entre lançar exceção e retornar silenciosamente.

Hint 3  
Observe [ReportGenerator.generateSimpleReport](../../library-maintenance-lab/src/ReportGenerator.java#L9) em [src/ReportGenerator.java](../../library-maintenance-lab/src/ReportGenerator.java).  
Confira os cálculos em torno de [totalLoans](../../library-maintenance-lab/src/ReportGenerator.java#L24) e [closedLoans](../../library-maintenance-lab/src/ReportGenerator.java#L33).

Hint 4  
Observe [LibrarySystem.handleDebugArea](../../library-maintenance-lab/src/LibrarySystem.java#L225) em [src/LibrarySystem.java](../../library-maintenance-lab/src/LibrarySystem.java).  
É um bom ponto para praticar redução de aninhamento e decomposição de responsabilidades.

Hint 5  
Observe [BookManager.registerBook](../../library-maintenance-lab/src/BookManager.java#L10) em [src/BookManager.java](../../library-maintenance-lab/src/BookManager.java).  
A assinatura longa sugere oportunidade para encapsular dados de entrada.

Hint 6  
Observe [BookManager.listBooksSimple](../../library-maintenance-lab/src/BookManager.java#L51) em [src/BookManager.java](../../library-maintenance-lab/src/BookManager.java).  
Teste o comportamento quando não houver livros cadastrados.

Hint 7  
Observe [LegacyDatabase.countOpenLoansByBook](../../library-maintenance-lab/src/LegacyDatabase.java#L181) em [src/LegacyDatabase.java](../../library-maintenance-lab/src/LegacyDatabase.java).  
Valide se o critério de filtro condiz com o nome e objetivo do método.

Hint 8  
Observe [LibrarySystem](../../library-maintenance-lab/src/LibrarySystem.java#L4) como um todo em [src/LibrarySystem.java](../../library-maintenance-lab/src/LibrarySystem.java).  
A classe centraliza muitas responsabilidades e depende diretamente de várias outras classes.

## Conceitos Praticados

- manutenção de software
- leitura e compreensão de código
- code smells
- coesão e acoplamento
- refatoração
- legibilidade de código
- versionamento e commits

## Formato de Entrega - Atividade 1

Data final de entrega: 16/04.

1. Pull request ou branch dedicada com as refatorações aplicadas.
2. Documento curto (no README da branch ou arquivo markdown) contendo:
   - lista dos 10+ problemas encontrados
   - classificação dos smells
   - lista das 5+ refatorações realizadas
3. Evidência de que o comportamento original foi preservado (logs de execução antes/depois ou roteiro de validação manual).
4. Commits pequenos e descritivos, com mensagem clara da intenção de manutenção preventiva.

## Regras de Submissão

### Repositório da Entrega

1. A entrega deve ser feita em um repositório público no GitHub.
2. O repositório deve ser um fork do repositório da disciplina.

### Envio por E-mail

Após publicar o repositório no GitHub, enviar o link por e-mail para:

joao.vsantos@unicesumar.edu.br

O e-mail deve seguir este formato:

Título do e-mail:

Atividade 1 Manutenção ESOFT5S

Substitua o número conforme o trabalho (Atividade 1 ou Atividade 2).

Corpo do e-mail:

1. Nome completo de cada integrante da equipe.
2. Usuário do GitHub de cada integrante.
3. Link do repositório no GitHub.

Exemplo:

Integrantes:

João Silva - github.com/joaosilva  
Maria Souza - github.com/mariasouza  
Pedro Lima - github.com/pedrolima

Repositório: https://github.com/grupo-exemplo/trabalho-mobile

### Cópia do E-mail (CC)

O e-mail deve incluir em cópia (CC) o endereço de e-mail de todos os integrantes da equipe.

### Trabalho em Grupo

Os trabalhos podem ser realizados em grupos de até 6 participantes.

### Participação Individual

Cada integrante do grupo deve possuir pelo menos um commit relevante relacionado ao desenvolvimento da atividade no repositório.

### Vídeo da Entrega

Deve ser gravado um vídeo da equipe para esta atividade.

1. Cada integrante deve explicar pelo menos uma manutenção realizada na atividade.
2. A explicação deve descrever o que foi feito e porque.
3. O vídeo deve mostrar o código correspondente à manutenção explicada.
4. O vídeo deve incluir a execução do código para demonstrar o funcionamento.

## Observação

Não reescreva o sistema do zero. O foco é melhorar incrementalmente.