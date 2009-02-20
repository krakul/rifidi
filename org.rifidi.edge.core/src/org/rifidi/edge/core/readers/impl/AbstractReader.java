/**
 * 
 */
package org.rifidi.edge.core.readers.impl;

import java.io.IOException;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rifidi.edge.core.commands.Command;
import org.rifidi.edge.core.commands.CommandState;
import org.rifidi.edge.core.readers.Reader;

/**
 * Base implementation of a reader. Extend for your own readers.
 * 
 * @author Jochen Mader - jochen@pramari.com
 * 
 */
public class AbstractReader implements Reader {
	/** Logger for this class. */
	private static final Log logger = LogFactory.getLog(AbstractReader.class);
	/** Queue for reading messages. */
	protected LinkedBlockingQueue<Object> readQueue = new LinkedBlockingQueue<Object>();
	/** Queue for writing messages. */
	protected LinkedBlockingQueue<Object> writeQueue = new LinkedBlockingQueue<Object>();
	/** Used to execute commands. */
	public ThreadPoolExecutor executor;

	/**
	 * Constructor.
	 */
	public AbstractReader() {
		executor = new ThreadPoolExecutor(1, 1, 30, TimeUnit.MILLISECONDS,
				new LinkedBlockingQueue<Runnable>());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.rifidi.edge.core.Reader#isMessageAvailable()
	 */
	@Override
	public boolean isMessageAvailable() throws IOException {
		return readQueue.peek() != null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.rifidi.edge.core.Reader#receiveMessage()
	 */
	@Override
	public Object receiveMessage() throws IOException {
		try {
			return readQueue.take();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			throw new IOException();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.rifidi.edge.core.Reader#receiveMessage(long)
	 */
	@Override
	public Object receiveMessage(long timeout) throws IOException {
		try {
			return readQueue.poll(timeout, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			throw new IOException();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.rifidi.edge.core.Reader#sendMessage(java.lang.Object)
	 */
	@Override
	public void sendMessage(Object o) throws IOException {
		writeQueue.add(o);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.rifidi.edge.core.Reader#execute(org.rifidi.edge.core.Command)
	 */
	@Override
	public Future<CommandState> execute(Command command) {
		logger.info("Submitting command for execution " + command
				+ ". Currently active commands: " + executor.getActiveCount());
		return executor.submit(command);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.rifidi.edge.core.readers.Reader#terminate()
	 */
	@Override
	public void terminate() {
		executor.shutdownNow();
	}
}
