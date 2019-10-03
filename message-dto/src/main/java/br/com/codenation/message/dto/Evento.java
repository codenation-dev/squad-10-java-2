package br.com.codenation.message.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Evento {
	private Nivel nivel;
	private Payload payload;
	private Ambiente ambiente;
}