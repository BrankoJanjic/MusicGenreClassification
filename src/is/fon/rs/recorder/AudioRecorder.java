package is.fon.rs.recorder;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

import javax.sound.sampled.*;

@SuppressWarnings("serial")
public class AudioRecorder  extends JFrame{

  boolean stopCapture = false;
  ByteArrayOutputStream
                 byteArrayOutputStream;
  AudioFormat audioFormat;
  TargetDataLine targetDataLine;
  AudioInputStream audioInputStream;
  SourceDataLine sourceDataLine;
  File file=new File("arun.au");
  FileOutputStream fout;
  AudioFileFormat.Type fileType;
  public static void main(
                        String args[]){
    new AudioRecorder();
  }
  
  public AudioRecorder(){
	  try {
		fout=new FileOutputStream(file);
	} catch (FileNotFoundException e1) {
		
		e1.printStackTrace();
	}
    final JButton captureBtn =
                new JButton("Capture");
    final JButton stopBtn =
                   new JButton("Stop");
    final JButton playBtn =
               new JButton("Save");

    captureBtn.setEnabled(true);
    stopBtn.setEnabled(false);
    playBtn.setEnabled(false);
    
    captureBtn.addActionListener(
      new ActionListener(){
        public void actionPerformed(
                        ActionEvent e){
          captureBtn.setEnabled(false);
          stopBtn.setEnabled(true);
          playBtn.setEnabled(false);
         
          captureAudio();
        }
      }
    );
    getContentPane().add(captureBtn);

    stopBtn.addActionListener(
      new ActionListener(){
        public void actionPerformed(
                        ActionEvent e){
          captureBtn.setEnabled(true);
          stopBtn.setEnabled(false);
          playBtn.setEnabled(true);
          //Terminate the capturing of
          // input data from the
          // microphone.
          stopCapture = true;
        }
      }
    );
    getContentPane().add(stopBtn);

    playBtn.addActionListener(
      new ActionListener(){
        public void actionPerformed(
                        ActionEvent e){
          
          saveAudio();
        }
      }
    );
    getContentPane().add(playBtn);

    getContentPane().setLayout(
                     new FlowLayout());
    setTitle("Capture/Playback Demo");
    setDefaultCloseOperation(
                        EXIT_ON_CLOSE);
    setSize(250,70);
    setVisible(true);
  }

 
  private void captureAudio(){
    try{
      
      audioFormat = getAudioFormat();
      DataLine.Info dataLineInfo =
                new DataLine.Info(
                  TargetDataLine.class,
                   audioFormat);
      targetDataLine = (TargetDataLine)
                   AudioSystem.getLine(
                         dataLineInfo);
      targetDataLine.open(audioFormat);
      targetDataLine.start();

      Thread captureThread =
                new Thread(
                  new CaptureThread());
      captureThread.start();
    } catch (Exception e) {
      System.out.println(e);
      System.exit(0);
    }
  }

  
  private void saveAudio() {
    try{
 
      byte audioData[] =
                 byteArrayOutputStream.
                         toByteArray();

      InputStream byteArrayInputStream
            = new ByteArrayInputStream(
                            audioData);
      AudioFormat audioFormat =
                      getAudioFormat();
      audioInputStream =
        new AudioInputStream(
          byteArrayInputStream,
          audioFormat,
          audioData.length/audioFormat.
                       getFrameSize());
      DataLine.Info dataLineInfo =
                new DataLine.Info(
                  SourceDataLine.class,
                          audioFormat);
      sourceDataLine = (SourceDataLine)
                   AudioSystem.getLine(
                         dataLineInfo);
      sourceDataLine.open(audioFormat);
      sourceDataLine.start();

     
      Thread saveThread =
          new Thread(new SaveThread());
      saveThread.start();
    } catch (Exception e) {
      System.out.println(e);
      System.exit(0);
    }
  }


  private AudioFormat getAudioFormat(){
	  float sampleRate = 44100.0F;
	    
	    int sampleSizeInBits = 16;
	   
	    //8,16
	    int channels = 2;
	    //1,2
 
    boolean signed = true;
    boolean bigEndian = true;
    return new AudioFormat(
                      sampleRate,
                      sampleSizeInBits,
                      channels,
                      signed,
                      bigEndian);
  }
class CaptureThread extends Thread{

  byte tempBuffer[] = new byte[10000];
  public void run(){
    byteArrayOutputStream =
           new ByteArrayOutputStream();
    stopCapture = false;
    try{
      while(!stopCapture){
        
        int cnt = targetDataLine.read(
                    tempBuffer,
                    0,
                    tempBuffer.length);
        if(cnt > 0){
          
          byteArrayOutputStream.write(
                   tempBuffer, 0, cnt);
         
        
         
        
        }
      }
      byteArrayOutputStream.close();
    }catch (Exception e) {
      System.out.println(e);
      System.exit(0);
    }
  }
}
class SaveThread extends Thread{
  byte tempBuffer[] = new byte[10000];

  @SuppressWarnings("unused")
public void run(){
    try{
      int cnt;
      
      
       if (AudioSystem.isFileTypeSupported(AudioFileFormat.Type.AU, 
              audioInputStream)) {
            AudioSystem.write(audioInputStream, AudioFileFormat.Type.AU, file);
       } 
     
    }catch (Exception e) {
      System.out.println(e);
      System.exit(0);
    }
  }
}

}
