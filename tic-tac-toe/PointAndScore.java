package tic_tac_toe;

public class PointAndScore {
	//this class will be useful for the evaluation during the minmax algorithm
	public int score;
	Point point;
	
/*********************************************************************************************************************
 * This constructor takes the information needed to perform the minmax algorithm
 * @param score
 * @param point
 */
	public PointAndScore(int score, Point point) {
		this.score = score;
		this.point = point;
	}

}
