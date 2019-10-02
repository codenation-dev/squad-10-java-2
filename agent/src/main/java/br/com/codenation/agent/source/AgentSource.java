package br.com.codenation.agent.source;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface AgentSource {
	@Output("log")
	MessageChannel sendLog();
}
