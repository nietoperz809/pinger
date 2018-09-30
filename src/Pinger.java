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
    private Thread _thread;
    private PingerCallback _callback;
    private boolean _playTone;

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
        }, 0, 10000);
    }

    public Pinger (String host)
    {
        Logger.log ("Pinger_started");
        _host = host;
    }

    public void setToneEnabled (boolean b)
    {
        _playTone = b;
    }

    private void tone (String name)
    {
        if (_playTone)
            Utils.playWaveFromResource(name);
    }

    private void setOpen()
    {
        _firstOpenDate = _measureDate;
        _currentState = InetState.OPEN;
        tone ("TADA.WAV");
        Logger.log ("+++_Internet_OK");
    }

    private void setClosed()
    {
        _firstClosedDate = _measureDate;
        _currentState = InetState.CLOSED;
        tone ("loose.wav");
        Logger.log ("---_NO_Internet!");
    }

    private void testInetConnection ()
    {
        _measureDate = new Date();
        try
        {
            InetAddress inet = null;
            inet = InetAddress.getByName(_host);
            boolean test1 = inet.isReachable(5000);
            inet = InetAddress.getByName(_host);
            boolean test2 = inet.isReachable(5000);
            boolean test = test1 & test2;
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
