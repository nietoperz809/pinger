public interface PingerCallback
{
    /**
     * post current state
     * @param state Inet state
     * @param timespan time in second the current state exists
     */
    void callback (InetState state, long timespan);
}
