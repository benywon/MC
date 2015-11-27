package Test.RTE;

import benywon.publicMethods.Myconfig;
import eu.excitementproject.eop.common.EDABasic;
import eu.excitementproject.eop.common.EDAException;
import eu.excitementproject.eop.common.configuration.CommonConfig;
import eu.excitementproject.eop.common.exception.ComponentException;
import eu.excitementproject.eop.common.utilities.configuration.ImplCommonConfig;
import eu.excitementproject.eop.core.MaxEntClassificationEDA;

import java.io.File;

/**
 * Created by benywon on 2015/11/27.
 */
public class trainmodel
{
    public static void main(String[] args)
    {
        CommonConfig config = null;
        try {
            //This is the configuration file to be used with the selected EDA (i.e. MaxEntClassification EDA).
            String ConfigFIle= Myconfig.Getconfiginfo("RTEFileRoot")+"configFile/MaxEntClassificationEDA_Base+OpenNLP_EN.xml";
            File configFile = new File(ConfigFIle);
            config = new ImplCommonConfig(configFile);
            EDABasic eda = null;
            //creating an instance of MaxEntClassification EDA
            eda = new MaxEntClassificationEDA();
            //EDA initialization and start training

            eda.startTraining(config); // This *MAY* take a some time.
        } catch (eu.excitementproject.eop.common.exception.ConfigurationException e)
        {
            e.printStackTrace();
        } catch (ComponentException e)
        {
            e.printStackTrace();
        } catch (EDAException e)
        {
            e.printStackTrace();
        }


    }
}
