package main;

import com.eudycontreras.othello.capsules.AgentMove;
import com.eudycontreras.othello.controllers.AgentController;
import com.eudycontreras.othello.controllers.Agent;
import com.eudycontreras.othello.enumerations.PlayerTurn;
import com.eudycontreras.othello.models.GameBoardState;
import com.eudycontreras.othello.threading.ThreadManager;
import com.eudycontreras.othello.threading.TimeSpan;

import minimax.MinimaxAlgorithm;

/**
 * <H2>Created by</h2> Eudy Contreras
 * <h4> Mozilla Public License 2.0 </h4>
 * Licensed under the Mozilla Public License 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <a href="https://www.mozilla.org/en-US/MPL/2.0/">visit Mozilla Public Lincense Version 2.0</a>
 * <H2>Class description</H2>
 * 
 * @author Eudy Contreras
 */
public class ExampleAgentOne extends Agent{
	
	
	public ExampleAgentOne() {
		this(PlayerTurn.PLAYER_ONE);
	}
	
	public ExampleAgentOne(String name) {
		super(name, PlayerTurn.PLAYER_ONE);
	}
	
	public ExampleAgentOne(PlayerTurn playerTurn) {
		super(playerTurn);
	
	}

	/**
	 * Delete the content of this method and Implement your logic here!
	 */
	@Override
	public AgentMove getMove(GameBoardState gameState) {
		
		AgentMove move = MinimaxAlgorithm.GetMove(gameState);
		
		this.setNodesExamined(MinimaxAlgorithm.nodesExamined);
		this.setPrunedCounter(MinimaxAlgorithm.prunedCounter);
		this.setReachedLeafNodes(MinimaxAlgorithm.reachedLeafNodes);
		this.setSearchDepth(MinimaxAlgorithm.depth);
		
		return move;
	}
	
	/**
	 * Default template move which serves as an example of how to implement move
	 * making logic. Note that this method does not use Alpha beta pruning and
	 * the use of this method can disqualify you
	 * 
	 * @param gameState
	 * @return
	 */
	private AgentMove getExampleMove(GameBoardState gameState){
		
		return MinimaxAlgorithm.GetMove(gameState);
		/*
		int waitTime = UserSettings.MIN_SEARCH_TIME; // 1.5 seconds
		
		ThreadManager.pause(TimeSpan.millis(waitTime)); // Pauses execution for the wait time to cause delay
		
		return AgentController.getExampleMove(gameState, playerTurn); // returns an example AI move Note: this is not AB Pruning
		*/
	}

}
