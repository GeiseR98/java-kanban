import java.util.ArrayList;
import java.util.HashMap;

public class Manager {
    HashMap<Integer, JustTask> justTask = new HashMap<>();
    HashMap<Integer, Epic> epicTask = new HashMap<>();
    HashMap<Integer, Subtask> subTask = new HashMap<>();
    int idTask = 0;

    void saveJustTask(String name, String description) {
        ++idTask;
        String status = "New";
        if (!justTask.containsKey(idTask)) {
            justTask.put(idTask, new JustTask(name, description, status));
        }
    }

    void saveEpicTask(String name, String description) {
        ++idTask;
        String status = "NEW";
        if (!epicTask.containsKey(idTask)) {
            ArrayList<Integer> listIdSubtask = new ArrayList<>();
            epicTask.put(idTask, new Epic(name, description, status, listIdSubtask));
        }
    }

    void saveSubTask(String name, String description, Integer idMaster) {
        ++idTask;
        String status = "NEW";
        if (!epicTask.containsKey(idMaster)) {
            System.out.println("Такого эпика не существует, создайте сначала эпик");
        } else {
            if (!subTask.containsKey(idTask)) {
                subTask.put(idTask, new Subtask(name, description, status, idMaster));
                epicTask.get(idMaster).getListIdSubtask().add(idTask);
            }
        }
        //нужно дописать проверку статуса эпика
    }

    void printAllJustTask() {
        System.out.println("Список задач: ");
        for (Integer key : justTask.keySet()) {
            System.out.println("Задача №" + key + justTask.get(key));
        }
    }

    void printAllEpicTask() {
        System.out.println("Список эпиков: ");
        for (Integer key : epicTask.keySet()) {
            if (epicTask.get(key).getListIdSubtask().size() != 0) {
                System.out.println("Эпик №" + key + epicTask.get(key));
                System.out.println("    подзадачи эпика: ");
                    for (int i = 0; i < epicTask.get(key).getListIdSubtask().size(); i++) {
                        System.out.println("        подзадача №" + epicTask.get(key).getListIdSubtask().get(i)
                           + " - " + subTask.get(epicTask.get(key).getListIdSubtask().get(i)).getName());
                    }
            } else {
                System.out.println("Эпик №" + key + epicTask.get(key));
                System.out.println("    Подзадач ещё не добавлено ");
            }
        }
    }
    void printAllSubTask(){
        System.out.println("Список подзадач: ");
        for (Integer key : subTask.keySet()) {
            System.out.println("Подзадача №:" + key + subTask.get(key) + ", находится в эпике №" + subTask.get(key).getIdMaster());
        }
    }
    void showTask(Integer id) {
          if (justTask.get(id) != null) System.out.println("Задача №" + id + justTask.get(id));
          else if (epicTask.get(id) != null) {
            System.out.println("Эпик №" + id + epicTask.get(id));
            System.out.println("    подзадачи эпика: ");
            for (int i = 0; i < epicTask.get(id).getListIdSubtask().size(); i++) {
                System.out.println("        подзадача №" + epicTask.get(id).getListIdSubtask().get(i)
                        + " - " + subTask.get(epicTask.get(id).getListIdSubtask().get(i)).getName());
            }
        } else if (subTask.get(id) != null) System.out.println("Подзадача №:" + id + subTask.get(id) + ", находится в эпике №" + subTask.get(id).getIdMaster());
          else System.out.println("задачи с таким номером не обнаружено");
    }
    void removeTask(Integer id){
        if (justTask.get(id) != null){
            justTask.remove(id);
            System.out.println("Задача №" + id + " успешно удалена...");
        }
        if (epicTask.get(id) != null){
            if (epicTask.get(id).getListIdSubtask().size() != 0) {
                for (int i = 0; i < epicTask.get(id).getListIdSubtask().size(); i++) {
                    subTask.remove(i);
                }
                epicTask.remove(id);
                System.out.println("Эпик №" + id + " успешно удален вместе с подзадачами.");
            } else {
                epicTask.remove(id);
                System.out.println("Эпик №" + id + " успешно удален");
            }
        }
        if (subTask.get(id) != null) {
            subTask.remove(id);
            //нужно дописать проверку статуса эпика
        }
    }
    /*
    ~~1) Возможность хранить задачи всех типов. Для этого вам нужно выбрать подходящую коллекцию.
    2) Методы для каждого из типа задач(Задача/Эпик/Подзадача):
        2.1 Получение списка всех задач.
        2.2 Удаление всех задач.
        2.3 Получение по идентификатору.
        2.4 Создание. Сам объект должен передаваться в качестве параметра.
        2.5 Обновление. Новая версия объекта с верным идентификатором передаётся в виде параметра.
        2.6 Удаление по идентификатору.
    3) Дополнительные методы:
        3.1 Получение списка всех подзадач определённого эпика.
    4) Управление статусами осуществляется по следующему правилу:
        4.1 Менеджер сам не выбирает статус для задачи. Информация о нём приходит менеджеру вместе с информацией о самой задаче.
            По этим данным в одних случаях он будет сохранять статус, в других будет рассчитывать.
        4.2 Для эпиков:
            4.2.1 если у эпика нет подзадач или все они имеют статус NEW, то статус должен быть NEW.
            4.2.2 если все подзадачи имеют статус DONE, то и эпик считается завершённым — со статусом DONE.
            4.2.3 во всех остальных случаях статус должен быть IN_PROGRESS.
     */

}
