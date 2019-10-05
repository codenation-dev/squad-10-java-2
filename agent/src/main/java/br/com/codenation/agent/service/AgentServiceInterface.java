package br.com.codenation.agent.service;

import br.com.codenation.message.dto.Evento;

public interface AgentServiceInterface {
	void sendEvento(Evento evento);
}
