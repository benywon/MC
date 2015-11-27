package Test.RTE;

import benywon.publicMethods.Myconfig;
import eu.excitementproject.eop.common.EDABasic;
import eu.excitementproject.eop.common.TEDecision;
import eu.excitementproject.eop.common.configuration.CommonConfig;
import eu.excitementproject.eop.common.utilities.configuration.ImplCommonConfig;
import eu.excitementproject.eop.core.MaxEntClassificationEDA;
import eu.excitementproject.eop.lap.PlatformCASProber;
import org.apache.uima.jcas.JCas;

import java.io.*;

import static eu.excitementproject.eop.util.runner.OutputUtils.getGoldLabel;
import static eu.excitementproject.eop.util.runner.OutputUtils.getPairID;

/**
 * Created by benywon on 2015/11/27.
 */
public class annofile
{
    public static void main(String[] args)
    {
        EDABasic eda = null;
        try {
            eda = new MaxEntClassificationEDA();
            //the configuration file to be used with the selected EDA
            String ConfigFIle= Myconfig.Getconfiginfo("RTEFileRoot")+"configFile/MaxEntClassificationEDA_Base+OpenNLP_EN.xml";
            File configFile = new File(ConfigFIle);
            //Loading the configuration file
            CommonConfig config = new ImplCommonConfig(configFile);
            //EDA initialization
            eda.initialize(config);

            File outputDir = new File(Myconfig.Getconfiginfo("RTEFileRoot")+"output/test/");

            Writer writer = null;
            writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(Myconfig.Getconfiginfo("RTEFileRoot")+"myResults.txt"),"utf-8"));
            //loading the pre-processed files
            for (File xmi : (outputDir.listFiles())) {
                if (!xmi.getName().endsWith(".xmi")) {
                    continue;
                }
                // The annotated pair has been added into the CAS.
                JCas cas = PlatformCASProber.probeXmi(xmi, null);
                // Call process() method to get Entailment decision.
                // Entailment decisions are represented with "TEDecision" class.
                TEDecision decision = eda.process(cas);
                // the file containing the annotated T/H pairs needed by EDAScorer is produced.
                // getGoldLabel (i.e. returns the gold standard annotation) and
                // getPairID (i.e.  returns the pairID of the T-H pair) are methods whose
                // implementation is given below.
                writer.write(getPairID(cas) + "\t" + getGoldLabel(cas) + "\t"  + decision.getDecision().toString() + "\t" + decision.getConfidence() + "\n");
                System.out.println("The result is: " + decision.getDecision().toString());
            }
            writer.close();

        } catch (Exception e) {
            System.err.print("EDA reported exception" + e.getMessage());
            System.exit(1);
        }







    }
}
