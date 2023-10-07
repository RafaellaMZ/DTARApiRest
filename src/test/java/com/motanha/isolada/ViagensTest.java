package com.motanha.isolada;

import com.motanha.config.Configuracoes;
import com.motanha.factory.UsuarioDataFactory;
import com.motanha.factory.ViagemDataFactory;
import com.motanha.pojo.Usuario;
import com.motanha.pojo.Viagem;
import io.restassured.http.ContentType;
import org.aeonbits.owner.Config;
import org.aeonbits.owner.ConfigFactory;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;
import static io.restassured.module.jsv.JsonSchemaValidator.*;
public class ViagensTest {
    private String token;
    private String tokenUsuario;

    @Before
    public void setUp() {
        //Configurações RestAssured
        Configuracoes configuracoes = ConfigFactory.create(Configuracoes.class);
        baseURI = configuracoes.baseURI();
        port = configuracoes.port();
        basePath = configuracoes.basePath();

        Usuario usuarioAdministrador = UsuarioDataFactory.criarUsuarioAdministrador();

        this.token = given()
                .contentType(ContentType.JSON)
                .body(usuarioAdministrador)
            .when()
                .post("/v1/auth")
            .then()
                .extract()
                    .path("data.token");

        Usuario usuarioComum = UsuarioDataFactory.criarUsuarioComum();

        this.tokenUsuario = given()
                .contentType(ContentType.JSON)
                .body(usuarioComum)
            .when()
                .post("/v1/auth")
            .then()
                .extract()
                    .path("data.token");
    }

    /*@Test
    public void testCadastroDeViagemRetornaSucesso() throws IOException {
        Viagem viagemValida = ViagemDataFactory.criarViagemValida();

        given()
                .contentType(ContentType.JSON)
                .body(viagemValida)
                .header("Authorization", token)
        .when()
                .post("/v1/auth")
        .then()
                .assertThat()
                    .statusCode(201)
                    .body("data.localDeDestino", equalTo("Osasco"))
                    .body("data.acompanhante", equalToIgnoringCase("Julio"));
    }

    @Test
    public void testViagensNaoPodemSerCadastradasSemLocalDeDestino() throws IOException {
        Viagem viagemSemLocalDeDestino = ViagemDataFactory.criarViagemSemLocalDeDestino();

        given()
                .contentType(ContentType.JSON)
                .body(viagemSemLocalDeDestino)
                .header("Authorization", token)
        .when()
                .post("/v1/viagens")
        .then()
                .assertThat()
                    .statusCode(400);
    }

    @Test
    public void testCadastroDeViagemValidaContrato() throws IOException {
        //Configurações RestAssured
        baseURI = "http://localhost";
        port = 8089;
        basePath = "/api";

        Viagem viagemValida = ViagemDataFactory.criarViagemValida();

        given()
                .contentType(ContentType.JSON)
                .body(viagemValida)
                .header("Authorization", token)
        .when()
                .post("/v1/auth")
        .then()
                .assertThat()
                    .statusCode(201)
                    .body(matchesJsonSchemaInClasspath("schema/postV1ViagemValida.json"));
    } */

    @Test
    public void testRetornaUmaViagemPossuiStatusCode200EMostraLocalDeDestino() {
        given()
                .header("Authorization", tokenUsuario)
        .when()
                .get("/v1/viagens/1")
        .then()
                .assertThat()
                .statusCode(200)
                .body("data.localDeDestino", equalTo("Osasco"));
    }

    @Test
    public void testViagemProcessaCorretamenteORetornoDaAPIDoTempo(){
        given()
            .header("Authorization", tokenUsuario)
        .when()
            .get("/v1/viagens/1")
        .then()
            .assertThat()
                .statusCode(200)
                .body("data.temperatura", equalTo(35.0f));
    }
}
