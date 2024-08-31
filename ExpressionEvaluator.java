package DS_INDIVIDUAL;
import java.util.Scanner;

class OperandStack {
    class Node {
        int operand;
        Node link;

        /**
         * Creates a Node of Stack implemented by Singly Linked-List
         * @param data
         */
        public Node(int operand) {
            this.operand = operand;
            this.link = null;
        }
    }

    // top pointer, points topmost element of Stack
    Node top = null;

    /**
     * @param x data to push into OperandStack
     */
    void push(int x) {
        Node newNode = new Node(x);
        newNode.link = top;
        top = newNode;
    }

    /**
     * removes top from OperandStack
     * @return topmost element of OperandStack
     */
    int pop() {
        if (top == null) {
            return -1;
        } else {
            int temp = top.operand;
            top = top.link;
            return temp;
        }
    }

    /**
     * @return topmost element of OperandStack
     */
    int peek() {
        if (top == null) {
            return -1;
        } else {
            return top.operand;
        }
    }

    /**
     * @return boolean values that stack is empty or not
     */
    boolean isEmpty() {
        if (top == null) {
            return true;
        } else {
            return false;
        }
    }
}

class OperatorStack {
    class Node {
        char operator;
        Node link;

        /**
         * Creates a Node of Stack implemented by Singly Linked-List
         * @param data
         */
        public Node(char operator) {
            this.operator = operator;
            this.link = null;
        }
    }

    // top pointer, points topmost element of Stack
    Node top = null;

    /**
     * @param x data to push into OperatorStack
     */
    void push(char x) {
        Node newNode = new Node(x);
        newNode.link = top;
        top = newNode;
    }

    /**
     * removes top from OperandStack
     * @return topmost element of OperatorStack
     */
    char pop() {
        if (top == null) {
            return '#';
        } else {
            char temp = top.operator;
            top = top.link;
            return temp;
        }
    }

    /**
     * @return topmost element of OperatorStack
     */
    char peek() {
        if (top == null) {
            return '#';
        } else {
            return top.operator;
        }
    }

    /**
     * @return boolean value that stack is empty or not
     */
    boolean isEmpty() {
        if (top == null) {
            return true;
        } else {
            return false;
        }
    }
}

public class ExpressionEvaluator {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("------- Welcome to Expression Evaluator -------");
        // String expression = "3 + 4 * ( 2 - 1 )";
        // String expression = "( 7 * 3 ) / 3 + 4 * ( 2 - 1 )";
        // String example = "( 9 ^ 3 ) / 3 + 4 * ( 2 - 1 )";
        // System.out.println("Here's an example of Infix Expression");
        // System.out.println("Example : " + example + " is Valid? : " +
        // validExpression(example));
        // Integer result = evaluate(example);
        // System.out.println("Result : " + result);
        // String prefixExpression = "(* 5 (+ 2 3))";
        // int result = evaluatePrefix(prefixExpression);
        // System.out.println(prefixExpression + " is Balanced - " +
        // validExpression(prefixExpression));
        // System.out.println("Result of the prefix expression " + prefixExpression + "
        // is: " + result);

        while (true) {
            Integer result = null;
            System.out.println("""
                    1 : Evaluate Infix
                    2 : Evaluate Prefix
                    3 : Evaluate Postfix
                    0 : Exit
                    """);
            try {
                System.out.print("Enter Choice : ");
                int choice = Integer.parseInt(sc.nextLine());
                switch (choice) {
                case 1:
                    System.out.print("Enter Infix Expression: ");
                    String infix = sc.nextLine();
                    result = evaluateInfix(infix);
                    if (result != null) {
                        System.out.println("Result of " + infix + " = " + result);
                    }
                    break;
                case 2:
                    System.out.print("Enter Prefix Expression: ");
                    String prefix = sc.nextLine();
                    result = evaluatePrefix(prefix);
                    if (result != null) {
                        System.out.println("Result of " + prefix + " = " + result);
                    }
                    break;
                case 3:
                    System.out.print("Enter Postfix Expression: ");
                    String postfix = sc.nextLine();
                    result = evaluatePostfix(postfix);
                    if (result != null) {
                        System.out.println("Result of " + postfix + " = " + result);
                    }
                    break;
                case 0:
                    System.out.println("--------------- EXIT ---------------");
                    sc.close();
                    System.exit(0);
                default:
                    System.out.println("Wrong Choice, Try Again !!!");
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid Input, Try Again !!");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Evaluates Prefix Expression
     * @param prefix
     * @return result of expression
     */
    public static Integer evaluatePrefix(String prefix) {
        if (!validExpression(prefix)) {
            System.out.println("You have entered invalid expression.");
            return null;
        } else {
            OperandStack stack = new OperandStack();
            for (int i = prefix.length() - 1; i >= 0; i--) {
                char c = prefix.charAt(i);
                if (Character.isDigit(c)) {
                    String num = "", rev = "";
                    while (i >= 0 && isDigit(prefix.charAt(i))) {
                        num += prefix.charAt(i);
                        i--;
                    }
                    for (int j = num.length() - 1; j >= 0; j--) {
                        rev += num.charAt(j);
                    }
                    stack.push(Integer.parseInt(rev));
                    i++;
                } else if (isOperator(c)) {
                    int operand1 = stack.pop();
                    int operand2 = stack.pop();
                    int result = performOperation(operand1, operand2, c);
                    stack.push(result);
                }
            }

            return stack.pop();
        }
    }

    /**
     * Evaluates Postfix Expression
     * @param postfix
     * @return result of expression
     */
    public static Integer evaluatePostfix(String postfix) {
        if (!validExpression(postfix)) {
            System.out.println("You have entered invalid expression.");
            return null;
        } else {
            OperandStack stack = new OperandStack();
            for (int i = 0; i < postfix.length(); i++) {
                char c = postfix.charAt(i);
                if (isDigit(c)) {
                    String num = "";
                    while (i < postfix.length() && isDigit(postfix.charAt(i))) {
                        num += postfix.charAt(i);
                        i++;
                    }
                    i--;
                    int operand = Integer.parseInt(num);
                    stack.push(operand);
                } else if (isOperator(c)) {
                    int operand2 = stack.pop();
                    int operand1 = stack.pop();
                    int result = performOperation(operand1, operand2, c);
                    stack.push(result);
                }
            }
            return stack.pop();
        }
    }

    /**
     * Evaluates Infix Expression
     * @param infix
     * @return result of expression
     */
    public static Integer evaluateInfix(String infix) {
        if (!validExpression(infix)) {
            System.out.println("You have entered invalid expression.");
            return null;
        } else {
            OperandStack operands = new OperandStack();
            OperatorStack operators = new OperatorStack();

            for (int i = 0; i < infix.length(); i++) {
                char c = infix.charAt(i);

                if (isDigit(c)) {
                    String num = "";
                    while (i < infix.length() && isDigit(infix.charAt(i))) {
                        num += infix.charAt(i);
                        i++;
                    }
                    i--;
                    int operand = Integer.parseInt(num);
                    operands.push(operand);
                } else if (c == '(') {
                    operators.push(c);
                } else if (c == ')') {
                    while (operators.peek() != '(') {
                        int operand2 = operands.pop();
                        int operand1 = operands.pop();
                        char operator = operators.pop();
                        int result = performOperation(operand1, operand2, operator);
                        operands.push(result);
                    }
                    operators.pop(); // Pop '('
                } else if (isOperator(c)) {
                    while (!operators.isEmpty() && precedence(operators.peek()) >= precedence(c)) {
                        int operand2 = operands.pop();
                        int operand1 = operands.pop();
                        char operator = operators.pop();
                        int result = performOperation(operand1, operand2, operator);
                        operands.push(result);
                    }
                    operators.push(c);
                }
            }

            while (!operators.isEmpty()) {
                int operand2 = operands.pop();
                int operand1 = operands.pop();
                char operator = operators.pop();
                int result = performOperation(operand1, operand2, operator);
                operands.push(result);
            }

            return operands.pop();
        }
    }

    /**
     * @param c char to check for operator
     * @return boolean value that character is operator or not
     */
    public static boolean isOperator(char c) {
        return (c == '+') || (c == '-') || (c == '*') || (c == '/') || (c == '$') || (c == '^');
    }

    /**
     * @param c char to check for digit
     * @return boolean value that character is digit or not
     */
    public static boolean isDigit(char c) {
        return (c == '1') || (c == '2') || (c == '3') || (c == '4') || (c == '5') || 
               (c == '6') || (c == '7') || (c == '8') || (c == '9') || (c == '0');
    }

    /**
     * @param c character
     * @return numeric value of given character
     */
    public static int getNumericValue(char c) {
        if (c >= '0' && c <= '9') {
            return c - '0';
        } else {
            return -1;
        }
    }

    /**
     * @param operator
     * @return precedence of given operator
     */
    public static int precedence(char operator) {
        if (operator == '+' || operator == '-') {
            return 1;
        } else if (operator == '*' || operator == '/') {
            return 2;
        } else if (operator == '$' || operator == '^') {
            return 3;
        }
        return 0;
    }

    /**
     * Calculates rank of given expression and returns boolean value according to the rank
     * @param expression
     * @return boolean value that given expression id valid or not
     */
    public static boolean validExpression(String expression) {
        int rank = 0;
        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);
            if (isDigit(c)) {
                while (i < expression.length() && isDigit(expression.charAt(i))) {
                    i++;
                }
                i--;
                rank++;
            } else if (isOperator(c)) {
                rank--;
            } else {
                continue;
            }
        }

        if (rank == 1 && balancedParenthesis(expression)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @param expression
     * @return boolean value that given expression has balanced parenthesis or not
     */
    public static boolean balancedParenthesis(String expression) {
        OperatorStack stack = new OperatorStack();
        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);

            if (c == '(') {
                stack.push(c);
            } else if (c == ')') {
                if (stack.isEmpty()) {
                    return false;
                }

                char top = stack.pop();

                if ((c == ')' && top != '(')) {
                    return false;
                }
            }
        }

        return stack.isEmpty();
    }

    /**
     * @param operand1,operand2,operator
     * @return result of operation of : operand1 [operator] operand2
     */
    public static int performOperation(int operand1, int operand2, char operator) {
        switch (operator) {
        case '+':
            return operand1 + operand2;
        case '-':
            return operand1 - operand2;
        case '*':
            return operand1 * operand2;
        case '/':
            if (operand2 == 0) {
                throw new ArithmeticException("Error : Can't Divide By Zero");
            }
            return operand1 / operand2;
        case '$':
        case '^':
            return (int) Math.pow(operand1, operand2);
        default:
            throw new IllegalArgumentException("Unknown Operator: " + operator);
        }
    }
}
