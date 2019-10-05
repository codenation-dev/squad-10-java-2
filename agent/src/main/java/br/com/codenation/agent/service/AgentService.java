package br.com.codenation.agent.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;

import br.com.codenation.agent.source.AgentSource;
import br.com.codenation.message.dto.Evento;

@EnableBinding(AgentSource.class)
public class AgentService implements AgentServiceInterface {

	@Autowired
	private AgentSource agentSource;

	@Override
	public void sendEvento(Evento evento) {
		agentSource.sendLog().send(MessageBuilder.withPayload(evento).build());
	}
}
