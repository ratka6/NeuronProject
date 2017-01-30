import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.linear.ArrayRealVector;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.exp;

/**
 * Created by krzysiek on 18.01.2017.
 */
@SuppressWarnings("Duplicates")
public class Neuron {

    private List<VectorWithNote> learningList;
    private List<VectorWithNote> testingList;
    private List<Double> errors;

    NormalDistribution distr;

    public Neuron() {
        ParseCSV c = new ParseCSV();
        learningList = new ArrayList<>();
        testingList = new ArrayList<>();
        distr = new NormalDistribution();
        divide(c.getVectorsList());
    }

    public void start() {
        errors = new ArrayList<>();
        learn(100, 0.01);
        learn(100, 0.001);
        learn(100, 0.0001);
        learn(500, 0.01);
        learn(500, 0.001);
        learn(500, 0.0001);
    }

    private void divide(List<VectorWithNote> list) {
        int i = (int) (list.size() * 0.9);

        for (int j = 0; j < list.size(); j++) {
            if (j < i) {
                learningList.add(list.get(j));
            }
            else {
                testingList.add(list.get(j));
            }
        }
    }

    private Double function(Double s) {
        return 1.0/(1.0 + exp(-1 * s));
    }

    private void learn(int iterations, double alfa) {
        ArrayRealVector v = new ArrayRealVector(distr.sample(5));
        for(int k = 0; k < iterations; k++) {
            for(int i = 0; i < learningList.size(); i++){
                double s = v.dotProduct(learningList.get(i));
                double f = function(s);
                double nav = alfa*(f - learningList.get(i).getNote());
                double derivative = f*(1.0-f);
                double wsp = nav * derivative;
                ArrayRealVector vc = new ArrayRealVector(learningList.get(i).mapMultiply(wsp));
                v = v.subtract(vc);
            }
            calculateError(v);
        }
        StringBuilder sb = new StringBuilder((String.format("%.4f", alfa)));
        sb.deleteCharAt(0);
        sb.deleteCharAt(0);
        saveToFile("Alfa" + sb.toString() + "Epoki" + iterations, v);
    }

    private void calculateError(ArrayRealVector vector) {
        Double errorSum = 0.0;
        for(int i = 0; i < testingList.size(); i ++) {
            double t = testingList.get(i).dotProduct(vector);
            double w = function(t);
            Double e = Math.pow((testingList.get(i).getNote() - w ), 2);
            errorSum += e;
        }
        Double average = errorSum / testingList.size();
        errors.add(average);
    }



    private void saveToFile(String filename, ArrayRealVector vector) {
        try {
            PrintWriter writer = new PrintWriter(filename + ".txt");
            Double errorSum = 0.0;
            for(int i = 0; i < testingList.size(); i ++) {
                double t = testingList.get(i).dotProduct(vector);
                double w = function(t);
                Double e = 0.5 * Math.pow((testingList.get(i).getNote() - w ), 2);
                writer.println(i+". Obliczono: " + w + "; Referencyjna: " + testingList.get(i).getNote() + "; Blad: " + e);
                errorSum += e;
            }
            Double average = errorSum / testingList.size();
            writer.println("Sredni blad: " + average);
            writer.close();

            Graph.draw(filename, errors);
            errors = new ArrayList<>();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }


}
