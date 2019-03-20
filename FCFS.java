/**
 * FCFS scheduling algorithm.
 * Author: Shirisha Vadlamudi, Peter Kemper
 */

import java.util.*;

public class FCFS implements Algorithm
{
  private List<Task> queue;
  private Task currentTask;
  private List<Task> finishedTasks;
  
  public FCFS(List<Task> queue) {
    this.queue = queue;
    this.currentTask = null;
    this.finishedTasks = new ArrayList<Task>(queue.size());
  }
  
  public void schedule() {
    System.out.println("FCFS Scheduling \n");
    
    while (!queue.isEmpty()) {
      currentTask = pickNextTask();
      
      CPU.run(currentTask, currentTask.getBurst());
      System.out.println( "Task " + currentTask.getName() + " finished.\n");
    }
  }
  
  /**
   * Picks the next task
   */
  public Task pickNextTask() {
    if (currentTask != null){
      finishedTasks.add(currentTask);
    }
    if (queue.size() == 1){
      finishedTasks.add(queue.get(0));
    }
    return queue.remove(0);
  }
}