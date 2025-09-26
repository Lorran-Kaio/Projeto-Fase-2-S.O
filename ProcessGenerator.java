import java.util.Random;

public class ProcessGenerator {
    private Random random = new Random();

    public Process generateProcess() {
        // sets the process size between 10 and 50
        return new Process(random.nextInt(41) + 10);
    }
}