/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.smarttermoazureiot;

import com.microsoft.azure.eventhubs.ConnectionStringBuilder;
import com.microsoft.azure.eventhubs.EventData;
import com.microsoft.azure.eventhubs.EventHubClient;
import com.microsoft.azure.eventhubs.EventHubException;
import com.microsoft.azure.eventhubs.EventPosition;
import com.microsoft.azure.eventhubs.EventHubRuntimeInformation;
import com.microsoft.azure.eventhubs.PartitionReceiver;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.nio.charset.Charset;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Turma A
 */
public class RemoteConsole extends javax.swing.JFrame {
    // az iot hub show --query properties.eventHubEndpoints.events.endpoint --name {your IoT Hub name}

    private static final String eventHubsCompatibleEndpoint = "sb://ihsuprodamres025dednamespace.servicebus.windows.net/";

    // az iot hub show --query properties.eventHubEndpoints.events.path --name {your IoT Hub name}
    private static final String eventHubsCompatiblePath = "iothub-ehub-smarttermo-2569718-382393b81b";

    // az iot hub policy show --name service --query primaryKey --hub-name {your IoT Hub name}
    private static final String iotHubSasKey = "MzWuyO86CAcXRffPn/X1bHW31vmH0atdbJXZMPkUagg=";
    private static final String iotHubSasKeyName = "service";

    private static ArrayList<PartitionReceiver> receivers = new ArrayList<PartitionReceiver>();

    private final ConnectionStringBuilder connStr;
    private final ScheduledExecutorService executorService;
    private final EventHubClient ehClient;
    private final EventHubRuntimeInformation eventHubInfo;

    // Track all the PartitionReciever instances created.
    /**
     * Creates new form RemoteConsole
     */
    public RemoteConsole() throws URISyntaxException, EventHubException, IOException, InterruptedException, ExecutionException {
        initComponents();

        connStr = new ConnectionStringBuilder()
                .setEndpoint(new URI(eventHubsCompatibleEndpoint))
                .setEventHubName(eventHubsCompatiblePath)
                .setSasKeyName(iotHubSasKeyName)
                .setSasKey(iotHubSasKey);

        executorService = Executors.newSingleThreadScheduledExecutor();
        ehClient = EventHubClient.createFromConnectionStringSync(connStr.toString(), executorService);

        eventHubInfo = ehClient.getRuntimeInformation().get();

        for (String partitionId : eventHubInfo.getPartitionIds()) {
            receiveMessages(ehClient, partitionId);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextAreaRemoteConsole = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTextAreaRemoteConsole.setColumns(20);
        jTextAreaRemoteConsole.setRows(5);
        jScrollPane1.setViewportView(jTextAreaRemoteConsole);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(RemoteConsole.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RemoteConsole.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RemoteConsole.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RemoteConsole.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new RemoteConsole().setVisible(true);
                } catch (URISyntaxException ex) {
                    Logger.getLogger(RemoteConsole.class.getName()).log(Level.SEVERE, null, ex);
                } catch (EventHubException ex) {
                    Logger.getLogger(RemoteConsole.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(RemoteConsole.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InterruptedException ex) {
                    Logger.getLogger(RemoteConsole.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ExecutionException ex) {
                    Logger.getLogger(RemoteConsole.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextAreaRemoteConsole;
    // End of variables declaration//GEN-END:variables

    private void receiveMessages(EventHubClient ehClient, String partitionId) throws EventHubException {
        final ExecutorService executorService = Executors.newSingleThreadExecutor();

        // Create the receiver using the default consumer group.
        // For the purposes of this sample, read only messages sent since 
        // the time the receiver is created. Typically, you don't want to skip any messages.
        ehClient.createReceiver(EventHubClient.DEFAULT_CONSUMER_GROUP_NAME, partitionId,
                EventPosition.fromEnqueuedTime(Instant.now())).thenAcceptAsync(receiver -> {
            jTextAreaRemoteConsole.append(String.format("Starting receive loop on partition: %s", partitionId));
            jTextAreaRemoteConsole.append(String.format("Reading messages sent since: %s", Instant.now().toString()));
            jTextAreaRemoteConsole.append(String.format("\n"));

            receivers.add(receiver);

            while (true) {
                try {
                    // Check for EventData - this methods times out if there is nothing to retrieve.
                    Iterable<EventData> receivedEvents = receiver.receiveSync(100);

                    // If there is data in the batch, process it.
                    if (receivedEvents != null) {
                        for (EventData receivedEvent : receivedEvents) {
                            jTextAreaRemoteConsole.append(String.format("Telemetry received:\n %s"));
                            jTextAreaRemoteConsole.append(String.format("Telemetry received:\n %s",
                                    new String(receivedEvent.getBytes(), Charset.defaultCharset())));
                            jTextAreaRemoteConsole.append(String.format("Application properties (set by device):\n%s", receivedEvent.getProperties().toString()));
                            jTextAreaRemoteConsole.append(String.format("System properties (set by IoT Hub):\n%s\n", receivedEvent.getSystemProperties().toString()));
                        }
                    }
                } catch (EventHubException e) {
                    jTextAreaRemoteConsole.append("Error reading EventData");
                }
            }
        }, executorService);
    }
}
