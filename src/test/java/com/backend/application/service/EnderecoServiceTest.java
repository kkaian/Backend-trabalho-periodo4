package com.backend.application.service;

import com.backend.application.dto.EnderecoDTO;
import com.backend.application.model.Endereco;
import com.backend.application.model.Pessoa;
import com.backend.application.repository.EnderecoRepository;
import com.backend.application.repository.PessoaRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

@SpringBootTest
public class EnderecoServiceTest {

    @Mock
    private EnderecoRepository enderecoRepository;

    @Mock
    private PessoaRepository pessoaRepository;

    @InjectMocks
    private EnderecoService enderecoService;

    // Teste para o método criarEndereco
    @Test
    public void testCriarEndereco() {
        // Mockando a pessoa
        Pessoa pessoa = new Pessoa();
        pessoa.setId(1L);

        // Mockando o retorno do repository de pessoa
        when(pessoaRepository.findById(1L)).thenReturn(java.util.Optional.of(pessoa));

        // Criando o EnderecoDTO
        EnderecoDTO enderecoDTO = new EnderecoDTO();
        enderecoDTO.setRua("Rua Teste");
        enderecoDTO.setCidade("Cidade Teste");
        enderecoDTO.setEstado("Estado Teste");
        enderecoDTO.setPessoaId(1L);

        // Mockando o retorno do repositório de Endereco
        Endereco endereco = new Endereco();
        endereco.setId(1L);
        endereco.setRua("Rua Teste");
        endereco.setCidade("Cidade Teste");
        endereco.setEstado("Estado Teste");

        when(enderecoRepository.save(any(Endereco.class))).thenReturn(endereco);

        // Chamando o método de criação
        EnderecoDTO enderecoCriado = enderecoService.criarEndereco(enderecoDTO);

        // Verificando o retorno
        assertNotNull(enderecoCriado);
        assertEquals("Rua Teste", enderecoCriado.getRua());
        assertEquals("Cidade Teste", enderecoCriado.getCidade());
        assertEquals("Estado Teste", enderecoCriado.getEstado());
        assertEquals(1L, enderecoCriado.getPessoaId());

        // Verificando se o método save foi chamado
        verify(enderecoRepository, times(1)).save(any(Endereco.class));
    }

    // Teste para o método listarEnderecos
    @Test
    public void testListarEnderecos() {
        // Mockando o repositório de endereços
        Endereco endereco1 = new Endereco(1L, "Rua Teste 1", "Cidade Teste", "Estado Teste", null);
        Endereco endereco2 = new Endereco(2L, "Rua Teste 2", "Cidade Teste", "Estado Teste", null);

        when(enderecoRepository.findAll()).thenReturn(List.of(endereco1, endereco2));

        // Chamando o método
        List<EnderecoDTO> enderecos = enderecoService.listarEnderecos();

        // Verificando o tamanho da lista
        assertEquals(2, enderecos.size());

        // Verificando o conteúdo dos endereços
        assertEquals("Rua Teste 1", enderecos.get(0).getRua());
        assertEquals("Rua Teste 2", enderecos.get(1).getRua());
    }

    // Teste para o método buscarPorId
    @Test
    public void testBuscarPorId() {
        // Mockando o repositório de endereços
        Endereco endereco = new Endereco(1L, "Rua Teste", "Cidade Teste", "Estado Teste", null);

        when(enderecoRepository.findById(1L)).thenReturn(java.util.Optional.of(endereco));

        // Chamando o método
        EnderecoDTO enderecoDTO = enderecoService.buscarPorId(1L);

        // Verificando os valores
        assertNotNull(enderecoDTO);
        assertEquals(1L, enderecoDTO.getId());
        assertEquals("Rua Teste", enderecoDTO.getRua());
    }

    // Teste para o método buscarPorId (endereço não encontrado)
    @Test
    public void testBuscarPorIdEnderecoNaoEncontrado() {
        // Mockando o repositório para retornar vazio
        when(enderecoRepository.findById(999L)).thenReturn(java.util.Optional.empty());

        // Chamando o método e verificando a exceção
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            enderecoService.buscarPorId(999L);
        });

        assertEquals("Endereço não encontrado.", exception.getMessage());
    }

    // Teste para o método buscarPorCidade
    @Test
    public void testBuscarPorCidade() {
        // Mockando os endereços na cidade
        Endereco endereco1 = new Endereco(1L, "Rua Teste 1", "Cidade Teste", "Estado Teste", null);
        Endereco endereco2 = new Endereco(2L, "Rua Teste 2", "Cidade Teste", "Estado Teste", null);

        when(enderecoRepository.findByCidade("Cidade Teste")).thenReturn(List.of(endereco1, endereco2));

        // Chamando o método
        List<EnderecoDTO> enderecos = enderecoService.buscarPorCidade("Cidade Teste");

        // Verificando o tamanho da lista
        assertEquals(2, enderecos.size());
    }

    // Teste para o método buscarPorCidade (endereço não encontrado)
    @Test
    public void testBuscarPorCidadeEnderecoNaoEncontrado() {
        // Mockando o repositório para retornar vazio
        when(enderecoRepository.findByCidade("Cidade Desconhecida")).thenReturn(List.of());

        // Chamando o método
        List<EnderecoDTO> enderecos = enderecoService.buscarPorCidade("Cidade Desconhecida");

        // Verificando se a lista está vazia
        assertTrue(enderecos.isEmpty());
    }
}
