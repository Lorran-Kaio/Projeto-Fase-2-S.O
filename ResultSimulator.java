import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.*;

public class ResultSimulator {
    
    public static void main(String[] args) {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream emptyStream = new PrintStream(baos);
        
        String[] algorithms = {"First Fit", "Next Fit", "Best Fit", "Worst Fit"};
        int numExecutions = 100;
        
        System.out.println("Executing " + numExecutions + " simulations for each algorithm\n");
        
        for (String algorithm : algorithms) {
            System.setOut(emptyStream);
            
            AlgorithmResult result = simulateAlgorithm(algorithm, numExecutions);
            
            System.setOut(originalOut);
            displayResults(result, algorithm);
        }
        
        System.out.println("\nSimulation completed!");
    }
    
    private static AlgorithmResult simulateAlgorithm(String algorithm, int numExecutions) {
        AlgorithmResult result = new AlgorithmResult();
        
        for (int execution = 0; execution < numExecutions; execution++) {
            SimulationResult executionResult = executeSimulation(algorithm);
            result.addResult(executionResult);
        }
        
        return result;
    }
    
    private static SimulationResult executeSimulation(String algorithm) {
        Memory memory = new Memory(algorithm);
        ProcessGenerator generator = new ProcessGenerator();
        Random random = new Random();
        List<Integer> allocatedProcesses = new ArrayList<>();
        
        int totalProcessesGenerated = 0;
        int totalProcessesDiscarded = 0;
        int sumProcessSizes = 0;
        double sumOccupancyPerSecond = 0;
        
        for (int second = 0; second < 100; second++) {
            for (int i = 0; i < 2; i++) {
                Process newProcess = generator.generateProcess();
                totalProcessesGenerated++;
                sumProcessSizes += newProcess.size;
                
                if (memory.allocateProcess(newProcess)) {
                    allocatedProcesses.add(newProcess.id);
                } else {
                    totalProcessesDiscarded++;
                }
            }
            
            int numToDeallocate = random.nextInt(2) + 1;
            for (int i = 0; i < numToDeallocate && !allocatedProcesses.isEmpty(); i++) {
                int index = random.nextInt(allocatedProcesses.size());
                int idToRemove = allocatedProcesses.remove(index);
                memory.deallocateProcess(idToRemove);
            }
            
            double currentOccupancy = calculateMemoryOccupancy(memory);
            sumOccupancyPerSecond += currentOccupancy;
        }
        
        double averageProcessSize = totalProcessesGenerated > 0 ? 
            (double) sumProcessSizes / totalProcessesGenerated : 0;
        double averageOccupancy = sumOccupancyPerSecond / 100;
        double discardRate = totalProcessesGenerated > 0 ? 
            (double) totalProcessesDiscarded / totalProcessesGenerated * 100 : 0;
        
        return new SimulationResult(averageProcessSize, averageOccupancy, discardRate);
    }
    
    private static double calculateMemoryOccupancy(Memory memory) {
        try {
            java.lang.reflect.Field headField = Memory.class.getDeclaredField("head");
            headField.setAccessible(true);
            Block head = (Block) headField.get(memory);
            
            int totalOccupied = 0;
            Block current = head;
            
            while (current != null) {
                if (!current.free) {
                    totalOccupied += current.size;
                }
                current = current.next;
            }
            
            return (totalOccupied / 1000.0) * 100;
            
        } catch (Exception e) {
            return 0.0;
        }
    }
    
    private static void displayResults(AlgorithmResult result, String algorithm) {
        System.out.println(algorithm);
        System.out.printf("Average process size: %.2f KB\n", result.getAverageProcessSize());
        System.out.printf("Average memory occupancy: %.2f%%\n", result.getAverageOccupancy());
        System.out.printf("Discard rate: %.2f%%\n", result.getAverageDiscardRate());
        System.out.println();
    }
}

class SimulationResult {
    double averageProcessSize;
    double averageOccupancy;
    double discardRate;
    
    public SimulationResult(double averageProcessSize, double averageOccupancy, double discardRate) {
        this.averageProcessSize = averageProcessSize;
        this.averageOccupancy = averageOccupancy;
        this.discardRate = discardRate;
    }
}

class AlgorithmResult {
    private List<SimulationResult> results;
    
    public AlgorithmResult() {
        this.results = new ArrayList<>();
    }
    
    public void addResult(SimulationResult result) {
        results.add(result);
    }
    
    public double getAverageProcessSize() {
        return results.stream()
                .mapToDouble(r -> r.averageProcessSize)
                .average()
                .orElse(0.0);
    }
    
    public double getAverageOccupancy() {
        return results.stream()
                .mapToDouble(r -> r.averageOccupancy)
                .average()
                .orElse(0.0);
    }
    
    public double getAverageDiscardRate() {
        return results.stream()
                .mapToDouble(r -> r.discardRate)
                .average()
                .orElse(0.0);
    }
    
}