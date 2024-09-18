import java.rmi.Naming;
import java.rmi.RemoteException;

public class Client {

    public static void printVectorClock(ProcessInterface remoteProcess) {
        try {
            int[] vectorClock = remoteProcess.getVectorClock();
            int processId = remoteProcess.getProcessId();

            System.out.print("Vector Clock for process " + (processId + 1) + ": [");
            for (int i = 0; i < vectorClock.length; i++) {
                System.out.print(vectorClock[i]);
                if (i < vectorClock.length - 1) {
                    System.out.print(", ");
                }
            }
            System.out.println("]");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            // Look up the remote processes and BSSManager
            ProcessInterface process1 = (ProcessInterface) Naming.lookup("rmi://localhost/Process0");
            ProcessInterface process2 = (ProcessInterface) Naming.lookup("rmi://localhost/Process1");
            ProcessInterface process3 = (ProcessInterface) Naming.lookup("rmi://localhost/Process2");
            BSSManagerInterface bssManager = (BSSManagerInterface) Naming.lookup("rmi://localhost/BSSManager");

            // Register processes with BSSManager
            bssManager.registerProcess(0, process1);
            bssManager.registerProcess(1, process2);
            bssManager.registerProcess(2, process3);

            // Define the test case: each process sends a message to the next one
            ProcessInterface[] sendProcesses = {process1, process2, process3};
            ProcessInterface[] receiveProcesses = {process2, process3, process1};

            // Execute the test case
            for (int i = 0; i < sendProcesses.length; i++) {
                ProcessInterface sender = sendProcesses[i];
                ProcessInterface receiver = receiveProcesses[i];

                // Print initial vector clocks
                System.out.println("\nBefore sending:");
                printVectorClock(sender);
                printVectorClock(receiver);

                // Perform the send event
                sender.send(i + 1, "Hello from Process " + (i + 1));

                // Print vector clocks after sending
                System.out.println("\nAfter sending:");
                printVectorClock(sender);
                printVectorClock(receiver);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
