/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Business.Logistics;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author zhuchenyan
 */
public class TaskDirectory {
    
    private ArrayList<Task> tasks;
    
    public TaskDirectory() {
        tasks = new ArrayList<>();
    }
    
    public ArrayList<Task> getTasks() {
        return tasks;
    }
    
    public Task createTask(String description, String priority, Date dueDate) {
        Task task = new Task(description, priority, dueDate);
        tasks.add(task);
        return task;
    }
    
    public void removeTask(Task task) {
        tasks.remove(task);
    }
    
    public ArrayList<Task> getPendingTasks() {
        ArrayList<Task> pendingTasks = new ArrayList<>();
        for (Task task : tasks) {
            if (!task.getStatus().equals("Completed")) {
                pendingTasks.add(task);
            }
        }
        return pendingTasks;
    }
    
    public ArrayList<Task> getTasksByPriority(String priority) {
        ArrayList<Task> priorityTasks = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getPriority().equals(priority)) {
                priorityTasks.add(task);
            }
        }
        return priorityTasks;
    }
    
    public ArrayList<Task> getTasksByStatus(String status) {
        ArrayList<Task> statusTasks = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getStatus().equals(status)) {
                statusTasks.add(task);
            }
        }
        return statusTasks;
    }
}
