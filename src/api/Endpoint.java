package api;

public enum Endpoint {
    GET_TASKS,          // получить все задачи
    GET_JUSTTASKS,      // получить все таски
    GET_EPICTASKS,      // получить все эпики
    GET_SUBTASKS,       // получить все сабтаски
    GET_HISTORY,        // посмотреть историю
    GET_PROIRITIZED,    // посмотреть приритетность задач
    POST_JUSTTASK,      // создать задачу
    POST_EPICTASK,      // создать эпик
    POST_SUBTASK,       // создать подзадачу
    DELETE_TASKS,       // удалить все
    PATCH_NAME,         // изменить имя
    PATCH_DESCRIPTION,  // изменить описание
    PATCH_STATUS,       // изменить статус
    UNKNOWN
}
