package src;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Stack;

public class GameLogic implements PlayableLogic {
    private final int BOARD_SIZE = 11;
    private final Stack<ConcreatePiece[][]> gameStates = new Stack<>();
    private final Stack<HashSet<String>[][]> squareStates = new Stack<>();
    private ConcreatePiece[][] board;
    private HashSet<String>[][] squares;
    private ArrayList<ConcreatePiece> attackers;
    private ArrayList<ConcreatePiece> defenders;
    private final ConcreatePlayer attackPlayer = new ConcreatePlayer(false);
    private final ConcreatePlayer defendPlayer = new ConcreatePlayer(true);
    private boolean isAttTurn;//is attacker turn
    private Position kingPos;
    private boolean isDefenderWon;
    private final String asterisks = "***************************************************************************"; // 75 asterisks
    public GameLogic() {
        reset();
    }

    @Override
    public void reset() {
        this.isAttTurn = true;
        this.kingPos = new Position(5,5);

        initDefenders();
        initAttackers();
        initSquares();
        initBoard();
    }

    private void initSquares() {
        squares = new HashSet[BOARD_SIZE][BOARD_SIZE];
    }

    private void initBoard() {
        this.board = new ConcreatePiece[BOARD_SIZE][BOARD_SIZE];
        for (ConcreatePiece piece : this.defenders) {
            SetBoardSquare(piece.getFirstPosition(), piece);
        }
        for (ConcreatePiece piece : this.attackers) {
            SetBoardSquare(piece.getFirstPosition(), piece);
        }
    }

    private void SetBoardSquare(Position position, ConcreatePiece piece) {
        int x = position.getX();
        int y = position.getY();
        this.board[x][y] = piece;
        if (this.squares[x][y] == null) {
            this.squares[x][y] = new HashSet<>();
        }
        if (piece != null) {
            this.squares[x][y].add(piece.getTitle());
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
            board[b.getX()][b.getY()].addDistance(a,b);
            if (isGameFinished()) {
                return true;
            }
            if(board[b.getX()][b.getY()] instanceof Pawn) {
                eat(b);
            }
            return true;
        }
    }

    private void printPiecesByMovesHistory(ArrayList<ConcreatePiece> pieces) {
        pieces.sort((p1, p2) -> new MoveHistorySizeComparator(isDefenderWon).compare(p1, p2));

        for (ConcreatePiece piece : pieces) {
            ArrayList<Position> movesHistory = piece.getMovesHistory();
            if(movesHistory.size() >=2) {
                //writer.print(piece.getTitle() + ": [");
                System.out.print(piece.getTitle() + ": [");
                for (int i = 0; i < movesHistory.size(); i++) {
                    Position position = movesHistory.get(i);
                    //writer.print("(" + position.getX() + ", " + position.getY() + ")");
                    System.out.print("(" + position.getX() + ", " + position.getY() + ")");

                    // Add comma only if it's not the last position
                    if (i != movesHistory.size() - 1) {
                        System.out.print(", ");
                    }
                }
                System.out.println(("]"));
            }
        }
        System.out.println(asterisks);
    }

    private void printPlayerByKillsCount(ArrayList<ConcreatePiece> pieces) {
        pieces.sort((p1, p2) -> new KillsCountComparator(isDefenderWon).compare(p1, p2));
        for (ConcreatePiece piece : pieces) {
            int killsCount = piece.getKillsCount();
            if(killsCount == 0) continue;
            System.out.println(piece.getTitle() + ": " + killsCount + " kills");
        }
        System.out.println(asterisks);
    }

    private void printPlayersByDistance(ArrayList<ConcreatePiece> pieces) {
        pieces.sort((p1, p2) -> new DistanceCountComparator(isDefenderWon).compare(p1, p2));
        for (ConcreatePiece piece : pieces) {
            int distanceCount = piece.getDistance();
            if(distanceCount == 0) continue;
            System.out.print(piece.getTitle() + ": ");
            System.out.println(distanceCount + " squares");
        }
        System.out.println(asterisks);
    }

    private void printSquares() {
        ArrayList<Square> squares = createSquaresList();
        squares.sort((s1, s2) -> new SquareComparator().compare(s1, s2));
        for (Square square : squares) {
            int x = square.getX();
            int y = square.getY();
            HashSet<String> piecesTitles = square.getPiecesTitles();
            System.out.println("(" + x + ", " + y + ")" + piecesTitles.size() + " pieces");
        }
        System.out.println(asterisks);
    }

    private ArrayList<Square> createSquaresList() {
        ArrayList<Square> squares = new ArrayList<>();
        for (int x = 0; x < BOARD_SIZE; x++) {
            for (int y = 0; y < BOARD_SIZE; y++) {
                HashSet<String> piecesTitles = this.squares[x][y];
                if (piecesTitles != null && piecesTitles.size() > 1) {
                    squares.add(new Square(x, y, piecesTitles));
                }
            }
        }
        return squares;
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
        SetBoardSquare(newPos, piece);
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
                            transformTwoSquares(a, otherPlayerX, otherPlayerY);
                        } else if (!isInsideTheBoard(myPlayerX, myPlayerY)) { // or the other player is on the edge of the board.
                            transformTwoSquares(a, otherPlayerX, otherPlayerY);
                        } else if (isCornerPosition(myPlayerPosition)) {
                            transformTwoSquares(a, otherPlayerX, otherPlayerY);
                        }
                    }
                }
            }
        }
    }

    private void transformTwoSquares(Position a, int otherPlayerX, int otherPlayerY) {
        (board[a.getX()][a.getY()]).increaseKills();
        board[otherPlayerX][otherPlayerY] = null;
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
            attackerWin();
            return true;
        }

        if(this.isCornerPosition(this.kingPos)) {
            defenderWon();
            return true;
        }
        return false;
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

    public void attackerWin()
    {
        isDefenderWon = false;
        attackPlayer.wins();
        printStatistics();
        reset();
    }

    private void printStatistics() {
        ArrayList<ConcreatePiece> players = createALlPlayersList();
        printPiecesByMovesHistory(players);
        printPlayerByKillsCount(players);
        printPlayersByDistance(players);
        printSquares();
    }

    public void defenderWon()
    {
        isDefenderWon = true;
        defendPlayer.wins();
        printStatistics();
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
        if (!squareStates.isEmpty()) {
            this.squares = squareStates.pop();
        }
        isAttTurn = !isAttTurn;
    }

    @Override
    public int getBoardSize()
    {
        return BOARD_SIZE;
    }

    private void saveState() {
        gameStates.push(copyBoard());
        squareStates.push(copySquares());
    }

    private HashSet<String>[][] copySquares() {
        HashSet<String>[][] copy = new HashSet[BOARD_SIZE][BOARD_SIZE];
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (squares[i][j] != null) {
                    copy[i][j] = new HashSet<>(squares[i][j]);
                }
            }
        }
        return copy;
    }

    // Helper method to create a deep copy of the board
    private ConcreatePiece[][] copyBoard() {
        ConcreatePiece[][] copy = new ConcreatePiece[BOARD_SIZE][BOARD_SIZE];
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                ConcreatePiece piece = board[i][j];
                if (piece != null) {
                    if (piece.getType().equals("♔")) {
                        copy[i][j] = new King(piece.getTitleNumber(), piece.getFirstPosition());
                    } else {
                        copy[i][j] = new Pawn(piece.getOwner(), piece.getTitleNumber(), piece.getFirstPosition());
                    }
                    copy[i][j].setDistance(piece.getDistance());
                    copy[i][j].setKillsCount(piece.getKillsCount());
                    // copy the moves history
                    copy[i][j].setMovesHistory(piece.getMovesHistory());
                }
            }

        }
        return copy;
    }

    private ArrayList<ConcreatePiece> createALlPlayersList() {
        ArrayList<ConcreatePiece> allPlayers = new ArrayList<>();
        // if there was at least one "undo", defenders and attackers lists are not updated.
        // therefore, we will add all the pieces from the board to the list.
        for (ConcreatePiece[] row : board) {
            for (ConcreatePiece piece : row) {
                if (piece != null) {
                    allPlayers.add(piece);
                }
            }
        }
        return allPlayers;
    }
}
