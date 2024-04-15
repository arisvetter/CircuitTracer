import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.List;
import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Stack;

import javax.swing.*;


//import Storage.DataStructure;

/**
 * Search for shortest paths between start and end points on a circuit board
 * as read from an input file using either a stack or queue as the underlying
 * search state storage structure and displaying output to the console or to
 * a GUI according to options specified via command-line arguments.
 * 
 * @author mvail, Aris Vetter
 */
public class CircuitTracer {

	/** Launch the program. 
	 * 
	 * @param args three required arguments:
	 *  first arg: -s for stack or -q for queue
	 *  second arg: -c for console output or -g for GUI output
	 *  third arg: input file name 
	 */
	public static void main(String[] args) {
		new CircuitTracer(args); //create this with args

	}

	/** Print instructions for running CircuitTracer from the command line. */
	private void printUsage() {
		System.out.println("Usage: $ java CircuitTracer <-s | -q> <-c | -g> filename\n" + //
				"\t\twhere\t-q is queue storage\n" + //
				"\t\t\t-s is stack storage\n" + 
				"\t\t\t-c is console output\n" + 
				"\t\t\t-g is GUI output\n");
		//TODO: print out clear usage instructions when there are problems with
		// any command line args
	}
	
	/** 
	 * Set up the CircuitBoard and all other components based on command
	 * line arguments.
	 * 
	 * @param args command line arguments passed through from main()
	 */
	public CircuitTracer(String[] args) {
		//TODO: parse and validate command line args - first validation provided
		if (args.length != 3) {
			printUsage();
			return; //exit the constructor immediately
		}
		//the only two acceptable first arguments
		if(!args[0].equals("-s") && !args[0].equals("-q")){
			printUsage();
			return;
		}
		//the only two acceptable second arguments
		if(!args[1].equals("-c") && !args[1].equals("-g")){
			printUsage();
			return;
		}
		//initializes empty stateStore (stack or queue) based on first argument
		Storage<TraceState> stateStore;
		if(args[0].equals("-s")){
			stateStore = new Storage<TraceState>(Storage.DataStructure.stack);
			}
		else {
			stateStore = new Storage<TraceState>(Storage.DataStructure.queue);
		}
		CircuitBoard myBoard = null;
		try {
			myBoard = new CircuitBoard(args[2]);
			//System.out.println("Tester: Success");

		} catch (FileNotFoundException e){
			System.out.println(e.toString());
			return;
		} catch (InvalidFileFormatException e){
			System.out.println(e.toString());
			return;
		} catch (Exception e){
			System.out.println(e.toString() + "Unexpected exception.");
			return;
		}

		//List<TraceState> bestPaths = new List<TraceState>();
		ArrayList<TraceState> bestPaths = new ArrayList<TraceState>();
		
		//add the initial traceStates (with just one trace) to stateStore
		Point startPoint = myBoard.getStartingPoint();
		int currX = (int)startPoint.getX();
		int currY = (int)startPoint.getY();
		if (myBoard.isOpen(currX, currY - 1)){
			stateStore.store(new TraceState(myBoard, currX, currY - 1));
		}
		if (myBoard.isOpen(currX, currY + 1)){
			stateStore.store(new TraceState(myBoard, currX, currY + 1));
		}
		if (myBoard.isOpen(currX + 1, currY)){
			stateStore.store(new TraceState(myBoard, currX + 1, currY));
		}
		if (myBoard.isOpen(currX - 1, currY)){
			stateStore.store(new TraceState(myBoard, currX - 1, currY));
		}


		//TraceState initialTraceState = new TraceState(myBoard, (int)startPoint.getX(), (int)startPoint.getY()); 
		/*for(int i = (int)startPoint.getX() - 1; i <  (int)startPoint.getX() + 1; i++){
			for(int k = (int)startPoint.getY() - 1; i < (int)startPoint.getY() + 1; i++){
				if (myBoard.isOpen(i,k)){
					TraceState addState = new TraceState(myBoard, i, k);
				stateStore.store(addState);
				}
				
			}
		}*/
		int shortestPath = 0;
		while (!stateStore.isEmpty()){
			TraceState currentState = stateStore.retrieve();
			if(currentState.isSolution()){
				
				if (!bestPaths.isEmpty()){
					shortestPath = bestPaths.get(0).pathLength();
				} 
				
				if(bestPaths.size() == 0 || shortestPath == currentState.pathLength()){
					bestPaths.add(currentState);
				} else if (shortestPath > currentState.pathLength()){
					bestPaths.clear();
					bestPaths.add(currentState);
				} 
			}
				else {
					//currentState.getRow should instead be most recent T
					//got error when i changed row and col ti match
					/*for(int i = (int)currentState.getRow() - 1; i <  currentState.getRow() + 2; i++){
						for(int k = (int)currentState.getCol() - 1; i < (int)currentState.getCol() + 2; i++){
							if(currentState.isOpen(i,k)){
								TraceState addState = new TraceState(currentState, i, k);
								stateStore.store(addState);
							}
						}
					}*/
					currX = (int)currentState.getRow();
					currY = (int)currentState.getCol();
					if (currentState.isOpen(currX, currY - 1)){
						stateStore.store(new TraceState(currentState, currX, currY - 1));
					}
					if (currentState.isOpen(currX, currY + 1)){
						stateStore.store(new TraceState(currentState, currX, currY + 1));
					}
					if (currentState.isOpen(currX + 1, currY)){
						stateStore.store(new TraceState(currentState, currX + 1, currY));
					}
					if (currentState.isOpen(currX - 1, currY)){
						stateStore.store(new TraceState(currentState, currX - 1, currY));
					}

				}
			
		}

		if (args[1].equals("-c")){
			for (TraceState option : bestPaths){
				//System.out.println(option.getBoard().numRows() + " " + option.getBoard().numCols());
				System.out.println(option.toString());
			}

		} else {
			//System.out.println("GUI option is currently unavailable.");
			CircuitTracerGUI myGUI = new CircuitTracerGUI(myBoard, bestPaths);
			/*JFrame mainFrame = new JFrame();
			JPanel mainPanel = new JPanel();
			mainPanel.setLayout(new BorderLayout());
			JPanel solutionOptions = new JPanel();
			
			JPanel gridPanel = new JPanel();
			gridPanel.setLayout(new GridLayout(myBoard.numRows(), myBoard.numCols()));
			
			for(int i = 0; i < myBoard.numRows(); i++){
				for(int k = 0; k < myBoard.numCols(); k++){
					JLabel currentLabel = new JLabel(String.valueOf(myBoard.charAt(i,k)));
					//JButton currButton = new JButton(currentLabel);
					gridPanel.add(currentLabel);
					//not a button tho
				}
			}
			for(int i = 0; i < bestPaths.size(); i++){
				JButton currentOption = new JButton();
				JLabel numOption = new JLabel("Solution #" + (i + 1));
				currentOption.add(numOption);
				solutionOptions.add(currentOption);
			}
			mainPanel.add(gridPanel, BorderLayout.WEST);
			mainPanel.add(solutionOptions, BorderLayout.EAST);
			mainFrame.setVisible(true);*/

		}
		/* 
	retrieve the next TraceState object from stateStore
	if that TraceState object is a solution (ends with a position adjacent to the ending component),
		if bestPaths is empty or the TraceState object's path is equal in length to one of the TraceStates in bestPaths,
			add it to bestPaths
	else if that TraceState object's path is shorter than the paths in bestPaths,
		clear bestPaths and add the current TraceState as the new shortest path
	else generate all valid next TraceState objects from the current TraceState and add them to stateStore
use a stack or queue (i think i want to do the queue)
things in the stack are the current state*/
		//what do i put for row and cols
		//one more or less than the current row and col
		//TODO: initialize the Storage to use either a stack or queue
		//TODO: read in the CircuitBoard from the given file
		//TODO: run the search for best paths
		//TODO: output results to console or GUI, according to specified choice
	}
	
} // class CircuitTracer
/*
 * initialize an empty Storage object called stateStore that stores objects of type TraceState
initialize an empty List called bestPaths that stores objects of type TraceState
add a new initial TraceState object (a path with one trace) to stateStore for each open position adjacent to the starting component
while (!stateStore.isEmpty)
	retrieve the next TraceState object from stateStore
	if that TraceState object is a solution (ends with a position adjacent to the ending component),
		if bestPaths is empty or the TraceState object's path is equal in length to one of the TraceStates in bestPaths,
			add it to bestPaths
	else if that TraceState object's path is shorter than the paths in bestPaths,
		clear bestPaths and add the current TraceState as the new shortest path
	else generate all valid next TraceState objects from the current TraceState and add them to stateStore
use a stack or queue (i think i want to do the queue)
things in the stack are the current state

is it ending state? is it valud state?

stack is depth first searching
only edit to storage can be a counter
driver class

count loop iterrations, maximum size of states
 */

 