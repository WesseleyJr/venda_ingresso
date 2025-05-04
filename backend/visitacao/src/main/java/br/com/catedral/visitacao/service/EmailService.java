package br.com.catedral.visitacao.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import br.com.catedral.visitacao.model.Usuario;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Component
public class EmailService {

	@Autowired
	public JavaMailSender javaMailSender;

	@Autowired
	private SpringTemplateEngine templateEngine;

	@Value("${spring.mail.username}")
	private String emailUsername;

	@Value("${spring.mail.host}")
	private String emailServerHost;

	@Value("${spring.mail.port}")
	private Integer emailServerPort;

	@Value("${spring.mail.password}")
	private String emailPassword;

	private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

	private String generateHtmlCadastro(String nomeCliente) {
		
		Context context = new Context();
		context.setVariable("nomeCliente", nomeCliente);
		
		return templateEngine.process("BoasVindasCadastro", context);
	}

	@Async
	public CompletableFuture<String> emailCadastro(Usuario novoUsuario) throws IOException {
		LocalDateTime time = LocalDateTime.now();
		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

		MimeMessage message = javaMailSender.createMimeMessage();

		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setSubject("Cadastro realizado com Sucesso! - " + time.format(format));
			helper.setFrom("Catedral São Pedro de Alcântara <" + emailUsername + ">");
			helper.setTo(novoUsuario.getEmail());

			String htmlContent = generateHtmlCadastro(novoUsuario.getNome());

			helper.setText(htmlContent, true);
			javaMailSender.send(message);
			return CompletableFuture.completedFuture("Email enviado com sucesso");
		} catch (MessagingException e) {
			return CompletableFuture.completedFuture("Erro ao enviar email\n\n" + e.getMessage());
		}
	}


	private String generateHtmlRecuperacaoSenha(String nomeCliente, String token) {
	    Context context = new Context();
	    context.setVariable("nomeCliente", nomeCliente);
	    context.setVariable("token", token);
	    return templateEngine.process("EsqueceuSenhaToken", context);
	}


	@Async
	public CompletableFuture<String> emailRecuperacaoSenha(Usuario usuario, String token) {
	    MimeMessage message = javaMailSender.createMimeMessage();

	    try {
	        MimeMessageHelper helper = new MimeMessageHelper(message, true);
	        helper.setSubject("Recuperação de Senha para " + usuario.getNome());
	        helper.setFrom("Catedral São Pedro de Alcântara <" + emailUsername + ">");
	        helper.setTo(usuario.getEmail());

	        String htmlContent = generateHtmlRecuperacaoSenha(usuario.getNome(), token);
	        helper.setText(htmlContent, true);
	        javaMailSender.send(message);
	    } catch (MailException | MessagingException e) {
	        e.printStackTrace();
	    }
	    return CompletableFuture.completedFuture("email enviado com sucesso");
	}

}
