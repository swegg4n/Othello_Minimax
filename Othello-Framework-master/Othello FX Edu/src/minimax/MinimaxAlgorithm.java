package minimax;

import java.util.List;

import com.eudycontreras.othello.capsules.AgentMove;
import com.eudycontreras.othello.capsules.ObjectiveWrapper;
import com.eudycontreras.othello.controllers.AgentController;
import com.eudycontreras.othello.enumerations.PlayerTurn;
import com.eudycontreras.othello.models.GameBoardState;



public class MinimaxAlgorithm {

	
	public final static AgentMove GetMove(GameBoardState gameState)
	{
		Minimax(gameState, 3, PlayerTurn.PLAYER_TWO);
		return null;
	}
	
	
	public final static int Minimax(GameBoardState gameState, int depth, PlayerTurn player)
	{
		if (depth == 0)
			return Evaluation(gameState);
		
		if (player == PlayerTurn.PLAYER_TWO)
		{
			int maxEval = Integer.MIN_VALUE;
			
			List<ObjectiveWrapper> moves = AgentController.getAvailableMoves(gameState, PlayerTurn.PLAYER_TWO);	
			for (ObjectiveWrapper m : moves)
			{
				int eval = Minimax(m.getCurrentCell().getGameBoard().getGameState(), depth-1, PlayerTurn.PLAYER_ONE);
				maxEval = Math.max(maxEval, eval);
			}
			
			return maxEval;
		}
		else
		{
			int minEval = Integer.MAX_VALUE;
			
			List<ObjectiveWrapper> moves =  AgentController.getAvailableMoves(gameState, PlayerTurn.PLAYER_ONE);
			for (ObjectiveWrapper m : moves)
			{
				int eval = Minimax(m.getCurrentCell().getGameBoard().getGameState(), depth-1, PlayerTurn.PLAYER_TWO);
				minEval = Math.min(minEval, eval);
			}
			
			return minEval;
		}
	}

	
	public final static int Evaluation(GameBoardState gameState)
	{
		return gameState.getWhiteCount() - gameState.getBlackCount();
	}
	
}
