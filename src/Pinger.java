import java.net.InetAddress;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class Pinger
{
    private String _host;
    private InetState _currentState = InetState.UNKNOWN;
    private Date _measureDate = new Date();
    private Date _firstOpenDate = new Date();
    private Date _firstClosedDate = new Date();
    //private DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    private Thread _thread;
    private PingerCallback _callback;
    private boolean playTone;

    public Pinger (String host, PingerCallback cb)
    {
        this (host);
        _callback = cb;
        Timer timer = new Timer();
        timer.schedule (new TimerTask()
        {
            @Override
            public void run()
            {
                updateState();
            }
        }, 0, 5000);
    }

    public Pinger (String host)
    {
        _host = host;
    }

    public void setToneEnabled (boolean b)
    {
        playTone = b;
    }

    private void tone (String name)
    {
        if (playTone)
            Utils.playWaveFromResource(name);
    }

    private void setOpen()
    {
        _firstOpenDate = _measureDate;
        _currentState = InetState.OPEN;
        tone ("TADA.WAV");
    }

    private void setClosed()
    {
        _firstClosedDate = _measureDate;
        _currentState = InetState.CLOSED;
        tone ("loose.wav");
    }

    private void testInetConnection ()
    {
        _measureDate = new Date();
        try
        {
            InetAddress inet = null;
            inet = InetAddress.getByName(_host);
            boolean test = inet.isReachable(5000);
            switch (_currentState)
            {
                case OPEN:
                    if (!test)  // Closing
                    {
                        setClosed();
                    }
                    break;

                case CLOSED:
                    if (test)  // Opening
                    {
                        setOpen();
                    }
                    break;

                case UNKNOWN:
                    if (test)
                    {
                        setOpen();
                    }
                    else
                    {
                        setClosed();
                    }
                    break;
            }
        }
        catch (Exception e)
        {
            if (_currentState != InetState.CLOSED)
            {
                setClosed();
            }
        }
    }

    private long timeDiffSeconds (Date d1, Date d2)
    {
        long diff = d1.getTime() - d2.getTime();
        if (diff != 0)
            diff = diff/1000;
        if (diff == 0)
            return 1;
        return diff;
    }

    public void updateState()  // ThreadProc
    {
            testInetConnection();

            switch (_currentState)
            {
                case OPEN:
                    _callback.callback (_currentState, timeDiffSeconds(_measureDate, _firstOpenDate));
                    break;

                case CLOSED:
                    _callback.callback (_currentState, timeDiffSeconds(_measureDate, _firstClosedDate));
                    break;
            }
    }
}
