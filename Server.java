import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class Server {
    public static void main(String[] args) {
        try {
            int numProcesses = 3;

            // Start the RMI registry
            LocateRegistry.createRegistry(1099);

            // Create and bind the BSSManager
            BSSManagerInterface bssManager = new BSSManager(3);
            Naming.rebind("BSSManager", bssManager);

            // Create and bind each process
            for (int i = 0; i < numProcesses; i++) {
                ProcessInterface process = new Process(i, numProcesses, bssManager);
                Naming.rebind("Process" + i, process);
                System.out.println("Process " + i + " is bound to the registry.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
