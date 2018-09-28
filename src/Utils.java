import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.BufferedInputStream;
import java.io.InputStream;

public class Utils
{
    public static void playWaveFromResource (String name)
    {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        InputStream is = new BufferedInputStream(loader.getResourceAsStream(name));
        playWave (is);
    }

    public static void playWave (InputStream is)
    {
        try
        {
            Clip clip = AudioSystem.getClip();
            clip.open (AudioSystem.getAudioInputStream(is));
            clip.start();
        }
        catch (Exception exc)
        {
            exc.printStackTrace(System.out);
        }
    }

//    public static void playWave (String filename)
//    {
//        try
//        {
//            playWave(new FileInputStream(new File(filename)));
//        }
//        catch (Exception exc)
//        {
//            exc.printStackTrace(System.out);
//        }
//    }
}
