import org.apache.commons.math3.linear.ArrayRealVector;

/**
 * Created by krzysiek on 23.01.2017.
 */


public class VectorWithNote extends ArrayRealVector {
    private Double note;

    public Double getNote() {
        return note;
    }

    public VectorWithNote(double[] d, Double note) {
        super(d);
        this.note = note;
    }

    @Override
    public String toString() {
        return super.toString() + ", wartosc referencyja = " + note;
    }
}
