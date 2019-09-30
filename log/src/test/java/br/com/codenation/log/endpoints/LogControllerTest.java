package br.com.codenation.log.endpoints;

import br.com.codenation.log.dto.LogDTO;
import br.com.codenation.log.endpoints.advice.LogNotFoundException;
import br.com.codenation.log.enums.Ambiente;
import br.com.codenation.log.enums.Nivel;
import br.com.codenation.log.enums.Ordenacao;
import br.com.codenation.log.service.implementations.LogService;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class LogControllerTest {

    @InjectMocks
    LogController logController;

    @Mock
    LogService logService;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void deveRetornarListaDeLogsParaConsultaComFiltros() {
        doReturn(List.of(new LogDTO(), new LogDTO())).when(logService).listarComFiltros(any(), any(), any(), any(), any());

        ResponseEntity<List<LogDTO>> response = logController.listarComFiltros(Ambiente.DESENVOLVIMENTO, Nivel.DEBUG, "descricao", "origem", Ordenacao.FREQUENCIA);

        List<LogDTO> logs = response.getBody();
        assertEquals(2, logs.size());
    }

    @Test
    public void deveRetornarCodigo200ERecursoEncontrado() {
        LogDTO logDTO = new LogDTO();
        doReturn(logDTO).when(logService).buscarPorId(any());

        ResponseEntity<LogDTO> response = logController.buscarPorId(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(logDTO, response.getBody());
    }

    @Test
    public void deveLancarExcecaoCasoRecursoNaoEncontrado() {
        doThrow(new LogNotFoundException(1L)).when(logService).buscarPorId(1L);
        thrown.expect(LogNotFoundException.class);
        thrown.expectMessage("Log id 1 not found.");

        logController.buscarPorId(1L);
    }

    @Test
    public void deveRetornarCodigo200SeRecursoArquivadoComSucesso() {
        doNothing().when(logService).arquivar(any());

        ResponseEntity response = logController.arquivar(1L);

        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void deveRetornarCodigo200SeRecursoApagadoComSucesso() {
        doNothing().when(logService).apagar(any());

        ResponseEntity response = logController.apagar(1L);

        assertEquals(200, response.getStatusCodeValue());
    }
}
