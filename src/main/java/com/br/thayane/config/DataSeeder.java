package com.br.thayane.config;

import com.br.thayane.entity.*;
import com.br.thayane.repository.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/** Cria o administrador inicial (via variáveis de ambiente) e conteúdo de demonstração se o banco estiver vazio. */
@Component @RequiredArgsConstructor @Slf4j
public class DataSeeder implements CommandLineRunner {
    private final UsuarioRepository usuarios; private final ServicoRepository servicos;
    private final PostRepository posts; private final TrabalhoRepository trabalhos;
    private final ContatoRepository contatos; private final PasswordEncoder encoder;
    @Value("${app.admin.nome}") private String adminNome;
    @Value("${app.admin.email}") private String adminEmail;
    @Value("${app.admin.senha}") private String adminSenha;

    @Override
    public void run(String... args) {
        if (usuarios.count() == 0) {
            if (adminEmail.isBlank() || adminSenha.length() < 8) {
                log.warn("Nenhum administrador criado: defina ADMIN_EMAIL e ADMIN_PASSWORD (mín. 8 caracteres) e reinicie.");
            } else {
                Usuario u = new Usuario();
                u.setNome(adminNome); u.setEmail(adminEmail.trim()); u.setSenha(encoder.encode(adminSenha));
                usuarios.save(u);
                log.info("Administrador criado: {}", adminEmail);
            }
        }
        if (contatos.count() == 0) {
            Contato c = new Contato();
            c.setEmail("alcantarathayaneluize@gmail.com"); c.setTelefone("+55 83 9830-1634");
            c.setWhatsapp("+55 83 9830-1634"); c.setInstagram("thayaneluize.nutri"); c.setCrn("49735");
            contatos.save(c);
        }
        if (servicos.count() == 0) {
            servicos.save(servico("Consulta Nutricional", "Primeiro atendimento com anamnese completa e plano alimentar individualizado.", "180.00",
                "Anamnese detalhada\nPlano alimentar personalizado\nOrientações para a rotina"));
            servicos.save(servico("Avaliação Nutricional", "Avaliação do estado nutricional e da composição corporal.", "150.00",
                "Medidas e antropometria\nAnálise de hábitos\nRelatório com metas"));
            servicos.save(servico("Retorno", "Reavaliação da evolução e ajustes no plano alimentar.", "100.00",
                "Análise da evolução\nAjustes no plano\nTira-dúvidas"));
            servicos.save(servico("Acompanhamento Mensal", "Suporte contínuo durante o mês para manter a constância.", "350.00",
                "Consultas de retorno\nSuporte entre as consultas\nAjustes sempre que preciso"));
        }
        if (trabalhos.count() == 0) {
            trabalhos.save(trabalho("Congresso Conecta · CRN-6", "Participação em evento de atualização profissional em nutrição.", "Eventos", "/images/trabalho-1.jpg"));
            trabalhos.save(trabalho("Atividade prática de campo", "Vivência prática em boas práticas e segurança dos alimentos.", "Formação", "/images/trabalho-2.jpg"));
            trabalhos.save(trabalho("Estágio em ambiente clínico", "Experiência de atuação no cuidado nutricional em serviço de saúde.", "Clínica", "/images/trabalho-3.jpg"));
        }
        if (posts.count() == 0) {
            posts.save(post("Alimentação saudável começa no planejamento", "Organizar a semana facilita escolhas melhores e reduz o improviso na hora da fome.",
                "Alimentação saudável", "/images/blog-1.svg", LocalDate.now().minusDays(3),
                "Comer bem não exige receitas complicadas. Reserve um momento da semana para pensar nas refeições e fazer uma lista de compras com alimentos in natura.\n\nTer frutas, verduras e proteínas já preparadas ajuda a manter a constância nos dias corridos.\n\nCada pessoa tem necessidades diferentes: um plano individualizado, feito com acompanhamento profissional, torna o processo mais leve."));
            posts.save(post("A importância da hidratação", "A água participa de funções essenciais do corpo, e pequenos lembretes ajudam a beber mais ao longo do dia.",
                "Hidratação", "/images/blog-2.svg", LocalDate.now().minusDays(10),
                "A água participa da regulação da temperatura, da digestão e do transporte de nutrientes.\n\nPara criar o hábito, mantenha uma garrafa por perto e associe os goles a momentos da rotina, como ao acordar e antes das refeições.\n\nA quantidade ideal varia com idade, clima e atividade física. Converse com o seu nutricionista."));
            posts.save(post("Como montar um prato equilibrado", "Um prato colorido, com variedade e porções adequadas, é um bom ponto de partida.",
                "Prato equilibrado", "/images/blog-3.svg", LocalDate.now().minusDays(17),
                "Uma referência prática: metade do prato com verduras e legumes, uma parte com fonte de proteína e outra com carboidratos de boa qualidade, como arroz, feijão e tubérculos.\n\nQuanto mais colorido, maior a variedade de nutrientes. Coma com calma e atenção aos sinais de fome e saciedade."));
            posts.save(post("Alimentação e qualidade de vida", "O que comemos influencia energia, humor, sono e disposição no dia a dia.",
                "Qualidade de vida", "/images/blog-4.svg", LocalDate.now().minusDays(24),
                "Alimentação equilibrada é um dos pilares do bem-estar, junto com sono de qualidade, atividade física e cuidado emocional.\n\nMudanças pequenas e sustentáveis costumam funcionar melhor do que dietas rígidas e passageiras."));
            posts.save(post("Hábitos alimentares: por onde começar?", "Mudar hábitos é um processo. Comece por um passo possível e avance aos poucos.",
                "Hábitos alimentares", "/images/blog-5.svg", LocalDate.now().minusDays(31),
                "Escolha um hábito para trabalhar por vez: incluir uma fruta ao dia, reduzir ultraprocessados ou fazer as refeições sem telas.\n\nCelebre o progresso e seja gentil com os dias difíceis. O acompanhamento nutricional ajuda a adaptar as metas à sua realidade."));
        }
    }

    private Servico servico(String n, String d, String p, String b) {
        Servico s = new Servico(); s.setNome(n); s.setDescricao(d); s.setPreco(new BigDecimal(p)); s.setBeneficios(b); return s;
    }
    private Trabalho trabalho(String t, String d, String c, String img) {
        Trabalho x = new Trabalho(); x.setTitulo(t); x.setDescricao(d); x.setCategoria(c); x.setImagemUrl(img); return x;
    }
    private Post post(String t, String r, String c, String img, LocalDate dt, String txt) {
        Post p = new Post(); p.setTitulo(t); p.setResumo(r); p.setCategoria(c); p.setImagemUrl(img); p.setDataPublicacao(dt); p.setConteudo(txt); return p;
    }
}
