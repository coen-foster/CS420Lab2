import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ProcessInterface extends Remote {
    void deliver(String message, int[] vectorClock) throws RemoteException;
    void getToken() throws RemoteException;
	void send(int toProcessId, String message) throws RemoteException;
	int[] getVectorClock() throws RemoteException;
	int getProcessId() throws RemoteException;
}
