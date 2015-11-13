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
        //我们处理mc的文件
        String mc500trainfile= Myconfig.Getconfiginfo("MCtestDir")+Myconfig.Getconfiginfo("MC500trainfilepath");
        List<String> filename= Filebases.GetListFromFile(mc500trainfile);
        //一个就是一个doc  然后我们对这个单独的doc进行处理
        MCStructure mcStructure=new MCStructure(filename);
        //then we add answer 2 this structure
        String mc500answerfile= Myconfig.Getconfiginfo("MCtestDir")+Myconfig.Getconfiginfo("MC500Answerfilepath");
        List<String> answers= Filebases.GetListFromFile(mc500answerfile);
        mcStructure.setallAnswers(answers);
        System.out.println(mc500answerfile);

        Filebases.WriteObj(mcStructure,"1.obj");
        mcStructure= (MCStructure) Filebases.ReadObj("1.obj");
        System.out.println("josdif");
    }
}
