import java.util.*;  
class Helper {
	    Random rand = new Random();
    	int[][] board = new int[4][4];
    	int[] dirRow = {1,0,-1,0};
    	int[] dirColumn = {0,1,0,-1};
    	public String generateUnoccupiedPosition(){
    		int occupied = 1; int row=-1,column=-1;
    		while(occupied==1) {
    			row = rand.nextInt(4);
    			column = rand.nextInt(4);
    			if(board[row][column] == 0) {
    				occupied=0;
    			}
    		}
    		
    		if(row==-1 || column==-1) return "-1";
    		String s = String.valueOf(row);
    		String s1 = String.valueOf(column);
    		return s+s1;
    	}
    	public boolean addPiece() {
    		String s = generateUnoccupiedPosition();
    		if(s=="-1") return false;
    		int val = rand.nextInt(2); 
        	board[s.charAt(0)-'0'][s.charAt(0)-'0']=(val%2==0?2:4);
        	return true;
    	}
        public void newGame() {
        	for(int i=0;i<4;i++) {
        		for(int j=0;j<4;j++) {
        			board[i][j]=0;
        		}
        	}
        	addPiece();
        }
        public void printUI() {
        	for(int i=0;i<4;i++) {
        		for(int j=0;j<4;j++) {
        			if(board[i][j] == 0) {
        				System.out.print("     |");
        			}else {
        				int m=board[i][j]; int cnt=0;
        				while(m>0) {cnt++; m/=10; }
        				System.out.print(board[i][j]);
        				m=5-cnt;
        				while(m-- > 0) System.out.print(" ");
        				System.out.print("|");
        			}
        			
        		}
        		System.out.println();
        		System.out.println("------------------------");
        	}
        	System.out.println("n: new game, w: up, s: down, a: left, q: quit");
        }
        public boolean canDoMove(int row,int column,int nextRow,int nextColumn) {
        	if(nextRow<0 || nextColumn<0 || nextRow>=4 || nextColumn>=4 ||
        			(board[row][column] != board[nextRow][nextColumn] && board[nextRow][nextColumn]!=0)) {
        		return false;
        	}
        	return true;
        }
        public boolean applyMove(int direction) {
        	int startRow=0,startColumn=0,rowStep=1,columnStep=1;
        	if(direction == 0) {
        		startRow=3; rowStep=-1;
        	}
        	if(direction == 1) {
        		startColumn=3; columnStep=-1;
        	}
        	boolean movePossible=false, canAddPiece=false;
        	do {
        		movePossible=false;
        	for(int i=startRow; i>=0 && i<4;i += rowStep) {
        		for(int j=startColumn; j>=0 && j<4;j+=columnStep) {
        			int nextI = i+dirRow[direction];
        			int nextJ = j+dirColumn[direction];
        			if(board[i][j]>0 && canDoMove(i,j,nextI,nextJ)) {
        				board[nextI][nextJ] += board[i][j];
        				
        				board[i][j]=0;
        				movePossible=true;
        				canAddPiece=true;
        			}
        		}
        	}
        	
        	printUI();
        	}while(movePossible);
        	for(int i=0;i<4;i++) {    // check for winning
        		for(int j=0;j<4;j++) {
        			if(board[i][j]==2048) {
        				System.out.println("You Won"); return false;
        			}
        		}
        	}
        	if(canAddPiece) { 
        		if(!addPiece()) {
        			System.out.println("Game Over");
        			return false;
        		}else return true;
        	}return true;
        
        }
    }
public class Code {
	
    
	public static void main(String[] args) {
		// TODO Auto-generated method stub
        //System.out.println("Hello World");
		
        Scanner sc= new Scanner(System.in);
        Helper g = new Helper();
        char[] commandToDir = new char[128];
        commandToDir['s']=0;
        commandToDir['d']=1;
        commandToDir['w']=2;
        commandToDir['a']=3;
        g.newGame();
		while(true) {
			
			g.printUI();
			String str = sc.next();
			char command = str.charAt(0);
			if(command == 'n') g.newGame();
			else if(command == 'q') break;
			else {
				int currentDirection = commandToDir[command];
				System.out.println(currentDirection);
				if(!g.applyMove(currentDirection)) break;
			}
			
		}
		
	}

}