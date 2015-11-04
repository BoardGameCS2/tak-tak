package tak.util;

import java.awt.Color;
import java.util.ArrayList;

import tak.com.Piece;
import tak.window.TakTakSingleplayerWindow;

public class PlayerAI {

	public static ArrayList<OrderedPair> getAllValidMoves(int row, int column) {

		Piece p = TakTakSingleplayerWindow.board[row][column];

		ArrayList<OrderedPair> moves = new ArrayList<>();

		if (p.getTopPiece().isKing()) {
			if (TakTakSingleplayerWindow.canPieceMoveToLocation(p.getTopPiece(), row + 1, column)) {
				moves.add(new OrderedPair(row + 1, column));
			}
			if (TakTakSingleplayerWindow.canPieceMoveToLocation(p.getTopPiece(), row + 2, column)) {
				moves.add(new OrderedPair(row + 2, column));
			}
			if (TakTakSingleplayerWindow.canPieceMoveToLocation(p.getTopPiece(), row + 1, column + 1)) {
				moves.add(new OrderedPair(row + 1, column + 1));
			}
			if (TakTakSingleplayerWindow.canPieceMoveToLocation(p.getTopPiece(), row + 2, column + 2)) {
				moves.add(new OrderedPair(row + 2, column + 2));
			}
			if (TakTakSingleplayerWindow.canPieceMoveToLocation(p.getTopPiece(), row + 1, column - 1)) {
				moves.add(new OrderedPair(row + 1, column - 1));
			}
			if (TakTakSingleplayerWindow.canPieceMoveToLocation(p.getTopPiece(), row + 2, column - 2)) {
				moves.add(new OrderedPair(row + 2, column - 2));
			}
			if (TakTakSingleplayerWindow.canPieceMoveToLocation(p.getTopPiece(), row - 1, column)) {
				moves.add(new OrderedPair(row - 1, column));
			}
			if (TakTakSingleplayerWindow.canPieceMoveToLocation(p.getTopPiece(), row - 2, column)) {
				moves.add(new OrderedPair(row - 2, column));
			}
			if (TakTakSingleplayerWindow.canPieceMoveToLocation(p.getTopPiece(), row - 1, column + 1)) {
				moves.add(new OrderedPair(row - 1, column + 1));
			}
			if (TakTakSingleplayerWindow.canPieceMoveToLocation(p.getTopPiece(), row - 2, column + 2)) {
				moves.add(new OrderedPair(row - 2, column + 2));
			}
			if (TakTakSingleplayerWindow.canPieceMoveToLocation(p.getTopPiece(), row - 1, column - 1)) {
				moves.add(new OrderedPair(row - 1, column - 1));
			}
			if (TakTakSingleplayerWindow.canPieceMoveToLocation(p.getTopPiece(), row - 2, column - 2)) {
				moves.add(new OrderedPair(row - 2, column - 2));
			}
			if (TakTakSingleplayerWindow.canPieceMoveToLocation(p.getTopPiece(), row, column - 1)) {
				moves.add(new OrderedPair(row, column - 1));
			}
			if (TakTakSingleplayerWindow.canPieceMoveToLocation(p.getTopPiece(), row, column - 2)) {
				moves.add(new OrderedPair(row, column - 2));
			}
			if (TakTakSingleplayerWindow.canPieceMoveToLocation(p.getTopPiece(), row, column + 1)) {
				moves.add(new OrderedPair(row, column + 1));
			}
			if (TakTakSingleplayerWindow.canPieceMoveToLocation(p.getTopPiece(), row, column + 2)) {
				moves.add(new OrderedPair(row, column + 2));
			}
		}

		if (!p.getTopPiece().isKing()) {
			if (TakTakSingleplayerWindow.canPieceMoveToLocation(p.getTopPiece(), row + 1, column)) {
				moves.add(new OrderedPair(row + 1, column));
			}
			if (TakTakSingleplayerWindow.canPieceMoveToLocation(p.getTopPiece(), row + 1, column + 1)) {
				moves.add(new OrderedPair(row + 1, column + 1));
			}
			if (TakTakSingleplayerWindow.canPieceMoveToLocation(p.getTopPiece(), row + 1, column - 1)) {
				moves.add(new OrderedPair(row + 1, column - 1));
			}
		}

		return moves;
	}

	public static ArrayList<OrderedPair> findMovements() {
		OrderedPair blacktomove;
                OrderedPair placetomoveto;
                
		for (int zRow = 0; zRow < TakTakSingleplayerWindow.ROWS; zRow++) {
			for (int zColumn = 0; zColumn < TakTakSingleplayerWindow.COLUMNS; zColumn++) {
				if (TakTakSingleplayerWindow.board[zRow][zColumn] != null
			            && TakTakSingleplayerWindow.board[zRow][zColumn].getTopPiece().getBackgroundColor() == Color.black) 
                                {
					ArrayList<OrderedPair> moves = getAllValidMoves(zRow, zColumn);
					if (!moves.isEmpty()) 
                                        {
						for (OrderedPair temp : moves)
                                                {
                                                        if(TakTakSingleplayerWindow.board[zRow][zColumn].getTopPiece().isKing() && TakTakSingleplayerWindow.board[zRow][zColumn].getTopPiece().getValue() >= 100
                                                           && temp.getY() > zRow)
                                                        {
                                                            blacktomove = new OrderedPair(zRow, zColumn);
								placetomoveto = new OrderedPair(temp.getX(), temp.getY());
                                                                ArrayList<OrderedPair> retpack = new ArrayList<>();
                                                                retpack.add(blacktomove);
                                                                retpack.add(placetomoveto);
                                                                return (retpack);
                                                        }
                                                        else if (TakTakSingleplayerWindow.board[temp.getX()][temp.getY()] != null
						            && TakTakSingleplayerWindow.board[temp.getX()][temp.getY()].getTopPiece().getBackgroundColor() == Color.WHITE) 
                                                        {
                                                                blacktomove = new OrderedPair(zRow, zColumn);
								placetomoveto = new OrderedPair(temp.getX(), temp.getY());
                                                                ArrayList<OrderedPair> retpack = new ArrayList<>();
                                                                retpack.add(blacktomove);
                                                                retpack.add(placetomoveto);
                                                                return (retpack);
							}
						}
					}
				}
			}
		}

                {
			int column = (int)(Math.random()*TakTakSingleplayerWindow.COLUMNS);
			int row = (int)(Math.random()*TakTakSingleplayerWindow.ROWS);
                        ArrayList<OrderedPair> moves;
                        
			while (TakTakSingleplayerWindow.board[row][column] == null
			       || TakTakSingleplayerWindow.board[row][column] != null && getAllValidMoves(row, column).isEmpty()
			       || TakTakSingleplayerWindow.board[row][column] != null && TakTakSingleplayerWindow.board[row][column].getTopPiece().getBackgroundColor() != Color.black)
                        {
                           // System.out.println(row + ", " + column);
				column = (int)(Math.random()*TakTakSingleplayerWindow.COLUMNS);
				row = (int)(Math.random()*TakTakSingleplayerWindow.ROWS);
			}
                        moves = getAllValidMoves(row, column);
                        System.out.println(moves);
                        blacktomove = new OrderedPair(row,column);
                        placetomoveto = moves.get((int)(Math.random()*moves.size()));
                        ArrayList<OrderedPair> retpack = new ArrayList<>();
                        retpack.add(blacktomove);
                        retpack.add(placetomoveto);
                        return (retpack);
                }
	}
	
	public static void makeMove() {
		if (TakTakSingleplayerWindow.numBlackPiecesOnBoard <= 0) {
			TakTakSingleplayerWindow.chooseWinner();
			return;
		}
		ArrayList<OrderedPair> movings = new ArrayList<>(findMovements());	
		OrderedPair moved = movings.get(0);
                OrderedPair movee = movings.get(1);
		
		TakTakSingleplayerWindow.movePieceToLocation(moved, movee);
	}

}
