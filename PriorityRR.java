/**
 * Priority Round Robin scheduling algorithm.
 * Author: Shirisha Vadlamudi, Peter Kemper
 */

import java.util.*;

public class PriorityRR implements Algorithm
{
  private List<Task> queue;
  private List<Task> finishedTasks;
  private List<Task> runningTasks;
  
  private Task currentTask;
  private Task prevTask;
  
  private int turnaround;
  
  private int totalTasks;
  
  public PriorityRR(List<Task> queue) {
    this.queue = queue;
    this.totalTasks = queue.size();
    this.finishedTasks = new ArrayList<Task>(totalTasks);
    this.runningTasks = new ArrayList<Task>(totalTasks);
    this.turnaround = 0;
    
  }

  public void schedule() {
    System.out.println("Priority Round Robin Scheduling \n");

    int runDuration;
    
    while (!queue.isEmpty()) {

      currentTask = pickNextTask();
      
      //If a task hasn't been previously started, set it's response time
      if(!runningTasks.contains(currentTask)){
        currentTask.setResponse(CPU.getTime());
      }
      //If it has been previously started, make sure it wasn't just
      //runing, & then set how long it's been waiting
      else{
        if(!currentTask.equals(prevTask)){
          currentTask.setWaitTime(CPU.getTime()-(currentTask.getFinBurst()+currentTask.getResponse()));
        }
      }
      
      if(currentTask.getBurst() < 10)
        runDuration = currentTask.getBurst();
      else
        runDuration = 10;
      
      CPU.run(currentTask, runDuration);
      
      //Decrease what task has left to run, increase what it has finished running
      currentTask.setFinBurst(currentTask.getFinBurst()+runDuration);
      currentTask.setBurst(currentTask.getBurst()-runDuration);
      
      prevTask = currentTask;
      
      //If it's not finished, add it back to the end of the queue
      if (currentTask.getBurst()>0){
        queue.add(currentTask);
        runningTasks.add(currentTask);
      }
      //Otherwise, add it to finished tasks, remove it from running, and 
      //record the turnaround time
      else{
        finishedTasks.add(currentTask);
        runningTasks.remove(currentTask);
        turnaround += CPU.getTime();
        System.out.println( "Task " + currentTask.getName() + " finished.\n");
      }
    }
  }
  
  /**
   * Picks the next task
   */
  public Task pickNextTask() {
    Task temp;
    Task nextTask = null;
    int highTask = 0;
    
    //Finds the task with the highest priority
    for(int i = 0; i<queue.size(); i++){
      temp = queue.get(i);
      if(temp.getPriority()>highTask){
        highTask = temp.getPriority();
        nextTask = temp;
      }
    }
    queue.remove(nextTask);
    return nextTask;
  }
}
