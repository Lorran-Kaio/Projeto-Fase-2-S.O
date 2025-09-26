package src;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.JOptionPane;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        String[] options = {"First Fit", "Next Fit", "Best Fit", "Worst Fit"};
        String algorithm = (String) JOptionPane.showInputDialog(
            null,
            "Choose allocation algorithm:",
            "Algorithm Selection",
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[0]
        );

        if (algorithm == null) {
            System.out.println("Execution cancelled by user");
            return;
        }

        Memory memory = new Memory(algorithm);
        ProcessGenerator generator = new ProcessGenerator();
        Random random = new Random();
        List<Integer> allocatedProcesses = new ArrayList<>();
        
        for (int second = 1; second <= 100; second++) {
            System.out.println("\n--- Second " + second + " ---");
            
            // Generate and allocate 2 processes
            for (int i = 0; i < 2; i++) {
                Process newProcess = generator.generateProcess();
                if (memory.allocateProcess(newProcess)) {
                    allocatedProcesses.add(newProcess.id);
                } else {
                    System.out.println("Failed to allocate Process " + newProcess.id);
                }
            }

            // Deallocate 1-2 random processes
            int numToDeallocate = random.nextInt(2) + 1;
            for (int i = 0; i < numToDeallocate && !allocatedProcesses.isEmpty(); i++) {
                int index = random.nextInt(allocatedProcesses.size());
                int idToRemove = allocatedProcesses.remove(index);
                memory.deallocateProcess(idToRemove);
            }

            memory.printState();
            Thread.sleep(1000);
        }
        
        System.out.println("\nSimulation completed!");
    }
}