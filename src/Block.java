package src;
public class Block {
    int start; 
    int size; 
    boolean free; 
    int processId;
    Block next;

    public Block(int start, int size, boolean free, int processId) {
        this.start = start;
        this.size = size;
        this.free = free;
        this.processId = processId;
        this.next = null;
    }
}