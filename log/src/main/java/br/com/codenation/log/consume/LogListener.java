package br.com.codenation.log.consume;

import br.com.codenation.log.service.implementations.LogService;
import br.com.codenation.message.dto.Evento;
import lombok.AllArgsConstructor;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

@EnableBinding(LogConsume.class)
@AllArgsConstructor
public class LogListener {
    private LogService logService;

    @StreamListener(target = "log")
    public void processEvento(Evento evento) {
        logService.salvar(evento);
    }
}
