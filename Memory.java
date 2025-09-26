public class Memory {
    private static final int TOTAL_SIZE = 1000;
    private Block head; // first block
    private String algorithm;
    private Block lastBlock; // used on Next Fit algorithm

    // Creates the Memory with 1000 KB free,
    // this memory will allocate the smallest blocks with the processes
    public Memory(String algorithm) {
        this.algorithm = algorithm;
        this.head = new Block(0, TOTAL_SIZE, true, -1);
        this.lastBlock = head;
    }

    // According to the selected algorithm, this allocateProcess will call the proper function to
    // allocate the process
    public boolean allocateProcess(Process process) {
        System.out.println("Allocating: Process " + process.id + " (" + process.size + "KB)");
        
        switch (algorithm) {
            case "First Fit": return firstFit(process);
            case "Next Fit": return nextFit(process);
            case "Best Fit": return bestFit(process);
            case "Worst Fit": return worstFit(process);
            default: return false;
        }
    }

    // Iterate the block and allocate the process
    private boolean firstFit(Process process) {
        Block current = head;
        while (current != null) {
            if (current.free && current.size >= process.size) {
                allocateBlock(current, process);
                return true;
            }
            current = current.next;
        }
        return false;
    }

    // allocates the process on the next free block after the last allocation
    private boolean nextFit(Process process) {
        Block current = lastBlock;
        do {
            if (current.free && current.size >= process.size) {
                allocateBlock(current, process);
                lastBlock = current;
                return true;
            }
            current = current.next != null ? current.next : head;
        } while (current != lastBlock);
        return false;
    }

    // tries to find the smallest block that has size enough to allocate the process
    private boolean bestFit(Process process) {
        Block best = null;
        Block current = head;
        while (current != null) {
            if (current.free && current.size >= process.size) {
                if (best == null || current.size < best.size) {
                    best = current;
                }
            }
            current = current.next;
        }
        if (best != null) {
            allocateBlock(best, process);
            return true;
        }
        return false;
    }

    // tries to find the biggest block that has size enough to allocate the process
    private boolean worstFit(Process process) {
        Block worst = null;
        Block current = head;
        while (current != null) {
            if (current.free && current.size >= process.size) {
                if (worst == null || current.size > worst.size) {
                    worst = current;
                }
            }
            current = current.next;
        }
        if (worst != null) {
            allocateBlock(worst, process);
            return true;
        }
        return false;
    }

    private void allocateBlock(Block block, Process process) {
        if (block.size > process.size) {
            Block newBlock = new Block(
                block.start + process.size,
                block.size - process.size,
                true,
                -1
            );
            newBlock.next = block.next;
            block.next = newBlock;
        }
        block.size = process.size;
        block.free = false;
        block.processId = process.id;
        System.out.println("Process " + process.id + " allocated at: " + block.start);
    }

    public void deallocateProcess(int processId) {
        Block current = head;
        while (current != null) {
            if (!current.free && current.processId == processId) {
                current.free = true;
                current.processId = -1;
                System.out.println("Process " + processId + " deallocated");
                coalesce();
                return;
            }
            current = current.next;
        }
    }

    //turn small fragmented blocks into a bigger unifed block
    private void coalesce() {
        Block current = head;
        while (current != null && current.next != null) {
            if (current.free && current.next.free) {
                current.size += current.next.size;
                current.next = current.next.next;
                System.out.println("Free blocks coalesced");
            } else {
                current = current.next;
            }
        }
    }

    // print format to show the memory blocks
    public void printState() {
        StringBuilder sb = new StringBuilder();
        Block current = head;
        while (current != null) {
            sb.append("[").append(current.start)
             .append("-").append(current.start + current.size - 1)
             .append(": ").append(current.free ? "Free" : "P" + current.processId)
             .append("] -> ");
            current = current.next;
        }
        System.out.println(sb.append("null").toString());
    }
}