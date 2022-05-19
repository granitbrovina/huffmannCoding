import javafx.util.Pair;
import sun.nio.cs.US_ASCII;

import java.util.*;

public class Encoder {

    List<Pair<Character, Double>> alph;
    PriorityQueue<Node> leafs;
    HashMap<Character, String> encoded;

    public Encoder(List<Pair<Character, Double>> alph) {
        this.alph = alph;

        encoded = new HashMap<>();

        //notr v leaves(vrsto s prednostjo) mecemo te keye od crk, ki so potem razvrsceni
        //in the leaves (priorityQueue) we throw kezs from the letters, which are then sorted
        leafs = new PriorityQueue<>(10, Comparator.comparingDouble(Node::getProb));
        PriorityQueue<Node> tmpQ = new PriorityQueue<>(10, Comparator.comparingDouble(Node::getProb));
        //adding pair to nodes
        for (Pair<Character, Double> pair : alph) {
            Node n = new Node(pair);
            n.prob = pair.getValue();
            tmpQ.add(n);
            leafs.add(n);
        }

        Node root = buildTree(tmpQ);
        encodeLetters();

    }

    public Node buildTree(PriorityQueue<Node> q) {
        if (q.size() == 1) return q.poll();

        // vzemi prva dva noda, ki
        // imata najmanjsi probability
        //take first two nodes with smallest probability
        Node n1 = q.poll();
        Node n2 = q.poll();

        //make new higher node in the tree
        Node n = new Node(null);
        n.next1 = n1;
        n.next2 = n2;
        n.prob = n1.prob + n2.prob;

        //dodelimo vrednosti 1 ali nic
        //we assign them 1 or 0
        n1.parent = n;
        n1.parentValue = 1;
        n2.parent = n;
        n2.parentValue = 0;

        q.add(n);

        return buildTree(q);
    }

    //od listov navzgor do vrha in klics za vsak leaf encode (v nodeu)
    //from leafs to top zou call for each leaf encode (in node)
    private void encodeLetters() {
        leafs.forEach(n -> {
            encoded.put(n.pair.getKey(), n.encode(new StringBuilder()));
        });
        System.out.println(encoded);
    }

    //tuki notr  naredimo ascii in po huffmanu hash, da potem v mainu klicemo izpis
    //we make ascii and huffman hash, then we call in main to print it
    public Pair<String, String> encodeSequence(List<Character> seq) {
        StringBuilder asciiEncoded = new StringBuilder();
        HashMap<Character, String> ascii = new HashMap<>();
        for (Node leaf : leafs) {
            String s = "" + leaf.pair.getKey();
            Character c = leaf.pair.getKey();
            byte[] bytes = s.getBytes(US_ASCII.defaultCharset());
            for (byte b : bytes) {
                ascii.put(c, Integer.toBinaryString(b & 255 | 256).substring(1));
            }
        }
        seq.forEach(c -> asciiEncoded.append(ascii.get(c)));

        StringBuilder huffmanEncoded = new StringBuilder();
        seq.forEach(c -> huffmanEncoded.append(encoded.get(c)));

        return new Pair<>(asciiEncoded.toString(), huffmanEncoded.toString());
    }
}
