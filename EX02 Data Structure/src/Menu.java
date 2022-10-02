import java.util.Stack;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Menu {
	int choice;

	public void menu() {
		do {
			choice = Integer
					.parseInt(JOptionPane.showInputDialog("1. Tradutor \n 2. Calculadora \n 3. Encerrar aplicação"));

			switch (choice) {
			case 1:
				translate();
				break;
			case 2:
				calculate();
				break;
			}
		} while (choice != 3);
	}

	public void calculate() {
		Stack<Double> stacknum = new Stack<Double>();
		String formula = JOptionPane.showInputDialog(
				"Por favor insira a fórmula em notação polonesa. Coloque apenas um espaço entre cada caracter");
		formula = formula.replaceAll("\\s+", " ");
		formula = formula.toUpperCase();
		char c;
		double number = 0;
		double num1, num2;
		String aux = "";
		for (int i = 0; i < formula.length(); i++) {
			c = formula.charAt(i);
			if (Character.isLetter(c)) {
				number = Integer.parseInt(JOptionPane.showInputDialog("Coloque o valor da letra " + c));
				formula = formula.replaceAll(""+c, number+"");
			}
		}
		for (int i = 0; i< formula.length(); i++) {
			c = formula.charAt(i);
			if(c!='+' && c!='-' && c!='*' && c!='/' && c!='%' && c!='^') {
				if(c != ' ') {
					aux+= c;
				} else if(aux.length()>0){
					stacknum.push(Double.parseDouble(aux));
					aux= "";
					
				}
			} else {
				aux = "";
				num2 = stacknum.pop();
				num1 = stacknum.pop();
				switch (c) {
					case '+':
						number = num1 + num2;
						break;
					case '-':
						number = num1 - num2;
						break;
					case '*':
						number = num1 * num2;
						break;
					case '/':
						number = num1 / num2;
						break;
					case '%':
						number = num1 % num2;
						break;
					case '^':
						number= Math.pow(num1, num2);
						break;
						
				}
				stacknum.push(number);
			}
			
		}
		JOptionPane.showMessageDialog(null, "O resultado da sua expressão é " + stacknum.pop());
	}

	public void translate() {
		String formula = JOptionPane
				.showInputDialog("Por favor insira a fórmula infixa para traduzir para notação polonesa");
		JTextArea textarea = new JTextArea(10, 3);
		textarea.setText(translator(formula));
		textarea.setEditable(false);
		JOptionPane.showMessageDialog(null, new JScrollPane(textarea), "Resultado", JOptionPane.INFORMATION_MESSAGE);
	}

	private String translator(String infixed) {
		Stack<Character> stack = new Stack<Character>();
		String postfixed = "";
		char c;
		for (int i = 0; i < infixed.length(); i++) {
			c = infixed.charAt(i);
			if (c != ' ') {
				switch (c) {
				case '+':
				case '-':
				case '*':
				case '/':
				case '%':
				case '^':
					while (!stack.isEmpty() && (prioritize(stack.peek()) >= prioritize(c))) {
						postfixed += stack.pop() + " ";
					}
					stack.push(c);
					break;
				case '(':
					stack.push(c);
					break;
				case ')':
					while (stack.peek() != '(') {
						postfixed += stack.pop()+ " ";
					}
					stack.pop();
					break;
				default:
					postfixed += c + " ";
				}
			}
		}
		while (!stack.isEmpty()) {
			postfixed += stack.pop() + " ";
		}
		return postfixed;
	}

	private int prioritize(char operator) {
		switch (operator) {
		case '(':
			return 1;
		case '+':
		case '-':
			return 2;
		case '*':
		case '/':
		case '%':
			return 3;
		case '^':
			return 4;
		}
		return 0;
	}
}
