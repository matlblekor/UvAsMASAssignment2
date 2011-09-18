package nl.uva.mas.amm.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import nl.uva.mas.amm.common.Position;

import org.junit.Test;
import static org.junit.Assert.*;

public class TestPosition {
	@Test
	public void testOfSortPredators() {
		List<Position> predators = new ArrayList<Position>();

		/*
		predators.add(new Position(0, -1));
		predators.add(new Position(-1, -1));
		predators.add(new Position(0, 0));
		predators.add(new Position(-1, 0));
		*/
		
		predators.add(new Position(0, 0));
		predators.add(new Position(1, 0));
		predators.add(new Position(0, 1));
		predators.add(new Position(1, 1));		

		Collections.sort(predators);
		for (int i = 0; i < predators.size() - 1; i++) {
			assertTrue(predators.get(i).x < predators.get(i + 1).x
					|| (predators.get(i).x == predators.get(i + 1).x && predators
							.get(i).y < predators.get(i + 1).y));
		}
	}

}
