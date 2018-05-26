package com.stats.rabbitmq;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public interface Receive {
	public void escuchar() throws IOException, TimeoutException;
}
