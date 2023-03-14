package pure.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

//Reference:
//https://www.logicbig.com/how-to/code-snippets/jcode-java-cmd-command-line-table.html
public class TableHelper {
    private static final String HORIZONTAL = "-";
    private String Vertical;
    private String Joint;
    private String[] Headers;
    private final List<String[]> Rows = new ArrayList<>();
    private boolean isCenter = false;
    private boolean isClearScreen;

    public TableHelper() {
        setShowVerticalLines(false);
    }

    public TableHelper(boolean isCenter, boolean isVerticalLines) {
        setCenter(isCenter);
        setShowVerticalLines(isVerticalLines);
    }

    public final static void clearConsole() {
        try {
            final String os = System.getProperty("os.name");
            if (os.contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }

    }

    public void setCenter(boolean isCenter) {
        this.isCenter = isCenter;
    }

    public void setShowVerticalLines(boolean isVerticalLines) {
        Vertical = isVerticalLines ? "|" : "";
        Joint = isVerticalLines ? "+" : " ";
    }

    public void setHeaders(String... headers) {
        this.Headers = headers;
    }

    public void addRow(String... cells) {
        Rows.add(cells);
    }

    public void print(boolean isAppend) {
        if (this.isClearScreen) {
            clearConsole();
            System.out.flush();
        }

        int[] maxWidths = Headers != null ?
                Arrays.stream(Headers).mapToInt(String::length).toArray() : null;

        for (String[] cells : Rows) {
            if (maxWidths == null) {
                maxWidths = new int[cells.length];
            }
            if (cells.length != maxWidths.length) {
                throw new IllegalArgumentException("Number of row-cells and headers should be consistent");
            }
            for (int i = 0; i < cells.length; i++) {
                maxWidths[i] = Math.max(maxWidths[i], cells[i].length());
            }
        }

        if (Headers != null) {
            printLine(maxWidths);
            printRow(Headers, maxWidths);
            printLine(maxWidths);
        }
        for (String[] cells : Rows) {
            printRow(cells, maxWidths);
        }
        if (Headers != null) {
            printLine(maxWidths);
        }

        if (!isAppend)
            Rows.clear();
    }

    private void printLine(int[] columnWidths) {
        for (int i = 0; i < columnWidths.length; i++) {
            String line = String.join("", Collections.nCopies(columnWidths[i] +
                    Vertical.length() + 1, HORIZONTAL));
            System.out.print(Joint + line + (i == columnWidths.length - 1 ? Joint : ""));
        }
        System.out.println();
    }

    private void printRow(String[] cells, int[] maxWidths) {
        for (int i = 0; i < cells.length; i++) {
            String s = cells[i];
            String verStrTemp = i == cells.length - 1 ? Vertical : "";
            if (isCenter) {
                System.out.printf("%s %s %s", Vertical, center(s, maxWidths[i]), verStrTemp);
            } else {
                System.out.printf("%s %-" + maxWidths[i] + "s %s", Vertical, s, verStrTemp);
            }
        }
        System.out.println();
    }

    //reference:
    //https://stackoverflow.com/questions/8154366/how-to-center-a-string-using-string-format
    private String center(String text, int len) {
        String out = String.format("%" + len + "s%s%" + len + "s", "", text, "");
        float mid = (out.length() / 2);
        float start = mid - (len / 2);
        float end = start + len;
        return out.substring((int) start, (int) end);
    }

    public void setClearScreen(boolean clearScreen) {
        isClearScreen = clearScreen;
    }
}
