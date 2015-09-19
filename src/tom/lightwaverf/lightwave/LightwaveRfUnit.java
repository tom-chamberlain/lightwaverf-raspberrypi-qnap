package tom.lightwaverf.lightwave;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Represents the LightwaveRF Wifi unit that commands are sent to
 * @author tom.chamberlain
 *
 */
public class LightwaveRfUnit {

	private String hostname;
	private int port;
	private int numberOfTimesToSendCommand;
	private int millisecondsToWaitBetweenSendingCommands;
	
	private Logger logger = LogManager.getLogger();

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public int getNumberOfTimesToSendCommand() {
		return numberOfTimesToSendCommand;
	}

	public void setNumberOfTimesToSendCommand(int numberOfTimesToSendCommand) {
		this.numberOfTimesToSendCommand = numberOfTimesToSendCommand;
	}

	public int getMillisecondsToWaitBetweenSendingCommands() {
		return millisecondsToWaitBetweenSendingCommands;
	}

	public void setMillisecondsToWaitBetweenSendingCommands(
			int millisecondsToWaitBetweenSendingCommands) {
		this.millisecondsToWaitBetweenSendingCommands = millisecondsToWaitBetweenSendingCommands;
	}
	
	
	
	
	/**
	 * Sends the UDP command to the LightwaveRF device
	 * @param command the command to send
	 */
	public void sendCommand(String command)
	{
		for (int i = 0; i < numberOfTimesToSendCommand; i++)
		{
			
			logger.debug("Issuing command {} ({} of {})", command, i, numberOfTimesToSendCommand);
	
			try {
				byte[] message = command.getBytes();
	
				DatagramSocket dsocket = new DatagramSocket();
				dsocket.send(new DatagramPacket(message, message.length, InetAddress.getByName(hostname), port));
				dsocket.close();
				Thread.sleep(millisecondsToWaitBetweenSendingCommands);
			} catch (Exception e) {
				
				logger.error("Unable to send UDP command", e);
			}
		}
	}	
	
}