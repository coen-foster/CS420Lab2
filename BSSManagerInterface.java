import java.rmi.Remote;
import java.rmi.RemoteException;

public interface BSSManagerInterface extends Remote {
    void registerProcess(int processId, ProcessInterface process) throws RemoteException;
    void requestToken(int processId) throws RemoteException;
	void send(int senderId, int recipientId, String message, int[] vectorClock) throws RemoteException;
}
