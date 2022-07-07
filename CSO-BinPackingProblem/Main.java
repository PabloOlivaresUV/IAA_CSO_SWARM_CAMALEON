public class Main {

	public static void main(String[] args) {
		try {
			//for(int r = 1; r <= 30; r++) {
			new Swarm().execute();
			//}
		} catch (Exception e) {
			StdOut.printf("---------%s\n%s", e.getMessage(), e.getCause());
		} 
	}
}