Gerenciamento de Conta Bancária
Implemente um sistema de gerenciamento de contas bancárias utilizando os conceitos de Programação Orientada a Objetos (POO) e persistência de dados. O objetivo é modelar uma classe Conta com diversas regras de negócio e garantir que os dados das contas e operações sejam persistidos em um banco de dados.
Especificações:
Classe Movimentacao:
Atributos:
nomeCorrentista: Nome do cliente.
cpfCorrentista: CPF do cliente (deve ser validado).
tipoTransacao: Tipo de operação realizada (saque, depósito, pagamento ou Pix).
descricao: Descrição da operação (pode ser alterada manualmente ou gerada automaticamente).
dataTransacao: Data da operação realizada.
valorOperacao: Valor da operação.
outros que você achar relevante.

Persistência de Dados:
As informações de cada conta e as operações realizadas devem ser armazenadas de forma persistente (em banco de dados). O sistema deve permitir salvar, carregar e consultar dados persistidos.

Regras de Negócio e funcionalidades:
Validar o CPF no momento de fazer uma transação (saque, depósito, pagamento ou Pix).
O saldo (produto de depósitos - saques - pagamento (boleto) - pix (só pagamento)) não pode ficar negativo. Verificar o saldo antes de fazer um saque, pagamento ou pix.
Limite de R$ 300,00 para operações de pix.
Limite diário de saques de R$ 5.000,00.
Tarifa de Operação: R$ 5,00 para pagamentos e pix, R$ 2,00 para saques.
As operações de Pix só podem ser realizadas entre 06:00 e 22:00.
Alerta de saldo baixo: Notificar o cliente se o saldo ficar abaixo de R$ 100,00 após uma operação.
Limite de Operações por Dia: Definir um limite máximo de 10 operações (saque, depósito, pagamento, Pix) por dia.
Detecção de Fraudes: Implementar uma lógica básica de detecção de fraudes, onde o sistema analisa o padrão de gastos do cliente e, se detectar uma operação suspeita (gasto incomum muito acima da média), bloqueia a operação.
Permitir a consulta de extrato mensal.
Permitir a consulta de extrato periódico.

