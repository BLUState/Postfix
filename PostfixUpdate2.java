/*
 * @author Brandon Lu
 * This program converts an infix expression to a postfix expression
 * Update 1.1
 * New Features:
 * Program can evaluate postfix expression
 * If the solution is a decimal, the program will display the solution rounded down to the nearest whole number
 */
import java.util.Scanner;
import java.util.ArrayList;
import java.lang.String;
import java.util.Stack;
import java.lang.Math;
public class PostfixUpdate2
{
	public static void main(String[] args)
	{
		Boolean valid = true;
		
		Scanner s = new Scanner(System.in);
		System.out.print("Please enter an arithmetic expression: ");

		/*
		 * Arithmetic expression input by user
		 */
		String infix = s.nextLine();
		System.out.println();		
		while (isValid(infix, valid) == false)
		{
			System.out.println("Invalid expression");
			System.out.println("Only use letters, numbers, operators, and/or parentheses");
			System.out.println("Please ensure that there are no consecutive operators or operands");
			System.out.println("If using parentheses, please ensure they are used correctly");
			System.out.println();
			System.out.print("Please try again: ");
			infix = s.nextLine();
			System.out.println();
			isValid(infix, valid);
		}
		
		/*
		 * ArrayList of each character in infix2
		 */
		ArrayList<Character> infix2 = new ArrayList<Character>();
		createList(infix, infix2);
		
		//Convert infix expression to postfix expression
		convert(infix, infix2);
	}
	
	/*
	 * This method verifies whether or not the user input is a valid arithmetic expression
	 */
	public static boolean isValid(String infix, boolean valid)
	{
		/*
		 * Number of left parentheses scanned
		 */
		int leftPar = 0;
		
		/*
		 * Number of right parentheses scanned
		 */
		int rightPar = 0;
		
		/*
		 * Character in string infix preceding character in String infix at index i
		 */
		
		for (int i = 0; i < infix.length(); i++)
		{
			if (i > 0)
			{
				char a = infix.charAt(i);
				char b = infix.charAt(i - 1);
				//If two consecutive numbers are detected
				if (Character.isDigit(a) && Character.isDigit(b) || Character.isLetter(a) && Character.isLetter(b) || isOper(a) == true && isOper(b) == true)
				{
					valid = false;
				}
			}
			char a = infix.charAt(i);
			
			//Check if char a is a left parenthesis
			if (a == '(')
			{
				leftPar ++;
				//If not a left parenthesis...
			} else if (a != '(')
			{
				//Check if char a is a right parenthesis
				if (a == ')')
				{
					rightPar ++;
					
					if (leftPar < rightPar)
					{
						valid = false;
						break;
					}
					//If not a right parenthesis
				} else if (a != ')')
				{
					//Check if char a is a digit
					if (!Character.isDigit(a))
					{
						//If not a digit, check if char a is a letter
						if (!Character.isLetter(a))
						{
							//If not a letter, check if char a is an operator
							if (isOper(a) == false)
							{
								//If not an operator, check if char a is an empty space
								if (a != (' '))
								{
									valid = false;
									break;
								}
							}
						}
					}
				}
			}
		}
		
		//If the number of left parentheses is not equal to that of right parentheses
		if (leftPar != rightPar)
		{
			valid = false;
		}
		

		return valid;
	}
	
	/*
	 * This method checks to see if a character is a mathematical operator 
	 */
	public static boolean isOper(char a)
	{
			if (a == '+')
			{
				return true;
			} else if (a == '-')
			{
				return true;
			} else if (a == '*')
			{
				return true;
			} else if (a == '/')
			{
				return true;
			} else if (a == '%')
			{
				return true;
			} else if (a == '^')
			{
				return true;
			} else
			{
				return false;
			}
	}
	
	/*
	 * This method uses ArrayList infix2 to remove any spaces from String infix and stores the resulting String to String infix3
	 */
	public static void removeSpace(ArrayList<Character> infix2)
	{
		/*
		 * String infix with any spaces removed
		 */
		String infix3 = "";
		
		for (int i = 0; i < infix2.size(); i++)
		{
			infix3 += infix2.get(i);
		}
		System.out.print("Infix Expression: ");
		System.out.println(infix3);
	}
	
	/*
	 * This method copies every character in a String into an ArrayList for analysis and modification
	 */
	public static void createList(String infix, ArrayList<Character> infix2)
	{
		for (int i = 0; i < infix.length(); i++)
		{
			if (infix.charAt(i) != (' '))
			{
				infix2.add(infix.charAt(i));				
			}
		}
	}

	/*
	 * This method is used to determine the precedence of each character
	 */
	public static int rank(char a)
	{
		int rank = 0;
		if (a == '+' || a == '-')
		{
			rank = 1;
		} else if (a == '*' || a == '/' || a == '%')
		{
			rank = 2;
		} else if (a == '^')
		{
			rank = 3;
		} else if (a == '(')
		{
			rank = 4;
		}
		return rank;
	}
	
	/*
	 * This method converts the String infix given by the user to a postfix expression using the ArrayList infix2
	 */
	public static void convert(String infix, ArrayList<Character> infix2)
	{
		/*
		 * Postix expression converted from infix expression input by user
		 */
		String postfix = "";
		
		/*
		 * Stack holds operators for conversion
		 */
		Stack<Character> oper = new Stack<Character>();
		int rankA;
		int rankB = 0;
		int leftPar = 0;
		
		for (int i = 0; i < infix2.size(); i++)
		{
			char a = infix2.get(i);
			
			//If char a is digit or letter, append it to end of String postfix
			if (Character.isDigit(a) && (a != '(' || a != ')') || Character.isLetter(a))
			{
				postfix += a;
				
				//If char a is an operator, incorporate Stack oper
			} else if (isOper(a))
			{
				//if Stack oper is empty, push a to top of stack
				if (oper.isEmpty())
				{
					oper.push(a);
					
					//Determine precedence of operator at top of Stack oper
					rankB = rank(a);					
					//If Stack is not empty, compare precedence of char a to precedence of operator at top of Stack oper
				} else
				{
					//Determine precedence of char a
					rankA = rank(a);
					
					//If char a has higher precedence than operator at top of Stack oper push char a to top of Stack oper
					if (rankA > rankB)
					{
						rankB = rank(a);
						oper.push(a);

						//If char a has precedence less than or equal that of operator at top of Stack oper...
					} else if (rankA <= rankB)
					{
						//Pop operator from top of Stack oper and append it to end of String postfix until precedence of operator at top of Stack oper is less than that ofchar a...
						while (rankA <= rankB && !oper.isEmpty())
						{
							//Check if operator at top of Stack oper is a left parenthesis
							if (oper.peek() == '(')
							{
								oper.push(a);
								break;
								//If not a left parenthesis...
							} else
							{
								//Pop operator at top of Stack oper and append to String postfix
								postfix += oper.pop();
								
								//If Stack oper is not empty, determine rank of operator at top of Stack oper
								if (!oper.isEmpty())
								{
									rankB = rank(oper.peek());
									
									//Push char a to top of Stack oper
								} else
								{
									oper.push(a);
									break;
								}
							}
						}
						
						//Determine rank of operator at top of Stack oper
						rankB = rank(oper.peek());
					}
				}
			} else if (a == '(')
			{
				leftPar ++;
				oper.push(a);
			} else if (a == ')')
			{
				while (oper.peek() != '(')
				{
					postfix += oper.pop();
				}
				oper.pop();
				if (!oper.isEmpty())
				{
					rankB = rank(oper.peek());	
				}

				leftPar --;
			}
		}
		
		//Pop all remaining operators from Stack oper and append to String postfix
		while (!oper.isEmpty())
		{
			postfix += oper.pop();
		}
		removeSpace(infix2);
		System.out.println("Postfix expression: " + postfix);
		solve(postfix);
	}
	
	/*
	 * This method evaluates a postfix expression
	 */
	public static void solve(String postfix)
	{
		/*
		 * This Stack holds the operands in String popstfix
		 */
		Stack<Integer> oper = new Stack<Integer>();
		Boolean solvable = true;
		
		for (int i = 0; i < postfix.length(); i++)
		{
			//If String postfix contains letters
			if (Character.isLetter(postfix.charAt(i)))
			{
				System.out.println("Only expressions with numeric operands can be evaluated");
				solvable = false;
				break;
				
				//If String postfix character at index i is a numeric operand...
			} else if (Character.isDigit(postfix.charAt(i)))
			{
				int a = Character.getNumericValue(postfix.charAt(i));
				oper.push(a);
				
				//If String postfix character at index i is an operator...
			} else if (isOper(postfix.charAt(i)))
			{
				//Pop the top two operands from Stack oper and store them into two int variables
				int y = oper.pop();
				int x = oper.pop();
				
				//Store the current operator in char o
				char o = postfix.charAt(i);
				
				//Evaluate the operation with the two ints
				int res = operation(o, x, y);
				
				//Push the result onto Stack oper
				oper.push(res);
			}
		}
		
		//If String postfix is solvable, print the solution
		if (solvable == true)
		{
			System.out.println("Solution: " + oper.pop());
		}
	}
	
	/*
	 * This method determines which operation to carry out between two operands by analyzing the operator character and then carries it out
	 */
	public static int operation(char o, int x, int y)
	{
		/*
		 * Result
		 */
		int res = 0;
		if (o == '+')
		{
			res = x + y;
		} else if (o == '-')
		{
			res = x - y;
		} else if (o == '*')
		{
			res = x * y;
		} else if (o == '/')
		{
			res = x / y;
		} else if (o == '%')
		{
			res = x % y;
		} else if (o == '^')
		{
			res = x;
			for (int i = 1; i < y; i++)
			{
				res = res * x;
			}
		}
		return res;
	}
}