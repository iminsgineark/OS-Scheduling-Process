package com.JAVA_DSA;

import java.util.PriorityQueue;
import java.util.Queue;
import java.util.LinkedList;

class Process {
    int pid;
    int priority;
    int burstTime;
    int remainingTime;
    int quantum;

    public Process(int pid, int priority, int burstTime, int quantum) {
        this.pid = pid;
        this.priority = priority;
        this.burstTime = burstTime;
        this.remainingTime = burstTime;
        this.quantum = quantum;
    }
}

public class SchedulingProgram {
    private Queue<Process> queue1;
    private Queue<Process> queue2;
    private int quantum2;
    private int currentTime;

    public SchedulingProgram(int quantum2) {
        this.queue1 = new PriorityQueue<>((p1, p2) -> p1.priority - p2.priority);
        this.queue2 = new LinkedList<>();
        this.quantum2 = quantum2;
        this.currentTime = 0;
    }

    public void addProcess(int pid, int priority, int burstTime) {
        Process process = new Process(pid, priority, burstTime, quantum2);
        queue1.offer(process);
    }

    public void run() {
        while (!queue1.isEmpty() || !queue2.isEmpty()) {
            if (!queue1.isEmpty()) {
                Process process = queue1.poll();
                int timeSlice = Math.min(process.remainingTime, 2); // time slice for level 1 queue
                process.remainingTime = process.remainingTime -  timeSlice;
                for (int i = 0; i < timeSlice; i++) {
                    currentTime++;
                    System.out.printf("Time %d\n Process %d\n", currentTime, process.pid);
                }
                if (process.remainingTime > 0) {
                    queue2.offer(process);
                }
            } else { // queue1 is empty, run queue2
                Process process = queue2.poll();
                int timeSlice = Math.min(process.remainingTime, process.quantum); // time slice for level 2 queue
                process.remainingTime  = process.remainingTime -  timeSlice;
                for (int i = 0; i < timeSlice; i++) {
                    currentTime++;
                    System.out.printf("Time %d: Process %d\n", currentTime, process.pid);
                }
                if (process.remainingTime > 0) {
                    queue2.offer(process);
                }
            }
        }
    }

    public static void main(String[] args) {
        SchedulingProgram scheduler = new SchedulingProgram(5);
        scheduler.addProcess(1, 0, 8);
        scheduler.addProcess(2, 1, 5);
        scheduler.addProcess(3, 2, 3);
        scheduler.addProcess(4, 3, 6);
        scheduler.addProcess(5,4,7);
        scheduler.run();
    }
}
