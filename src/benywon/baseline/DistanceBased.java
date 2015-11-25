package benywon.baseline;

import benywon.MCTest.*;
import benywon.publicMethods.StringUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by benywon on 2015/11/25.
 */
public class DistanceBased
{
    public DistanceBased(MCStructure mcStructure)
    {
        if(checkValid(mcStructure))
        {
            buildDfromDocs(mcStructure.documents);
        }
        else
        {
            try
            {
                buildDfromDocs(mcStructure.documents);
                throw new Exception("not test file");
            } catch (Exception e)
            {
                return;
            }
        }
    }

    private void buildDfromDocs(List<Document> docs)
    {
        for(Document document:docs)
        {
            buildDperDoc(document);
        }
    }
    private void buildDperDoc(Document document)
    {
        String docstr=document.getDocument();
        Map<String,Double> ITF= StringUtils.getDocumentITF(docstr);
        List<String> list=StringUtils.getLeximaList(docstr);
        for(Question question:document.getQuestions())
        {
            buildDperQuestion(ITF, list, question);
        }
    }
    private void  buildDperQuestion(Map<String,Double> ITF,List<String>  document,Question question)
    {
        String questionstr=question.getQuestion();

        Double max_score=-Double.MAX_VALUE;
        int  realAnswerid=0;//默认第一个
        for(Answer answer:question.getAnswer())
        {
            Double score=getAnswerSimilarity(document,answer,questionstr,ITF);
            Double Dvalue=Dvalue(document,answer,questionstr);
            score-=Dvalue;
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
    private Double Dvalue(List<String>  document,Answer answer,String question)
    {
        Set<String> docset=new HashSet<String>(document);
        Set<String> questionset=StringUtils.getLeximaFromString(question);
        Set<String> answerSet=StringUtils.getLeximaFromString(answer.getAnswer());
        Set<String> Sq=StringUtils.wordsetInSet1ButNotInSet2(StringUtils.commonWordsSet(questionset,docset), StopWords.stopWordsSet);
        Set<String> Sa=StringUtils.wordsetInSet1ButNotInSet2(StringUtils.wordsetInSet1ButNotInSet2(StringUtils.commonWordsSet(answerSet, docset),questionset), StopWords.stopWordsSet);
        double d=1;
        if(Sq.size()>0&&Sa.size()>0)
        {
            d=CalcDi(document,Sq,Sa);
        }
        return d;
    }
    private double CalcDi(List<String> doc,Set<String> Sq,Set<String> Sa)
    {
        int minDis=Integer.MAX_VALUE;
        double dominater=doc.size()-1;
        for(String q:Sq)
        {
            for(String a:Sa)
            {
                int tempdistance=WordsdistanceInDoc(doc,q,a);
                if(tempdistance<minDis)
                {
                    minDis=tempdistance;
                }
            }
        }
        return minDis/dominater;
    }
    private int WordsdistanceInDoc(List<String> doc,String w1,String w2)
    {
        boolean start=false;
        Set<String> iniset=new HashSet<String>();
        iniset.add(w1);
        iniset.add(w2);
        int distance=0;
        for (int i = 0; i <doc.size() ; i++)
        {

            if(iniset.contains(doc.get(i)))
            {
                start=!start;
                iniset.remove(doc.get(i));
                if(iniset.isEmpty())
                {
                    break;
                }
            }
            if(start)
            {
                distance++;
            }

        }
        return distance;//has aleady plus one
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
