import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Stack;

public class GroupingSymbolChecker {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Usage: java GroupingSymbolChecker <filename>");
            return;
        }

        String filename = args[0];
        try {
            if (checkGroupingSymbols(filename)) {
                System.out.println("All grouping symbols in " + filename + " are correct.");
            } else {
                System.out.println("Some grouping symbols in " + filename + " are incorrect.");
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }

    public static boolean checkGroupingSymbols(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        Stack<Character> stack = new Stack<>();

        int lineNumber = 1;
        int ch;
        while ((ch = reader.read()) != -1) {
            char c = (char) ch;
            if (c == '\n') {
                lineNumber++;
            } else if (isGroupingSymbol(c)) {
                if (isOpenSymbol(c)) {
                    stack.push(c);
                } else {
                    if (stack.isEmpty() || !isMatchingPair(stack.pop(), c)) {
                        System.out.println("Error: Mismatched grouping symbol " + c + " at line " + lineNumber);
                        return false;
                    }
                }
            }
        }

        reader.close();

        if (!stack.isEmpty()) {
            System.out.println("Error: Unclosed grouping symbol(s) at the end of file.");
            return false;
        }

        return true;
    }

    private static boolean isGroupingSymbol(char c) {
        return c == '(' || c == ')' || c == '{' || c == '}' || c == '[' || c == ']';
    }

    private static boolean isOpenSymbol(char c) {
        return c == '(' || c == '{' || c == '[';
    }

    private static boolean isMatchingPair(char open, char close) {
        return (open == '(' && close == ')') ||
                (open == '{' && close == '}') ||
                (open == '[' && close == ']');
    }
}
