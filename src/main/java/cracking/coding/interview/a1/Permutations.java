package cracking.coding.interview.a1;

public class Permutations {
    void permutations(String str){
        permutations(str,"");
    }

    private void permutations(String str, String prefix) {
        if (str.length() == 0) {
            System.out.println(prefix);
        } else {
            for (int i = 0; i < str.length(); i++) {
                    String rem = str.substring(0,i) + str.substring(i+1);
                    permutations(rem, prefix + str.charAt(i));
            }
        }

    }

    public static void main(String[] args) {
        (new Permutations()).permutations("ABCD");
    }

    /*
                    F(ABCD, "")
                 F(BCD, A)
               F(CD, AB)
             F(D, ABC)
          F("", ABCD)

                      F(ABCD, "")
                 F(BCD, A)
               F(CD, AB)
             F(D, ABC)
           F(

     */
}
