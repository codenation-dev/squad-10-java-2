package br.com.codenation.log.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PayloadDTO {
    private String titulo;
    private String descricao;
    private String origem;
    private LocalDateTime dataHora;
    private List<String> detalhes;
}
