package benywon.baseline;

import benywon.MCTest.Answer;
import benywon.MCTest.Document;
import benywon.MCTest.MCStructure;
import benywon.MCTest.Question;
import benywon.publicMethods.Myconfig;
import benywon.publicMethods.StringUtils;

import java.util.List;
import java.util.Map;


/** this is the baseline method sliding window
 * Created by benywon on 2015/11/24.
 */
public class SW
{
    public static void main(String[] args)
    {
        String mc500testfile= Myconfig.Getconfiginfo("MCtestDir")+Myconfig.Getconfiginfo("MC500testfilepath");
        MCStructure mcStructure=new MCStructure(mc500testfile);
        SW sw=new SW(mcStructure);
        System.out.println("hsiud");
    }
    public SW(MCStructure mcStructure)
    {
        if(checkValid(mcStructure))
        {
            buildSWfromDocs(mcStructure.documents);
        }
    }
    private void buildSWfromDocs(List<Document> docs)
    {
        for(Document document:docs)
        {
            buildSWperDoc(document);
        }
    }
    private void buildSWperDoc(Document document)
    {
        Map<String,Double> ITF= StringUtils.getDocumentITF(document.getDocument());
        for(Question question:document.getQuestions())
        {
            buildSWperQuestion(ITF,question);
        }
    }
    private void  buildSWperQuestion(Map<String,Double> ITF,Question question)
    {
        String questionstr=question.getQuestion();
        for(Answer answer:question.getAnswer())
        {

        }
    }

    /**
     * we only build the sw model on test data since this method
     * did not require train set
     * @param mcStructure
     * @return
     */
    private boolean checkValid(MCStructure mcStructure)
    {
        String purpose=mcStructure.Purpose;
        if(purpose.equals(MCStructure.TEST))
        {
            return true;
        }
        return false;
    }
}
