/*-----------------------------------
AudioRecorder v0.1
-------------------------------------
a audio recorder module using JMF
-------------------------------------
Developed By : deepak pk
Email : deepakpk009@yahoo.in
-------------------------------------
This Project is Licensed under LGPL
-------------------------------------

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Lesser General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU Lesser General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.deepak.audiorecorder;

import java.io.IOException;
import javax.media.*;
import javax.media.format.*;
import javax.media.protocol.*;
import javax.media.datasink.*;

/*
 * this class provides the audio rcorder
 */
public class AudioRecorder {

    // the media loccator object
    private MediaLocator ml = null;
    // The Processor processes data and creates an output in the destination format required
    private Processor processor = null;
    // takes a DataSource as input and renders the output to a specified destination
    private DataSink dataSink = null;
    private TheDataSinkListener dataSinkListener = null;

    /*
     * method the initiates the recording process and saves the recorded audio into the file specified
     */
    public void startRecording(String saveFile) throws IOException, NoDataSourceException, NoProcessorException, CannotRealizeException, NoDataSinkException {
        // get the default audio device and create an audio data source
        ml = new MediaLocator("javasound://0");

        // create the audio data source
        DataSource audioDataSource = Manager.createDataSource(ml);

        // setup output file format to wave format
        FileTypeDescriptor outputType = new FileTypeDescriptor(FileTypeDescriptor.WAVE);
        // setup output audio data format
        Format outputFormat[] = new Format[1];
        outputFormat[0] = new AudioFormat(AudioFormat.LINEAR);

        // create a new processor
        ProcessorModel processorModel = new ProcessorModel(audioDataSource, outputFormat, outputType);
        processor = Manager.createRealizedProcessor(processorModel);

        // get the output of the processor to be used as the datasink input
        DataSource source = processor.getDataOutput();

        // create a File protocol MediaLocator with the location of the file to which bits are to be written
        MediaLocator mediadestination = new MediaLocator("file:" + saveFile);

        // create a datasink to create the video file
        dataSink = Manager.createDataSink(source, mediadestination);

        // create a listener to control the datasink
        dataSinkListener = new TheDataSinkListener();
        dataSink.addDataSinkListener(dataSinkListener);
        dataSink.open();

        // now start the datasink and processor
        dataSink.start();
        processor.start();
    }

    /*
     * method used to stop the recording process
     */
    public void stopRecording() {
        // Stop the processor doing the movie capture first
        processor.stop();
        processor.close();
        // Closing the processor will end the data stream to the data sink.
        // Wait for the end of stream to occur before closing the datasink
        dataSinkListener.waitEndOfStream(100);
        dataSink.close();
    }
}

/**
 *
 * Control the ending of the program prior to closing the data sink
 */
class TheDataSinkListener implements DataSinkListener {

    boolean endOfStream = false;

    // Flag the ending of the data stream
    public void dataSinkUpdate(DataSinkEvent event) {
        if (event instanceof javax.media.datasink.EndOfStreamEvent) {
            endOfStream = true;
        }
    }

    /**
     * Cause the current thread to sleep if the data stream is still available.
     * This makes certain that JMF threads are done prior to closing the data sink
     * and finalizing the output file
     */
    public void waitEndOfStream(long checkTimeMs) {
        while (!endOfStream) {
            try {
                //Thread.currentThread().sleep(checkTimeMs);
                Thread.sleep(checkTimeMs);
            } catch (InterruptedException ie) {
                //exception handling here
            }
        }
    }
}
