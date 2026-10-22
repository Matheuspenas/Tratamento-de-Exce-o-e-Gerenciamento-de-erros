# PITS v2.0 — Aula 09: Tratamento de exceções e gerenciamento de erros

## Pré-requisitos
- JDK 17 ou superior.
- Terminal Bash (Linux, macOS ou Git Bash no Windows).

## Executar testes
Na pasta `pits-refatoracao-atividade`, execute:

```bash
bash verificar.sh testes
```

Para executar testes e as duas demonstrações:

```bash
bash verificar.sh todos
```

## O que foi acrescentado
- `RepositorioIndisponivelException`: sinaliza falha técnica de persistência e preserva a causa original.
- `FalhaAuditoriaException`: tipo definido para falha de auditoria quando a implementação de persistência puder distingui-la.
- Logs no `AccessEventService` com identificador de correlação, evento e resultado.
- Tratamento de falha de persistência para impedir que a operação pareça ter sido concluída com sucesso.
- Teste de regressão que verifica a exceção específica e a causa original.

## Política de logging
O projeto usa `java.util.logging` (JUL), biblioteca de logging incluída no JDK, para manter a compilação simples e sem dependências externas. Eventos autorizados usam INFO; negativas esperadas usam WARNING; falhas técnicas usam SEVERE. O identificador de correlação é gerado por tentativa. Não registrar senhas, biometria, tokens ou credenciais integrais.

## Limite atual da auditoria
No modelo atual, o repositório grava o registro de acesso e sua auditoria em uma única operação (`RepositorioAcesso.salvar`). Portanto, a camada de serviço não consegue distinguir, por si só, uma falha exclusiva de auditoria de uma falha geral de persistência. Para lançar `FalhaAuditoriaException` corretamente, a interface deverá expor uma operação de auditoria separada ou um resultado técnico tipado. A exceção está criada, mas não é lançada artificialmente sem essa separação.

## Simulação de falha
O teste `falhaPersistenciaNaoRetornaAutorizacao` usa um repositório falso que lança `IllegalStateException`. O serviço deve converter essa falha em `RepositorioIndisponivelException`, preservar a causa e não retornar um evento como se o salvamento tivesse sido concluído.

## Segurança e resiliência
- Negativas de autorização são resultados esperados, não exceções técnicas.
- Não autorizar quando falha uma validação obrigatória.
- Não repetir indefinidamente operações de persistência/auditoria.
- A implementação atual usa repositório em memória; não representa indisponibilidade real de serviço externo.
