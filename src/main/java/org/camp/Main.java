package org.camp;

import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        List<String> inputData = readData();

        List<String> outputData = new ArrayList<>();
        Map<String, Subscriber> subscribers = new HashMap<>();

        for (String callData: inputData) {
            String[] tmp = callData.replace(" ", "").split(",");
            Subscriber currentSubscriber = subscribers.get(tmp[1]);
            if (currentSubscriber == null) {
                subscribers.put(tmp[1], new Subscriber(tmp[0], tmp[1], tmp[2], tmp[3], tmp[4]));
            } else {
                currentSubscriber.addCall(tmp[0], tmp[2], tmp[3]);
                System.out.println();
            }
        }

        writeData(outputData);
    }

    private static List<String> readData() {
        List<String> text = new ArrayList<>();
        try (BufferedReader inputFile = new BufferedReader(new FileReader("cdr.txt"))) {
            String currentLine = inputFile.readLine();

            while (currentLine != null) {
                text.add(currentLine);
                currentLine = inputFile.readLine();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return text;
    }

    private static void writeData(List<String> writeData) {
        try (BufferedWriter outputFile = new BufferedWriter(new FileWriter("reports\\output.txt"))) {

            for (String currentLine : writeData) {
                outputFile.write(currentLine);
            }

            outputFile.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


}