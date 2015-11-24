package benywon.MCTest;

import java.io.Serializable;

/**
 * Created by benywon on 2015/10/24.
 */
public class Answer implements Serializable
{
    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    private int id=-1;
    public Answer(String answer)
    {
        this.answer = answer;
    }
    public Answer(String answer,int id)
    {
        this.answer = answer;
        this.id=id;
    }
    public String getAnswer()
    {
        return answer;
    }

    public void setAnswer(String answer)
    {
        this.answer = answer;
    }

    public boolean isAnswer()
    {
        return IsAnswer;
    }

    public void setIsAnswer(boolean isAnswer)
    {
        IsAnswer = isAnswer;
    }

    private String answer;
    private boolean IsAnswer=false;

    public boolean isPredictResult()
    {
        return PredictResult;
    }

    public void setPredictResult(boolean predictResult)
    {
        PredictResult = predictResult;
    }

    private boolean PredictResult=false;
}
