### Objetivo da aplicação

Essa aplicação é um micro-serviço construído com Java 21 e SpringBoot 3 que tem como principal objetivo orquestrar
e emitir a nota fiscal a partir de um pedido. Para a geração da nota fiscal é necessário realizar um cálculo que varia
conforme a alíquota definida a partir da faixa de valor dos itens fornecidos e do tipo de pessoa (física ou jurídica).
Ademais, essa peça é responsável por fazer a comunicação com outros serviços de geração de entrega, criação e persistência
da nota fiscal, além da baixa de produto no estoque.

# Arquitetura

O projeto utiliza **Arquitetura Hexagonal (Ports and Adapters)** com o objetivo de manter as regras de negócio e os 
casos de uso independentes de componentes externos. Essa comunicação entre o micro-serviço e aplicações externas
é realizada por meio de **Ports (contratos)**, enquanto os **Adapters** são responsáveis pelas implementações concretas 
dessa comunicação.

---

## Estrutura do projeto

```text
src/main/java/br/com/itau/geradornotafiscal

├── adapter
│   ├── in
│   └── out
│
├── application
│   ├── port
│   │   ├── in
│   │   └── out
│   │
│   ├── service
│   └── usecases
│
├── domain
│   ├── constant
│   ├── model
│   │   └── enums
│   └── strategy
│
└── exceptions
```
## Responsabilidade de cada camada

| Camada                 | Responsabilidade                             |
| ---------------------- | -------------------------------------------- |
| `adapter/in`            | Receber informações de outras aplicaçõe        |
| `application/port/in`    | Definir os contratos de entrada da aplicação |
| `application/usecases` | Orquestrar outros serviços                   |
| `application/service` | Centralizar operações de aplicação            |
| `domain`              | Concentrar regras e conceitos de negócio (cálculos)     |
| `application/port/out` | Definir contratos para dependências externas |
| `adapter/out`         | Implementar de fato as integrações com peças externas |
| `exceptions`           | Organização e centralização de erros   |