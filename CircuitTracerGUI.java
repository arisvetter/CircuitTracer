import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CircuitTracerGUI {
    private ArrayList<TraceState> bestPaths;
    private JPanel gridPanel;

    public CircuitTracerGUI(CircuitBoard myBoard, ArrayList<TraceState> bestPaths) {
        this.bestPaths = bestPaths;
        JFrame mainFrame = new JFrame("Circuit Tracer");
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        JPanel solutionOptions = new JPanel();

        gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(myBoard.numRows(), myBoard.numCols()));

        // or do i just wanna change the path

        for (int i = 0; i < myBoard.numRows(); i++) {
            for (int k = 0; k < myBoard.numCols(); k++) {
                JLabel currentLabel = new JLabel(String.valueOf(myBoard.charAt(i, k)));
                // JButton currButton = new JButton(currentLabel);
                gridPanel.add(currentLabel);
                // not a button tho
            }
        }
        for (int i = 0; i < bestPaths.size(); i++) {
            JButton currentOption = new JButton("Solution #" + (i + 1));
            //JLabel numOption = new JLabel("Solution #" + (i + 1));
            //currentOption.add(numOption);
            currentOption.addActionListener(new ButtonListener());
            solutionOptions.add(currentOption);
        }
        mainPanel.add(gridPanel, BorderLayout.WEST);
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