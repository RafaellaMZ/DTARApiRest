package com.motanha.isolada;

import com.motanha.factory.UsuarioDataFactory;
import com.motanha.factory.ViagemDataFactory;
import com.motanha.pojo.Usuario;
import com.motanha.pojo.Viagem;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;
import static io.restassured.module.jsv.JsonSchemaValidator.*;
public class ViagensTest {
    private String token;

    @Before
    public void setUp() {
        //Configurações RestAssured
        baseURI = "http://localhost";
        port = 8089;
        basePath = "/api";

        Usuario usuarioAdministrador = UsuarioDataFactory.criarUsuarioAdministrador();

        String token = given()
                .contentType(ContentType.JSON)
                .body(usuarioAdministrador)
            .when()
                .post("/v1/auth")
            .then()
                .extract()
                    .path("data.token");
    }

    @Test
    public void testCadastroDeViagemRetornaSucesso(){
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
                    .body("data.localDeDestino", equalTo("Fortaleza"))
                    .body("data.acompanhante", equalToIgnoringCase("Nelson"));
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
    public void testCadastroDeViagemValidaContrato(){
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
    }
}
