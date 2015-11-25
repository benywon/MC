package benywon.baseline;

import benywon.MCTest.Answer;
import benywon.MCTest.Document;
import benywon.MCTest.MCStructure;
import benywon.MCTest.Question;
import benywon.publicMethods.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;


/** this is the baseline method sliding window
 * Created by benywon on 2015/11/24.
 */
public class SWbeta
{

    public SWbeta(MCStructure mcStructure)
    {
        if(checkValid(mcStructure))
        {
            buildSWfromDocs(mcStructure.documents);
        }
        else
        {
            try
            {
                throw new Exception("not test file");
            } catch (Exception e)
            {
                e.printStackTrace();
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
        Map<String,Double> ITF= StringUtils.getDocumentITF(document.getDocument());
        for(Question question:document.getQuestions())
        {
            buildSWperQuestion(ITF,question);
        }
    }
    private void  buildSWperQuestion(Map<String,Double> ITF,Question question)
    {
        String questionstr=question.getQuestion();
        Double max_score=-10.0;
        int  realAnswerid=0;//默认第一个
        for(Answer answer:question.getAnswer())
        {
            Double score=getAnswerSimilarity(answer,questionstr,ITF);
            if(score>max_score)
            {
                max_score=score;
                realAnswerid=answer.getId();
            }
        }
        question.setPredicAnswer(realAnswerid);
    }
    private Double getAnswerSimilarity(Answer answer,String question,Map<String,Double> ITF)
    {
        String pair=answer.getAnswer()+" "+question;
        Set<String> set=StringUtils.getLeximaFromString(pair);
        Double score=0.0;
        for(String word:set)
        {
            if(ITF.containsKey(word))
            {
                score+=ITF.get(word);
            }
        }
        return score;
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
