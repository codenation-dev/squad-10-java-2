package br.com.codenation.main;

import br.com.codenation.message.dto.Ambiente;
import br.com.codenation.message.dto.Evento;
import br.com.codenation.message.dto.Nivel;
import br.com.codenation.message.dto.Payload;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args) {

            Scanner sc = new Scanner(System.in);
            System.out.print("Quantidade de requisições:");
            int range = sc.nextInt();

            IntStream.rangeClosed(1, range).mapToObj(Main::gerarEvento).forEach(Main::enviarRequisicao);

    }

    private static String gerarEvento(int sequencial)  {

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
        System.out.println(json);
        return json;
    }


    private static void enviarRequisicao(String eventoJson) {
        try {
            URL url = new URL("http://localhost:8080/evento");

            HttpURLConnection httpcon = (HttpURLConnection) url.openConnection();


            httpcon.setDoOutput(true);
            httpcon.setRequestMethod("POST");
            httpcon.setRequestProperty("Content-Type", "application/json");

            OutputStreamWriter output = new OutputStreamWriter(httpcon.getOutputStream());

            output.write(eventoJson);
            output.flush();
            output.close();
            httpcon.connect();

            int responseCode = httpcon.getResponseCode();
            System.out.println("Retorno: " + responseCode);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
