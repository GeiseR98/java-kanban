import java.util.HashMap;

public class Manager {
       HashMap<Integer, JustTask> justTask = new HashMap<>();
       HashMap<Integer, Epic> epicTask = new HashMap<>();
       HashMap<Integer, Subtask> subTask = new HashMap<>();
       int idTask = 0;

    Manager(){
    }
    void saveJustTask(String name, String description, String status){
        ++idTask;
        if (!justTask.containsKey(idTask)){
            justTask.put(idTask, new JustTask(name, description, status));
        }
        //JustTask task = justTask.get(idTask);
    }
    void saveSubTask(String name, String description, String status, Integer idMaster){
        ++idTask;
        if (!subTask.containsKey(idTask)){
            subTask.put(idTask, new Subtask(name, description, status, idMaster));
        }
        //Subtask task = subTask.get(idTask);
    } //нужно дописать добавление номера суба в эпик

    /*
    1) Возможность хранить задачи всех типов. Для этого вам нужно выбрать подходящую коллекцию.
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
