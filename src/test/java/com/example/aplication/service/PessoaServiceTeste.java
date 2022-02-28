package com.example.aplication.service;

import com.example.aplication.exception.CpfExistenteException;
import com.example.aplication.exception.TelefoneExistenteException;
import com.example.aplication.exception.TelefoneNaoEncontradoException;
import com.example.aplication.model.Pessoa;
import com.example.aplication.model.Telefone;
import com.example.aplication.repository.PessoaRepository;
import com.example.aplication.service.impl.PessoaServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class PessoaServiceTeste {

    private static final String NOME = "Mateus Braz";
    private static final String CPF = "123456789";
    private static final String DDD = "91";
    private static final String NUMERO = "998765432";

    @MockBean
    private PessoaRepository pessoaRepository;

    private PessoaService pessoaService;

    private Pessoa pessoa;

    private Telefone telefone;

    @BeforeEach
    void setUp() {
        pessoaService = new PessoaServiceImpl(pessoaRepository);

        pessoa = new Pessoa();
        pessoa.setNome(NOME);
        pessoa.setCpf(CPF);

        telefone = new Telefone();
        telefone.setDdd(DDD);
        telefone.setNumero(NUMERO);

        pessoa.setTelefones(Arrays.asList(telefone));

        when(pessoaRepository.findByCpf(CPF)).thenReturn(Optional.empty());
        when(pessoaRepository.findByTelefoneDddAndTelefoneNumero(DDD, NUMERO)).thenReturn(Optional.empty());
    }

    @Test
    void deve_salvar_pessoa_no_repositorio() {
        pessoaService.salvar(pessoa);

        verify(pessoaRepository).save(pessoa);
    }

    @Test
    void nao_deve_salvar_duas_pessoas_com_o_mesmo_cpf() {
        when(pessoaRepository.findByCpf(CPF)).thenReturn(Optional.of(pessoa));

        CpfExistenteException exception = assertThrows(CpfExistenteException.class, () -> pessoaService.salvar(pessoa));

        assertTrue(exception.getMessage().equals(String.format("Já existe pessoa cadastrada com o CPF '%s'", CPF)));
    }

    @Test
    void nao_deve_salvar_duas_pessoa_com_o_mesmo_telefone() {
        when(pessoaRepository.findByTelefoneDddAndTelefoneNumero(DDD, NUMERO)).thenReturn(Optional.of(pessoa));

        TelefoneExistenteException exception = assertThrows(TelefoneExistenteException.class, () -> pessoaService.salvar(pessoa));
        assertTrue(exception.getMessage().equals(String.format("Já existe pessoa cadastrada com o telefone (%s)%s", DDD, NUMERO)));
    }

    @Test
    void deve_retornar_excecao_de_nao_encontrato_quando_nao_existir_pessoa_com_o_ddd_e_numero_de_telefone() {
        when(pessoaRepository.findByTelefoneDddAndTelefoneNumero(DDD, NUMERO)).thenReturn(Optional.empty());

        TelefoneNaoEncontradoException exception = assertThrows(TelefoneNaoEncontradoException.class, () -> pessoaService.buscarPorTelefone(telefone));

        assertTrue(exception.getMessage().equals(String.format("Não existe pessoa com o telefone (%s)%s", DDD, NUMERO)));
    }

    @Test
    void deve_procurar_pessoa_pelo_ddd_e_numero_do_telefone() {
        when(pessoaRepository.findByTelefoneDddAndTelefoneNumero(DDD, NUMERO)).thenReturn(Optional.of(pessoa));

        Pessoa pessoaTeste = pessoaService.buscarPorTelefone(telefone);

        verify(pessoaRepository).findByTelefoneDddAndTelefoneNumero(DDD, NUMERO);

        assertThat(pessoaTeste).isNotNull();
        assertThat(pessoaTeste.getNome()).isEqualTo(NOME);
        assertThat(pessoaTeste.getCpf()).isEqualTo(CPF);
    }

}
