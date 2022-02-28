package com.example.aplication.controller;

import com.example.aplication.ApplicationTests;
import com.example.aplication.model.Pessoa;
import com.example.aplication.model.Telefone;
import com.example.aplication.repository.filtro.PessoaFiltro;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.Arrays;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;

public class PessoaControllerTest extends ApplicationTests {

    @Test
    void deve_procurar_pessoa_pelo_ddd_e_numero_de_telefone() {
        given()
                .pathParam("ddd", "91")
                .pathParam("numero", "975923309")
                .get("/pessoas/{ddd}/{numero}")
                .then()
                .log().body().and()
                .statusCode(HttpStatus.OK.value())
                .body("id", equalTo(4),
                        "nome", equalTo("Maurício"),
                        "cpf", equalTo("87247434023"));
    }

    @Test
    void deve_retornar_erro_nao_encontrato_quando_buscar_pessoa_por_telefone_inexistente() {
        given()
                .pathParam("ddd", "11")
                .pathParam("numero", "995784126")
                .get("/pessoas/{ddd}/{numero}")
                .then()
                .log().body().and()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body("erro", equalTo("Não existe pessoa com o telefone (11)995784126"));
    }

    @Test
    void deve_salvar_nova_pessoa_no_sistema() {
        final Pessoa pessoa = new Pessoa();
        pessoa.setNome("Edson");
        pessoa.setCpf("50313675716");

        final Telefone telefone = new Telefone();
        telefone.setDdd("91");
        telefone.setNumero("982147896");

        pessoa.setTelefones(Arrays.asList(telefone));

        given()
                .request()
                .header("Accept", ContentType.ANY)
                .header("Content-type", ContentType.JSON)
                .body(pessoa)
                .when()
                .post("/pessoas")
                .then()
                .log().headers()
                .and()
                .log().body()
                .and()
                .statusCode(HttpStatus.CREATED.value())
                .header("Location", equalTo("http://localhost:" + porta + "/pessoas/91/982147896"))
                .body("id", equalTo(6),
                        "nome", equalTo("Edson"),
                        "cpf", equalTo("50313675716"));
    }

    @Test
    void nao_deve_salvar_duas_pessoas_com_o_mesmo_cpf() {
        final Pessoa pessoa = new Pessoa();
        pessoa.setNome("Edson");
        pessoa.setCpf("93095361017");

        final Telefone telefone = new Telefone();
        telefone.setDdd("91");
        telefone.setNumero("982147896");

        pessoa.setTelefones(Arrays.asList(telefone));

        given()
                .request()
                .header("Accept", ContentType.ANY)
                .header("Content-type", ContentType.JSON)
                .body(pessoa)
                .when()
                .post("/pessoas")
                .then()
                .log().body()
                .and()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("erro", equalTo("Já existe pessoa cadastrada com o CPF '93095361017'"));
    }

    @Test
    void nao_deve_salvar_duas_pessoas_com_o_mesmo_numero_de_telefone() {
        final Pessoa pessoa = new Pessoa();
        pessoa.setNome("Edson");
        pessoa.setCpf("50313675716");

        final Telefone telefone = new Telefone();
        telefone.setDdd("91");
        telefone.setNumero("979751356");

        pessoa.setTelefones(Arrays.asList(telefone));

        given()
                .request()
                .header("Accept", ContentType.ANY)
                .header("Content-type", ContentType.JSON)
                .body(pessoa)
                .when()
                .post("/pessoas")
                .then()
                .log().body()
                .and()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("erro", equalTo("Já existe pessoa cadastrada com o telefone (91)979751356"));
    }

    @Test
    void deve_filtrar_pessoas_pelo_nome() {
        final PessoaFiltro filtro = new PessoaFiltro();
        filtro.setNome("a");

        given()
                .request()
                .header("Accept", ContentType.ANY)
                .header("Content-type", ContentType.JSON)
                .body(filtro)
                .when()
                .post("/pessoas/filtrar")
                .then()
                .log().body()
                .and()
                .statusCode(HttpStatus.OK.value())
                .body("id", containsInAnyOrder(2, 4, 5),
                        "nome", containsInAnyOrder("Marcos", "Maurício", "Bernardo"),
                        "cpf", containsInAnyOrder("47855639071", "87247434023", "84900228010"));
    }

}
