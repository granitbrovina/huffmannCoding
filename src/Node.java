import javafx.util.Pair;

public class Node {

    Pair<Character, Double> pair;

    double prob;
    Node next1;
    Node next2;

    Node parent;
    int parentValue; // 0 or 1

//    char charr;

    public Node(Pair<Character, Double> pair) {
        this.pair = pair;
    }

    public double getProb() {
        return prob;
    }

    //parent je puscica k kaze do vrsta
    //parent is the arrow which points to
    //rekurzivna fja, vsak stars appendamo v string dokler ne pridemo do root
    //recursive function, every stars appends to string until we get to the root
    // in potem obrnemo string, ker smo sli od spodaj navzgor
    //then we reverse the string, because we went from down uppwards
    public String encode(StringBuilder sb) {
        if (parent == null) return sb.reverse().toString();
        sb.append(parentValue);
        System.out.println(parentValue);
        return parent.encode(sb);
    }

}
