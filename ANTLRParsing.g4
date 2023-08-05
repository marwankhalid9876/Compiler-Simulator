/**
 * Write your info here
 *
 * @name Marwan Khalid Farag
 * @id 46-14780
 * @labNumber 23
 */

grammar Task9;

@members {
	/**
	 * Compares two integer numbers
	 *
	 * @param x the first number to compare
	 * @param y the second number to compare
	 * @return 1 if x is equal to y, and 0 otherwise
	 */
	public static int equals(int x, int y) {
	    return x == y ? 1 : 0;
	}
}


s returns [int check]:
    a=a_rule c=c_rule[$a._2n, $a._3n, 0, 1] b=b_rule { $check = $c.slf * $c.suf * equals($a.n, $b.n); };

a_rule returns [int n, int _2n, int _3n]:
     A a1=a_rule { $n = $a1.n + 1; $_2n = $a1._2n * 2; $_3n = $a1._3n * 3; }
    | { $n = 0; $_2n = 1; $_3n = 1; };

b_rule returns [int n]:
    B b1=b_rule { $n = $b1.n + 1; }
    | { $n = 0; };

c_rule [int l, int u, int ilf, int iuf] returns [int m, int slf, int suf]:
    C c1=c_rule[$l, $u, $ilf, $iuf] {  $m = $c1.m + 1; $slf = $c1.slf + equals($l, $m); $suf = $c1.suf - equals($u, $c1.m);  }
    | { $m = 0; $slf = ilf; $suf = iuf; };

A : 'a';
B : 'b';
C : 'c';