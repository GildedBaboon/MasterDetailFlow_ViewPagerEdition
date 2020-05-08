package com.example.masterdetailflow_viewpageredition;

public class toDoList {
    public ListTask[] task_list;

    public String[] getAllTasks() {
        String[] tasks = {task_list[0].task, task_list[1].task, task_list[2].task};
        return tasks;
    }

    public String getTask(int indx) {
        return task_list[indx].task;
    }

    public class ListTask {
        public String task;
    }
}