# Gerenciamento de Conta Bancária

Implemente um sistema de gerenciamento de contas bancárias utilizando os conceitos de Programação Orientada a Objetos (POO) e persistência de dados. O objetivo é modelar uma classe `Conta` com diversas regras de negócio e garantir que os dados das contas e operações sejam persistidos em um banco de dados.

## Especificações:

### Classe Movimentacao:

#### Atributos:
- **nomeCorrentista**: Nome do cliente.
- **cpfCorrentista**: CPF do cliente (deve ser validado).
- **tipoTransacao**: Tipo de operação realizada (CARTÃO DE DÉBITO, saque, depósito, pagamento ou Pix).
- **descricao**: Descrição da operação (pode ser alterada manualmente ou gerada automaticamente).
- **dataTransacao**: Data da operação realizada.
- **valorOperacao**: Valor da operação.
- Outros atributos que você achar relevante.

### Persistência de Dados:
As informações de cada conta e as operações realizadas devem ser armazenadas de forma persistente (em banco de dados). O sistema deve permitir:
- Salvar dados.
- Carregar dados.
- Consultar dados persistidos.

### Regras de Negócio e Funcionalidades:

- **Validação de CPF**: O CPF deve ser validado no momento de realizar uma transação (saque, depósito, pagamento ou Pix).
- **Verificação de Saldo**: O saldo (produto de depósitos - saques - pagamento (boleto) - Pix (só pagamento)) não pode ficar negativo. O saldo deve ser verificado antes de realizar saques, pagamentos ou Pix.
- **Limite para Pix**: Limite de R$ 300,00 para operações de Pix.
- **Limite Diário de Saques**: Limite diário de R$ 5.000,00 para saques.
- **Tarifa de Operação**:
  - R$ 5,00 para pagamentos e Pix.
  - R$ 2,00 para saques.
- **Limitação de Horário para Pix**: As operações de Pix só podem ser realizadas entre 06:00 e 22:00.
- **Alerta de Saldo Baixo**: Notificar o cliente caso o saldo fique abaixo de R$ 100,00 após uma operação.
- **Limite de Operações por Dia**: Limite máximo de 10 operações (saque, depósito, pagamento, Pix) por dia.
- **Detecção de Fraudes**: Implementar uma lógica básica de detecção de fraudes, onde o sistema analisa o padrão de gastos do cliente e, se detectar uma operação suspeita (gasto incomum muito acima da média), bloqueia a operação.
- **Consulta de Extrato**:
  - Permitir a consulta de extrato mensal.
  - Permitir a consulta de extrato periódico.

## Complemento 

### Novas Implementações:

#### 1. **Cliente e Conta**:
Implemente pelo menos mais duas classes (`Cliente` e `Conta`) que devem se relacionar entre si. Defina os atributos que você achar pertinente.

#### 2. **Limite de Contas por Cliente**:
Cada cliente pode ter no máximo 3 contas diferentes. Ao tentar criar uma nova conta para um cliente que já possui 3 contas, o sistema deve impedir a operação.

#### 3. **Análise de Crédito e Empréstimo**:
Adicionar um método para retornar um limite de crédito pré-aprovado com base no saldo médio dos últimos 3 meses da conta corrente.

#### 4. **Cálculo de Rendimento para Conta Poupança**:
Implementar um método que calcula o rendimento mensal utilizando juros compostos.

#### 5. **Cálculo de Cashback em Transações de Cartão de Débito**:
Implementar um sistema de cashback, onde **0,2%** do valor de compras feitas com cartão de débito é creditado de volta na conta do cliente no início do mês subsequente ao mês das transações.

## Diagrama de Classe

Antes de fazer a implementação, é necessário criar um diagrama de classe para ilustrar as relações entre as entidades do sistema. As classes principais são:

- **Cliente**
- **Conta**
- **Movimentacao**
  
No diagrama, a classe `Cliente` pode ter um relacionamento de um para muitos com a classe `Conta`, e a classe `Conta` pode ter um relacionamento de um para muitos com a classe `Movimentacao`. 
