package application;

public class Intersection {
	
	public enum Move{
		MOVE,
		YIELD
	}
	
	public enum Result{
		PASS,
		CLASH
	}
	
	private Traffic traffic1;
	private Traffic traffic2;
	
//	private int x;
//	private int y;

	private Move traffic1_move;
	private Move traffic2_move;
	private Result result;
	
	public Intersection(Traffic traffic1, Traffic traffic2, Move xx, Move yy, Result r) {
		this.traffic1 = traffic1;
		this.traffic2 = traffic2;
//		this.x = x;
//		this.y = y;
		this.traffic1_move = xx;
		this.traffic2_move = yy;
		this.result = r;
	}
	
	public Traffic getTraffic1() {
		return traffic1;
	}

	public void setTraffic1(Traffic traffic1) {
		this.traffic1 = traffic1;
	}

	public Traffic getTraffic2() {
		return traffic2;
	}

	public void setTraffic2(Traffic traffic2) {
		this.traffic2 = traffic2;
	}

	public Move getTraffic1_move() {
		return traffic1_move;
	}

	public void setTraffic1_move(Move traffic1_move) {
		this.traffic1_move = traffic1_move;
	}

	public Move getTraffic2_move() {
		return traffic2_move;
	}

	public void setTraffic2_move(Move traffic2_move) {
		this.traffic2_move = traffic2_move;
	}

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}
	
}
