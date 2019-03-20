/**
 * Round Robin scheduling algorithm.
 * Author: Shirisha Vadlamudi, Peter Kemper
 */

import java.util.*;

public class RR implements Algorithm
{
  //List variables to keep track of what is left, finished, and started
  private List<Task> queue;
  private List<Task> finishedTasks;
  private List<Task> runningTasks;
  
  //Task variables to hold the current and previous Tasks
  private Task currentTask;
  private Task prevTask;
  
  //Integer variable to keep track of turnaround time
  private int turnaround;
  
  //Total amount of tasks
  private int totalTasks;

  
  public RR(List<Task> queue) {
    this.queue = queue;
    this.totalTasks = queue.size();
    this.finishedTasks = new ArrayList<Task>(totalTasks);
    this.runningTasks = new ArrayList<Task>(totalTasks);
    this.turnaround = 0;
    
  }
  
  public void schedule() {
    System.out.println("Round Robin Scheduling \n");
    
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
      
      //Decide how long to run the task
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
      else{
        finishedTasks.add(currentTask);
        runningTasks.remove(currentTask);
        turnaround += CPU.getTime();
        System.out.println( "Task " + currentTask.getName() + " finished.\n");
      }
    }
  }
  
  public Task pickNextTask() {
    Task temp;
    Task nextTask = null;
    int highTask = 0;
    
    if (currentTask != null){
      finishedTasks.add(currentTask);
    }
    if (queue.size() == 1){
      finishedTasks.add(queue.get(0));
    }
    return queue.remove(0);
  }
}
