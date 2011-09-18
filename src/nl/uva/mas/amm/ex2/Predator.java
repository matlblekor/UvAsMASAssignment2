package nl.uva.mas.amm.ex2;

import java.io.*;
import java.lang.*;
import java.util.*;

import nl.uva.mas.amm.common.Position;
import nl.uva.mas.amm.pursuit.Agent;

/** This class defines the functionality of the predator. */
public class Predator extends Agent {
	// List of position arrays, where index 0 is x and index 1 is y
	List<Position> preys;
	List<Position> predators;

	int nMoveDirection;
	int predatorRole = -1;
	private Integer preyToChase;
	private Map<Integer, Position> targetPos;

	public Predator() {

	}

	/**
	 * This method initialize the predator by sending the initialization message
	 * to the server.
	 */
	public void initialize() throws IOException {
		g_socket.send("(init predator)");
	}

	/**
	 * This message determines a new movement command. Currently it only moves
	 * random. This can be improved..
	 */
	public String determineMovementCommand() {
		switch (this.nMoveDirection) {
		case 0:
			return ("(move north)");
		case 1:
			return ("(move south)");
		case 2:
			return ("(move east)");
		case 3:
			return ("(move west)");
		default:
			return ("(move none)");
		}
	}

	/**
	 * This method processes the visual information. It receives a message
	 * containing the information of the preys and the predators that are
	 * currently in the visible range of the predator.
	 */
	public void processVisualInformation(String strMessage) {
		// Add this predator to the list
		predators = new ArrayList<Position>();
		preys = new ArrayList<Position>();

		predators.add(new Position(0, 0));

		int i = 0, x = 0, y = 0;
		String strName = "";
		StringTokenizer tok = new StringTokenizer(strMessage.substring(5),
				") (");

		while (tok.hasMoreTokens()) {
			if (i == 0)
				strName = tok.nextToken(); // 1st = name
			if (i == 1)
				x = Integer.parseInt(tok.nextToken()); // 2nd = x coord
			if (i == 2)
				y = Integer.parseInt(tok.nextToken()); // 3rd = y coord
			if (i == 2) {

				// gather information about the 2 prays relative positions
				if (strName.equals("prey")) {
					preys.add(new Position(x, y));
				}

				// gather information about the other 3 predators relative
				// positions
				if (strName.equals("predator")) {
					predators.add(new Position(x, y));
				}

				//System.out.println(strName + " seen at (" + x + ", " + y + ")");
			}
			i = (i + 1) % 3;
		}

		// Index of the prey we are going for
		preyToChase = Helper.GetClosestPrey(preys, predators);
		System.out.println("Chasing Pray: " + preys.get(preyToChase).toString());

		Collections.sort(predators);
		predatorRole = Helper.GetRoleOfAgent(predators);		
		targetPos = Helper.GetTargetPosition(preyToChase, preys);
		nMoveDirection = Helper.GetMoveDirection(targetPos, predatorRole, predators);

		/*
		 * 
		 * blocked[0] = 0; blocked[1] = 0; blocked[2] = 0; blocked[3] = 0;
		 * 
		 * // are any adjent positions blocked by another predator? // do not
		 * enforce collision detection if (false) { for (i = 0; i < nPredator;
		 * ++i) { if ((xprd[i] == -1 || xprd[i] == -2) && Math.abs(yprd[i]) < 2)
		 * { blocked[3] = 1; System.out.println("Blocked: 3");
		 * 
		 * } if (Math.abs(xprd[i]) < 2 && (yprd[i] == 1 || yprd[i] == 2)) {
		 * blocked[0] = 1; System.out.println("Blocked: 1"); }
		 * 
		 * if ((xprd[i] == 1 || xprd[i] == 2) && Math.abs(yprd[i]) < 2) {
		 * blocked[2] = 1; System.out.println("Blocked: 2"); } if
		 * (Math.abs(xprd[i]) < 2 && (yprd[i] == -1 || yprd[i] == -2)) {
		 * blocked[1] = 1; System.out.println("Blocked: 1"); } } }
		 */
		/*
		 * // initially - bogus move directions, meaning "stand still"
		 * nMoveDirection = 5;
		 * 
		 * // random() gives some chance to move on the smaller axis, to avoid
		 * // "deadlocks" that happen otherwise if (Math.abs(targetx) >
		 * Math.abs(targety) && (Math.abs(targety) == 0 || Math.random() > 0.8))
		 * { // move on the X axis if (targetx < 0 && blocked[3] != 1) {
		 * nMoveDirection = 3; } else { if (blocked[2] != 1) { nMoveDirection =
		 * 2; } } }
		 * 
		 * if (nMoveDirection == 5) { if (Math.abs(targety) > 0) { // move on
		 * the Y axis if (targety < 0 && blocked[1] != 1) { nMoveDirection = 1;
		 * } else { if (blocked[0] != 1) { nMoveDirection = 0; } } } }
		 */

		// System.out.println( "Pray 1:" + " seen at (" + xp[0] + ", " + yp[0] +
		// ")" );
		// System.out.println( "Pray 2:" + " seen at (" + yp[1] + ", " + yp[1] +
		// ")" );
		// System.out.println( "Chasing pray:" + nPrayToChase );
		// System.out.println( "My role:" + nRole );
	}

	/**
	 * This method is called after a communication message has arrived from
	 * another predator.
	 */
	public void processCommunicationInformation(String strMessage) {
		// TODO: exercise 3 to improve capture times
	}

	/**
	 * This method is called and can be used to send a message to all other
	 * predators. Note that this only will have effect when communication is
	 * turned on in the server.
	 */
	public String determineCommunicationCommand() {
		// TODO: exercise 3 to improve capture times
		return "";
	}

	/**
	 * This method is called when an episode is ended and can be used to reset
	 * some variables.
	 */
	public void episodeEnded() {
		// this method is called when an episode has ended and can be used to
		// reinitialize some variables
		System.out.println("EPISODE ENDED\n");
	}

	/**
	 * This method is called when this predator is involved in a collision.
	 */
	public void collisionOccured() {
		// this method is called when a collision occured and can be used to
		// reinitialize some variables
		System.out.println("COLLISION OCCURED\n");
	}

	/**
	 * This method is called when this predator is involved in a penalization.
	 */
	public void penalizeOccured() {
		// this method is called when a predator is penalized and can be used to
		// reinitialize some variables
		System.out.println("PENALIZED\n");
	}

	/**
	 * This method is called when this predator is involved in a capture of a
	 * prey.
	 */
	public void preyCaught() {
		preyToChase = null;
		System.out.println("PREY CAUGHT\n");
	}

	public static void main(String[] args) {
		Predator predator = new Predator();
		if (args.length == 2)
			predator.connect(args[0], Integer.parseInt(args[1]));
		else
			predator.connect();
	}
}
