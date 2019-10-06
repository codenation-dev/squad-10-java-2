package br.com.codenation.main;

import br.com.codenation.message.dto.Ambiente;
import br.com.codenation.message.dto.Evento;
import br.com.codenation.message.dto.Nivel;
import br.com.codenation.message.dto.Payload;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.IntStream;

import static io.restassured.RestAssured.given;

public class Main {


    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        System.out.print("Quantidade de requisições:");
        int range = sc.nextInt();

        String accessToken = autenticarSimulador();

        IntStream.rangeClosed(1, range).mapToObj(Main::gerarEvento).forEach(evento -> enviarRequisicao(evento, accessToken));

    }

    private static String autenticarSimulador() {
        Response response =
                given()
                        .auth().preemptive().basic("squad10", "squad10#123")
                        .contentType("application/x-www-form-urlencoded")
                        .formParam("grant_type", "password")
                        .formParam("username", "squad10")
                        .formParam("password", "agent#321")
                        .when()
                        .post("http://localhost:8080/oauth/token");

        JSONObject jsonObject = new JSONObject(response.getBody().asString());
        String accessToken = jsonObject.get("access_token").toString();
        return accessToken;
    }

    private static String gerarEvento(int sequencial) {

        Evento evento = new Evento();
        evento.setNivel(Nivel.values()[new Random().nextInt(Nivel.values().length)]);
        evento.setAmbiente(Ambiente.values()[new Random().nextInt(Ambiente.values().length)]);

        Payload payload = new Payload();
        payload.setTitulo("Requisicao numero: " + sequencial);
        payload.setDescricao("Requisicao gerada automaticamente");
        payload.setOrigem("192.168.0." + sequencial);

        List<String> detalhes = new ArrayList<String>();

        detalhes.add("Sequencial " + sequencial);

        payload.setDetalhes(detalhes);

        payload.setDataHora(LocalDateTime.now());

        evento.setPayload(payload);

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        String json = null;
        try {
            json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(evento);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }

    private static void enviarRequisicao(String eventoJson, String token) {

        Response response =
                given()
                        .headers(
                                "Authorization",
                                "Bearer " + token,
                                "Content-Type",
                                ContentType.JSON,
                                "Accept",
                                ContentType.JSON)
                        .body(eventoJson)
                        .when()
                        .post("http://localhost:8080/evento")
                        .then()
                        .extract()
                        .response();

        System.out.println("Retorno: " + response.getStatusCode());
    }

}
