package nl.uva.mas.amm.ex2;

import java.util.ArrayList;
import java.util.List;

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

	public static Position GetTargetPosition(int predatorRole, int preyToChase,
			List<Position> preys) {
		Position targetPrey = preys.get(preyToChase);
		switch (predatorRole) {
		case 0:
			return new Position(targetPrey.x, targetPrey.y - 1);
		case 1:
			return new Position(targetPrey.x - 1, targetPrey.y);
		case 2:
			return new Position(targetPrey.x + 1, targetPrey.y);
		case 3:
			return new Position(targetPrey.x, targetPrey.y + 1);
		default:
			throw new RuntimeException("Should not happen");
		}
	}

	public static int GetMoveDirection(Position targetPos) {
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

	}
}
