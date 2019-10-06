package br.com.codenation.log.projection;

public interface LogProjection {

    Long getId();

    void setId(Long id);

    Long getUsuarioId();

    void setUsuarioId(Long usuarioId);

    String getDescricao();

    void setDescricao(String descricao);

    String getNivel();

    void setNivel(String nivel);

    String getPayload();

    void setPayload(String payload);

    String getAmbiente();

    void setAmbiente(String ambiente);

    Long getFrequencia();

    void setFrequencia(Long frequencia);
}
