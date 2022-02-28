package com.example.aplication.repository;

import com.example.aplication.model.Pessoa;
import com.example.aplication.repository.filtro.PessoaFiltro;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Sql(value = "/load-database.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = "/clean-database.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@ExtendWith(SpringExtension.class)
@DataJpaTest
@TestPropertySource("classpath:application-test.properties")
public class PessoaRepositoryTest {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Test
    void deve_procurar_pessoa_pelo_cpf() {
        Optional<Pessoa> optional = pessoaRepository.findByCpf("87247434023");

        assertThat(optional.isPresent()).isTrue();
        Pessoa pessoa = optional.get();
        assertThat(pessoa.getId()).isEqualTo(4L);
        assertThat(pessoa.getNome()).isEqualTo("Maurício");
        assertThat(pessoa.getCpf()).isEqualTo("87247434023");
    }

    @Test
    void nao_deve_encontrar_pessoa_de_cpf_inexistente() {
        Optional<Pessoa> optional = pessoaRepository.findByCpf("4894534774991");

        assertThat(optional.isPresent()).isFalse();
    }

    @Test
    void deve_encontrar_pessoa_pelo_ddd_e_numero_de_telefone() {
        Optional<Pessoa> optional = pessoaRepository.findByTelefoneDddAndTelefoneNumero("91", "975923309");

        assertThat(optional.isPresent()).isTrue();
        Pessoa pessoa = optional.get();
        assertThat(pessoa.getId()).isEqualTo(4L);
        assertThat(pessoa.getNome()).isEqualTo("Maurício");
        assertThat(pessoa.getCpf()).isEqualTo("87247434023");
    }

    @Test
    void nao_deve_encontrar_pessoa_cujo_ddd_e_telefone_nao_estejam_cadastrados() {
        Optional<Pessoa> optional = pessoaRepository.findByTelefoneDddAndTelefoneNumero("91", "889849442");

        assertThat(optional.isPresent()).isFalse();
    }

    @Test
    void deve_filtrar_pessoas_por_parte_do_nome() {
        PessoaFiltro filtro = new PessoaFiltro();
        filtro.setNome("a");

        List<Pessoa> pessoas = pessoaRepository.filtrar(filtro);

        assertThat(pessoas.size()).isEqualTo(3);
    }

    @Test
    void deve_filtrar_pessoas_por_parte_do_cpf() {
        PessoaFiltro filtro = new PessoaFiltro();
        filtro.setCpf("93");

        List<Pessoa> pessoas = pessoaRepository.filtrar(filtro);

        assertThat(pessoas.size()).isEqualTo(2);
    }

    @Test
    void deve_filtrar_pessoas_por_filtro_composto() {
        PessoaFiltro filtro = new PessoaFiltro();
        filtro.setNome("a");
        filtro.setCpf("84");

        List<Pessoa> pessoas = pessoaRepository.filtrar(filtro);

        assertThat(pessoas.size()).isEqualTo(1);
    }

    @Test
    void deve_filtrar_pessoas_pelo_ddd_do_telefone() {
        PessoaFiltro filtro = new PessoaFiltro();
        filtro.setDdd("91");

        List<Pessoa> pessoas = pessoaRepository.filtrar(filtro);

        assertThat(pessoas.size()).isEqualTo(5);
    }

    @Test
    void deve_filtrar_pessoas_pelo_numero_do_telefone() {
        PessoaFiltro filtro = new PessoaFiltro();
        filtro.setTelefone("982431444");

        List<Pessoa> pessoas = pessoaRepository.filtrar(filtro);

        assertThat(pessoas.size()).isEqualTo(1);
    }

}
