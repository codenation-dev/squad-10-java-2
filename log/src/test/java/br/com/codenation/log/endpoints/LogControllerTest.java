package br.com.codenation.log.endpoints;

import br.com.codenation.log.dto.LogDTO;
import br.com.codenation.log.endpoints.advice.LogNotFoundException;
import br.com.codenation.log.enums.AmbienteEnum;
import br.com.codenation.log.service.implementations.LogService;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class LogControllerTest {

    @InjectMocks
    LogController logController;

    @Mock
    LogService logService;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void deveRetornarListaDeLogs() {
        doReturn(Arrays.asList(new LogDTO(), new LogDTO())).when(logService).listaPorAmbiente(AmbienteEnum.DESENVOLVIMENTO);

        ResponseEntity<List<LogDTO>> response = logController.findAll(AmbienteEnum.DESENVOLVIMENTO);

        List<LogDTO> logs = response.getBody();
        assertEquals(2, logs.size());
    }

    @Test
    public void deveRetornarListaVaziaSeLogsNaoEncontrados() {
        doReturn(Collections.emptyList()).when(logService).listaPorAmbiente(AmbienteEnum.DESENVOLVIMENTO);

        ResponseEntity<List<LogDTO>> response = logController.findAll(AmbienteEnum.DESENVOLVIMENTO);

        List<LogDTO> logs = response.getBody();
        assertEquals(0, logs.size());
    }

    @Test
    public void deveRetornarCodigo200ERecursoEncontrado() {
        LogDTO logDTO = new LogDTO();
        doReturn(Optional.of(logDTO)).when(logService).findById(any());

        ResponseEntity<LogDTO> response = logController.findById(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(logDTO, response.getBody());
    }

    @Test
    public void deveLancarExcecaoCasoRecursoNaoEncontrado() {
        thrown.expect(LogNotFoundException.class);
        thrown.expectMessage("Log id 1 not found.");

        logController.findById(1L);
    }
}
