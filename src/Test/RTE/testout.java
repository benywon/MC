package Test.RTE;

import benywon.publicMethods.Myconfig;
import eu.excitementproject.eop.util.eval.EDAScorer;

import java.io.File;

/**
 * Created by benywon on 2015/11/27.
 */
public class testout
{
    public static void main(String[] args)
    {
        EDAScorer.score(new File(Myconfig.Getconfiginfo("RTEFileRoot")+"myResults.txt"), Myconfig.Getconfiginfo("RTEFileRoot")+"myResults.eval");
    }
}
