package org.camp;

import java.io.*;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        List<String> inputData = readData();

        Map<String, Subscriber> subscribers = new HashMap<>();

        for (String callData: inputData) {
            String[] tmp = callData.replace(" ", "").split(",");
            Subscriber currentSubscriber = subscribers.get(tmp[1]);
            if (currentSubscriber == null) {
                subscribers.put(tmp[1], new Subscriber(new Call(tmp[0], tmp[2], tmp[3]), tmp[4]));
            } else {
                currentSubscriber.addCall(new Call(tmp[0], tmp[2], tmp[3]));
            }
        }

        writeData(subscribers);
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

    private static void writeData(Map<String, Subscriber> subscribers) {
        DateFormat format = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
        NumberFormat numberFormat = new DecimalFormat("#0.00");
        for (Map.Entry<String, Subscriber> tmp : subscribers.entrySet()) {
            try (BufferedWriter outputFile = new BufferedWriter(new FileWriter("reports/" + tmp.getKey() + ".txt"))) {
                Subscriber currentSubscriber = tmp.getValue();
                StringBuilder outputData = new StringBuilder();
                outputData.append("Tariff index: ").append(currentSubscriber.getTariff()).append("\n");
                outputData.append("---------------------------------------------------------------------------\n");
                outputData.append("Report for phone number ").append(tmp.getKey()).append(":\n");
                outputData.append("---------------------------------------------------------------------------\n");
                outputData.append("  Call Type |   Start Time        |     End Time        | Duration | Cost \n");
                for (Call currentCall : currentSubscriber.getCalls()) {
                    outputData.append("     ").append(currentCall.getCallType()).append("     | ");
                    outputData.append(format.format(currentCall.getStartedTime())).append(" | ");
                    outputData.append(format.format(currentCall.getFinishedTime())).append(" | ");
                    Duration duration = Duration.between(currentCall.getStartedTime().toInstant(), currentCall.getFinishedTime().toInstant());
                    outputData.append(String.format("%02d:%02d:%02d", duration.toHoursPart(), duration.toMinutesPart(), duration.toSecondsPart()))
                            .append(" | ");
                    outputData.append(numberFormat.format(currentCall.getCallCost()));
                    currentSubscriber.setMoneyAmount(currentSubscriber.getMoneyAmount() + currentCall.getCallCost());

                    outputData.append("\n");
                }
                outputData.append("---------------------------------------------------------------------------\n");
                outputData.append("                                            Total Cost: | ").
                        append(numberFormat.format(currentSubscriber.getMoneyAmount())).append(" rubles |\n");
                outputData.append("---------------------------------------------------------------------------\n");

                outputFile.write(outputData.toString());
                outputFile.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }


}