package src;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
//import java.util.Comparator;
import java.util.Comparator;
import java.util.Stack;

import static java.lang.System.*;

public class GameLogic implements PlayableLogic {
    private final int BOARD_SIZE = 11;
    private final Stack<ConcreatePiece[][]> gameStates = new Stack<>();
    private ConcreatePiece[][] board = new ConcreatePiece[BOARD_SIZE][BOARD_SIZE];

    private ArrayList<ConcreatePiece> attackers;
    private ArrayList<ConcreatePiece> defenders;
    private final ConcreatePlayer attackPlayer = new ConcreatePlayer(false);
    private final ConcreatePlayer defendPlayer = new ConcreatePlayer(true);
    private boolean gameFinish; //check this Boolean
    private boolean isAttTurn;//is attacker turn
    private Position kingPos;
    private boolean isDefenderWin;
    public GameLogic() {
        reset();
    }

    @Override
    public void reset() {
        this.gameFinish = false;
        this.isAttTurn = true;
        this.kingPos = new Position(5,5);

        initDefenders();
        initAttackers();
        initBoard();
    }

    private void initBoard() {
        this.board = new ConcreatePiece[BOARD_SIZE][BOARD_SIZE];
        for (ConcreatePiece piece : this.defenders) {
            this.board[piece.getFirstPosition().getX()][piece.getFirstPosition().getY()] = piece;
        }
        for (ConcreatePiece piece : this.attackers) {
            this.board[piece.getFirstPosition().getX()][piece.getFirstPosition().getY()] = piece;
        }
    }

    private void initDefenders() {
        this.defenders = new ArrayList<>();
        this.defenders.add(new Pawn(this.defendPlayer, 1, new Position(5, 3)));
        // Place pawns of type "♙" around the king - defender
        this.defenders.add(new Pawn(this.defendPlayer, 2, new Position(4, 4)));
        this.defenders.add(new Pawn(this.defendPlayer, 3, new Position(5, 4)));
        this.defenders.add(new Pawn(this.defendPlayer, 4, new Position(6, 4)));
        this.defenders.add(new Pawn(this.defendPlayer, 5, new Position(3, 5)));
        this.defenders.add(new Pawn(this.defendPlayer, 6, new Position(4, 5)));
        // Place the king at the center of the board
        this.defenders.add(new King(7, this.kingPos));
        this.defenders.add(new Pawn(this.defendPlayer, 8, new Position(6, 5)));
        this.defenders.add(new Pawn(this.defendPlayer, 9, new Position(7, 5)));
        this.defenders.add(new Pawn(this.defendPlayer, 10, new Position(4, 6)));
        this.defenders.add(new Pawn(this.defendPlayer, 11, new Position(5, 6)));
        this.defenders.add(new Pawn(this.defendPlayer, 12, new Position(6, 6)));
        this.defenders.add(new Pawn(this.defendPlayer, 13, new Position(5, 7)));
    }

    private void initAttackers() {
        this.attackers = new ArrayList<>();
        // Place pawns of type "♟" around the board - attacker
        this.attackers.add(new Pawn(this.attackPlayer,1, new Position(3, 0)));
        this.attackers.add(new Pawn(this.attackPlayer,2, new Position(4, 0)));
        this.attackers.add(new Pawn(this.attackPlayer,3, new Position(5, 0)));
        this.attackers.add(new Pawn(this.attackPlayer,4, new Position(6, 0)));
        this.attackers.add(new Pawn(this.attackPlayer,5, new Position(7, 0)));
        this.attackers.add(new Pawn(this.attackPlayer,6, new Position(5, 1)));

        this.attackers.add(new Pawn(this.attackPlayer,7, new Position(0, 3)));
        this.attackers.add(new Pawn(this.attackPlayer,9, new Position(0, 4)));
        this.attackers.add(new Pawn(this.attackPlayer,11, new Position(0, 5)));
        this.attackers.add(new Pawn(this.attackPlayer,15, new Position(0, 6)));
        this.attackers.add(new Pawn(this.attackPlayer,17, new Position(0, 7)));
        this.attackers.add(new Pawn(this.attackPlayer,12, new Position(1, 5)));

        this.attackers.add(new Pawn(this.attackPlayer,8, new Position(10, 3)));
        this.attackers.add(new Pawn(this.attackPlayer,10, new Position(10, 4)));
        this.attackers.add(new Pawn(this.attackPlayer,14, new Position(10, 5)));
        this.attackers.add(new Pawn(this.attackPlayer,16, new Position(10, 6)));
        this.attackers.add(new Pawn(this.attackPlayer,18, new Position(10, 7)));
        this.attackers.add(new Pawn(this.attackPlayer,13, new Position(9, 5)));

        this.attackers.add(new Pawn(this.attackPlayer,20, new Position(3, 10)));
        this.attackers.add(new Pawn(this.attackPlayer,21, new Position(4, 10)));
        this.attackers.add(new Pawn(this.attackPlayer,22, new Position(5, 10)));
        this.attackers.add(new Pawn(this.attackPlayer,23, new Position(6, 10)));
        this.attackers.add(new Pawn(this.attackPlayer,24, new Position(7, 10)));
        this.attackers.add(new Pawn(this.attackPlayer,19, new Position(5, 9)));
    }
    public boolean move(Position a, Position b) {
        if(!isMoveLegal(a,b)) {
            return false;
        }
        else {
            saveState();
            isAttTurn = !isAttTurn;
            movePiece(a,b);
            if (isGameFinished()) {
                return true;
            }
            if(board[b.getX()][b.getY()] instanceof Pawn) {
                eat(b);
            }
            return true;
        }
    }

    private void createStatistics1() {
        try {
            PrintWriter writer = new PrintWriter("statistics.txt", StandardCharsets.UTF_8);
            if (isDefenderWin) {
                writeDefenders1(writer);
                writeAttackers1(writer);
            } else {
                writeAttackers1(writer);
                writeDefenders1(writer);
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("An error occurred while creating statistics.txt");
            e.printStackTrace();
        }
    }

    private void createStatistics2() {
        try {
            PrintWriter writer = new PrintWriter("statistics.txt", StandardCharsets.UTF_8);
            writeAllPlayers2(writer);
            writer.close();
        } catch (IOException e) {
            System.out.println("An error occurred while creating statistics.txt");
            e.printStackTrace();
        }
    }

    private void writeAttackers1(PrintWriter writer) {
        writePieces1(writer, attackers);
    }

    private void writeDefenders1(PrintWriter writer) {
        writePieces1(writer, defenders);
    }


    private void writeAllPlayers2(PrintWriter writer) {
        ArrayList<ConcreatePiece> allPlayers = new ArrayList<>();
        for (ConcreatePiece piece : this.attackers) {
            if (!(piece instanceof King)) {
                allPlayers.add(piece);
            }
        }
        for (ConcreatePiece piece : this.defenders) {
            if (!(piece instanceof King)) {
                allPlayers.add(piece);
            }
        }
        writePieces2(writer, allPlayers);
    }

    private void writePieces1(PrintWriter writer, ArrayList<ConcreatePiece> pieces) {
        pieces.sort((p1, p2) -> new ListSizeComparator().compare(p1, p2));

        for (ConcreatePiece piece : pieces) {
            ArrayList<Position> movesHistory = piece.getMovesHistory();
            if(movesHistory.size() >=2) {
                writer.print(piece.getTitle() + ": [");
                    for (int i = 0; i < movesHistory.size(); i++) {
                        Position position = movesHistory.get(i);
                        writer.print("(" + position.getX() + ", " + position.getY() + ")");
                        // Add comma only if it's not the last position
                        if (i != movesHistory.size() - 1) {
                            writer.print(", ");
                        }
                    }
            }
            writer.println("]");
        }
    }

    private void writePieces2(PrintWriter writer, ArrayList<ConcreatePiece> pieces) {
        pieces.sort((p1, p2) -> new KillsCountComp().compare(p1, p2));
        String asterisks = "*******************************************************************"; // 75 asterisks
        writer.print(asterisks);
        writer.println();
        for (ConcreatePiece piece : pieces) {
            int killsCount = ((Pawn) piece).getKillsCount();
            if(killsCount == 0) continue;
            writer.print(piece.getTitle() + ": [");
            writer.print("kills count: " + killsCount);
        }

        writer.println("]");
    }


    public boolean isMoveLegal(Position a, Position b) {
        if (a == null) {
            return false;
        }
        if (board[a.getX()][a.getY()] instanceof Pawn) {
            if (isCornerPosition(b))
                return false;
        }
        if ((isAttTurn && board[a.getX()][a.getY()].getOwner().isPlayerOne()) || (!isAttTurn && !board[a.getX()][a.getY()].getOwner().isPlayerOne())) {
            return false;
        }
        if (a.getX() != b.getX() && a.getY() != b.getY()) {
            return false;
        }
        if (board[b.getX()][b.getY()] != null) {
            return false;
        }
        if (a.getX() < b.getX()) {
            for (int i = a.getX() + 1; i < b.getX(); i++) {
                if (board[i][a.getY()] != null) {
                    return false;
                }
            }
        } else if (a.getX() > b.getX()) {
            for (int j = a.getX() - 1; j > b.getX(); j--) {
                if (board[j][a.getY()] != null) {
                    return false;
                }
            }
        } else if (a.getY() < b.getY()) {
            for (int k = a.getY() + 1; k < b.getY(); k++) {
                if (board[a.getX()][k] != null) {
                    return false;
                }
            }
        } else if (a.getY() > b.getY()) {
            for (int l = a.getY() - 1; l > b.getY(); l--) {
                if (board[a.getX()][l] != null) {
                    return false;
                }
            }
        }
        return true;
    }

    public void movePiece(Position curPos, Position newPos)
    {
        ConcreatePiece piece = board[curPos.getX()][curPos.getY()];
        piece.addMove(newPos);
        this.board[newPos.getX()][newPos.getY()] = piece;
        this.board[curPos.getX()][curPos.getY()] = null;
        if (piece instanceof King) {
            this.kingPos = newPos;
        }
    }
    public void eat(Position a) {
        ConcreatePiece myLock = board[a.getX()][a.getY()];
        int[] xArr = {0,-1,0,1};
        int[] yArr = {-1,0,1,0};
        for (int i=0;i<4;i++) {
            int otherPlayerX = a.getX() + xArr[i];
            int otherPlayerY = a.getY() + yArr[i];
            if (isInsideTheBoard(otherPlayerX, otherPlayerY)) {
                ConcreatePiece lookAround = board[otherPlayerX][otherPlayerY];
                if (lookAround != null && (!lookAround.getType().equals("♔"))) {
                    if (!(lookAround.getType()).equals(myLock.getType())) {
                        int myPlayerX = otherPlayerX + xArr[i];
                        int myPlayerY = otherPlayerY + yArr[i];
                        Position myPlayerPosition = new Position(myPlayerX, myPlayerY);
                        // if the next piece is the same type as the current player (my players surround the other player)
                        if (isInsideTheBoard(myPlayerX, myPlayerY) && board[myPlayerX][myPlayerY] != null && isOfTheSameType(a, myPlayerPosition)) {
                            ((Pawn) board[a.getX()][a.getY()]).increaseKills();
                            board[otherPlayerX][otherPlayerY] = null;
                        } else if (!isInsideTheBoard(myPlayerX, myPlayerY)) { // or the other player is on the edge of the board.
                            ((Pawn) board[a.getX()][a.getY()]).increaseKills();
                            board[otherPlayerX][otherPlayerY] = null;
                        } else if (isCornerPosition(myPlayerPosition)) {
                            ((Pawn) board[a.getX()][a.getY()]).increaseKills();
                            board[otherPlayerX][otherPlayerY] = null;
                        }
                    }
                }
            }
        }
    }

    private boolean isCornerPosition(Position myPlayerPosition) {
        int x = myPlayerPosition.getX();
        int y = myPlayerPosition.getY();
        return (x == 0 && y == 0) || (x == 0 && y == BOARD_SIZE - 1) || (x == BOARD_SIZE - 1 && y == 0) || (x == BOARD_SIZE - 1 && y == BOARD_SIZE - 1);
    }

    private boolean isOfTheSameType(Position a, Position b) {
        return board[a.getX()][a.getY()].getType().equals(board[b.getX()][b.getY()].getType());
    }

    private boolean isInsideTheBoard(int x, int y) {
        return x >= 0 && x < BOARD_SIZE && y >= 0 && y < BOARD_SIZE;
    }

    public Piece getPieceAtPosition(Position position)
    {
        return this.board[position.getX()][position.getY()];
    }

    @Override
    public Player getFirstPlayer()
    {
        return this.defendPlayer;
    }

    @Override
    public Player getSecondPlayer()
    {
        return this.attackPlayer;
    }

    @Override
    public boolean isGameFinished()
    {
        if(isKingSurrounded())
        {
            attWin();
            return true;
        }

        if(this.isCornerPosition(this.kingPos)) {
            defWin();
            return true;
        }
        return false;
    }

     class KillsCountComp implements Comparator<ConcreatePiece> {
        @Override
        public int compare(ConcreatePiece o1, ConcreatePiece o2) {
            // First, compare by killsCount in descending order
            int killsCompare = Integer.compare(((Pawn)o2).getKillsCount(),((Pawn)o1).getKillsCount());
            if (killsCompare != 0) {
                return killsCompare;
            }

            // If killsCount is equal, compare by serial title
            int titleCompare = o1.getTitle().compareTo(o2.getTitle());
            // elian please check that !sub(01) == sub(s2)
            if (titleCompare != 0 ) {
                return titleCompare;
            }

            // If both killsCount and serial title are equal, compare by winning team
            if(compareByWinningTeam(o1, o2) == -1)
                return Integer.compare(((Pawn)o2).getKillsCount(),((Pawn)o1).getKillsCount());
            else return Integer.compare(((Pawn)o1).getKillsCount(),((Pawn)o2).getKillsCount());
        }
    }

    private int compareByWinningTeam(ConcreatePiece o1, ConcreatePiece o2) {
        if(isDefenderWin) {
            if (o1.getOwner().isPlayerOne()) {
                return 1;
            } else {
                return -1;
            }
        }
        else {
            if (o1.getOwner().isPlayerOne()) {
                return 1;
            } else {
                return -1;
            }
        }
    }

    private boolean isKingSurrounded() {
        int x = this.kingPos.getX();
        int y = this.kingPos.getY();
        int enemyCount = 0;
        if (x > 0 && this.board[x - 1][y] != null && this.board[x - 1][y].getOwner().isPlayerOne() != this.board[x][y].getOwner().isPlayerOne()) {
            ++enemyCount;
        }

        if (x < this.board.length - 1 && this.board[x + 1][y] != null && this.board[x + 1][y].getOwner().isPlayerOne() != this.board[x][y].getOwner().isPlayerOne()) {
            ++enemyCount;
        }

        if (y > 0 && this.board[x][y - 1] != null && this.board[x][y - 1].getOwner().isPlayerOne() != this.board[x][y].getOwner().isPlayerOne()) {
            ++enemyCount;
        }

        if (y < this.board[0].length - 1 && this.board[x][y + 1] != null && this.board[x][y + 1].getOwner().isPlayerOne() != this.board[x][y].getOwner().isPlayerOne()) {
            ++enemyCount;
        }

        if (x != 0 && x != this.board.length - 1 && y != 0 && y != this.board[0].length - 1) {
            return enemyCount == 4;
        } else {
            return enemyCount >= 3;
        }
    }

    public void attWin()
    {
        isDefenderWin = false;
        attackPlayer.wins();
        createStatistics1();
        createStatistics2();
        reset();
    }

    public void defWin()
    {
        isDefenderWin = true;
        defendPlayer.wins();
        createStatistics1();
        createStatistics2();
        reset();
    }

    @Override
    public boolean isSecondPlayerTurn()
    {
        return this.isAttTurn;
    }

    @Override
    public void undoLastMove()
    {
        if (!gameStates.isEmpty()) {
            this.board = gameStates.pop();
        }
    }

    @Override
    public int getBoardSize()
    {
        return BOARD_SIZE;
    }

    private void saveState() {
        gameStates.push(copyBoard());
    }
    // Helper method to create a copy of the board
    private ConcreatePiece[][] copyBoard() {
        ConcreatePiece[][] copy = new ConcreatePiece[BOARD_SIZE][BOARD_SIZE];
        for (int i = 0; i < BOARD_SIZE; i++) {
            arraycopy(board[i], 0, copy[i], 0, BOARD_SIZE);
        }
        return copy;
    }
}


