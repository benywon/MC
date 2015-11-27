package Test.RTE;

import eu.excitementproject.eop.common.DecisionLabel;
import eu.excitementproject.eop.common.EDABasic;
import eu.excitementproject.eop.common.EDAException;
import eu.excitementproject.eop.common.TEDecision;
import eu.excitementproject.eop.common.configuration.CommonConfig;
import eu.excitementproject.eop.common.exception.ComponentException;
import eu.excitementproject.eop.common.exception.ConfigurationException;
import eu.excitementproject.eop.common.utilities.configuration.ImplCommonConfig;
import eu.excitementproject.eop.core.ClassificationTEDecision;
import eu.excitementproject.eop.core.MaxEntClassificationEDA;
import eu.excitementproject.eop.lap.LAPAccess;
import eu.excitementproject.eop.lap.LAPException;
import eu.excitementproject.eop.lap.PlatformCASProber;
import eu.excitementproject.eop.lap.dkpro.OpenNLPTaggerEN;
import org.apache.uima.jcas.JCas;

import java.io.File;
//
//
// Hello World!
// 
// A simple, minimal code that runs one LAP & EDA to check if the EOP installation is Okay.
//
//
public class test {
    //
// Data Set pre-processing by using a LAP (i.e. OpenNLP tagger)
// @param dataSet the data set to be pre-processed (e.g. RTE-3)
// @param dirOut the directory containing the .xmi files produced by pre-processing the data set
//
    private void preprocessingDataSet(String dataSet, String dirOut) {

        LAPAccess ttLap = null;
        try {
            //ttLap = new TreeTaggerEN(); //this requires to have TreeTagger already installed
            ttLap = new OpenNLPTaggerEN(); //the OpenNLP tagger
        } catch (LAPException e) {
            System.err.println("Unable to initiated LAP: " + e.getMessage());
            System.exit(1);
        }

        //the English RTE data set
        File f = new File(dataSet);
        //the output directory; the directory has to exist prior to starting.
        File outputDir = new File(dirOut);

        try {
            ttLap.processRawInputFormat(f, outputDir);
        } catch (LAPException e) {
            System.err.println("Failed to process EOP RTE data format: " + e.getMessage());
            System.exit(1);
        }

    }


    //
// Train the EDA (i.e. MaxEntClassificationEDA) on the pre-processed Data Set
// @param configurationFile the EDA configuration file
//
    public void creatingNewModels(String configurationFile) {

        CommonConfig config = null;
        try {
            //This is the configuration file to be used with the selected EDA (i.e. MaxEntClassification EDA).
            File configFile = new File(configurationFile);

            config = new ImplCommonConfig(configFile);
        }
        catch (ConfigurationException e) {
            System.err.println("Failed to read configuration file: "+ e.getMessage());
            System.exit(1);
        }

        try {
            @SuppressWarnings("rawtypes")
            EDABasic eda = null;
            //creating an instance of MaxEntClassification EDA
            eda = new MaxEntClassificationEDA();
            //EDA initialization and start training
            eda.startTraining(config); // This *MAY* take some time.
            eda.shutdown(); //shutdown
        } catch (EDAException e) {
            System.err.println("Failed to do the training: "+ e.getMessage());
            System.exit(1);
        } catch (ConfigurationException e) {
            System.err.println("Failed to do the training: "+ e.getMessage());
            System.exit(1);
        } catch (ComponentException e) {
            System.err.println("Failed to do the training: "+ e.getMessage());
            System.exit(1);
        }

    }


    //
// Annotating a single T/H pair b using a pre-trained model
// @param configurationFile the EDA configuration file
// @param T the text
// @param H the hypothesis
//
    public void annotatingSingle_T_H_Pair(String configurationFile, String T, String H) {

        //1) Pre-processing T/H pair by using the LAP (i.e. OpenNLP)
        JCas annotated_THpair = null;
        try {
            LAPAccess lap = new OpenNLPTaggerEN(); // make a new OpenNLP based LAP
            annotated_THpair = lap.generateSingleTHPairCAS(T, H); // ask it to process this T-H.
        } catch (LAPException e) {
            System.err.print(e.getMessage());
            System.exit(1);
        }
        //
        //2) Initialize an EDA with a configuration (& corresponding model). You have to check that the
        //   model path in the configuration file points to the directory where the model is, e.g.:
        //   home/user_name/eop-resources-1.2.0/model/MaxEntClassificationEDAModel_Base+OpenNLP_EN
        //
        System.out.println("Initializing the EDA.");
        EDABasic<ClassificationTEDecision> eda = null;
        try {
            // TIE (i.e. MaxEntClassificationEDA): a simple configuration with no knowledge resource.
            File configFile = new File(configurationFile);
            CommonConfig config = new ImplCommonConfig(configFile);
            eda = new MaxEntClassificationEDA();
            eda.initialize(config);
        } catch (Exception e) {
            System.err.print(e.getMessage());
            System.exit(1);
        }
        //
        //
        // 3) Now, one input data is ready, and the EDA is also ready.
        //    Call the EDA.
        //
        System.out.println("Calling the EDA for decision.");
        TEDecision decision = null; // the generic type that holds Entailment decision result
        try {
            decision = eda.process(annotated_THpair);
        } catch (Exception e) {
            System.err.print(e.getMessage());
            System.exit(1);
        }
        //
        System.out.println("Run complete: EDA returned decision: " + decision.getDecision().toString());
        //
    }


    //
    // This method shows how to annotate a data set of multiple T/H pairs by using an EDA with
    // one existing (already trained) model. The data set has to been pre-processed as
    // described in the Preprocessing class.
    // @param configurationFile the EDA configuration file
    // @param preprocessedDataSet the directory containing the .xmi files
    //
    public void annotatingDataSet(String configurationFile, String preprocessedDataSet) {

        //EDA configuration
        @SuppressWarnings("rawtypes")
        EDABasic eda = null;
        try {
            //creating an instance of MaxEntClassification EDA
            eda = new MaxEntClassificationEDA();
            //this is the configuration file we want to use with MaxEntClassification EDA
            File configFile = new File(configurationFile);
            //Loading the configuration file
            CommonConfig config = new ImplCommonConfig(configFile);
            //EDA initialization
            eda.initialize(config);
        }
        catch (EDAException e)
        {
            System.err.println("Failed to init the EDA: "+ e.getMessage());
            System.exit(1);
        }
        catch (ConfigurationException e)
        {
            System.err.println("Failed to init the EDA: "+ e.getMessage());
            System.exit(1);
        }
        catch (ComponentException e)
        {
            System.err.println("Failed to init the EDA: "+ e.getMessage());
            System.exit(1);
        }

        //The directory where the pre-processed data set (i.e. the serialized annotated files) is
        File outputDir = new File(preprocessedDataSet);

        //Annotating the data set
        try {
            //loading the pre-processed files
            for (File xmi : (outputDir.listFiles())) {
                if (!xmi.getName().endsWith(".xmi")) {
                    continue;
                }
                // The annotated pair has been added into the UIMA CAS.
                JCas cas = PlatformCASProber.probeXmi(xmi, null);
                // Call process() method to get Entailment decision.
                TEDecision decision = eda.process(cas);
                // Entailment decisions are represented with "TEDecision" class.
                DecisionLabel r = decision.getDecision();
                System.out.println("The result is: " + r.toString());
            }
        } catch (EDAException e) {
            System.err.print("EDA reported exception" + e.getMessage());
            System.exit(1);
        } catch (ComponentException e) {
            System.err.print("EDA reported exception" + e.getMessage());
            System.exit(1);
        }

        //shutdown
        eda.shutdown();

    }


    public static void main( String[] args ) {

        System.out.println("Hello World!");

        test helloWorld = new test();

        //Pre-processing the Data Set to be used fot training the EDA
        //The pre-processed data set will be put in /tmp/EN/dev/; make sure that this directory exists
        helloWorld.preprocessingDataSet("L:\\program\\otherProgram\\MCtest\\RTE\\eop-resources-1.1.2\\data-set/English_dev.xml", "L:\\program\\otherProgram\\MCtest\\RTE\\eop-resources-1.1.2\\data-set/tmp/EN/dev/");

        //Training the EDA on the pre-processed Data Set
        String configurationFile = "L:\\program\\otherProgram\\MCtest\\RTE\\eop-resources-1.1.2\\configuration-files/MaxEntClassificationEDA_Base+OpenNLP_EN.xml";
        helloWorld.creatingNewModels(configurationFile);

        //Annotating a T/H pair by using the created model
        //The text
        String T = "The students had 15 hours of lectures and practice sessions on the topic of Textual Entailment.";
        //The hypothesis
        String H = "The students must have learned quite a lot about Textual Entailment.";
        helloWorld.annotatingSingle_T_H_Pair(configurationFile, T, H);

        //Annotating a T/H pair by using the created model
        //The pre-processed data set will be put in /tmp/EN/test/; make sure that this directory exists
        //helloWorld.preprocessingDataSet("/home/user_name/eop-resources-1.2.0/data-set/English_test.xml", "/tmp/EN/test/");
        //helloWorld.annotatingDataSet(configurationFile, "/tmp/EN/test/");

    }
}