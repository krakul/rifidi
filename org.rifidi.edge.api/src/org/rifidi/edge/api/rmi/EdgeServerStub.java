package org.rifidi.edge.api.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * This is the interface for the Edge Server RMI Stub.
 * 
 * @author Kyle Neumeier - kyle@pramari.com
 */
public interface EdgeServerStub extends Remote {

	/**
	 * Saves the current configurations to a file
	 * 
	 * @throws RemoteException
	 */
	void save() throws RemoteException;

	/**
	 * Returns the last time this server was started.  
	 * 
	 * @return The timestamp of the last time this server was started
	 * @throws RemoteException
	 */
	Long getStartupTime() throws RemoteException;
}