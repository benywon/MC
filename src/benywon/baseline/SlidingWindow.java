package benywon.baseline;

import benywon.MCTest.Answer;
import benywon.MCTest.Document;
import benywon.MCTest.MCStructure;
import benywon.MCTest.Question;
import benywon.publicMethods.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by benywon on 2015/11/25.
 */
public class SlidingWindow
{
    public SlidingWindow(MCStructure mcStructure)
    {
        if(checkValid(mcStructure))
        {
            buildSWfromDocs(mcStructure.documents);
        }
        else
        {
            try
            {
                buildSWfromDocs(mcStructure.documents);
                throw new Exception("not test file");
            } catch (Exception e)
            {
               return;
            }
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
        String docstr=document.getDocument();
        Map<String,Double> ITF= StringUtils.getDocumentITF(docstr);
        List<String> list=StringUtils.getLeximaList(docstr);
        for(Question question:document.getQuestions())
        {
            buildSWperQuestion(ITF,list,question);
        }
    }
    private void  buildSWperQuestion(Map<String,Double> ITF,List<String>  document,Question question)
    {
        String questionstr=question.getQuestion();

        Double max_score=-10.0;
        int  realAnswerid=0;//默认第一个
        for(Answer answer:question.getAnswer())
        {
            Double score=getAnswerSimilarity(document,answer,questionstr,ITF);
            if(score>max_score)
            {
                max_score=score;
                realAnswerid=answer.getId();
            }
        }
        question.setPredicAnswer(realAnswerid);
    }
    private Double getAnswerSimilarity(List<String>  document,Answer answer,String question,Map<String,Double> ITF)
    {
        String pair=answer.getAnswer()+" "+question;
        Set<String> set=StringUtils.getLeximaFromString(pair);
        Double score=0.0;
        double maxValue=-Double.MAX_VALUE;
        int windowsize=set.size();
        int totalsize=document.size();
        for(int i=0;i<totalsize;i++)
        {
            double sum=0;
            for(int j=0;j<windowsize&&(i+j)<totalsize;j++)
            {
                String word=document.get(i+j);
                if(set.contains(word))
                {
                    sum+=ITF.get(word);
                }
            }
            if(sum > maxValue)
            {
                maxValue = sum;
            }
        }
        return maxValue;
    }
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
