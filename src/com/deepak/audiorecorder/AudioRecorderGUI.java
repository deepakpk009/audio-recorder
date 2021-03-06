/*
This file is part of AudioRecorder v0.1

AudioRecorder is free software: you can redistribute it and/or modify
it under the terms of the GNU Lesser General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

AudioRecorder is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public License
along with AudioRecorder.  If not, see <http://www.gnu.org/licenses/>.
 */

/*
 * AudioRecorderGUI.java
 *
 * Created on Oct 5, 2012, 6:50:22 PM
 */
package com.deepak.audiorecorder;

import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.media.CannotRealizeException;
import javax.media.NoDataSinkException;
import javax.media.NoDataSourceException;
import javax.media.NoProcessorException;

/**
 *
 * @author deepak
 */
/*
 * the class which provides the gui for the recorder
 */
public class AudioRecorderGUI extends javax.swing.JFrame {

    // the audio recorder object
    private AudioRecorder audioRecorder = null;

    /** Creates new form AudioRecorderGUI */
    public AudioRecorderGUI() {
        initComponents();
        // initialise the video recorder object
        audioRecorder = new AudioRecorder();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        recordToggleButton = new javax.swing.JToggleButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Audio Recorder");

        recordToggleButton.setText("Record");
        recordToggleButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                recordToggleButton_MouseClickedEvent(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(recordToggleButton, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(recordToggleButton, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void recordToggleButton_MouseClickedEvent(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_recordToggleButton_MouseClickedEvent
        // TODO add your handling code here:
        if (recordToggleButton.getText().contentEquals("Record")) {
            try {
                // start the recorder and provide a random file name for saving
                // @update <correction> changed .mp3 to .wav
                audioRecorder.startRecording("Recorded_Audios/" + new Random().nextInt(Integer.MAX_VALUE) + ".wav");
            } catch (IOException ex) {
                Logger.getLogger(AudioRecorderGUI.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NoDataSourceException ex) {
                Logger.getLogger(AudioRecorderGUI.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NoProcessorException ex) {
                Logger.getLogger(AudioRecorderGUI.class.getName()).log(Level.SEVERE, null, ex);
            } catch (CannotRealizeException ex) {
                Logger.getLogger(AudioRecorderGUI.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NoDataSinkException ex) {
                Logger.getLogger(AudioRecorderGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
            // set the toggle button name as stop
            recordToggleButton.setText("Stop");
        } // else stop recording
        else {
            // stop recorder
            audioRecorder.stopRecording();
            // reset the toggle button name to record
            recordToggleButton.setText("Record");
        }
    }//GEN-LAST:event_recordToggleButton_MouseClickedEvent

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new AudioRecorderGUI().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton recordToggleButton;
    // End of variables declaration//GEN-END:variables
}
