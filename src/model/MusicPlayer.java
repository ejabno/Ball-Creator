package model;

import javax.sound.sampled.*;
import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

public class MusicPlayer implements MusicInfoProvider{
    Clip leftMouseClip, rightMouseClip, backgroundClip;
    double volumeMultiplier;
    FloatControl gainControlLeft, gainControlRight, gainControlBG;

    public MusicPlayer() {
        volumeMultiplier = 1.0;
        try {
            File leftMouseSoundFile = new File("src/music/Leftmouse.wav");
            File rightMouseSoundFile = new File("src/music/Rightmouse.wav");
            File backgroundSoundFile = new File ("src/music/Background.wav");
            AudioInputStream audioInLeft = AudioSystem.getAudioInputStream(leftMouseSoundFile);
            AudioInputStream audioInRight = AudioSystem.getAudioInputStream(rightMouseSoundFile);
            AudioInputStream audioInBG = AudioSystem.getAudioInputStream(backgroundSoundFile);
            leftMouseClip = AudioSystem.getClip();
            leftMouseClip.open(audioInLeft);
            rightMouseClip = AudioSystem.getClip();
            rightMouseClip.open(audioInRight);
            backgroundClip = AudioSystem.getClip();
            backgroundClip.open(audioInBG);

            gainControlLeft = (FloatControl) leftMouseClip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControlRight = (FloatControl) rightMouseClip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControlBG = (FloatControl) backgroundClip.getControl(FloatControl.Type.MASTER_GAIN);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        gainControlLeft.setValue(gainToDb(0));
        gainControlRight.setValue(gainToDb(0));
        gainControlBG.setValue(gainToDb(0.50));

        FloatControl leftPan = (FloatControl) leftMouseClip.getControl(FloatControl.Type.PAN);
        leftPan.setValue((float) -1);
        FloatControl rightPan = (FloatControl) rightMouseClip.getControl(FloatControl.Type.PAN);
        rightPan.setValue((float) 1);


        backgroundClip.loop(Clip.LOOP_CONTINUOUSLY);
        leftMouseClip.loop(Clip.LOOP_CONTINUOUSLY);
        rightMouseClip.loop(Clip.LOOP_CONTINUOUSLY);

    }

    private boolean leftToggled = false;
    public void toggleLeftLoop() {
        if (!leftToggled) {
            gainControlLeft.setValue(gainToDb(1.0 * volumeMultiplier));
            leftToggled = true;
        }
        else {
            gainControlLeft.setValue(gainToDb(0 * volumeMultiplier));
            leftToggled = false;
        }
    }

    private boolean rightToggled = false;
    public void toggleRightLoop() {
        if (!rightToggled) {
            gainControlRight.setValue(gainToDb(1.0 * volumeMultiplier));
            rightToggled = true;
        }
        else {
            gainControlRight.setValue(gainToDb(0 * volumeMultiplier));
            rightToggled = false;
        }
    }

    @Override
    public boolean isMuted() {
        return (volumeMultiplier > 0.0);
    }

    float gainToDb(double gain) {
        return (float) (Math.log(gain) / Math.log(10.0) * 20);
    }

    float dBToGain(double dB) {
        return (float) (20 * Math.log(10.0) * dB);
    }
}
