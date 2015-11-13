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
        //���Ǵ���mc���ļ�
        String mc500trainfile= Myconfig.Getconfiginfo("MCtestDir")+Myconfig.Getconfiginfo("MC500trainfilepath");
        List<String> filename= Filebases.GetListFromFile(mc500trainfile);
        //һ������һ��doc  Ȼ�����Ƕ����������doc���д���
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
