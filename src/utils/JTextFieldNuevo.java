package utils;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.Toolkit;

public class JTextFieldNuevo extends JTextField {
	private int limite = -1;
	private boolean numeros = false;
	private boolean letras = false;
	private boolean sinEspacios = false;

	public JTextFieldNuevo() {
		// Aplicamos un DocumentFilter para validar todas las entradas
		((AbstractDocument) this.getDocument()).setDocumentFilter(new DocumentFilter() {
			@Override
			public void insertString(FilterBypass fb, int offset, String text, AttributeSet attr)
					throws BadLocationException {
				// Validamos el texto que se intenta insertar
				if (validarTexto(text)) {
					super.insertString(fb, offset, text, attr);
				} else {
					Toolkit.getDefaultToolkit().beep(); // Beep si no es válido
				}
			}

			@Override
			public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
					throws BadLocationException {
				// Validamos el texto que se intenta reemplazar
				if (validarTexto(text)) {
					super.replace(fb, offset, length, text, attrs);
				} else {
					Toolkit.getDefaultToolkit().beep(); // Beep si no es válido
				}
			}
		});
	}

	// Método para validar el texto según las reglas
	private boolean validarTexto(String texto) {
		if (texto == null || texto.isEmpty()) {
			return true;
		}

		// Validar límite de caracteres
		if (limite != -1 && (this.getText().length() + texto.length()) > limite) {
			return false;
		}

		// Validar cada carácter del texto
		for (int i = 0; i < texto.length(); i++) {
			char c = texto.charAt(i);

			// Validar números
			if (numeros && !(c >= '0' && c <= '9')) {
				return false;
			}

			// Validar letras
			if (letras && !((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z') || c == ' ')) {
				return false;
			}

			// Validar sin espacios
			if (sinEspacios && c == ' ') {
				return false;
			}
		}

		return true;
	}

	// Getters y Setters (igual que antes)
	public boolean isSinEspacios() {
		return sinEspacios;
	}

	public void setSinEspacios(boolean sinEspacios) {
		this.sinEspacios = sinEspacios;
	}

	public boolean isNumeros() {
		return numeros;
	}

	public void setNumeros(boolean numeros) {
		this.numeros = numeros;
	}

	public boolean isLetras() {
		return letras;
	}

	public void setLetras(boolean letras) {
		this.letras = letras;
	}

	public int getLimite() {
		return limite;
	}

	public void setLimite(int limite) {
		if (limite >= -1) {
			this.limite = limite;
		}
	}
}