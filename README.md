# 🚀 PlaceMe API

Backend de uma API REST desenvolvida em Java com Spring Boot para uma plataforma de gerenciamento e reserva de propriedades.

## 🎯 Sobre o Projeto

O **Place-me** é um projeto de API RESTful que simula um sistema de "aluguel por temporada" ou reserva de locais. O sistema permite o cadastro de usuários (proprietários e clientes), o cadastro de suas propriedades e a realização de reservas.

## ✨ Features Principais

* **Gerenciamento de Usuários:** CRUD completo de usuários.
* **Gerenciamento de Propriedades:** CRUD de propriedades associadas a um usuário.
* **Sistema de Reservas:** Lógica para criação e gerenciamento de reservas.
* **Validação:** Validação de entrada de dados (Bean Validation).
* **Mapeamento de DTOs:** Uso do MapStruct para conversões limpas e performáticas entre Entidades e DTOs.

## 🛠️ Tecnologias Utilizadas

* **Java 17**
* **Spring Boot 3**
* **Spring Data JPA** (Persistência de dados)
* **PostgreSQL** (Banco de dados relacional)
* **Spring Security & OAuth2 Resource Server** (Segurança e Autenticação)
* **OpenFeign** (Cliente HTTP declarativo para integração com APIs externas)
* **MapStruct** (Mapeamento performático entre Entidades e DTOs)
* **Bean Validation** (Validação de dados de entrada)
* **Lombok** (Redução de código boilerplate)
* **Maven** (Gerenciamento de dependências)
