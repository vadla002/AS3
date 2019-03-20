/**
 * Priority scheduling algorithm.
 * Author: Shirisha Vadlamudi, Peter Kemper
 */

import java.util.*;

public class Priority implements Algorithm
{
  private List<Task> queue;
  private Task currentTask;
  private List<Task> finishedTasks;
  
  public Priority(List<Task> queue) {
    this.queue = queue;
    this.finishedTasks = new ArrayList<Task>(queue.size());
  }
  
  public void schedule() {
    System.out.println("Priority Scheduling \n");
    
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
    Task temp;
    Task nextTask = null;
    int highTask = 0;
    //Returns the task with the highest priority
    for(int i = 0; i<queue.size(); i++){
      temp = queue.get(i);
      if(temp.getPriority()>highTask){
        highTask = temp.getPriority();
        nextTask = temp;
      }
    }
    if (currentTask != null){
      finishedTasks.add(currentTask);
    }
    if (queue.size() == 1){
      finishedTasks.add(queue.get(0));
    }
    queue.remove(nextTask);
    return nextTask;
  }
}