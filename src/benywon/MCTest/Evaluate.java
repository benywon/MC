package benywon.MCTest;

import java.util.List;

/**
 * Created by benywon on 2015/11/25.
 */
public class Evaluate
{
    public Float precision;
    public Float Precision(MCStructure mcStructure)
    {
        if(!checkMCvalid(mcStructure))
        {
            System.out.println("data wrong,check data");
            return 0.0f;
        }
        precision= calcPrecision(mcStructure);
        System.out.println(precision);
        return precision;
    }

    private Float calcPrecision(MCStructure mcStructure)
    {
        float[] number=new float[2];
        for(Document document:mcStructure.documents)
        {
            int[] numberperdoc=calcDocPresisionNumber(document);
            number[0]+=numberperdoc[0];
            number[1]+=numberperdoc[1];
        }
        return number[1]/number[0];
    }

    private int[] calcDocPresisionNumber(Document document)
    {
        int[] docresult=new int[2];
        docresult[0]=document.getQuestions().size();
        docresult[1]=calcQuestionList(document.getQuestions());
        return docresult;
    }
    private int calcQuestionList(List<Question> questionList)
    {
        int number=0;
        for(Question question:questionList)
        {
            if(isQuestionRight(question))
            {
                number++;
            }
        }
        return number;
    }

    private boolean isQuestionRight(Question question)
    {
        for(Answer answer:question.getAnswer())
        {
            if(answer.isAnswer())
            {
                if(answer.isPredictResult())
                {
                    return true;
                }
                return false;
            }
        }
        try
        {
            throw new Exception("no answer given");
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return false;
    }
    private boolean checkMCvalid(MCStructure mcStructure)
    {
        if(mcStructure.MC_id==500)
        {
            return checkMCnumbervalid500(mcStructure);
        }
        else
        {
            return checkMCnumbervalid160(mcStructure);
        }
    }
    private boolean checkMCnumbervalid500(MCStructure mcStructure)
    {
        int number=mcStructure.documents.size();
        if(mcStructure.Purpose.equals("train"))
        {
            if(number==300)
            {
                return true;
            }
            return false;
        }
        else if(mcStructure.Purpose.equals("dev"))
        {
            if(number==50)
            {
                return true;
            }
            return false;
        }
        else
        {
            if(number==150)
            {
                return true;
            }
            return false;
        }
    }
    private boolean checkMCnumbervalid160(MCStructure mcStructure)
    {
        int number=mcStructure.documents.size();
        if(mcStructure.Purpose.equals("train"))
        {
            if(number==70)
            {
                return true;
            }
            return false;
        }
        else if(mcStructure.Purpose.equals("dev"))
        {
            if(number==30)
            {
                return true;
            }
            return false;
        }
        else
        {
            if(number==60)
            {
                return true;
            }
            return false;
        }
    }
}
