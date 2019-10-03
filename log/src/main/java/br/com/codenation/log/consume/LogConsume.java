package br.com.codenation.log.consume;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface LogConsume {
	@Input("log")
	SubscribableChannel consumeLog();
}
