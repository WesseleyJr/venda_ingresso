package br.com.catedral.visitacao.service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.catedral.visitacao.exception.EmailException;
import br.com.catedral.visitacao.exception.SenhaException;
import br.com.catedral.visitacao.model.EsqueceuSenhaToken;
import br.com.catedral.visitacao.model.Usuario;
import br.com.catedral.visitacao.repository.EsqueceuSenhaTokenRepository;
import br.com.catedral.visitacao.repository.UsuarioRepository;

@Service

public class EsqueceuSenhaService {
	
	@Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EsqueceuSenhaTokenRepository tokenRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private BCryptPasswordEncoder encoder;

    public void solicitarRedefinicaoSenha(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email);
        if (usuario == null) {
            throw new EmailException("Email não cadastrado");
        }

        String token = String.format("%06d", new Random().nextInt(1_000_000));

        EsqueceuSenhaToken resetToken = tokenRepository.findByUsuario(usuario)
            .orElse(new EsqueceuSenhaToken());

        resetToken.setToken(token);
        resetToken.setUsuario(usuario);
        resetToken.setExpirationDate(LocalDateTime.now().plusMinutes(15));

        tokenRepository.save(resetToken);

        emailService.emailRecuperacaoSenha(usuario, token);
    }

    public void validarToken(String token) {
    	EsqueceuSenhaToken resetToken = tokenRepository.findByToken(token);
        if (resetToken == null || resetToken.getExpirationDate().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Token inválido ou expirado");
        }
    }

    public void redefinirSenha(String token, String novaSenha, String confirmaSenha) {
        if (!novaSenha.equals(confirmaSenha)) {
            throw new SenhaException("Senhas não conferem");
        }

        EsqueceuSenhaToken resetToken = tokenRepository.findByToken(token);
        if (resetToken == null || resetToken.getExpirationDate().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Token inválido ou expirado");
        }

        Usuario usuario = resetToken.getUsuario();
        usuario.setSenha(encoder.encode(novaSenha));
        usuarioRepository.save(usuario);

        tokenRepository.delete(resetToken);
    }
    

}
