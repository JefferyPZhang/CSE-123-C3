**Assignment Spec:**

In our quizzes, users will be asked repeatedly to choose which of two options they prefer until they are presented with a final result. We will represent a quiz using a binary tree, where leaf nodes represent possible results, and non-leaf nodes (branches) represent choices the user will make. When a user takes a quiz, they will be presented with the choice from the root node of the tree. Based on their response, the system will traverse to either the left or right child of the root. If the node found is a leaf, the user will be shown their result. Otherwise, the process will repeat from the new node until a leaf is reached. See below for a full sample quiz and execution.

**Quiz File Format**
In addition to representing quizzes as a binary tree in our program, we will also read quizzes from and store quizzes to text files in a standard file format. In a quiz file, each node will be represented by a single line in the file containing the text for that node. 

"Choice" nodes (i.e. nodes that represent a choice between two options) will be written with the two choices separated by a single slash (/) character. When taking the quiz, choosing the option before the slash will move to the left child of the node, whereas choosing the option after the slash will move the right child of the node.

For example, red/blue  represents a choice between red and blue, where red is the "left" choice and blue is the "right" choice.

You may assume that no choice will contain a slash.

"Result" nodes will be written as the result option prefixed with the text END:.

For example, END:froot loops represents a result node for the result "froot loops"

You may assume that no result will contain the exact text END:. 
