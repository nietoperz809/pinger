import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger
{
    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    private static final String FNAME = "pinglog.txt";

    public static String getContent()
    {
        try
        {
            return new String (Files.readAllBytes(Paths.get(FNAME)));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return "ERROR";
    }

    public static String getCurrentTime()
    {
        return dateFormat.format(new Date());
    }

    public static void log (String txt)
    {
        try(PrintWriter _out = new PrintWriter(new BufferedWriter(new FileWriter(FNAME, true))))
        {
            _out.println (getCurrentTime() + " " + txt);
        }
        catch (IOException e)
        {
            System.err.println("Logger not open");
            System.err.println(e);
        }
    }
}
