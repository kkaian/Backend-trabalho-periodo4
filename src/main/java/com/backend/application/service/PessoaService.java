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

    private static final Logger logger = LoggerFactory.getLogger(PessoaService.class); // Logger para a classe
    private final PessoaRepository pessoaRepository;
    private final PasswordEncoder passwordEncoder;

    public PessoaService(PessoaRepository pessoaRepository, PasswordEncoder passwordEncoder) {
        this.pessoaRepository = pessoaRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public PessoaDTO criarPessoa(PessoaDTO pessoaDTO) {
        logger.info("Criando nova pessoa com nome: {}", pessoaDTO.getNome()); // Log de criação de pessoa

        // Criação de um novo usuário com senha e role
        Pessoa pessoa = new Pessoa();
        pessoa.setNome(pessoaDTO.getNome());
        pessoa.setEmail(pessoaDTO.getEmail());
        pessoa.setSenha(passwordEncoder.encode(pessoaDTO.getSenha())); // Codificando a senha
        pessoa.setRole(Role.USER); // Definindo o role do usuário com a enum Role (ROLE_USER)
        Pessoa pessoaSalva = pessoaRepository.save(pessoa);

        logger.info("Pessoa criada com sucesso, ID: {}", pessoaSalva.getId()); // Log após criação

        return new PessoaDTO(pessoaSalva.getId(), pessoaSalva.getNome(), pessoaSalva.getEmail(), pessoaSalva.getRole());
    }

    public List<PessoaDTO> listarPessoas() {
        logger.info("Listando todas as pessoas"); // Log de listagem de pessoas

        return pessoaRepository.findAll().stream()
                .map(pessoa -> new PessoaDTO(pessoa.getId(), pessoa.getNome(), pessoa.getEmail(), pessoa.getRole()))
                .collect(Collectors.toList());
    }

    public PessoaDTO buscarPorId(Long id) {
        logger.info("Buscando pessoa com ID: {}", id); // Log de busca por ID

        Pessoa pessoa = pessoaRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Pessoa não encontrada com ID: {}", id); // Log de erro caso a pessoa não seja encontrada
                    return new RuntimeException("Pessoa não encontrada.");
                });
        
        return new PessoaDTO(pessoa.getId(), pessoa.getNome(), pessoa.getEmail(), pessoa.getRole());
    }

    public PessoaDTO atualizarPessoa(Long id, PessoaDTO pessoaDTO) {
        logger.info("Atualizando pessoa com ID: {}", id); // Log de atualização

        Pessoa pessoa = pessoaRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Pessoa não encontrada com ID: {}", id); // Log de erro caso a pessoa não seja encontrada
                    return new RuntimeException("Pessoa não encontrada.");
                });
        pessoa.setNome(pessoaDTO.getNome());
        pessoa.setEmail(pessoaDTO.getEmail());
        pessoa.setRole(pessoaDTO.getRole()); // Atualizando o role
        Pessoa pessoaAtualizada = pessoaRepository.save(pessoa);

        logger.info("Pessoa com ID: {} atualizada com sucesso", pessoaAtualizada.getId()); // Log após atualização

        return new PessoaDTO(pessoaAtualizada.getId(), pessoaAtualizada.getNome(), pessoaAtualizada.getEmail(), pessoaAtualizada.getRole());
    }

    public void deletarPessoa(Long id) {
        logger.info("Deletando pessoa com ID: {}", id); // Log de exclusão

        Pessoa pessoa = pessoaRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Pessoa não encontrada com ID: {}", id); // Log de erro caso a pessoa não seja encontrada
                    return new RuntimeException("Pessoa não encontrada.");
                });
        pessoaRepository.delete(pessoa);

        logger.info("Pessoa com ID: {} deletada com sucesso", id); // Log após exclusão
    }
}
