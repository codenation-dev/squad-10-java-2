package br.com.codenation.log.consume;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

import br.com.codenation.message.dto.Evento;

@EnableBinding(LogConsume.class)
public class LogListener {

	@StreamListener(target = "log")
	public void processEvento(Evento evento) {
		System.out.println(evento);
	}
}
