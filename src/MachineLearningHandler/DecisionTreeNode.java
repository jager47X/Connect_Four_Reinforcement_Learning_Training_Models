package MachineLearningHandler;


public abstract class DecisionTreeNode {
    private boolean decision;
    private DecisionTreeNode leftChild;
    private DecisionTreeNode rightChild;
    private Object result; // Result associated with a leaf node

    // Constructor for internal nodes
    public DecisionTreeNode(boolean decision, DecisionTreeNode leftChild, DecisionTreeNode rightChild) {
        this.decision = decision;
        this.leftChild = leftChild;
        this.rightChild = rightChild;
    }

    // Constructor for leaf nodes
    public DecisionTreeNode(Object result) {
        this.result = result;
        this.leftChild = null;
        this.rightChild = null;
    }

    // Getter methods
    public boolean getDecision() {
        return decision;
    }

    public DecisionTreeNode getLeftChild() {
        return leftChild;
    }

    public DecisionTreeNode getRightChild() {
        return rightChild;
    }

    public Object getResult() {
        return result;
    }

    // Setter methods (useful for building the tree)
    public void setLeftChild(DecisionTreeNode leftChild) {
        this.leftChild = leftChild;
    }

    public void setRightChild(DecisionTreeNode rightChild) {
        this.rightChild = rightChild;
    }
}
