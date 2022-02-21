package ru.nsu.fit;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.FileOutputStream;

public class Main {
    static final int NUM_OF_IN_FILENAME = 0;
    static final int NUM_OF_OUT_FILENAME = 1;
    public static void main(String[] args) {

        BufferedReader reader = null;
        Data data = null;
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(args[NUM_OF_IN_FILENAME])));
            Parser parser = new Parser();
            parser.parseData(reader);
            data = parser.getParsedData();
        }
        catch (IOException e) {
            System.err.println("Error while reading file: " + e.getLocalizedMessage());
        }
        finally {
            if (null != reader) {
                try {
                    reader.close();
                }
                catch (IOException e) {
                    e.printStackTrace(System.err);
                    // Prints this throwable and its backtrace to the specified print stream.
                }
            }
        }
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(args[NUM_OF_OUT_FILENAME])));
            CSVWriter csvWriter = new CSVWriter();
            csvWriter.writeSortedData(data, writer);
        }
        catch (IOException e) {
            System.err.println("Error while writing file: " + e.getLocalizedMessage());
        }
        finally {
            if (null != writer) {
                try {
                    writer.close();
                }
                catch (IOException e) {
                    e.printStackTrace(System.err);
                    // Prints this throwable and its backtrace to the specified print stream.
                }
            }
        }
    }
}

