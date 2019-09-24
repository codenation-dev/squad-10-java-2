package br.com.codenation.log.enums;

import lombok.Getter;

@Getter
public enum OrdenacaoEnum {
    NIVEL("nivel"), FREQUENCIA("frequencia");

    private final String descricao;

    OrdenacaoEnum(String descricao) {
        this.descricao = descricao;
    }
}
