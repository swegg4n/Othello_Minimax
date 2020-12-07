package minimax;

import java.util.List;
import com.eudycontreras.othello.capsules.AgentMove;
import com.eudycontreras.othello.capsules.Index;
import com.eudycontreras.othello.capsules.MoveWrapper;
import com.eudycontreras.othello.capsules.ObjectiveWrapper;
import com.eudycontreras.othello.controllers.AgentController;
import com.eudycontreras.othello.enumerations.BoardCellState;
import com.eudycontreras.othello.enumerations.PlayerTurn;
import com.eudycontreras.othello.models.GameBoardCell;
import com.eudycontreras.othello.models.GameBoardState;



public class MinimaxAlgorithm {

	public static int depth = 3;
	public static int nodesExamined = 0;
	public static int prunedCounter = 0;
	public static int reachedLeafNodes = 0;
	
	
	public final static AgentMove GetMove(GameBoardState gameState, PlayerTurn playerTurn)
	{
		nodesExamined = 0;
		prunedCounter = 0;
		reachedLeafNodes = 0;
		
		return FindMiniMaxMove(gameState, playerTurn);
	}
	
	
	public final static float Minimax(GameBoardState gameState, int depth, float alpha, float beta, PlayerTurn player)
	{
		nodesExamined++;
		
		if (depth == 0)
			return Evaluation_weights(gameState);
		
		if (player == PlayerTurn.PLAYER_ONE)
		{
			float maxEval = Float.MIN_VALUE;
			
			List<ObjectiveWrapper> moves = AgentController.getAvailableMoves(gameState, player);	
			for (ObjectiveWrapper m : moves)
			{			
				GameBoardState childState = AgentController.getNewState(gameState, m);	
				
				float eval = Minimax(childState, depth-1, alpha, beta, PlayerTurn.PLAYER_TWO);
				maxEval = Math.max(maxEval, eval);
				
				alpha = Math.max(alpha, eval);
				if (alpha >= beta)
				{
					prunedCounter++;
					break;
				}
			}
			
			return maxEval;
		}
		else
		{
			float minEval = Float.MAX_VALUE;
			
			List<ObjectiveWrapper> moves =  AgentController.getAvailableMoves(gameState, player);
			for (ObjectiveWrapper m : moves)
			{
				GameBoardState childState = AgentController.getNewState(gameState, m);
				
				float eval = Minimax(childState, depth-1, alpha, beta, PlayerTurn.PLAYER_ONE);
				minEval = Math.min(minEval, eval);
				
				beta = Math.min(beta, eval);
				if (beta <= alpha)
				{
					prunedCounter++;
					break;
				}
			}
			
			return minEval;
		}
	}
	
	
	public final static AgentMove FindMiniMaxMove(GameBoardState gameState, PlayerTurn playerTurn)
	{
		float alpha = Float.MIN_VALUE;
		float beta = Float.MAX_VALUE;
		
		float bestVal = -Float.MAX_VALUE;
		AgentMove bestMove = null;
		
		List<ObjectiveWrapper> moves = AgentController.getAvailableMoves(gameState, playerTurn);	
		for (ObjectiveWrapper m : moves)
		{	
			GameBoardState childState = AgentController.getNewState(gameState, m);
			float moveVal = Minimax(childState, depth, alpha, beta, playerTurn);
			
			if (moveVal > bestVal)
			{
				bestMove = new MoveWrapper(m);
				bestVal = moveVal;
			}
		}
		
		System.out.println("Best Move: " + bestMove + "\n");
		return bestMove;
	}

	
	public final static float Evaluation_manhattan(GameBoardState gameState)
	{
		reachedLeafNodes++;
		
		int boardSize = gameState.getGameBoard().getBoardSize();
		
		int whiteWeightedSum = 0;
		int blackWeightedSum = 0;
		
		GameBoardCell[][] cells = gameState.getGameBoard().getCells();
		for	(int x = 0; x < boardSize; x++)
		{
			for	(int y = 0; y < boardSize; y++)
			{
				if (cells[x][y].getCellState() == BoardCellState.WHITE)
					whiteWeightedSum += (int)(Math.pow(x-boardSize/2,4) + Math.pow(y-boardSize/2,4));
				else if (cells[x][y].getCellState() == BoardCellState.BLACK)
					blackWeightedSum += (int)(Math.pow(x-boardSize/2,4) + Math.pow(y-boardSize/2,4));
			}
		}
		
		return whiteWeightedSum - blackWeightedSum;
	}
	
	
	
	static float[][] cellWeights =
	{
		new float[] { 16.16f, -3.03f, 0.99f, 0.43f, 0.43f, 0.99f, -3.03f, 16.16f },
		new float[] { -4.12f, -1.81f, -0.08f, -0.27f, -0.27f, -0.08f, -1.81f, -4.12f }, 
		new float[] { 1.33f, -0.04f, 0.51f, 0.07f, 0.07f, 0.51f, -0.04f, 1.33f },
		new float[] { 0.63f, -0.18f, -0.04f, -0.1f, -0.1f, -0.04f, -0.18f, 0.63f },
		new float[] { 0.63f, -0.18f, -0.04f, -0.1f, -0.1f, -0.04f, -0.18f, 0.63f },
		new float[] { 1.33f, -0.04f, 0.51f, 0.07f, 0.07f, 0.51f, -0.04f, 1.33f },
		new float[] { -4.12f, -1.81f, -0.08f, -0.27f, -0.27f, -0.08f, -1.81f, -4.12f }, 
		new float[] { 16.16f, -3.03f, 0.99f, 0.43f, 0.43f, 0.99f, -3.03f, 16.16f },
	};
	
	public final static float Evaluation_weights(GameBoardState gameState)
	{
		int boardSize = gameState.getGameBoard().getBoardSize();
		
		float whiteWeightedSum = 0;
		float blackWeightedSum = 0;
		
		GameBoardCell[][] cells = gameState.getGameBoard().getCells();
		for	(int x = 0; x < boardSize; x++)
		{
			for	(int y = 0; y < boardSize; y++)
			{
				if (cells[x][y].getCellState() == BoardCellState.WHITE)
					whiteWeightedSum += cellWeights[x][y];
				else if (cells[x][y].getCellState() == BoardCellState.BLACK)
					blackWeightedSum += cellWeights[x][y];
			}
		}
		
		System.out.println("Evaluation ->  white: " + whiteWeightedSum + "  black: " +  blackWeightedSum);
		return whiteWeightedSum - blackWeightedSum;
	}
}
