public class Swarm {

	private final int 	  ps 	   = 10;
	private final int 	  T 	   = 500;
	private final double  p1 	   = 0.25f;
	private final double  p2 	   = 1.50f;
	private final double  gamma    = 1.0;
	private final float   e 	   = 2.7f;
	private final float   alpha    = 3.5f, beta = 3.0f;
	private final float   Pp 	   = 0.1f;
	private final float   c1 	   = 1.75f; 
	private final float   c2 	   = 1.75f; 
	
	
	
	
	private java.util.List<Camaleon> swarm = null;
	private Camaleon g = null;

	public void execute() {
		initRand();
		evolve();
	}

	private void initRand() {
		swarm = new java.util.ArrayList<>();
		g = new Camaleon();
		Camaleon p = null;
		
		for (int i = 1; i <= ps; i++) {
			do {
				p = new Camaleon();
			} while (!p.isFeasible());
			p.updatePBest();
			swarm.add(p);
		}
		
		g.copy(swarm.get(0));
		
		for (int i = 1; i < ps; i++)
			if (swarm.get(i).isBetterThan(g))
				g.copy(swarm.get(i));
		log(0);
	}

	private void evolve() {
		int t = 1;
		int [] fit = new int[250];
	
		while (t <= T) {
			Camaleon p = new Camaleon();
			for (int i = 0; i < ps; i++) {
				
				do {
					//System.out.println(p);
					int r = (int) StdRandom.uniform(2);
					double  epsilon  = StdRandom.uniform();
					double  eyeRot   = StdRandom.uniform();
					p.copy(swarm.get(i));
												
					if (r >= Pp) {
					
					p.move(g, alpha, beta, e , gamma, T ,t, p1, p2 , eyeRot);
					//p.tongueAttack(g, alpha, beta, e , gamma, T ,t, c1, c2);
					//System.out.println(p);
					}else {
						
					p.move2(g, alpha, beta, e , gamma, T ,t, p1, p2, epsilon , eyeRot);
					//p.tongueAttack(g, alpha, beta, e , gamma, T ,t, c1, c2);
					
					}
					
					p.tongueAttack(g, alpha, beta, e , gamma, T ,t, c1, c2);
					//System.out.println(p);
				
					//System.out.println(mayor);
					
				} while (!p.isFeasible());
				
				
				//System.out.println(p);
				if (p.isBetterThanPBest()) {
					p.updatePBest();
					swarm.get(i).copy(p);
				}else {
					double  epsilon  = StdRandom.uniform();
					p.randomWalk(epsilon, average());	
				}
			}
			
			//System.out.print(p);					
			for (int i = 0; i < ps; i++)
				if (swarm.get(i).isBetterThan(g))					
					g.copy(swarm.get(i));
		
			log(t);
			t++;
			
		}
	} // fin de T
	
	private void log(int t) {
		StdOut.printf("t=%d,\t%s\n", t, g);
	}
	private double average() {
		double[] data = new double[ps];
		for (int i = 0; i < ps; i++) {
			data[i] = swarm.get(i).avgA();
		} return java.util.Arrays.stream(data).average().getAsDouble();
	}
}