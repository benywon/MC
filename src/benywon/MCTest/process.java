package benywon.MCTest;

import benywon.baseline.DistanceBased;
import benywon.baseline.SWbeta;
import benywon.baseline.SlidingWindow;
import benywon.publicMethods.Myconfig;

/**
 * Created by benywon on 2015/10/24.
 */
public class process
{
    public MCStructure MCstructure;
    public static final String DataRootDir=Myconfig.Getconfiginfo("MCtestDir");

    private static final String MCtestFilePathTemplate=DataRootDir+"MCTest/mc_MCID_._PURPOSE_._IsAnswer_";

    public process(int id,String purpose)
    {
        String filepath=getFilePath(id,purpose,false);
        this.MCstructure=new MCStructure(filepath);
        filepath=getFilePath(id,purpose,true);
        this.MCstructure.setAnswerFromFile(filepath);
    }
    public float evaluate(String method)
    {
        Evaluate evaluate=new Evaluate();
        if(method.equals("precision"))
        {
            return evaluate.Precision(this.MCstructure);
        }
        return 0.0f;
    }
    private String getFilePath(int id,String purpose,boolean IsAnswer)
    {
        String filepath=MCtestFilePathTemplate;
        if(id==500)
        {
            filepath=filepath.replaceAll("_MCID_","500");
        }
        else
        {
            filepath=filepath.replaceAll("_MCID_","160");
        }

        filepath=filepath.replaceAll("_PURPOSE_",purpose);
        if(IsAnswer)
        {
            filepath = filepath.replaceAll("_IsAnswer_","ans");
        }
        else
        {
            filepath = filepath.replaceAll("_IsAnswer_","tsv");
        }
        return filepath;
    }
    public void Process(String method)
    {
        if(method.equals("SWbeta"))
        {
            SWbeta sWbeta=new SWbeta(this.MCstructure);
        }
        else if(method.equals("SW"))
        {
            SlidingWindow slidingWindow=new SlidingWindow(this.MCstructure);
        }
        else if(method.equals("SW+D"))
        {
            DistanceBased ds=new DistanceBased(this.MCstructure);
        }

    }

}
