import java.io.IOException;

public class DisplayText {
    private enum English
    {
        TestString1,
        TestString2,
        TestString3
    }

    private enum Chinese
    {
        第一,
        第二,
        第三
    }

    private int Language = 1;

    public DisplayText(int Language)
    {
        setLanguage(Language);
    }

    public void setLanguage(int Language)
    {
        this.Language = Language;
    }

    public String getText(int index) throws IOException {
        try {
            if (this.Language == 1)
                return English.values()[index].toString();
            else if (this.Language == 2)
                return Chinese.values()[index].toString();
            else
                return "";
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            //log e
            Logger.WriteLog(e.toString());
            return "An error occurred. Please contact the bank for assistance.";
        }
    }

}
