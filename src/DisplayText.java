import java.util.logging.Level;

public class DisplayText {
    private int Language = 1;

    public DisplayText(int Language) {
        setLanguage(Language);
    }

    public void setLanguage(int Language) {
        this.Language = Language;
    }

    public String getText(int index) {
        try {
            if (this.Language == 1)
                return English.values()[index].toString();
            else if (this.Language == 2)
                return Chinese.values()[index].toString();
            else
                return "";
        } catch (ArrayIndexOutOfBoundsException e) {
            LogManager.LOGGER.log(Level.SEVERE, e.getMessage());
            return "An error occurred. Please contact the bank for assistance.";
        }
    }

    private enum English {
        TestString1,
        TestString2,
        TestString3
    }

    private enum Chinese {
        第一,
        第二,
        第三
    }

}
