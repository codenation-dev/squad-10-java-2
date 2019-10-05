package br.com.codenation.log.projection;

public interface LogProjection {

    Long getId();
    Long getUsuarioId();
    String getDescricao();
    String getNivel();
    String getPayload();
    String getAmbiente();
    Long getFrequencia();
}
