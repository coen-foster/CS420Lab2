import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;

public class Process extends UnicastRemoteObject implements ProcessInterface {
    private static final long serialVersionUID = 1L;
    private int[] vectorClock;
    private final int processId;
    private final int numProcesses;
    private BSSManagerInterface bssManager;

    public Process(int processId, int numProcesses, BSSManagerInterface bssManager) throws RemoteException {
        this.processId = processId;
        this.numProcesses = numProcesses;
        this.bssManager = bssManager;
        this.vectorClock = new int[numProcesses];
    }

    @Override
    public synchronized void send(int toProcessId, String message) throws RemoteException {
        // Increment the vector clock
        vectorClock[processId]++;
        System.out.println("Process " + processId + " sending message: " + message + " to Process " + toProcessId + " with clock: " + Arrays.toString(vectorClock));
        
        // Pass the message and clock to BSSManager
        bssManager.send(processId, toProcessId, message, vectorClock);
    }

    @Override
    public synchronized void deliver(String message, int[] receivedClock) throws RemoteException {
        System.out.println("Process " + processId + " received message: " + message + " with clock: " + Arrays.toString(receivedClock));
        
        // Update the local vector clock
        for (int i = 0; i < numProcesses; i++) {
            vectorClock[i] = Math.max(vectorClock[i], receivedClock[i]);
        }
        // Increment the local clock
        vectorClock[processId]++;
    }

    @Override
    public synchronized void getToken() throws RemoteException {
        bssManager.requestToken(processId);
    }

    @Override
    public int[] getVectorClock() {
        return vectorClock;
    }
    
    @Override
    public int getProcessId() {
        return processId;
    }
}
