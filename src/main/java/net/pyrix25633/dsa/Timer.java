package net.pyrix25633.dsa;

import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;

public class Timer {
    private final LinkedList<Task> tasks;

    public Timer() {
        this.tasks = new LinkedList<>();
    }

    public void startTask(String name) {
        System.out.println("> " + getSpaces() + "Executing task " + name + "...");
        tasks.addLast(new Task(name));
    }

    public void endTask() {
        Task task = tasks.removeLast();
        task.end();
        System.out.println("> " + getSpaces() + "Task " + task.name + " finished in " + task.getDuration() + "ms");
    }

    private @NotNull String getSpaces() {
        return "  ".repeat(tasks.size());
    }

    private static class Task {
        public final String name;
        private final long start;
        private long end;

        public Task(String name) {
            this.name = name;
            this.start = System.currentTimeMillis();
        }

        public void end() {
            this.end = System.currentTimeMillis();
        }

        public long getDuration() {
            return end - start;
        }
    }
}