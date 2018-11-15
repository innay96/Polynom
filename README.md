year 2 , class project - " polynom"

General Orientation:
Polynom is class that implements a polynomial by ArrayList of Monoms, implements Polynom_able interface.
The proper way to insert a Monom is: a*x^b
The proper way to insert a Polynom is: a*x^b / ax^b


Introduction and Summary:
This class represents a general polynomial:
f(x) = a1*x^b1 + a2*x^b2 ... an*xbn,
 * where: a1, a2 ... an are real numbers, and b1< b2 ... <bn >=0 are none negative integers (naturals).
 * For formal definitions see: https://en.wikipedia.org/wiki/Polynomial

Variables:
1.	polynom - ArrayList of Monoms.
2.	COMP - Monom_Comperato.

Such polynomial has the following functionality:
→Constructors:
1.	Empty constructor- Zero polynomial
2.	Deep copy constructor
3.	Constructor creates a polynomial according to the String input:
Throws Exception if the String is empty or if the String input is a String of invalid characters.
Uses the StringTokenizer object to divide the String by connection marks “+”. 
For example: input String “2*x^3 + 2*x –x^2 – 3”
StringTokenizer divides the String: “2*x^3” , “2*x –x^2 – 3”.
While there is more connected in the StringTokenizer - create new String contains the next connected.
Handle the possibility there are negative coefficients:
Gets a String represents a polynomial:
Checks whether the String contains the symbol “-“ , then divides it. Besides the first String in the array- all the other with negative coefficient for sure. 
Checks if the first coefficient is also negative or positive and then sends it to a function that divides each connected to the coefficient and power and add to the list.
If there only positive coefficient in the String input: sends it to a function that divides each connected to the coefficient and power and add to the list.
Sort the monoms at the polynomial by the power of each.

The String constructor supported by 3 private void methods: 

a.	stringtokenPositive /stringtokenNegative - 	gets String represent positive/negative monom. Uses the StringTokenizer Object to divide each by the marks “*xX^(space)”. Each method handles the options the coefficient does not 1 (first char is not X/x), the option the power is 0 (^/X/x do not exist in the String) and more.
b.	addMonomInit-  gets monom c and adding it to the list., Checks if the c monom’s power exists already in the polynomial, and if so updates it’s coefficient.

→Basic operations:
Value at x, printing, check if empty and more.

→Arithmetic operations on polynomials:
Addition, subtraction, multiplication. (reference source: https://github.com/benmoshe/Polynoms/tree/master/src/myMath )

→Methods for advanced operations on polynomial: 
Calculating the approximated area above X axis below this function, derivative, polynomial’s roots. (reference source:
 https://en.wikipedia.org/wiki/Bisection_method )
 
 -class Graph representing polonomial graph using GRAL GUI, in addition it calculates the area of a given polonomial.




