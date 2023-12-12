// Jeffery Zhang
// TA: Jake Page
// CSE 123
// Due: November 29th, 2023
// C3: "BrettFeed" Quiz

// A QuizTree represents the architecture of a binary quiz.

import java.util.*;
import java.io.*;

public class QuizTree {
    public static final String END_PREFIX = "END:";
    public QuizTreeNode root;

    // Constructs a new QuizTree based on an input file
    // (as a Scanner) containing a list of nodes (B, P).
    // *The format of the input file is one such that
    // "question" nodes are represented with lines containing
    // a '/', and "answer" nodes are represented with lines
    // containing the prefix "END:". The order of the file is
    // handled from the top of the list to the bottom of the list.*
    public QuizTree(Scanner inputFile) {
        this.root = create(inputFile);
    }

    // Creates the nodes of the QuizTree (B).
    // *Takes in the Scanner input containing a list 
    // of the to-be-created nodes as a parameter (P). 
    // Returns the overall root QuizTreeNode (R)*.
    private QuizTreeNode create(Scanner input) {
        String line = input.nextLine();
        QuizTreeNode curr = new QuizTreeNode(line);
        if (!line.contains(END_PREFIX)) {
            curr.left = create(input);
            curr.right = create(input);
        }
        return curr;
    }

    // Has the user take the quiz using a Scanner parameter, 
    // *allowing for user input in the form of an answer to a question
    // (B, P).*
    public void takeQuiz(Scanner console) {
        boolean done = false;
        QuizTreeNode curr = root;
        while (!done) {
            if (curr.data.contains("/")) {
                String[] options = curr.data.split("/");
                System.out.print("Do you prefer " + options[0] + " or " + options[1] + "? ");
                String choice = console.nextLine();
                if (choice.equalsIgnoreCase(options[0]) || choice.equalsIgnoreCase(options[1])) {
                    curr = (choice.equalsIgnoreCase(options[0])) ? curr.left : curr.right;
                } else {
                    System.out.println("  Invalid response; try again.");
                }
            } else {
                System.out.println("Your result is: " + curr.data.substring(4));
                done = true;
            }
        }
    }

    // Replaces a result node with a further question, leading 
    // to two more results (B). Takes in the String of the result
    // to be replaced, and the two further String choices for the
    // new question, with both their String results (P).
    public void addQuestion(String toReplace, String leftChoice, String rightChoice,
                            String leftResult, String rightResult) {
        root = addQuestionHelper(root, toReplace, leftChoice, 
                                 rightChoice, leftResult, rightResult);
    }

    // *Helper method for addQuestion() to replace a result node 
    // with a further question (B). Takes in the String of the result
    // to be replaced, and the two further String choices for the
    // new question, with both their String results (P). Returns the
    // modified QuizTreeNode root (R).*
    private QuizTreeNode addQuestionHelper(QuizTreeNode curr, String toReplace,
                                        String leftChoice, String rightChoice,
                                        String leftResult, String rightResult) {
        if (curr != null) {
            if (curr.data.equalsIgnoreCase(END_PREFIX + toReplace)) {
                curr = new QuizTreeNode(leftChoice + "/" + rightChoice, 
                                        new QuizTreeNode(END_PREFIX + leftResult), 
                                        new QuizTreeNode(END_PREFIX + rightResult));
            } else {
                curr.left = addQuestionHelper(curr.left, toReplace, leftChoice, 
                                              rightChoice, leftResult, rightResult);
                curr.right = addQuestionHelper(curr.right, toReplace, leftChoice, 
                                               rightChoice, leftResult, rightResult);
            }
        }
        return curr;
    }

    // Exports a representation of the quiz to a file on the quiztaker's
    // computer (B). Takes in the editor at the location of the file
    // to be edited (as a PrintStream) (P).
    public void export(PrintStream outputFile) {
        String toExport = "";
        toExport = exportHelper(root);
        outputFile.println(toExport);
    }

    // Helper method for export() to construct the String
    // to be printed to the file (B). Takes in the current
    // QuizTreeNode as a parameter (P). Returns the representative
    // String (R).
    private String exportHelper(QuizTreeNode curr) {
        if (curr != null) {
            String leftStr = exportHelper(curr.left);
            String rightStr = exportHelper(curr.right);
            return curr.data + "\n" + leftStr + rightStr;
        }
        return "";
    }

    // A QuizTreeNode represents a data container unit for the QuizTree.
    public class QuizTreeNode {
        public String data;
        public QuizTreeNode left;
        public QuizTreeNode right;

        // Constructs a new QuizTreeNode, using given data to be inputed
        // into the node as a parameter (B, P).
        public QuizTreeNode(String data) {
            this.data = data;
        }

        // Constructs a new QuizTreeNode, using given data to be inputed
        // into the node, as well as references to both the left and right child
        // as parameters (B, P).
        public QuizTreeNode(String data, QuizTreeNode left, QuizTreeNode right) {
            this.data = data;
            this.left = left;
            this.right = right;
        }
    }
}
