package br.com.codenation.message.dto.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {
	private String descricao;
	private Nivel nivel;
	private String payload;
	private LocalDateTime dataColeta;
	private Ambiente ambiente;
}
