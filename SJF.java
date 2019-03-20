/**
 * SJF scheduling algorithm.
 * Author: Shirisha Vadlamudi, Peter Kemper
 */

import java.util.*;

public class SJF implements Algorithm
{
  private List<Task> queue;
  private Task currentTask;
  private List<Task> finishedTasks;
  
  public SJF(List<Task> queue) {
    this.queue = queue;
    this.finishedTasks = new ArrayList<Task>(queue.size());
  }
    
  public void schedule() {
    System.out.println("SJF Scheduling \n");
    
    while (!queue.isEmpty()) {
      currentTask = pickNextTask();
      
      CPU.run(currentTask, currentTask.getBurst());
      System.out.println( "Task " + currentTask.getName() + " finished.\n");      
    }
  }
  
  public Task pickNextTask() {
    Task temp;
    Task nextTask = null;
    int minTask = Integer.MAX_VALUE;
    
    for(int i = 0; i<queue.size(); i++){
      temp = queue.get(i);
      if(temp.getBurst()<minTask){
        minTask = temp.getBurst();
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