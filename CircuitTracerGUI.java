import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

//import com.apple.laf.AquaButtonBorder.Toolbar;

public class CircuitTracerGUI {
    private ArrayList<TraceState> bestPaths;
    private JPanel gridPanel;
    private JFrame mainFrame;
    private Font myFont;

    public CircuitTracerGUI(CircuitBoard myBoard, ArrayList<TraceState> bestPaths) {
        this.bestPaths = bestPaths;
        mainFrame = new JFrame("Circuit Tracer");
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        JPanel solutionOptions = new JPanel();
        solutionOptions.setLayout(new BoxLayout(solutionOptions, BoxLayout.Y_AXIS));

        gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(myBoard.numRows(), myBoard.numCols()));

        //JPanel topOptions = new JPanel();
        JMenuBar fileMenuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenu helpMenu = new JMenu("Help");
        JMenuItem quitItem = new JMenuItem("Quit");
        quitItem.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
            
        }); 


        fileMenu.add(quitItem);

        JMenuItem aboutItem = new JMenuItem("About");
        aboutItem.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog myPopUp = new JDialog(mainFrame, "About", true);
                JLabel myLabel = new JLabel("Written by Aris Vetter (arisvetter@u.boisestate.edu)");
                myPopUp.add(myLabel);
                myPopUp.setSize(300, 300);
                //myPopUp.pack();
                myPopUp.setVisible(true);
                //JOptionPane.showMessageDialog(null, "Eggs are not supposed to be green.");
            }
            
        }); 
        helpMenu.add(aboutItem);
        fileMenuBar.add(fileMenu);
        fileMenuBar.add(helpMenu);
        mainFrame.setJMenuBar(fileMenuBar);
        myFont = new Font("Arial", Font.BOLD, 80);
        for (int i = 0; i < myBoard.numRows(); i++) {
            for (int k = 0; k < myBoard.numCols(); k++) {
                JLabel currentLabel = new JLabel(String.valueOf(myBoard.charAt(i, k)));
                currentLabel.setFont(myFont);
                // JButton currButton = new JButton(currentLabel);
                gridPanel.add(currentLabel);
                // not a button tho
            }
        }
        
        for (int i = 0; i < bestPaths.size(); i++) {
            JButton currentOption = new JButton("Solution #" + (i + 1));
            currentOption.setFont(new Font("Arial", Font.PLAIN, 20));
            currentOption.addActionListener(new ButtonListener());
            solutionOptions.add(currentOption);
        }
        mainPanel.add(gridPanel, BorderLayout.CENTER);
        mainPanel.add(solutionOptions, BorderLayout.EAST);
        mainFrame.add(mainPanel);
        mainFrame.setVisible(true);
        // had to look up to get how to set the size
       mainFrame.pack();
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private class ButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent event) {
            JButton nextSolution = (JButton) event.getSource();
            int optionNumber = Integer.parseInt(nextSolution.getText().substring(10)) - 1;
            TraceState nextSolutionState = bestPaths.get(optionNumber);
            CircuitBoard nextSolutionBoard = nextSolutionState.getBoard();
            gridPanel.removeAll();
            for (int i = 0; i < nextSolutionBoard.numRows(); i++) {
                for (int k = 0; k < nextSolutionBoard.numCols(); k++) {
                    JLabel currentLabel = new JLabel(String.valueOf(nextSolutionBoard.charAt(i, k)));
                    currentLabel.setFont(myFont);
                    if (currentLabel.getText().equals("T")) {
                        
                        currentLabel.setForeground(Color.RED);
                    }
                    // JButton currButton = new JButton(currentLabel);
                    gridPanel.add(currentLabel);
                    // not a button tho
                }
            }
            gridPanel.revalidate();
            gridPanel.repaint();
            

        }
    }



private class MenuListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent event) {
        JMenuItem clickedMenuOption = (JMenuItem) event.getSource();
        //String s = event.getActionCommand();
        // if (s.equals("About")){
        //     JDialog popUp = new JDialog(mainFrame, "About");
        //     JLabel myLabel = new JLabel("Written by Aris Vetter (arisvetter@u.boisestate.edu)");
        //     popUp.add(myLabel);
        //     popUp.setVisible(true);
        // }
        if(clickedMenuOption.getText().equals("About")){
            System.out.println("HELLO");
            JOptionPane.showMessageDialog(mainFrame, "Eggs are not supposed to be green.");
        }

    }

}
}
/*
 * if a button on the tic tac toe grid is chosen, the button displays the move,
 * and the opponent's position is randomly chosen
 * 
 * private class buttonListener implements ActionListener {
 * 
 * @Override
 * public void actionPerformed(ActionEvent event) {
 * 
 * //identify the button that was clicked
 * JButton clicked = (JButton) event.getSource();
 * 
 * //find the row and the column of the button that was clicked
 * int row;
 * int col;
 * boolean tryPlay = true;
 * 
 * //if the button is the one chosen, move if valid
 * for (int i = 0; i < 3; i++) {
 * for (int k = 0; k < 3; k++) {
 * if (grid[i][k] == event.getSource()) {
 * row = i;
 * col = k;
 * 
 * //make move if the player is X
 * if (playerIsX) {
 * if (myGame.choose(TicTacToe.BoardChoice.X, row, col)) {
 * clicked.setText("X");
 * movesString += "\n X: row " + row + ", col " + col;
 * 
 * //opponent moves if player is X
 * while (tryPlay && (!(myGame.gameOver()))) {
 * int oppRow = rndm.nextInt(3);
 * int oppCol = rndm.nextInt(3);
 * if (myGame.choose(TicTacToe.BoardChoice.O, oppRow, oppCol)) {
 * grid[oppRow][oppCol].setText("O");
 * movesString += "\n O: row " + oppRow + ", col " + oppCol;
 * tryPlay = false;
 * }
 * }
 * 
 * }
 * 
 * //make move if the player is O
 * } else if (!playerIsX) {
 * if (myGame.choose(TicTacToe.BoardChoice.O, row, col)) {
 * clicked.setText("O");
 * movesString += "\n O: row " + row + ", col " + col;
 * 
 * //opponent moves if player is O
 * while (tryPlay && (!(myGame.gameOver()))) {
 * int oppRow = rndm.nextInt(3);
 * int oppCol = rndm.nextInt(3);
 * if (myGame.choose(TicTacToe.BoardChoice.X, oppRow, oppCol)) {
 * grid[oppRow][oppCol].setText("X");
 * movesString += "\n X: row " + oppRow + ", col " + oppCol;
 * tryPlay = false;
 * }
 * }
 * }
 * }
 * }
 * }
 * }
 * 
 * //if the game ends, follow the end of game protocol
 * if (myGame.gameOver()) {
 * guiGameOver();
 * }
 * }
 * 
 * }
 */