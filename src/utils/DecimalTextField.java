package utils;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class DecimalTextField extends JTextField {
    public DecimalTextField() {
        super();
        // Aplicar el estilo FlatLaf
        setColumns(10);

        // Aplicar el DocumentFilter
        ((AbstractDocument) getDocument()).setDocumentFilter(new DecimalFilter());
    }

    private class DecimalFilter extends DocumentFilter {
        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
                throws BadLocationException {
            StringBuilder sb = new StringBuilder();
            sb.append(fb.getDocument().getText(0, fb.getDocument().getLength()));
            sb.insert(offset, string);

            if (isValidDecimal(sb.toString())) {
                super.insertString(fb, offset, string, attr);
            }
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                throws BadLocationException {
            StringBuilder sb = new StringBuilder();
            sb.append(fb.getDocument().getText(0, fb.getDocument().getLength()));
            sb.replace(offset, offset + length, text);

            if (isValidDecimal(sb.toString())) {
                super.replace(fb, offset, length, text, attrs);
            }
        }

        private boolean isValidDecimal(String text) {
            if (text.isEmpty()) return true;

            // Verificar que solo contenga dígitos y un punto decimal
            if (!text.matches("^\\d*\\.?\\d*$")) {
                return false;
            }

            // Verificar que no haya más de un punto
            int dotCount = text.length() - text.replace(".", "").length();
            return dotCount <= 1;
        }
    }
}