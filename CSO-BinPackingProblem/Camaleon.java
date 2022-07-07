public class Camaleon extends Problem {


	private int[] x = new int[nVars];
	private int[] p = new int[nVars];
	
	private double[] v = new double[nVars];
	
	private double[] u = new double[nVars];
	private double[] l = new double[nVars];
	private double[] A = new double[nVars];
	
	public Camaleon() {
		for (int j = 0; j < nVars; j++) {
			x[j] = StdRandom.uniform(2);
			v[j] = StdRandom.uniform(2);
			u[j] = StdRandom.uniform();// factor predominante de fittness
			l[j] = StdRandom.uniform();// factor predominante de fittness
			A[j] = StdRandom.uniform();
			
		}
	}

	protected int computeFitness() {
		return computeFitness(x);
	}

	protected int computeFitnessPBest() {
		return computeFitness(p);
	}

	protected boolean isBetterThanPBest() {
		return computeFitness() < computeFitnessPBest();
	}

	protected boolean isBetterThan(Camaleon g) {
		return computeFitnessPBest() < g.computeFitnessPBest();
	}

	protected void updatePBest() {
		System.arraycopy(x, 0, p, 0, x.length);
	}

	protected boolean isFeasible() {
		return checkConstraint(x);
	}


	protected void move(Camaleon g,  float alpha, float beta, float e , double gamma, int T , int t , double p1, double p2 , double eyeRot) {
		for (int j = 0; j < nVars; j++) {
			
			double r1 = StdRandom.uniform();
			double r2 = StdRandom.uniform();
					
			x[j] = (int) (x[j] + p1 * (p[j] - g.x[j]) * r1 + p2 * (g.x[j] - v[j]) * r2);
			x[j] = toBinary(eyeRot + x[j]);
	
			
		}
	}
	
	protected void move2(Camaleon g,  float alpha, float beta, float e , double gamma, int T , int t , double p1, double p2, double epsilon, double eyeRot ) {
		for (int j = 0; j < nVars; j++) {
		
			double mu = Math.pow(Math.pow((gamma * e),(-alpha * (double)j/T)),gamma); 
			
			double r3 = StdRandom.uniform();
			
							
			x[j] = (int) (x[j] + mu * (u[j] - l[j])* r3 + l[j] * epsilon); 
			x[j] = toBinary(x[j]);
			
			
		}
	}
	
	protected void tongueAttack(Camaleon g,  float alpha, float beta, float e , double gamma, int T , int t , double c1, double c2) {
		int aux=0;
		for (int j = 0; j < nVars; j++) {
			
			if(j != 0) {
			aux = j -1;			
			}
			double 	tT     = (double)j/T;
			float 	RaiztT = (float)Math.sqrt(tT);
			float 	logt   = (float)Math.log(j);
			float 	w  	   = (float) Math.pow(1 - tT,(1 * RaiztT));
			double 	a      = 2590 * Math.pow(1-(1/e) ,(float)-logt);
			float 	r1     = (float) StdRandom.uniform(0.0,1.0);
			float 	r2 	   = (float) StdRandom.uniform(0.0,1.0);
			double 	CV     = (double) Math.pow(v[j],2);
			double 	CVa    = (double) Math.pow(v[aux],2);
			double  difVaV = (double) CV - CVa;
			int  Z   = (int) (2 * a);
			float b = (float) StdRandom.uniform(0.0,1.0);
			
			if (difVaV > 1) {
				b = (float)(difVaV/Z);
			}
			
			v[j] = w * v[j] + c1 * (g.x[j] - x[j]) * r1 + c2 * (p[j] - x[j]) * r2;
			x[j] = toBinary(x[j] + (CV - CVa)/b);
			//System.out.println(x[j]);
			
		}
	}
	

	private float diff() {
		return computeFitness(p) - optimum();
	}

	private float rpd() {
		return diff() / optimum() * 100f;
	}

	private String showSolution() {
		return java.util.Arrays.toString(p);
	}

	private int toBinary(double x) {
		return StdRandom.uniform() <= (1 / (1 + Math.pow(Math.E, -x))) ? 1 : 0;
	}

	protected void randomWalk(final double epsilon, final double avgA) {
        for (int j = 0; j < nVars; j++) {
            x[j] = toBinary(x[j] + epsilon * avgA);
        }
    }
	   protected double avgA() {
	    	return avg(A);
	    }
	   
	   private double avg(double[] a) {
	    	 return java.util.Arrays.stream(a).average().getAsDouble();
	    }
	    
	protected void copy(Object object) {
		if (object instanceof Camaleon) {
			System.arraycopy(((Camaleon) object).x, 0, this.x, 0, nVars);
			System.arraycopy(((Camaleon) object).p, 0, this.p, 0, nVars);
			System.arraycopy(((Camaleon) object).v, 0, this.v, 0, nVars);
		}
	}
	

	@Override
	public String toString() {
		return String.format("optimal value: %d, fitness: %d, diff: %.1f, rpd: %.2f%%, p: %s", optimum(),computeFitnessPBest(), diff(), rpd(),showSolution());
		//return String.format("fitness: %d",computeFitnessPBest());
	}
}