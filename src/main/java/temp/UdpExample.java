package temp;


import org.strix.mom.server.communication.impl.UdpServer;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.DatagramPacket;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 * <p>This is a sample app to show some ways to interact with the {@link UdpServer}.</p>
 *
 * <p>This code is released into the Public Domain.
 * Since this is Public Domain, you don't need to worry about
 * licensing, and you can simply copy this UdpServer.java file
 * to your own package and use it as you like. Enjoy.
 * Please consider leaving the following statement here in this code:</p>
 *
 * <p><em>This <tt>UdpServer</tt> class was copied to this project from its source as 
 * found at <a href="http://iharder.net" target="_blank">iHarder.net</a>.</em></p>
 *
 * @author Robert Harder
 * @author rharder@users.sourceforge.net
 * @version 0.1
 * @see UdpServer
 * @see UdpServer.Listener
 * @see UdpServer.Event
 */
public class UdpExample extends javax.swing.JFrame implements UdpServer.Listener {

    private final static long serialVersionUID = 1;

    /** Creates new form UdpExample */
    public UdpExample() {
        initComponents();
        myInitComponents();
    }

    private void myInitComponents(){

        UdpServer.setLoggingLevel(Level.OFF);

        this.udpServer.addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {

                final String prop = evt.getPropertyName();
                final Object oldVal = evt.getOldValue();
                final Object newVal = evt.getNewValue();
                System.out.println("Property: " + prop + ", Old: " + oldVal + ", New: " + newVal );

                if( UdpServer.STATE_PROP.equals( prop ) ){
                    final UdpServer.State state = (UdpServer.State)newVal;
                    SwingUtilities.invokeLater( new Runnable() {
                        public void run() {
                            switch( state ){
                                case STARTING:
                                    stateLabel.setText( "Starting" );
                                    startStopButton.setEnabled( false );
                                    break;
                                case STARTED:
                                    stateLabel.setText( "Started" );
                                    startStopButton.setText( "Stop" );
                                    startStopButton.setEnabled( true );
                                    break;
                                case STOPPING:
                                    stateLabel.setText( "Stopping" );
                                    startStopButton.setEnabled( false );
                                    break;
                                case STOPPED:
                                    stateLabel.setText( "Stopped" );
                                    startStopButton.setText( "Start" );
                                    startStopButton.setEnabled( true );
                                    break;
                                default:
                                    assert false : state;
                                    break;
                            }   // end switch
                        }   // end run
                    });
                }

                if( UdpServer.PORT_PROP.equals( evt.getPropertyName() ) ){
                    SwingUtilities.invokeLater( new Runnable() {
                        public void run() {
                            portField.setValue( newVal );
                        }   // end run
                    }); // end swing utilities

                } else if( UdpServer.GROUPS_PROP.equals( evt.getPropertyName() ) ){
                    SwingUtilities.invokeLater( new Runnable() {
                        public void run() {
                            groupField.setText( newVal == null ? "" : newVal.toString() );
                        }   // end run
                    }); // end swing utilities

                }
            }
        });
        this.udpServer.fireProperties();
        this.udpServer.addUdpServerListener(this);
    }




    public void packetReceived(UdpServer.Event evt) {
        this.receiveIndicator.indicate();
        DatagramPacket packet = evt.getUdpServer().getPacket(); // Not actually using this here.
        final String s = evt.getPacketAsString();

        // A more efficient way would be to subclass SwingWorker
        // and make a public "myPublish" method, and use that to
        // call SwingWorker's publish method, queuing up items
        // to process in a batch.
        // Still, this works fine for demonstration.
        SwingUtilities.invokeLater( new Runnable() {
            public void run() {
                incomingArea.setText( incomingArea.getText() + s + "\n" );
            }   // end run
        }); // end swing utilities
    }


    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        udpServer = new UdpServer();
        jLabel1 = new javax.swing.JLabel();
        portField = new javax.swing.JFormattedTextField(0);
        jLabel2 = new javax.swing.JLabel();
        groupField = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        incomingArea = new javax.swing.JTextArea();
        jLabel3 = new javax.swing.JLabel();
        stateLabel = new javax.swing.JLabel();
        startStopButton = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        receiveIndicator = new IndicatorLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Udp Server Example");

        jLabel1.setText("Port:");

        portField.setColumns(12);
        portField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                portFieldActionPerformed(evt);
            }
        });
        portField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                portFieldFocusLost(evt);
            }
        });

        jLabel2.setText("Multicast Group:");

        groupField.setColumns(12);
        groupField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                groupFieldActionPerformed(evt);
            }
        });
        groupField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                groupFieldFocusLost(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Incoming"));

        incomingArea.setColumns(20);
        incomingArea.setLineWrap(true);
        incomingArea.setRows(5);
        incomingArea.setWrapStyleWord(true);
        jScrollPane1.setViewportView(incomingArea);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 443, Short.MAX_VALUE)
                                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
                                .addContainerGap())
        );

        jLabel3.setText("State:");

        stateLabel.setText("Unknown");

        startStopButton.setText("Start/Stop");
        startStopButton.setEnabled(false);
        startStopButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startStopButtonActionPerformed(evt);
            }
        });

        jButton1.setText("Stress");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        receiveIndicator.setText("RECV");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel2)
                                                        .addComponent(jLabel1))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(portField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(18, 18, 18)
                                                                .addComponent(jLabel3)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(stateLabel))
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(groupField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(18, 18, 18)
                                                                .addComponent(startStopButton)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(jButton1)
                                                                .addGap(18, 18, 18)
                                                                .addComponent(receiveIndicator, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                .addGap(4, 4, 4)))
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jLabel1)
                                                        .addComponent(portField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jLabel3)
                                                        .addComponent(stateLabel))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jLabel2)
                                                        .addComponent(groupField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(startStopButton)
                                                        .addComponent(jButton1)))
                                        .addComponent(receiveIndicator, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void portFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_portFieldActionPerformed
        try {
            JFormattedTextField field = (JFormattedTextField) evt.getSource();
            field.commitEdit();//GEN-LAST:event_portFieldActionPerformed
            this.udpServer.setPort((Integer)field.getValue());
        } catch (ParseException ex) {
            Logger.getLogger(UdpExample.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void groupFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_groupFieldActionPerformed
        JTextField field = (JTextField)evt.getSource();
        String val = field.getText();
        this.udpServer.setGroups(val);
    }//GEN-LAST:event_groupFieldActionPerformed

    private void startStopButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startStopButtonActionPerformed
        UdpServer.State state = this.udpServer.getState();
        switch( state ){
            case STOPPED:
                this.udpServer.start();
                break;
            case STARTED:
                this.udpServer.stop();
                break;
            default:
                System.err.println("Shouldn't see this. State: " + state );
                break;
        }
    }//GEN-LAST:event_startStopButtonActionPerformed

    private void portFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_portFieldFocusLost
        try {
            JFormattedTextField field = (JFormattedTextField) evt.getSource();
            field.commitEdit();
            this.udpServer.setPort((Integer)field.getValue());
        } catch (ParseException ex) {
            Logger.getLogger(UdpExample.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_portFieldFocusLost

    private void groupFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_groupFieldFocusLost
        JTextField field = (JTextField)evt.getSource();
        String val = field.getText();
        this.udpServer.setGroups(val);
    }//GEN-LAST:event_groupFieldFocusLost

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        this.incomingArea.setText( this.incomingArea.getText() +
                "(Stressing the server by starting and stopping it randomly " +
                "with 100 threads, 100 times over ~10 seconds. Look for thrown " +
                "Exceptions in console.)\n" );
        for( int i = 0; i < 100; i++ ){
            Thread t = new Thread( new Runnable() {
                public void run() {
                    for( int i = 0; i < 100; i++ ){
                        double d = Math.random();
                        if( d < .5 ){
                            udpServer.start();
                        } else {
                            udpServer.stop();
                        }
                        try{
                            Thread.sleep( (int)(Math.random()*100) );
                        } catch( InterruptedException exc ){
                            exc.printStackTrace();
                        }   // end catch
                    }   // end for
                }   // end runnable
            }); // end thread
            t.start();
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new UdpExample().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField groupField;
    private javax.swing.JTextArea incomingArea;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JFormattedTextField portField;
    private IndicatorLabel receiveIndicator;
    private javax.swing.JButton startStopButton;
    private javax.swing.JLabel stateLabel;
    private UdpServer udpServer;
    // End of variables declaration//GEN-END:variables


}
