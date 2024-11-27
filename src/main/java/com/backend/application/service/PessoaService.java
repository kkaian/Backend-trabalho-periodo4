package com.backend.application.service;

import com.backend.application.dto.PessoaDTO;
import com.backend.application.model.Pessoa;
import com.backend.application.model.Role; // Importando o Role
import com.backend.application.repository.PessoaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PessoaService {

    private static final Logger logger = LoggerFactory.getLogger(PessoaService.class); 
    private final PessoaRepository pessoaRepository;
    private final PasswordEncoder passwordEncoder;

    public PessoaService(PessoaRepository pessoaRepository, PasswordEncoder passwordEncoder) {
        this.pessoaRepository = pessoaRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public PessoaDTO criarPessoa(PessoaDTO pessoaDTO) {
        logger.info("Criando nova pessoa com nome: {}", pessoaDTO.getNome()); 

        
        Pessoa pessoa = new Pessoa();
        pessoa.setNome(pessoaDTO.getNome());
        pessoa.setEmail(pessoaDTO.getEmail());
        pessoa.setSenha(passwordEncoder.encode(pessoaDTO.getSenha())); 
        pessoa.setRole(Role.USER); 
        Pessoa pessoaSalva = pessoaRepository.save(pessoa);

        logger.info("Pessoa criada com sucesso, ID: {}", pessoaSalva.getId()); 

        return new PessoaDTO(pessoaSalva.getId(), pessoaSalva.getNome(), pessoaSalva.getEmail(), pessoaSalva.getRole());
    }

    public List<PessoaDTO> listarPessoas() {
        logger.info("Listando todas as pessoas");

        return pessoaRepository.findAll().stream()
                .map(pessoa -> new PessoaDTO(pessoa.getId(), pessoa.getNome(), pessoa.getEmail(), pessoa.getRole()))
                .collect(Collectors.toList());
    }

    public PessoaDTO buscarPorId(Long id) {
        logger.info("Buscando pessoa com ID: {}", id); 

        Pessoa pessoa = pessoaRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Pessoa não encontrada com ID: {}", id); 
                    return new RuntimeException("Pessoa não encontrada.");
                });
        
        return new PessoaDTO(pessoa.getId(), pessoa.getNome(), pessoa.getEmail(), pessoa.getRole());
    }

    public PessoaDTO atualizarPessoa(Long id, PessoaDTO pessoaDTO) {
        logger.info("Atualizando pessoa com ID: {}", id); 

        Pessoa pessoa = pessoaRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Pessoa não encontrada com ID: {}", id); 
                    return new RuntimeException("Pessoa não encontrada.");
                });
        pessoa.setNome(pessoaDTO.getNome());
        pessoa.setEmail(pessoaDTO.getEmail());
        pessoa.setRole(pessoaDTO.getRole());
        Pessoa pessoaAtualizada = pessoaRepository.save(pessoa);

        logger.info("Pessoa com ID: {} atualizada com sucesso", pessoaAtualizada.getId()); 

        return new PessoaDTO(pessoaAtualizada.getId(), pessoaAtualizada.getNome(), pessoaAtualizada.getEmail(), pessoaAtualizada.getRole());
    }

    public void deletarPessoa(Long id) {
        logger.info("Deletando pessoa com ID: {}", id);

        Pessoa pessoa = pessoaRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Pessoa não encontrada com ID: {}", id); 
                    return new RuntimeException("Pessoa não encontrada.");
                });
        pessoaRepository.delete(pessoa);

        logger.info("Pessoa com ID: {} deletada com sucesso", id);
    }
}
