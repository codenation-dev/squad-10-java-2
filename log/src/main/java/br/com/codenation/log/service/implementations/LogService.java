package br.com.codenation.log.service.implementations;

import br.com.codenation.log.dto.LogDTO;
import br.com.codenation.log.dto.PayloadDTO;
import br.com.codenation.log.endpoints.advice.LogNotFoundException;
import br.com.codenation.log.entity.Usuario;
import br.com.codenation.log.enums.Ambiente;
import br.com.codenation.log.enums.Nivel;
import br.com.codenation.log.enums.Ordenacao;
import br.com.codenation.log.projection.LogProjection;
import br.com.codenation.log.repository.LogRepository;
import br.com.codenation.log.repository.UsuarioRepository;
import br.com.codenation.log.service.interfaces.LogServiceInterface;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class LogService implements LogServiceInterface {

    private static final Logger log = LoggerFactory.getLogger(LogService.class);
    private LogRepository repository;
    private UsuarioRepository usuarioRepository;

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

        if (Objects.nonNull(nivel))
            return filtraPorNivel(ambiente, nivel, ordenacao);

        if (Objects.nonNull(descricao))
            return filtraPorDescricao(ambiente, descricao, ordenacao);

        if (Objects.nonNull(origem))
            return filtraPorOrigem(ambiente, origem, ordenacao);

        return filtraPorAmbiente(ambiente);
    }

    private List<LogDTO> filtraPorAmbiente(Ambiente ambiente) {
        return mapLogProjectionToLogDTO(repository.findAllByAmbiente(ambiente.name()));
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
        if (Objects.isNull(ordenacao))
            return logs;

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
        PayloadDTO payloadDTO = stringToPayloadDTO(projection.getPayload()).orElse(new PayloadDTO());
        Ambiente ambiente = Ambiente.valueOf(projection.getAmbiente());
        Nivel nivel = Nivel.valueOf(projection.getNivel());

        return new LogDTO(ambiente, nivel, payloadDTO, projection.getFrequencia(), usuario);
    }

    private Optional<PayloadDTO> stringToPayloadDTO(String json) {
        try {
            return Optional.of(new ObjectMapper().readValue(json, PayloadDTO.class));
        } catch (IOException e) {
            log.error("Erro ao ler json", e);
            return Optional.empty();
        }
    }

    @Override
    public void apagar(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void arquivar(Long id) {
        // TODO: implementar arquivar
    }
}
