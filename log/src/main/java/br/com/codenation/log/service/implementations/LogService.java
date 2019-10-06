package br.com.codenation.log.service.implementations;

import br.com.codenation.log.dto.LogDTO;
import br.com.codenation.log.endpoints.advice.LogNotFoundException;
import br.com.codenation.log.entity.Log;
import br.com.codenation.log.entity.Usuario;
import br.com.codenation.log.enums.Ordenacao;
import br.com.codenation.log.projection.LogProjection;
import br.com.codenation.log.repository.LogRepository;
import br.com.codenation.log.repository.UsuarioRepository;
import br.com.codenation.log.service.interfaces.LogServiceInterface;
import br.com.codenation.message.dto.Ambiente;
import br.com.codenation.message.dto.Evento;
import br.com.codenation.message.dto.Nivel;
import br.com.codenation.message.dto.Payload;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class LogService implements LogServiceInterface {

    private LogRepository repository;
    private UsuarioRepository usuarioRepository;
    private ObjectMapper objectMapper;

    @Override
    public Optional<Log> salvar(Evento evento) {
        objectMapper.registerModule(new JavaTimeModule());
        Optional<Log> log = converterParaEntidade(evento);
        return log.map(log1 -> repository.save(log1));
    }

    private Optional<Log> converterParaEntidade(Evento evento) {
        try {
            Log log = new Log();
            log.setDescricao(evento.getPayload().getDescricao());
            log.setAmbiente(evento.getAmbiente());
            log.setNivel(evento.getNivel());
            log.setPayload(objectMapper.writeValueAsString(evento.getPayload()));
            usuarioRepository.findById(evento.getUsuarioId()).ifPresent(log::setUsuario);
            log.setDataColeta(evento.getPayload().getDataHora());
            return Optional.of(log);
        } catch (JsonProcessingException e) {
            log.error("Erro ao serialzar payload", e);
            return Optional.empty();
        }
    }

    @Override
    public LogDTO buscarPorId(Long id) {
        return repository.findLogById(id)
                .map(this::mapLogProjectionToLogDTO)
                .orElseThrow(() -> new LogNotFoundException(id));
    }

    @Override
    public List<LogDTO> listarComFiltros(Ambiente ambiente,
                                         Nivel nivel,
                                         String descricao,
                                         String origem,
                                         Ordenacao ordenacao) {

        if (Objects.nonNull(nivel)) {
            return filtraPorNivel(ambiente, nivel, ordenacao);
        }

        if (Objects.nonNull(descricao)) {
            return filtraPorDescricao(ambiente, descricao, ordenacao);
        }

        if (Objects.nonNull(origem)) {
            return filtraPorOrigem(ambiente, origem, ordenacao);
        }

        return filtraPorAmbiente(ambiente, ordenacao);
    }

    private List<LogDTO> filtraPorAmbiente(Ambiente ambiente, Ordenacao ordenacao) {
        List<LogDTO> logs = mapLogProjectionToLogDTO(repository.findAllByAmbiente(ambiente.name()));
        return ordenaLista(logs, ordenacao);
    }

    private List<LogDTO> filtraPorNivel(Ambiente ambiente, Nivel nivel, Ordenacao ordenacao) {
        List<LogDTO> logs = mapLogProjectionToLogDTO(repository.findAllByAmbienteAndNivel(ambiente.name(), nivel.name()));
        return ordenaLista(logs, ordenacao);
    }

    private List<LogDTO> filtraPorDescricao(Ambiente ambiente, String descricao, Ordenacao ordenacao) {
        List<LogDTO> logs = mapLogProjectionToLogDTO(repository.findAllByAmbienteAndDescricao(ambiente.name(), descricao));
        return ordenaLista(logs, ordenacao);
    }

    private List<LogDTO> filtraPorOrigem(Ambiente ambiente, String origem, Ordenacao ordenacao) {
        List<LogDTO> logs = mapLogProjectionToLogDTO(repository.findAllByAmbienteAndOrigem(ambiente.name(), origem));
        return ordenaLista(logs, ordenacao);
    }

    private List<LogDTO> ordenaLista(List<LogDTO> logs, Ordenacao ordenacao) {
        if (Objects.isNull(ordenacao)) {
            return logs;
        }

        switch (ordenacao) {
            case FREQUENCIA:
                logs.sort(Comparator.comparingLong(LogDTO::getFrequencia));
                break;
            case NIVEL:
                logs.sort(Comparator.comparing(log -> log.getNivel().name()));
                break;
        }

        return logs;
    }

    private List<LogDTO> mapLogProjectionToLogDTO(List<LogProjection> projections) {
        return projections.stream()
                .map(this::mapLogProjectionToLogDTO)
                .collect(Collectors.toList());
    }

    private LogDTO mapLogProjectionToLogDTO(LogProjection projection) {
        Usuario usuario = usuarioRepository.findById(projection.getUsuarioId()).orElse(null);
        Payload payloadDTO = stringToPayloadDTO(projection.getPayload()).orElse(new Payload());
        Ambiente ambiente = Ambiente.valueOf(projection.getAmbiente());
        Nivel nivel = Nivel.valueOf(projection.getNivel());

        return new LogDTO(projection.getId(), ambiente, nivel, payloadDTO, projection.getFrequencia(), usuario);
    }

    private Optional<Payload> stringToPayloadDTO(String json) {
        objectMapper.registerModule(new JavaTimeModule());

        try {
            return Optional.of(objectMapper.readValue(json, Payload.class));
        } catch (IOException e) {
            log.error("Erro ao ler json", e);
            return Optional.empty();
        }
    }

    @Override
    public void apagar(Long id) {
        repository.deleteById(id);
    }

}
