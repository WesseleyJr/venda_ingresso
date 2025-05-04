package br.com.catedral.visitacao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.catedral.visitacao.dto.AlterarSenhaDTO;
import br.com.catedral.visitacao.dto.EsqueceuSenhaRequestDTO;
import br.com.catedral.visitacao.dto.TokenValidationDTO;
import br.com.catedral.visitacao.service.EsqueceuSenhaService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/senha")
public class EsqueceuSenhaController {

	@Autowired
	private EsqueceuSenhaService esqueceuSenhaService;
	
	@PostMapping("/esqueci")
	public ResponseEntity<?> solicitarRedefinicaoSenha(@RequestBody @Valid EsqueceuSenhaRequestDTO dto) {
	    esqueceuSenhaService.solicitarRedefinicaoSenha(dto.getEmail());
	    return ResponseEntity.ok("Token enviado para o e-mail informado.");
	}
    
    @PostMapping("/validar-token")
    public ResponseEntity<?> validarToken(@RequestBody TokenValidationDTO dto) {
    	esqueceuSenhaService.validarToken(dto.getToken());
        return ResponseEntity.ok("Token válido.");
    }
    
    @PostMapping("/redefinir")
    public ResponseEntity<?> redefinirSenha(@RequestBody AlterarSenhaDTO dto) {
    	esqueceuSenhaService.redefinirSenha(dto.getToken(), dto.getNovaSenha(), dto.getConfirmaSenha());
        return ResponseEntity.ok("Senha redefinida com sucesso.");
    }
    
    

}
