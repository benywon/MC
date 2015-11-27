package Test.RTE;

import eu.excitementproject.eop.common.DecisionLabel;
import eu.excitementproject.eop.common.EDABasic;
import eu.excitementproject.eop.common.TEDecision;
import eu.excitementproject.eop.common.configuration.CommonConfig;
import eu.excitementproject.eop.common.utilities.configuration.ImplCommonConfig;
import eu.excitementproject.eop.core.MaxEntClassificationEDA;
import eu.excitementproject.eop.lap.LAPAccess;
import eu.excitementproject.eop.lap.LAPException;
import eu.excitementproject.eop.lap.dkpro.OpenNLPTaggerEN;
import org.apache.uima.jcas.JCas;

import java.io.File;

/**
 * Created by benywon on 2015/11/27.
 */
public class annos
{
    public static void main(String[] args)
    {
        EDABasic eda = null;
        try {
            //creating an instance of MaxEntClassification EDA
            eda = new MaxEntClassificationEDA();
            //this is the configuration file we want to use with MaxEntClassification EDA
            File configFile = new File("L:\\program\\otherProgram\\MCtest\\RTE\\eop-resources-1.1.2\\configuration-files/MaxEntClassificationEDA_Base+OpenNLP_EN.xml");
            //Loading the configuration file
            CommonConfig config = new ImplCommonConfig(configFile);
            //EDA initialization
            eda.initialize(config);
        } catch (Exception e) {
            System.err.println("Failed to init the EDA: "+ e.getMessage());
            System.exit(1);
        }

        //T
        String text = "The sale was made to pay Yukos' US$ 27.5 billion tax bill, Yuganskneftegaz was originally sold for US$ 9.4 billion to a little known company Baikalfinansgroup which was later bought by the Russian state-owned oil company Rosneft.";
//H
        String hypothesis = "Baikalfinansgroup was sold to Rosneft.";

        JCas thPair = null;
        try {
            //this requires to have TreeTagger already installed
            //LAPAccess lap = new TreeTaggerEN();
            //OpenNLP tagger
            LAPAccess lap = new OpenNLPTaggerEN();
            // T/H pair preprocessed by LAP
            thPair = lap.generateSingleTHPairCAS(text, hypothesis);
        } catch (LAPException e) {
            System.err.print("LAP annotation failed:" + e.getMessage());
            System.exit(1);
        }
        TEDecision decision = null;
        try {
            decision = eda.process(thPair);
            DecisionLabel r = decision.getDecision();
            System.out.println("The result is: " + r.toString());
        } catch (Exception e) {
            System.err.print("EDA reported exception" + e.getMessage());
            System.exit(1);
        }

    }
}
