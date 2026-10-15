# Thayane-Nutri

Portfólio profissional da nutricionista Thayane Luíze Lopes de Souza Alcântara (CRN 49735).

Site público em página única (Início, Agendamento, Contato, Preços com carrossel 3D e Blog) + painel administrativo protegido por login.
Stack: Java 17, Spring Boot 3, Spring MVC, Spring Data JPA, Spring Security, Bean Validation, Thymeleaf, PostgreSQL, Maven.

## 1. Pré-requisitos
- JDK 17+
- Maven 3.9+
- PostgreSQL 14+ (ou Docker)

## 2. Banco de dados
```bash
psql -U postgres -c "CREATE DATABASE thayane_db;"
```
Alternativa com Docker: `docker compose up -d` (usa `DB_PASSWORD` do `.env`).
As tabelas são criadas automaticamente na primeira execução (`ddl-auto: update`).

## 3. Variáveis de ambiente
```bash
cp .env.example .env     # edite e preencha (o .env está no .gitignore)
```
| Variável | Descrição |
|---|---|
| `DB_PASSWORD` | senha do PostgreSQL (**obrigatória**) |
| `DB_HOST`, `DB_PORT`, `DB_NAME`, `DB_USERNAME` | opcionais (padrão: localhost, 5432, thayane_db, postgres) |
| `MAIL_HOST`, `MAIL_PORT`, `MAIL_USERNAME`, `MAIL_PASSWORD` | SMTP para o formulário de agendamento |
| `MAIL_DESTINO` | e-mail que recebe as solicitações |
| `ADMIN_EMAIL`, `ADMIN_PASSWORD` | cria o primeiro administrador (mín. 8 caracteres) |

A aplicação lê o arquivo `.env` automaticamente (ou use variáveis de ambiente do sistema). **Nunca** versione senhas.

## 4. Executar
```bash
mvn spring-boot:run
```
Acesse http://localhost:8080

## 5. Primeiro administrador
Na **primeira execução**, se não existir nenhum usuário, o sistema cria o administrador com `ADMIN_EMAIL` e `ADMIN_PASSWORD`
(a senha é salva com BCrypt). Depois disso, pode remover `ADMIN_PASSWORD` do `.env`.

## 6. Painel administrativo
Acesse `/admin/login` com o e-mail e a senha definidos. Menu lateral:
- **Preços** (`/admin/precos`): criar, editar, excluir, ativar/desativar. O site público reflete a alteração na hora. Benefícios: um por linha.
- **Trabalhos** (`/admin/trabalhos`): título, descrição, categoria e imagem.
- **Blog** (`/admin/posts`): título, resumo, texto, imagem, categoria, data e publicação.
- **Configurações** (`/admin/configuracoes`): e-mail, WhatsApp, Instagram, CRN e redes sociais adicionais.

**Imagens:** informe um caminho (ex.: `/images/minha-foto.jpg`, arquivo salvo em `src/main/resources/static/images/`) ou um link `https://...`.

## 7. E-mail de agendamento (Gmail)
1. Ative a verificação em duas etapas na conta Google.
2. Crie uma **senha de app** em https://myaccount.google.com/apppasswords
3. No `.env`: `MAIL_HOST=smtp.gmail.com`, `MAIL_PORT=587`, `MAIL_USERNAME=seu@gmail.com`, `MAIL_PASSWORD=<senha de app>`.
Se o SMTP não estiver configurado ou falhar, o visitante vê uma mensagem amigável e o erro é registrado no log.

## 8. Segurança
BCrypt; `/admin/**` só para ADMIN; CSRF ativo (inclusive no formulário público via cabeçalho); Thymeleaf escapa todo conteúdo (XSS);
cabeçalhos CSP/X-Frame-Options; JPA com consultas parametrizadas; validação no front e no back; tratamento global de exceções.

## 9. Estrutura
```
src/main/java/com/br/thayane/{controller,service,repository,entity,dto,config,security,exception}
src/main/resources/{static/{css,js,images,icons},templates/{index,post,legal,error,admin/*,fragments/*}}
```

## 10. Deploy
`mvn clean package` gera `target/thayane-nutri-1.0.0.jar`; rode com `java -jar` definindo as variáveis de ambiente. Use HTTPS em produção.
