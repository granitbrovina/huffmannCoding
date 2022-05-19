import javafx.util.Pair;
import sun.nio.cs.US_ASCII;

import java.util.*;

public class Main {

    static ArrayList<Pair<Character, Double>> alph2;
    static ArrayList<Pair<Character, Double>> alph3;
    static {
        alph2 = new ArrayList<>();
        alph2.add(new Pair<>('a', 0.05));
        alph2.add(new Pair<>('b', 0.1));
        alph2.add(new Pair<>('c', 0.15));
        alph2.add(new Pair<>('d', 0.18));
        alph2.add(new Pair<>('e', 0.22));
        alph2.add(new Pair<>('f', 0.3));
        alph2.sort(Comparator.comparingDouble(Pair::getValue)); //sortira glede na probabilitije (#sort them comparing to probabilities)

        alph3 = new ArrayList<>(); //alph3 je ubistvuce si na premici predstavljas vrednosti: (#intervals between:
        alph3.add(new Pair<>('a', 0.05)); // od 0.05 do 0.15 je a
        alph3.add(new Pair<>('b', 0.15)); // od 0.15 do 0.3 je b
        alph3.add(new Pair<>('c', 0.3)); // od 0.3 do 0.48 je c itd... (in potem spodaj v generateRandomSequence()
        alph3.add(new Pair<>('d', 0.48));// zgenereras cifro in v kater interval pade, tista crka je (#and then lower in generateRandomSequence() you generate a number in the interval that the number falls)
        alph3.add(new Pair<>('e', 0.7));
        alph3.add(new Pair<>('f', 1.0));
    }

    public static void main(String[] args) {
//        System.out.println(alph3);

        int lengthOfWord = 300;
        int size = 3 * lengthOfWord;

        double sizeDouble =Double.valueOf(size);


        ArrayList<Character> sequence = generateRandomSequence();

//        System.out.println(sequence);

        Encoder enc = new Encoder(alph2);

//        checkGeneratedSequence(sequence);

        Pair<String, String> result = enc.encodeSequence(sequence);

        System.out.println("Uncompressed size | Huffman: " + size + " | " + result.getValue().length());

        System.out.println("Compression ratio: " + sizeDouble / (result.getValue().length()));
    }


    //naredis 300 dolg zaporedje v array listu, vn mece cifre, (#300 long sequence in arrazlist, spilling out numbers
    //in v result dodas key od crke, ki je bila interval v katerega je pristala random cifra (#adding key from letter to the result in which interval the letter lended
    // v resultu so potem crke )#letters in the result
    private static ArrayList<Character> generateRandomSequence() {
        int sequenceLength = 300;
        Random r = new Random();
        ArrayList<Character> result = new ArrayList<>(sequenceLength);
        while (sequenceLength-- > 0) {
            double coin = r.nextDouble();
            for (Pair<Character, Double> pair : alph3) {
                if (coin < pair.getValue()) {
                    result.add(pair.getKey());
                    break;
                }
            }
        }
        return result;
    }
}
