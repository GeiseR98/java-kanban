package managers;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class EpicTaskTest {

    ArrayList<Integer> list = new ArrayList<>();

    LocalDateTime start = LocalDateTime.of(2023,01,18,07,00);

    EpicTask epicTask = new EpicTask(1, "name", "description", Status.NEW,
            start, Duration.ofMinutes(150), start.plusMinutes(150), list);


    @Test
    void getEndTime() {
        LocalDateTime end = start.plusMinutes(150);
        assertEquals(end, epicTask.getEndTime());
    }

    @Test
    void getListIdSubtask() {
        ArrayList<Integer> list2 = epicTask.getListIdSubtask();
        assertArrayEquals(list.toArray(), list2.toArray());
    }

    @Test
    void setListIdSubtask() {
        ArrayList<Integer> list3 = new ArrayList<>();
        list3.add(1);
        list3.add(2);
        epicTask.setListIdSubtask(list3);
        assertArrayEquals(list3.toArray(), epicTask.getListIdSubtask().toArray());
    }
}