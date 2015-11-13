package benywon.MCTest;

import java.io.Serializable;

/**
 * Created by benywon on 2015/10/24.
 */
public class Answer implements Serializable
{
    public Answer(String answer)
    {
        this.answer = answer;
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
}
