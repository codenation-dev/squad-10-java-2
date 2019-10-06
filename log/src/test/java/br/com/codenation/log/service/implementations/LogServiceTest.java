package br.com.codenation.log.service.implementations;

import br.com.codenation.log.dto.LogDTO;
import br.com.codenation.log.endpoints.advice.LogNotFoundException;
import br.com.codenation.log.entity.Usuario;
import br.com.codenation.log.enums.Ambiente;
import br.com.codenation.log.enums.Nivel;
import br.com.codenation.log.enums.Ordenacao;
import br.com.codenation.log.projection.LogProjection;
import br.com.codenation.log.repository.LogRepository;
import br.com.codenation.log.repository.UsuarioRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class LogServiceTest {

    @InjectMocks
    LogService service;

    @Mock
    LogRepository logRepository;

    @Mock
    UsuarioRepository usuarioRepository;

    private LogProjection projection;

    private ProjectionFactory factory = new SpelAwareProxyProjectionFactory();

    @Before
    public void setUp() {
        projection = factory.createProjection(LogProjection.class);
        projection.setPayload("{\"origem\":\"1213\"}");
        projection.setAmbiente("DESENVOLVIMENTO");
        projection.setNivel("DEBUG");
        projection.setFrequencia(1L);
        projection.setUsuarioId(1L);
    }

    @Test
    public void deveBuscarLogPeloIdQuandoRecebeId() {
        doReturn(Optional.of(projection)).when(logRepository).findLogById(any());

        LogDTO logDTO = service.buscarPorId(1L);

        assertNotNull(logDTO);
    }

    @Test(expected = LogNotFoundException.class)
    public void deveLancarLogNotFoundExceptionQuandoNaoEncontraLog() {

        doReturn(Optional.empty()).when(logRepository).findLogById(any());
        service.buscarPorId(1L);
    }

    @Test
    public void deveMapearLogProjectionParaLogDTOCorretamente() {
        doReturn(Optional.of(projection)).when(logRepository).findLogById(any());

        Usuario usuario = new Usuario();
        usuario.setId(1L);
        doReturn(Optional.of(usuario)).when(usuarioRepository).findById(1L);

        LogDTO logDTO = service.buscarPorId(1L);

        assertNotNull(logDTO);
        assertThat(logDTO.getAmbiente(), is(Ambiente.DESENVOLVIMENTO));
        assertThat(logDTO.getNivel(), is(Nivel.DEBUG));
        assertThat(logDTO.getPayload(), is(notNullValue()));
        assertThat(logDTO.getUsuario().getId(), is(1L));
        assertThat(logDTO.getFrequencia(), is(1L));
    }

    @Test
    public void deveMapearStringParaPayloadDTO() {
        doReturn(Optional.of(projection)).when(logRepository).findLogById(any());

        LogDTO logDTO = service.buscarPorId(1L);

        assertThat(logDTO.getPayload().getOrigem(), is("1213"));
    }

    @Test
    public void deveFiltrarPorAmbienteEhNivelQuandoRecebeAmbienteEhNivel() {
        service.listarComFiltros(Ambiente.DESENVOLVIMENTO,
                Nivel.WARNING,
                null,
                null,
                Ordenacao.FREQUENCIA);
        verify(logRepository).findAllByAmbienteAndNivel("DESENVOLVIMENTO", "WARNING");
    }

    @Test
    public void deveFiltrarPorAmbienteEhDescricaoQuandoRecebeAmbienteEhDescricao() {
        service.listarComFiltros(Ambiente.DESENVOLVIMENTO,
                null,
                "teste",
                null,
                Ordenacao.FREQUENCIA);
        verify(logRepository).findAllByAmbienteAndDescricao("DESENVOLVIMENTO", "teste");
    }

    @Test
    public void deveFiltrarPorAmbienteEhOrigemQuandoRecebeAmbienteEhOrigem() {
        service.listarComFiltros(Ambiente.DESENVOLVIMENTO,
                null,
                null,
                "1213",
                Ordenacao.FREQUENCIA);
        verify(logRepository).findAllByAmbienteAndOrigem("DESENVOLVIMENTO", "1213");
    }

    @Test
    public void deveFiltrarPorAmbienteQuandoRecebeSomenteAmbiente() {
        service.listarComFiltros(Ambiente.DESENVOLVIMENTO,
                null,
                null,
                null,
                Ordenacao.FREQUENCIA);
        verify(logRepository).findAllByAmbiente("DESENVOLVIMENTO");
    }

    @Test
    public void deveOrdenarLogsPorFrequencia() {
        doReturn(criaListaDeLogProjectionDesordenada()).when(logRepository).findAllByAmbiente(any());

        List<LogDTO> logs = service.listarComFiltros(Ambiente.DESENVOLVIMENTO,
                null,
                null,
                null,
                Ordenacao.FREQUENCIA);

        assertThat(logs.get(0).getFrequencia(), is(1L));
        assertThat(logs.get(1).getFrequencia(), is(2L));
        assertThat(logs.get(2).getFrequencia(), is(3L));
    }

    @Test
    public void deveOrdenarLogsPorNivel() {
        doReturn(criaListaDeLogProjectionDesordenada()).when(logRepository).findAllByAmbiente(any());

        List<LogDTO> logs = service.listarComFiltros(Ambiente.DESENVOLVIMENTO,
                null,
                null,
                null,
                Ordenacao.NIVEL);

        assertThat(logs.get(0).getNivel(), is(Nivel.DEBUG));
        assertThat(logs.get(1).getNivel(), is(Nivel.ERROR));
        assertThat(logs.get(2).getNivel(), is(Nivel.WARNING));
    }

    private List<LogProjection> criaListaDeLogProjectionDesordenada() {
        LogProjection projection2 = factory.createProjection(LogProjection.class);
        projection2.setPayload("{\"origem\":\"1213\"}");
        projection2.setAmbiente("DESENVOLVIMENTO");
        projection2.setNivel("ERROR");
        projection2.setFrequencia(2L);
        projection2.setUsuarioId(1L);

        LogProjection projection3 = factory.createProjection(LogProjection.class);
        projection3.setPayload("{\"origem\":\"1213\"}");
        projection3.setAmbiente("DESENVOLVIMENTO");
        projection3.setNivel("WARNING");
        projection3.setFrequencia(3L);
        projection3.setUsuarioId(1L);

        return List.of(projection3, projection, projection2);
    }

    @Test
    public void deveApagaLogPeloIdQuandoRecebeId() {
        service.apagar(1L);
        verify(logRepository).deleteById(1L);
    }
}