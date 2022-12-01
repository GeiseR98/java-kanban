package history;

import tasks.Task;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {

    class Node {
        public Task task;
        public int idPrev;
        public int idNext;

        public Node(int idPrev, Task task, int idNext) {
            this.idPrev = idPrev;
            this.task = task;
            this.idNext = idNext;
        }
    }

    public static int first;    // id первого элемента
    public static int last;     // id последнего элемента


    private static final Map<Integer, Node> nodeMap = new HashMap<>();

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
        if (nodeMap.containsKey(id)) {
            removeNode(nodeMap.get(id));
            nodeMap.remove(id);
        }
    }

    @Override
    public List<Task> getHistory() {
    List<Task> result = new ArrayList<>();
        if (!nodeMap.isEmpty()) {
            int next = first;
            while (next != 0) {
                result.add(nodeMap.get(next).task);
                next = nodeMap.get(next).idNext;
            }
        }
        return result;
    }

    public void linkLast(Task task) {
        final Node newNode = new Node(last, task, 0);
        nodeMap.put(task.getId(), newNode);
        if (first == 0) {
            first = task.getId();
            last = task.getId();
        } else {
            nodeMap.get(last).idNext = task.getId();
            last = task.getId();
        }
    }
    public void removeNode(Node node) {
        if (nodeMap.size() > 1) {
            if (first == node.task.getId()) {
                nodeMap.get(node.idNext).idPrev = 0;
                first = nodeMap.get(node.idNext).task.getId();
            } else if (last == node.task.getId()) {
                nodeMap.get(node.idPrev).idNext = 0;
                last = nodeMap.get(node.idPrev).task.getId();
            } else {
                nodeMap.get(node.idPrev).idNext = nodeMap.get(node.idNext).task.getId();
                nodeMap.get(node.idNext).idPrev = nodeMap.get(node.idPrev).task.getId();
            }
        } else {
            first = 0;
            last = 0;
        }
    }
    public boolean getNodeMapId (int id) {
        return nodeMap.containsKey(id);
    }

    @Override
    public void removeAllHistory() {
        first = 0;
        last = 0;
        nodeMap.clear();
    }

}

