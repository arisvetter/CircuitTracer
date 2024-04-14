import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.FileNotFoundException;
import java.util.EventListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class BasicDriver {

    public static void main(String[] args) {

        /* 
        try {

            String[] argsFake = new String[3];
            argsFake[0] = "-s";
            argsFake[1] = "-g";
            argsFake[2] = "valid1.dat";
            

            
            CircuitTracer myTracer = new CircuitTracer(argsFake);

            //CircuitBoard hello = new CircuitBoard("boards/validFile.txt");

        //    System.out.println(hello.toString());
        //}// catch (FileNotFoundException e) {
        //    System.out.println("error");
       // }*/
            try {
            CircuitBoard myBoard = new CircuitBoard("valid1.dat");
            JFrame mainFrame = new JFrame();
			JPanel mainPanel = new JPanel();
			mainPanel.setLayout(new BorderLayout());
			JPanel solutionOptions = new JPanel();
			JPanel headerPanel = new JPanel();
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
                currentOption.add(new buttonListener());
				currentOption.add(numOption);
                //EventListener clickListener = new EventListener() {
                    
               // };
                currentOption.add()
				solutionOptions.add(currentOption);

			}
			mainPanel.add(gridPanel, BorderLayout.WEST);
			mainPanel.add(solutionOptions, BorderLayout.EAST);
			mainFrame.setVisible(true);
        } catch (FileNotFoundException e){}
    }

}
