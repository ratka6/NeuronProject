import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by krzysiek on 23.01.2017.
 */
public class ParseCSV {
    private CSVParser parser;
    private File csvData;

    private List<VectorWithNote> vectorsList;

    public List<VectorWithNote> getVectorsList() {
        return vectorsList;
    }

    public ParseCSV() {
        csvData = new File("transfusion.data.txt");
        try {
            parser = CSVParser.parse(csvData, Charset.defaultCharset(), CSVFormat.MYSQL);
            vectorsList = new ArrayList<>();
            parseToLists();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void parseToLists() {
        int i = 0;
        for (CSVRecord record : parser) {
            if (i != 0) {
                VectorWithNote v;
                for (String field: record) {
                    int j = 0;
                    double[] d = new double[5];
                    double n = 0.0;
                    for (String num: field.split(",")){
                        num = num.replaceAll("\\s+", "");
                        if (j < 4){
                            d[j] = (double)Integer.parseInt(num);
                        }
                        else {
                            n = (double)Integer.parseInt(num);

                        }
                        j++;
                    }
                    d[4] = 1;
                    v = new VectorWithNote(d, n);
                    vectorsList.add(v);
                }
            }
            i++;
        }

        normalizeInput();
    }

    private void normalizeInput() {

        double[] min = {0.0,0.0,0.0,0.0};
        double[] max = {0.0,0.0,0.0,0.0};

        for (VectorWithNote v: vectorsList) {
            for (int i = 0; i < 4; i++) {
                double a = v.getEntry(i);
                if (a < min[i]) {
                    min[i] = a;
                }
                if (a > max[i]) {
                    max[i] = a;
                }
            }
        }

        for (VectorWithNote v: vectorsList) {
            for (int i = 0; i < 4; i++) {
                double k = (v.getEntry(i) - min[i]) / (max[i] - min[i]);
                v.setEntry(i, k);
            }
        }
    }

}
