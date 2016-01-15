package util;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class TextFieldFilter extends PlainDocument {

	private static final long serialVersionUID = 1L;
	public static final String NUMERIC = "0123456789";
    public static final String FLOAT = NUMERIC + ".";
    public static final int MAX_NUMERIC_INPUT_LENGTH = 15;
    public static final int PRECISION = 2;
    
    protected String acceptedChars = null;
    protected boolean negativeAccepted = false;
    protected int maxLen = 0;
    protected boolean hasLimitedLength = false;
    protected boolean isFloat = false;
    protected int digitsAfterPoint = 4;
    
    public TextFieldFilter(String acceptedchars) {
        this.acceptedChars = acceptedchars;
    }

    public TextFieldFilter(String acceptedchars, int maxLength) {
        this(acceptedchars);
        this.maxLen = maxLength;
        this.hasLimitedLength = true;
    }
    
    public void setNegativeAccepted(boolean negativeaccepted) {
        this.negativeAccepted = negativeaccepted;
        if(negativeaccepted){
            this.acceptedChars += "-";
        }
    }
    
    /**
     * Specify how many digits are valid after the point of a float.
     * Should only be used for float numbers.
     * 
     * @param noOfDigitsAfterPoint
     */
    public void setPrecisionForFloat(int noOfDigitsAfterPoint) {
        if(this.acceptedChars.equals(FLOAT)) {
            this.isFloat = true;
            this.digitsAfterPoint = noOfDigitsAfterPoint;
        }
        else {
            isFloat = false;
        }
    }
    
    @Override
    public void insertString (int offset, String  str, AttributeSet attr) 
    											throws BadLocationException {
    	
        if (str == null){
            return;
        }

        //ignore input which is not allowed
        for (int i=0; i < str.length(); i++) {
            if (this.acceptedChars.indexOf(String.valueOf(str.charAt(i))) == -1) {
                return;
            }
        }

        //if is float, ignore all point after the first one
        if (this.acceptedChars.equals(FLOAT) ||
                (this.acceptedChars.equals(FLOAT + "-") && this.negativeAccepted)) {
            if (str.indexOf(".") != -1) {
                if (getText(0, getLength()).indexOf(".") != -1) {
                    return;
                }
            }
        }

        //"-" is only good at first place
        if (this.negativeAccepted && str.indexOf("-") != -1) {
            if (str.indexOf("-") != 0 || offset != 0 ) {
                return;
            }
        }

        if(this.hasLimitedLength && getLength() + str.length() > this.maxLen) {
            return;
        }
        if(this.isFloat) {
            String curStr = getText(0, getLength()) + str;
            int indexOfPoint = curStr.indexOf('.');
            String strAfterPoint = "";
            if(indexOfPoint >= 0)
            {
            	strAfterPoint = curStr.substring(indexOfPoint + 1);
            }
            if(strAfterPoint.length() > this.digitsAfterPoint) {
                return;
            }
        }
        super.insertString(offset, str, attr);
    }
}
