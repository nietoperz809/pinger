import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger
{
    private DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    public Logger()
    {
    }

    public void log (String txt)
    {
        try(PrintWriter _out = new PrintWriter(new BufferedWriter(new FileWriter("pinglog.txt", true))))
        {
            String time = dateFormat.format(new Date());
            _out.println (time + " " + txt);
        }
        catch (IOException e)
        {
            System.err.println("Logger not open");
            System.err.println(e);
        }
    }
}
