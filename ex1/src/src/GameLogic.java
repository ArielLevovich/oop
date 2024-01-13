package src;

import java.util.Stack;

import static java.lang.System.*;

public class GameLogic implements PlayableLogic {
    private final int BOARD_SIZE = 11;
    private final Stack<ConcreatePiece[][]> gameStates = new Stack<>();
    private ConcreatePiece[][] board = new ConcreatePiece[BOARD_SIZE][BOARD_SIZE];
    private final ConcreatePlayer attackPlayer = new ConcreatePlayer(false);
    private final ConcreatePlayer defendPlayer = new ConcreatePlayer(true);
    private boolean gameFinish; //check this Boolean
    private boolean isAttTurn;//is attacker turn
    private Position kingPos;

    public GameLogic() {
        reset();
    }

    @Override
    public void reset() {
        this.board = new ConcreatePiece[BOARD_SIZE][BOARD_SIZE];
        this.gameFinish = false;
        this.isAttTurn = true;
        this.kingPos= new Position(5,5);

        int center = BOARD_SIZE / 2;
        // Place pawns of type "♙" around the king - defender
        board[5][3] = new Pawn(this.defendPlayer, 1);
        board[4][4] = new Pawn(this.defendPlayer, 2);
        board[5][4] = new Pawn(this.defendPlayer, 3);
        board[6][4] = new Pawn(this.defendPlayer, 4);
        board[3][5] = new Pawn(this.defendPlayer, 5);
        board[4][5] = new Pawn(this.defendPlayer, 6);
        // Place the king at the center of the board
        board[center][center] = new King(7);
        board[6][5] = new Pawn(this.defendPlayer, 8);
        board[7][5] = new Pawn(this.defendPlayer, 9);
        board[4][6] = new Pawn(this.defendPlayer, 10);
        board[5][6] = new Pawn(this.defendPlayer, 11);
        board[6][6] = new Pawn(this.defendPlayer, 12);
        board[5][7] = new Pawn(this.defendPlayer, 13);

        // Place pawns of type "♟" around the board - attacker
        board[3][0] = new Pawn(this.attackPlayer,1);
        board[4][0] = new Pawn(this.attackPlayer,2);
        board[5][0] = new Pawn(this.attackPlayer,3);
        board[6][0] = new Pawn(this.attackPlayer,4);
        board[7][0] = new Pawn(this.attackPlayer,5);
        board[5][1] = new Pawn(this.attackPlayer,6);

        board[0][3] = new Pawn(this.attackPlayer,7);
        board[0][4] = new Pawn(this.attackPlayer,9);
        board[0][5] = new Pawn(this.attackPlayer,11);
        board[0][6] = new Pawn(this.attackPlayer,15);
        board[0][7] = new Pawn(this.attackPlayer,17);
        board[1][5] = new Pawn(this.attackPlayer,12);

        board[10][3] = new Pawn(this.attackPlayer,8);
        board[10][4] = new Pawn(this.attackPlayer,10);
        board[10][5] = new Pawn(this.attackPlayer,14);
        board[10][6] = new Pawn(this.attackPlayer,16);
        board[10][7] = new Pawn(this.attackPlayer,18);
        board[9][5] = new Pawn(this.attackPlayer,13);

        board[3][10] = new Pawn(this.attackPlayer,20);
        board[4][10] = new Pawn(this.attackPlayer,21);
        board[5][10] = new Pawn(this.attackPlayer,22);
        board[6][10] = new Pawn(this.attackPlayer,23);
        board[7][10] = new Pawn(this.attackPlayer,24);
        board[5][9] = new Pawn(this.attackPlayer,19);
    }


    public boolean move(Position a, Position b) {
        if(!isMoveLegal(a,b))
            return false;
        else if(board[a.getX()][a.getY()] instanceof King) {
            saveState();
            isAttTurn = !isAttTurn;
            kingMove(a,b);
            isGameFinished();
            return true;
        }
        else {
            saveState();
            isAttTurn = !isAttTurn;
            if (isGameFinished())
                return true;

            ConcreatePiece temp= board[a.getX()][a.getY()];
            this.board[b.getX()][b.getY()] = temp;
            this.board[a.getX()][a.getY()]=null;
            eat(b);
            return true;
        }
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

    public void kingMove(Position a,Position b)
    {
        this.kingPos.setPos(b.getX(), b.getY());
        ConcreatePiece temp= board[a.getX()][a.getY()];
        this.board[b.getX()][b.getY()] = temp;
        this.board[a.getX()][a.getY()]=null;

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
                            board[otherPlayerX][otherPlayerY] = null;
                        } else if (!isInsideTheBoard(myPlayerX, myPlayerY)) { // or the other player is on the edge of the board.
                            board[otherPlayerX][otherPlayerY] = null;
                        } else if (isCornerPosition(myPlayerPosition)) {
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
        int x=this.kingPos.getX();
        int y=this.kingPos.getY();
        if(isKingSurrounded(x,y))
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

    private boolean isKingSurrounded(int x, int y) {
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
        attackPlayer.wins();
        reset();
    }

    public void defWin()
    {
        defendPlayer.wins();
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

