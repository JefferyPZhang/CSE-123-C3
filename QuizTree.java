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
    public QuizTree(Scanner inputFile) {
        String line = inputFile.nextLine();
        this.root = new QuizTreeNode(line);
        while (inputFile.hasNextLine()) {
            line = inputFile.nextLine();
            addQuizNode(root, line, false);
        }
    }

    // Adds the quiz nodes to the QuizTree (B).
    // Takes in the current QuizTreeNode, a String of the data
    // in the to-be-created node, and a boolean tracker to check
    // whether the node has already been created or not (P). 
    // Returns the boolean (R).
    private boolean addQuizNode(QuizTreeNode curr, String line, boolean added) {
        if (curr != null) {
            if (curr.left == null) {
                curr.left = new QuizTreeNode(line);
                added = true;
            } else if (!curr.left.data.contains(END_PREFIX)) {
                added = addQuizNode(curr.left, line, added);
            }
            if (!added && curr.right == null) {
                curr.right = new QuizTreeNode(line);
                added = true;
            } else if (!added && !curr.right.data.contains(END_PREFIX)) {
                added = addQuizNode(curr.right, line, added);
            }
        }
        return added;
    }

    // Has the user take the quiz using a Scanner parameter, 
    // allowing for user input (B, P).
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
        QuizTreeNode nodeToModify = findTarget(root, toReplace);
        if (nodeToModify != null) {
            nodeToModify.data = leftChoice + "/" + rightChoice;
            nodeToModify.left = new QuizTreeNode(END_PREFIX + leftResult);
            nodeToModify.right = new QuizTreeNode(END_PREFIX + rightResult);
        }
    }

    // Helper method for addQuestion to find the node containing
    // the String to be replaced (B). Takes in the current QuizTreeNode
    // and the String to be replaced as parameters (P). Returns the
    // node containing the target string, or null if not found (R).
    private QuizTreeNode findTarget(QuizTreeNode curr, String toReplace) {
        if (curr != null) {
            if (curr.data.equalsIgnoreCase(END_PREFIX + toReplace)) {
                return curr;
            } else {
                QuizTreeNode foundInLeft = findTarget(curr.left, toReplace);
                QuizTreeNode foundInRight = findTarget(curr.right, toReplace);
                return (foundInLeft != null) ? foundInLeft : foundInRight;
            }
        }
        return null;
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
    }
}
