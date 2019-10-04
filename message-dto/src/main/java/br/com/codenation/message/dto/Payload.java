package br.com.codenation.message.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class Payload {
	private String titulo;
	private String descricao;
	private String origem;
	private LocalDateTime dataHora;
	private List<String> detalhes;
}
