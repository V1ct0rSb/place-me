# 🏠 PlaceMe API

## 📖 Sobre o Projeto

O **Place Me** é um sistema backend robusto que simula o funcionamento de plataformas de aluguel de temporada (como Airbnb ou Booking). O sistema gerencia todo o ciclo de vida de uma locação: desde o cadastro do usuário e da propriedade (com preenchimento automático de endereço), passando pela solicitação de reserva, até a confirmação do pagamento.

O projeto foi construído seguindo as melhores práticas de desenvolvimento, utilizando DTOs (Records), Mappers e cobertura de testes unitários.

## 🚀 Tecnologias Utilizadas

* **Java 17**: Linguagem base utilizando recursos modernos como `Records`.
* **Spring Boot 3**: Framework principal.
* **Spring Data JPA**: Persistência de dados.
* **Spring Security & OAuth2 Resource Server**: Autenticação e autorização via JWT (Assinatura assimétrica com chaves RSA).
* **PostgreSQL**: Banco de dados relacional.
* **OpenFeign**: Cliente HTTP declarativo para integração com a API do **ViaCep**.
* **MapStruct**: Mapeamento eficiente entre Entidades e DTOs.
* **Lombok**: Redução de código boilerplate.
* **Flyway / Hibernate**: Gerenciamento de schema (DDL auto).
* **JUnit 5 & Mockito**: Testes unitários para garantir a qualidade do código.

## ✨ Funcionalidades

### 👤 Usuários
* Cadastro de usuários (Hóspedes e Anfitriões).
* Autenticação segura com criptografia de senha (BCrypt) e tokens JWT.
* CRUD completo de usuários.

### 🏡 Propriedades
* Cadastro de propriedades com valores, descrição e tipo.
* **Integração ViaCep**: Busca automática de endereço (Logradouro, Bairro, UF, etc.) ao informar o CEP.
* Validação de CEP.

### 📅 Reservas
* Cálculo automático do valor total baseado na quantidade de diárias (`ChronoUnit.DAYS`).
* Validação de disponibilidade da propriedade.
* Geração automática de uma intenção de pagamento ao criar a reserva.

### 💸 Pagamentos
* Processamento transacional de pagamentos.
* Mudança de status em cascata: Ao confirmar o pagamento, a Reserva é aceita e a Propriedade tem sua disponibilidade atualizada.
* Possibilidade de desistência/cancelamento, liberando a propriedade novamente.

## 🏗️ Arquitetura e Design

O projeto segue uma arquitetura em camadas bem definida:
1.  **Controller**: Exposição dos endpoints REST.
2.  **Service**: Regras de negócio (Cálculos, Validações, Fluxos de status).
3.  **Repository**: Acesso ao banco de dados.
4.  **Clients**: Integrações externas (ViaCep).

## 🧪 Testes

O projeto conta com testes unitários utilizando **JUnit 5** e **Mockito**, cobrindo cenários de sucesso e exceções.

Exemplos de cenários testados:
* Criação e edição de usuários.
* Busca de dados de endereço via Mock do ViaCep.
* Exceções ao tentar editar usuários inexistentes.
