public class Test {
	
	
	public static void main(String[] args) {
		Neuron n = new Neuron();
		n.start();

		//Test
		double[] d = {2.0 ,6.,1500.,16.};
		VectorWithNote t1 = new VectorWithNote(d, 1.0);
		n.classify(t1);
		d = new double[]{2., 6., 1500., 16.};
		t1 = new VectorWithNote(d, 1.0);
		n.classify(t1);
		d = new double[]{9. ,9.,2250.,16.};
		t1 = new VectorWithNote(d, 0.0);
		n.classify(t1);
		d = new double[]{4. ,14.,3500.,40.};
		t1 = new VectorWithNote(d, 0.0);
		n.classify(t1);

	}

}
