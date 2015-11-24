package benywon.preprocess;

import benywon.MCTest.MCStructure;
import benywon.publicMethods.Filebases;
import benywon.publicMethods.Myconfig;

import java.util.List;

/**
 * Created by benywon on 2015/10/24.
 */
public class Main
{
    public static void main(String[] args)
    {
        String mc500trainfile= Myconfig.Getconfiginfo("MCtestDir")+Myconfig.Getconfiginfo("MC500testfilepath");


        MCStructure mcStructure=new MCStructure(mc500trainfile);
        //then we add answer 2 this structure
        String mc500answerfile= Myconfig.Getconfiginfo("MCtestDir")+Myconfig.Getconfiginfo("MC500Answerfilepath");
        List<String> answers= Filebases.GetListFromFile(mc500answerfile);
        mcStructure.setallAnswers(answers);
        System.out.println(mc500answerfile);


    }
}
