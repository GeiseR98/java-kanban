package api;

public enum Endpoint {
    GET_TASKS,          // получить все задачи
    GET_JUSTTASKS,      // получить все таски
    GET_EPICTASKS,      // получить все эпики
    GET_SUBTASKS,       // получить все сабтаски
    GET_HISTORY,        // посмотреть историю
    GET_TASK_BY_ID,     // получить задачу по номеру
    GET_PROIRITIZED,    // посмотреть приритетность задач
    POST_JUSTTASK,      // создать задачу
    POST_EPICTASK,      // создать эпик
    POST_SUBTASK,       // создать подзадачу
    DELETE_TASKS,       // удалить все
    DELETE_HISTORY,     // удалить историю
    DELETE_TASK_BY_ID,  // удалить задачу по номеру
    PATCH,              // изменить какой либо параметр
    UNKNOWN
}
