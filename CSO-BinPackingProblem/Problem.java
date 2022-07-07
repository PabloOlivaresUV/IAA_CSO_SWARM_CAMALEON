public class Problem {

	private final int[] weight = {87,70,43,62,40,37,71,34,29,70,41,78,74,51,71,47,21,32,37,80,48,93,22,46,96,44,94,99,100,
			65,61,34,25,35,60,52,90,81,93,74,85,43,21,89,100,56,55,88,52,63,40,41,62,35,77,72,75,93,55,95,54,87,38,84,83,88,
			37,65,58,89,89,48,85,25,100,28,20,96,52,100,94,94,69,94,39,62,86,43,61,88,78,72,71,31,45,72,87,60,91,100,66,44,
			83,23,22,81,22,55,67,73,68,42,83,40,86,63,33,24,54,48,41,56,48,29,51,78,85,68,35,99,74,42,26,49,65,92,51,43,97,
			91,24,79,30,58,76,59,92,94,43,31,87,59,56,74,91,88,85,70,59,80,54,66,55,61,64,80,53,80,44,74,22,91,91,83,51,57,
			20,83,46,54,56,76,24,41,26,37,91,52,94,94,49,61,69,79,38,78,25,57,70,81,57,34,22,58,99,39,99,29,34,58,94,46,41,
			56,86,92,81,82,38,42,99,59,73,57,59,67,44,29,53,54,40,83,55,66,72,80,25,92,78,97,28,99,73,66,44,59,95,53,81,83 };
	
	private final int capacity = 150;
	protected final int nVars = 250; 

	protected int optimum() {
		return 105;
	}

	protected boolean checkConstraint(int[] x) {
		int chosenBin = computeFitness(x);
		//System.out.println(chosenBin);
		if (chosenBin == 0)
			return false;

		int[] remaining = new int[chosenBin];
		for (int i = 0; i < chosenBin; i++)
			remaining[i] = capacity;
		
		if (remaining[0] < weight[0])
			return false;

		remaining[0] -= weight[0];
		for (int j = 1, i = 0; j < nVars; j++) {
			if (remaining[i] - weight[j] >= 0) {
				remaining[i] -= weight[j];
				i = 0;
			} else {
				i++;
				if (i == chosenBin)
					return false;
				j--;
			}
		}
		return true;
	}

	protected int computeFitness(int[] bin) {
		return java.util.Arrays.stream(bin).sum();
	}
}