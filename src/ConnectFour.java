import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class ConnectFour {

	public static String playerOne;
	public static String playerTwo;
	
	public static int xSize;
	public static int ySize;
	
	public static int winCount;
	
	public static int orderSelector;
	
    public static void main(String[] args) {
        int state = 0;
        
// >>>>>>>>>>>>>>   =========================================================================================== 
    	
        promptPlayerOne();
        promptPlayerTwo();
        promptRows(); 	//between 4 and 12 rows
        promptColumns();  //between 4 and 15 rows
        promptVictoryConditions(); //how many consecutive pieces required
        promptRandomOrder();
        
// >>>>>>>>>>>>>>   =========================================================================================== 
        	
    	gui Gui = new gui();
    	while (state != -1) {//checks if program is in quitting stage
    		switch (state) {
    		case 0://runtime
    			Gui.updateBoard();
    			if (Gui.getHasWon()) {
    				state = 1;
    			} else if (Gui.getHasDraw()) {
    				state = 2;
    			} else if (Gui.getNewGame()) {
    				Gui = new gui();
    				state = 0;
    			}
    			break;
    		case 1://endgame with winner
    			Gui.showWon();
    			if (Gui.getQuit()) {
    				state = -1;
    			} else  if (Gui.getNewGame()) {
    				Gui = new gui();
    				state = 0;
    			}
    			break;
    		case 2://endgame with drawgame
    			Gui.showDraw();
    			if (Gui.getQuit()) {
    				state = -1;
    			} else if (Gui.getNewGame()) {
    				Gui = new gui();
    				state = 0;
    			}
    			break;
    		}
    	}
    } 

    
// >>>>>>>>>>>>>>   ===========================================================================================     
    
    public static void promptPlayerOne(){
    	JFrame frame = new JFrame("playerPrompt");    	
    	
    	playerOne = (String)JOptionPane.showInputDialog(
    			frame,
    			"Enter Player 1 Name:\n",
    			"Player 1",
    			JOptionPane.PLAIN_MESSAGE,
    			null, 
    			null,
    			"Enter Name"
    			);   	
    }   
    
    public static void promptPlayerTwo(){
    	JFrame frame = new JFrame("playerPrompt");    	
    	
    	playerTwo = (String)JOptionPane.showInputDialog(
    			frame,
    			"Enter Player 2 Name:\n",
    			"Player 2",
    			JOptionPane.PLAIN_MESSAGE,
    			null, 
    			null,
    			"Enter Name"
    			);   	
    }
    
    public static void promptRows(){
    	JFrame frame = new JFrame("playerPrompt"); 
    	
    	String[] choices = { "4", "5", "6", "7", "8", "9", "10", "11", "12" };
    	
    	xSize = Integer.parseInt( (String) JOptionPane.showInputDialog(
    			frame, 
    			"Row Input",
    			playerOne, 
    			JOptionPane.PLAIN_MESSAGE,
    			null, 
    			choices,
    			choices[0]
    			));   	
    }
    
    public static void promptColumns(){
    	JFrame frame = new JFrame("playerPrompt");
    	
    	String[] choices = { "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15" };
    	
    	ySize = Integer.parseInt((String)JOptionPane.showInputDialog(
    			frame,
    			"Enter Player 2 Name:\n",
    			"Column Input",
    			JOptionPane.PLAIN_MESSAGE,
    			null, 
    			choices,
    			choices[4]
    			));   	
    }
    
    public static void promptVictoryConditions(){
    	JFrame frame = new JFrame("Victory Prompt");
    	
    	String[] choices = {"4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15"};
    	
    	winCount = Integer.parseInt((String)JOptionPane.showInputDialog(
    			frame,
    			"How many consecutive items to win? \n",
    			"Winning Conditions",
    			JOptionPane.PLAIN_MESSAGE,
    			null, 
    			choices,
    			choices[0]
    			));   	
    } 
    
    public static void promptRandomOrder(){
    	
    	JFrame frame = new JFrame("Random Prompt");
    	
    	//Custom button text
    	String[] choices = {"Random Order",
    	                    playerOne + " First",
    	                    playerTwo + " First"};    	
    	
    	orderSelector = JOptionPane.showOptionDialog(
    		frame,
    	    "Who Goes First?",
    	    "Order Selection",
    	    JOptionPane.YES_NO_CANCEL_OPTION,
    	    JOptionPane.PLAIN_MESSAGE,
    	    null,
    	    choices,
    	    choices[0]);
    	
    	System.out.println(orderSelector);  	
    	    	
    }
    
    public static boolean firstTurn(){
		int min = 1;
		int max = 50;

		Random r = new Random();
		int selector = r.nextInt(max - min + 1) + min;

		if (selector%2 == 0){
			return true;
		} else {
			return false;
		}
	}
    
// >>>>>>>>>>>>>>   =========================================================================================== 

}

class Grid {

    private int xsize;
    private int ysize;
    private int[][] matrix;
    private int cells_left = 0;

    public Grid() {
//        xsize = 7;
//        ysize = 6;
    	
    	xsize = ConnectFour.xSize;
    	ysize = ConnectFour.ySize;

        matrix = new int[xsize][ysize];
        for (int i = 0; i < xsize; i++) {
            for (int j = 0; j < ysize; j++) {
                matrix[i][j] = 0;
                cells_left++;
            }
        }
    }
    //methods to gain access to internal private data

    public int get_cells_left() {
        return cells_left;
    }

    public int[][] get_matrix() {
        return matrix;
    }

    public boolean matrix_equals(int a, int b, int c) {
        return matrix [a][b] == c;
    }

    public void set_matrix(int a, int b, int temp_player) {
        matrix[a][b] = temp_player;
    }

    public int get_xsize() {//returns the xsize
        return xsize;
    }

    public int get_ysize() {//returns the xsize
        return ysize;
    }

    public int find_y(int x) {//checks for room in collumn and returns free spot.
        int y = -1;
        for (int i = 0; i < ysize; i++) {
            if (matrix[x][i] == 0) {
                y = i;
            }
        }
        return y;
    }

    public int changeplayer(int player, int max_players) {
        player++;
        if (player > max_players) {
            return 1;
        }
        return player;
    }
}

class gui {
    //declaration of gui objects

    private JFrame frame;
    private JLabel[][] slots;
    private JButton[] buttons;
    //variables used in grid
//    private int xsize = 7;
//    private int ysize = 6;
    private int xsize = ConnectFour.xSize;
    private int ysize = ConnectFour.ySize;
    public static int currentPlayer = 1;
    //game variables to communicate with top program
    private boolean hasWon = false;
    private boolean hasDraw = false;
    private boolean quit = false;
    private boolean newGame = false;
    private int choice = 0;
    //making of grid and logic
    Grid my_grid = new Grid();
    logic my_logic = new logic(my_grid); //create game logic
    

    public gui() {

        frame = new JFrame("connect four");
        
        // <<<<<<<<<<<<<<<<<< START DAVID EDIT
        
        if (ConnectFour.orderSelector == 0 ){
        	if (ConnectFour.firstTurn() == true) {
        		currentPlayer = 1;
        	} else {
        		currentPlayer = 2;
        	}
        } else if (ConnectFour.orderSelector == 1){
        	currentPlayer = 1;
        } else {
        	currentPlayer = 2;
        }
        
        // set the title of the frame equal to whoever's turn it is firts
        if (currentPlayer == 1){
        	frame.setTitle("Connect Four! " + ConnectFour.playerOne + "'s turn");
        } else {
        	frame.setTitle("Connect Four! " + ConnectFour.playerTwo + "'s turn");
        }
        
        // <<<<<<<<<<<<<<<<<< END DAVID EDIT
        
        

        JPanel panel = (JPanel) frame.getContentPane();
        panel.setLayout(new GridLayout(xsize, ysize + 1));

        slots = new JLabel[xsize][ysize];
        buttons = new JButton[xsize];

        for (int i = 0; i < xsize; i++) {
            buttons[i] = new JButton("" + (i + 1));
            buttons[i].setActionCommand("" + i);
            buttons[i].addActionListener(
                    new ActionListener() {

                        public void actionPerformed(ActionEvent e) {
                            int a = Integer.parseInt(e.getActionCommand());
                            int y = my_grid.find_y(a);//check for space in collumn
                            if (y != -1) {
                                //sets a place to current player
                                if (my_logic.set_and_check(a, y, currentPlayer)) {
                                    hasWon = true;
                                } else if (my_logic.draw_game()) {//checks for drawgame
                                    hasDraw = true;
                                } else {
                                    //change player
                                    currentPlayer = my_grid.changeplayer(currentPlayer, 2);
                                    
// >>>>>>>>>>>>>>   ===========================================================================================                                    
                                    if (currentPlayer == 1){
                                    	frame.setTitle("Connect Four! " + ConnectFour.playerOne + "'s turn");
                                    } else {
                                    	frame.setTitle("Connect Four! " + ConnectFour.playerTwo + "'s turn");
                                    }
// >>>>>>>>>>>>>>   ===========================================================================================                            

                                }
                            } else {
                                JOptionPane.showMessageDialog(null, "choose another one", "column is filled", JOptionPane.INFORMATION_MESSAGE);
                            }
                        }
                    });
            panel.add(buttons[i]);
        }
        for (int column = 0; column < ysize; column++) {
            for (int row = 0; row < xsize; row++) {
                slots[row][column] = new JLabel();
                slots[row][column].setHorizontalAlignment(SwingConstants.CENTER);
                slots[row][column].setBorder(new LineBorder(Color.black));
                panel.add(slots[row][column]);
            }
        }

        //jframe stuff
        frame.setContentPane(panel);
        frame.setSize(700, 600);
        frame.setVisible(
                true);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
              
        
        // <<<<<<<<<<< David Edits
        frame.addWindowListener(new WindowAdapter(){
        	public void windowClosing(WindowEvent e){
        		handleClosing();
        	}
        });
  
    }
    
    private void handleClosing() {     	
    	
    	String[] buttonLabels = new String[] {"Yes", "No"};
        String defaultOption = buttonLabels[0];
        Icon icon = null;
         
        choice = JOptionPane.showOptionDialog(
        		frame,
                "There's still something unsaved.\n" +
                "Do you want to save before exiting?",
                "Warning",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE,
                icon,
                buttonLabels,
                defaultOption); 
        
        if (choice == 0){
        	frame.dispose();
        	System.exit(0);
        } else if (choice == 1){
        	
        }
        
        System.out.println(choice);
    }

    
    // <<<<<<< DAVID EDITS
    
    
    public void updateBoard() {//keeps the gui in sync with the logggggtjiic and grid
        for (int row = 0; row < xsize; row++) {
            for (int column = 0; column < ysize; column++) {
                if (my_grid.matrix_equals(row, column, 1)) {
                    slots[row][column].setOpaque(true);
                    Graphics2D g = (Graphics2D)slots[row][column].getGraphics();
                    int radiusX = frame.getWidth() / ConnectFour.xSize; 
                    int radiusY = frame.getHeight() / ConnectFour.ySize;
                    g.setColor(Color.red);                            
                    g.fillOval(0, 0, radiusX, radiusY);                    
                }
                if (my_grid.matrix_equals(row, column, 2)) {
                    slots[row][column].setOpaque(true);
                    Graphics2D g = (Graphics2D)slots[row][column].getGraphics();
                    int radiusX = frame.getWidth() / ConnectFour.xSize; 
                    int radiusY = frame.getHeight() / ConnectFour.ySize;
                    g.setColor(Color.yellow);                            
                    g.fillOval(0, 0, radiusX, radiusY);
                }
            }
        }
    }
    
 // <<<<<<< DAVID EDITS

    public void showWon() {
// =============================================================================
    	
    	String winner;
    	
    	if (gui.currentPlayer == 1){
    		winner = ConnectFour.playerOne + " wins";
    	} else {
    		winner = ConnectFour.playerTwo + " wins";
    	}
    	
// =============================================================================          

        int n = JOptionPane.showConfirmDialog(
                frame,
                "new game?",
                winner,
                JOptionPane.YES_NO_OPTION);
        if (n < 1) {
            frame.dispose();
            newGame = true;
        } else {
            frame.dispose();
            quit = true;
        }
    }

    public void showDraw() {
        String winner = "draw game";
        int n = JOptionPane.showConfirmDialog(
                frame,
                "new game?",
                winner,
                JOptionPane.YES_NO_OPTION);
        if (n < 1) {
            frame.dispose();
            newGame = true;
        } else {
            frame.dispose();
            quit = true;
        }
    }

    public boolean getHasWon() {
        return hasWon;
    }

    public boolean getHasDraw() {
        return hasDraw;
    }

    public boolean getQuit() {
        return quit;
    }

    public boolean getNewGame() {
        return newGame;
    }

    public static void main(String[] args) {
        gui Gui = new gui();
    }
    
//    public void WindowClosing(WindowEvent e)
//    {
//      int confirmed = JOptionPane.showConfirmDialog(null, 
//          "Are you sure you want to exit the program?", "Exit Program Message Box", 
//          JOptionPane.YES_NO_OPTION);
//
//      if (confirmed == JOptionPane.YES_OPTION)
//      {
//        dispose();
//      }
//    }

//    public Application()
//    {
//      setTitle("Primary School Application");
//      setLocation(50, 50);
//      setSize(800,600);
//      setLayout(new BorderLayout(20, 20));
//      setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
//      addWindowListener(new WindowAdapter());
//    }
    
    
    
    
}

class logic {

    private int cells_left = 0;
    private int max;
    private int xsize;
    private int ysize;
    Grid my_grid;

    public logic(Grid tempGrid) {
    	// CHANGE THIS VARIABLE TO CONTROL HOW MANY CONSECUTIVE SPOTS FOR VICTORY    	
        max = 5; //connect 4 or n
        // ^^^^^^^^^^^^^^^^^^^^^^^^^^^ 
        my_grid = tempGrid;
        cells_left = my_grid.get_cells_left();
        xsize = my_grid.get_xsize();
        ysize = my_grid.get_ysize();
    }

    public boolean set_and_check(int x, int y, int player) {//sets the found coordinate to current player
        my_grid.set_matrix(x, y, player);
        cells_left--;
        return check_one(x, y, 0, 1, player) //syd
                || check_one(x, y, -1, 1, player) //sydvest
                || check_one(x, y, -1, 0, player) //vest
                || check_one(x, y, 1, 1, player);//sydøst
    }

    public boolean draw_game() {//checks for draw game
        return cells_left == 0;
    }

    private boolean check_one(int x, int y, int dx, int dy, int player) {
        int count = 0;
        int tempx = x;
        int tempy = y;

        while (count < max && valid(tempx, tempy)) {
            if (!my_grid.matrix_equals(tempx, tempy, player)) {
                break;

            }
            tempx += dx;
            tempy += dy;
            count++;
        }
        tempx = x - dx;
        tempy = y - dy;
        while (count < max && valid(tempx, tempy)) {
            if (!my_grid.matrix_equals(tempx, tempy, player)) {
                break;
            }
            tempx -= dx;
            tempy -= dy;
            count++;
        }
        return count == max;
    }

    private boolean valid(int x, int y) {
        //if the bounds are set to be >0 only then first row and collumn 
        //doesnt work
        return x >= 0 && x < xsize && y >= 0 && y < ysize;
    }
}