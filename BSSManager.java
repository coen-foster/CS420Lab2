import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class BSSManager extends UnicastRemoteObject implements BSSManagerInterface {
    private static final long serialVersionUID = 1L;
    private final ProcessInterface[] processes;

    // Constructor
    public BSSManager(int numProcesses) throws RemoteException {
        processes = new ProcessInterface[numProcesses];
    }

    @Override
    public void registerProcess(int processId, ProcessInterface process) throws RemoteException {
        if (processId >= 0 && processId < processes.length) {
            processes[processId] = process;
        } else {
            System.out.println("Invalid process ID: " + processId);
        }
    }

    @Override
    public void send(int fromProcessId, int toProcessId, String message, int[] vectorClock) throws RemoteException {
        System.out.println("BSSManager received message from Process " + fromProcessId + " to Process " + toProcessId);
        if (toProcessId >= 0 && toProcessId < processes.length) {
            ProcessInterface receiver = processes[toProcessId];
            if (receiver != null) {
                receiver.deliver(message, vectorClock);
            } else {
                System.out.println("No process found with ID " + toProcessId);
            }
        } else {
            System.out.println("Invalid destination process ID: " + toProcessId);
        }
    }

    @Override
    public void requestToken(int processId) throws RemoteException {
        System.out.println("Process " + processId + " is requesting the token.");
    }
}
