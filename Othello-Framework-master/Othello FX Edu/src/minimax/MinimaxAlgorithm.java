package minimax;

import com.eudycontreras.othello.capsules.AgentMove;
import com.eudycontreras.othello.controllers.AgentController;
import com.eudycontreras.othello.models.GameBoardState;



public class MinimaxAlgorithm {

	
	public final static AgentMove GetMove(GameBoardState gameState)
	{
		Minimax(gameState, 3, true);
		return null;
	}
	
	
	public final static int Minimax(GameBoardState gameState, int depth, boolean max)
	{
		if (depth == 0)
			return Evaluation(gameState);
		
		if (max)
		{
			int maxEval = Integer.MIN_VALUE;
			
			
			return maxEval;
		}
		else
		{
			int minEval = Integer.MAX_VALUE;
			
			
			return minEval;
		}
		
	}

	
	public final static int Evaluation(GameBoardState gameState)
	{
		return 0;
	}
	
}
