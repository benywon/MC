package benywon.preprocess;

import benywon.MCTest.process;

/**
 * Created by benywon on 2015/10/24.
 */
public class Main
{
    public static void main(String[] args)
    {
        process process=new process(500,"train");
        process.Process("SW");
        process.evaluate("precision");
    }
}
