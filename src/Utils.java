import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.concurrent.CountDownLatch;

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
        CountDownLatch syncLatch = new CountDownLatch(1);
        try
        {
            Clip clip = AudioSystem.getClip();
            clip.addLineListener(e ->
            {
                if (e.getType() == LineEvent.Type.STOP)
                {
                    syncLatch.countDown();
                }
            });
            clip.open (AudioSystem.getAudioInputStream(is));
            clip.start();
            syncLatch.await();
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
