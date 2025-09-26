public class Process {
    int id;
    int size;
    private static int nextId = 1;

    public Process(int size) {
        //increments id automatically
        this.id = nextId++;
        this.size = size;
    }
}