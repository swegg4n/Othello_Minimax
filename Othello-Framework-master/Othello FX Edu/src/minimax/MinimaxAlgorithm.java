package minimax;

import java.util.List;

import com.eudycontreras.othello.capsules.AgentMove;
import com.eudycontreras.othello.capsules.MoveWrapper;
import com.eudycontreras.othello.capsules.ObjectiveWrapper;
import com.eudycontreras.othello.controllers.AgentController;
import com.eudycontreras.othello.enumerations.PlayerTurn;
import com.eudycontreras.othello.models.GameBoardState;



public class MinimaxAlgorithm {

	public static int depth = 5;
	public static int nodesExamined = 0;
	public static int prunedCounter = 0;
	public static int reachedLeafNodes = 0;
	
	
	public final static AgentMove GetMove(GameBoardState gameState)
	{
		nodesExamined = 0;
		prunedCounter = 0;
		reachedLeafNodes = 0;
		
		return FindMiniMaxMove(gameState);
	}
	
	
	public final static int Minimax(GameBoardState gameState, int depth, int alpha, int beta, PlayerTurn player)
	{
		nodesExamined++;
		
		if (depth == 0)
			return Evaluation(gameState);
		
		if (player == PlayerTurn.PLAYER_TWO)
		{
			int maxEval = Integer.MIN_VALUE;
			
			List<ObjectiveWrapper> moves = AgentController.getAvailableMoves(gameState, PlayerTurn.PLAYER_TWO);	
			for (ObjectiveWrapper m : moves)
			{
				int eval = Minimax(m.getCurrentCell().getGameBoard().getGameState(), depth-1, alpha, beta, PlayerTurn.PLAYER_ONE);
				maxEval = Math.max(maxEval, eval);
				
				alpha = Math.max(alpha, maxEval);
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
			int minEval = Integer.MAX_VALUE;
			
			List<ObjectiveWrapper> moves =  AgentController.getAvailableMoves(gameState, PlayerTurn.PLAYER_ONE);
			for (ObjectiveWrapper m : moves)
			{
				int eval = Minimax(m.getCurrentCell().getGameBoard().getGameState(), depth-1, alpha, beta, PlayerTurn.PLAYER_TWO);
				minEval = Math.min(minEval, eval);
				
				beta = Math.min(beta, minEval);
				if (beta <= alpha)
				{
					prunedCounter++;
					break;
				}
			}
			
			return minEval;
		}
	}
	
	
	public final static AgentMove FindMiniMaxMove(GameBoardState gameState)
	{
		int alpha = Integer.MIN_VALUE;
		int beta = Integer.MAX_VALUE;
		
		int bestVal = Integer.MIN_VALUE;
		AgentMove bestMove = null;
		
		List<ObjectiveWrapper> moves = AgentController.getAvailableMoves(gameState, PlayerTurn.PLAYER_ONE);	
		for (ObjectiveWrapper m : moves)
		{
			int moveVal = Minimax(gameState, depth, alpha, beta, PlayerTurn.PLAYER_ONE);
			
			if (moveVal > bestVal)
			{
				bestMove = new MoveWrapper(m);
				bestVal = moveVal;
			}
		}
		
		return bestMove;
	}

	
	public final static int Evaluation(GameBoardState gameState)
	{
		reachedLeafNodes++;
		
		return (int)AgentController.getGameEvaluation(gameState, PlayerTurn.PLAYER_ONE);
		
		//return gameState.getWhiteCount() - gameState.getBlackCount();
	}
	
}
