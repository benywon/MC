package Test.RTE;

import benywon.publicMethods.Myconfig;
import eu.excitementproject.eop.lap.LAPAccess;
import eu.excitementproject.eop.lap.LAPException;
import eu.excitementproject.eop.lap.dkpro.OpenNLPTaggerEN;

import java.io.File;

/**
 * Created by benywon on 2015/11/27.
 */
public class preprocess
{
    public static void main(String[] args)
    {
        LAPAccess ttLap = null;
        try {
            //ttLap = new TreeTaggerEN(); //this requires to have TreeTagger already installed
            ttLap = new OpenNLPTaggerEN(); //the OpenNLP tagger
        } catch (LAPException e) {
            System.err.println("Unable to initiated LAP: " + e.getMessage());
            System.exit(1);
        }

        //the English RTE data set
        String trainfilepath= Myconfig.Getconfiginfo("RTEFileRoot")+"corpusFile/train/mc500.train.statements.pairs";
        File f = new File(trainfilepath);
        //the output directory
        File outputDir = new File(Myconfig.Getconfiginfo("RTEFileRoot")+"corpusFile/temp/train/");

        try {
            ttLap.processRawInputFormat(f, outputDir);
        } catch (LAPException e) {
            System.err.println("Failed to process EOP RTE data format: " + e.getMessage());
            System.exit(1);
        }

    }
}
