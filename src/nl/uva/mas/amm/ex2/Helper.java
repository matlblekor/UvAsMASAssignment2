package nl.uva.mas.amm.ex2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import nl.uva.mas.amm.common.Position;

public class Helper {

	public static int GetClosestPrey(List<Position> preys,
			List<Position> predators) {
		int preyToChase = 0;
		List<Integer> teamDistances = new ArrayList<Integer>();

		// Calculate the team distances
		if (preys.size() > 1) {
			for (Position prey : preys) {
				int distance = 0;
				for (Position predator : predators) {
					distance += Math.abs(prey.x - predator.x)
							+ Math.abs(prey.y - predator.y);
				}
				teamDistances.add(distance);
			}

			// See which prey is closest to the team
			int minDistance = Integer.MAX_VALUE;
			int preyIndex = 0;
			for (Integer distance : teamDistances) {
				if (minDistance > distance) {
					minDistance = distance;
					preyToChase = preyIndex;
				}
			}

			return preyToChase;
		} else {
			return preyToChase;
		}
	}

	public static int GetRoleOfAgent(List<Position> predators) {
		for (Position position : predators) {
			if (position.x == 0 && position.y == 0)
				return predators.indexOf(position);
		}
		return 0;
	}

	public static Map<Integer, Position> GetTargetPosition(int preyToChase,
			List<Position> preys) {
		Position targetPrey = preys.get(preyToChase);
		Map<Integer, Position> targetPositions = new HashMap<Integer, Position>();
		
		targetPositions.put(0, new Position(targetPrey.x, targetPrey.y - 1));
		targetPositions.put(1, new Position(targetPrey.x - 1, targetPrey.y));
		targetPositions.put(2, new Position(targetPrey.x + 1, targetPrey.y));
		targetPositions.put(3, new Position(targetPrey.x, targetPrey.y + 1));
		
		return targetPositions;
	}

	public static int GetMoveDirection(Map<Integer, Position> targetPos, int predatorRole, List<Position> predators) {
		Map<String, Integer> collisionMap = new HashMap<String, Integer>();
		Map<Integer, Position> movePositionMap = new HashMap<Integer, Position>();
		
		for(Position predator : predators)
		{
			int predatorNumber = predators.indexOf(predator);
			Stack<Position> movePositions = getMovePosition(targetPos.get(predatorNumber), predator);
			
			while(true)
			{
				Position movePosition = movePositions.pop();
				if(!collisionMap.containsKey(movePosition))
				{
					collisionMap.put(movePosition.toString(), predatorNumber);
					movePositionMap.put(predatorNumber, movePosition);
					break;
				}
			}
		}
		
		Position currentAgentMovePosition = movePositionMap.get(predatorRole);
		
		if(currentAgentMovePosition.x != 0 && currentAgentMovePosition.y == 0)
			if(currentAgentMovePosition.x > 0)
				return 2;
			else
				return 3;
		else if(currentAgentMovePosition.x == 0 && currentAgentMovePosition.y != 0)
		{
			if(currentAgentMovePosition.y > 0)
				return 0;
			else
				return 1;			
		}
		else
			throw new RuntimeException("Should not happen");
		/*
		if (targetPos.x > 0 && targetPos.y > 0)
			return Math.random() > 0.5 ? 0 : 2;
		else if (targetPos.x < 0 && targetPos.y < 0)
			return Math.random() > 0.5 ? 3 : 1;
		else if (targetPos.x > 0 && targetPos.y < 0)
			return Math.random() > 0.5 ? 1 : 2;
		else if (targetPos.x < 0 && targetPos.y > 0)
			return Math.random() > 0.5 ? 3 : 0;
		else if (targetPos.x == 0 && targetPos.y > 0)
			return 0;
		else if (targetPos.x > 0 && targetPos.y == 0)
			return 2;
		else if (targetPos.x == 0 && targetPos.y < 0)
			return 1;
		else if (targetPos.x == 0 && targetPos.y > 0)
			return 3;
		else
			return 4; //Don't move
*/
	}
	
	private static Stack<Position> getMovePosition(Position targetPosition, Position predPos)
	{
		//Corrected position to model view from other predators
		int x = targetPosition.x - predPos.x;
		int y = targetPosition.y - predPos.y;
		
		//Top on Stack is the favorite position to move
		Stack<Position> positions = new Stack<Position>();
		//last option don't move
		positions.push(new Position(predPos.x,predPos.y));
		
		if (x > 0 && y > 0)
		{
			positions.push(new Position(predPos.x,predPos.y-1));
			positions.push(new Position(predPos.x-1,predPos.y));
		}
		else if (x < 0 && y < 0)
		{
			positions.push(new Position(predPos.x,predPos.y+1));
			positions.push(new Position(predPos.x+1,predPos.y));
		}
		else if (x > 0 && y < 0)
		{
			positions.push(new Position(predPos.x,predPos.y+1));
			positions.push(new Position(predPos.x-1,predPos.y));
		}
		else if (x < 0 && y > 0)
		{
			positions.push(new Position(predPos.x,predPos.y-1));
			positions.push(new Position(predPos.x+1,predPos.y));
		}
		else if (x == 0 && y > 0)
		{
			positions.push(new Position(predPos.x,predPos.y-1));
		}
		else if (x > 0 && y == 0)
		{
			positions.push(new Position(predPos.x-1,predPos.y));
		}
		else if (x == 0 && y < 0)
		{
			positions.push(new Position(predPos.x,predPos.y+1));
		}
		else if (x == 0 && y > 0)
		{
			positions.push(new Position(predPos.x,predPos.y-1));
		}
		else
		{
			throw new RuntimeException("Should not happen");
		}
		
		return positions;
	}
}
