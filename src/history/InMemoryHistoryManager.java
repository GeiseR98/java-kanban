package history;

import tasks.Task;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {

    class Node {
        public Task task;
        public Integer idPrev;
        public Integer idNext;

        public Node(Integer idPrev, Task task, Integer idNext) {
            this.idPrev = idPrev;
            this.task = task;
            this.idNext = idNext;
        }
    }

    public Integer first;    // id первого элемента
    public Integer last;     // id последнего элемента
    private int size = 0;    // не нужен

    private final Map<Integer, Node> nodeMap = new HashMap<>();

    @Override
    public void addHistory(Task task) {
        if (!nodeMap.containsKey(task.getId())) {
            linkLast(task);
        } else {
            remove(task.getId());
            linkLast(task);
        }
    }

    @Override
    public void remove(int id) {
        removeNode(nodeMap.get(id));
        nodeMap.remove(id);
    }

    @Override
    public List<Task> getHistory() {

        return null;
    }

    public void linkLast(Task task) {
        final Node newNode = new Node(last, task, null);
        nodeMap.put(task.getId(), newNode);
        if (first == null) {
            first = task.getId();
            last = task.getId();
        } else {
            nodeMap.get(last).idNext = task.getId();
            last = task.getId();
        }
    }
    public void removeNode(Node node) {
        nodeMap.get(node.idPrev).idNext = nodeMap.get(node.idNext).idPrev;
    }

}

