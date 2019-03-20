/**
 * "Virtual" CPU
 *
 * This virtual CPU also maintains system time.
 *
 * @author Greg Gagne - March 2016
 */
 
public class CPU
{
	public final static int MULTIPLIER = 10;

	public static int time = 0;
    /**
     * Run the specified task for the specified slice of time.
     */
    public static void run(Task task, int slice) {
        System.out.println("Will run " + task);

        try {
            Thread.sleep(slice * MULTIPLIER);
        }
        catch (InterruptedException ie) {
            System.err.println(ie);
        }

        // update the current time
        time += slice;
    }

    public static int getTime() {
        return time;
    }
}
