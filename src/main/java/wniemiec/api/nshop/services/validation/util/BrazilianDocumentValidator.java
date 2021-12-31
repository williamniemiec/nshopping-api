package wniemiec.api.nshop.services.validation.util;


/**
 * Responsible for validate documents according to Brazil document rules.
 * 
 * Source: https://gist.github.com/adrianoluis/5043397d378ae506d87366abb0ab4e30
 */
public class BrazilianDocumentValidator {
    
    //-------------------------------------------------------------------------
    //		Attributes
    //-------------------------------------------------------------------------
    private static final int[] weightCpf;
    private static final int[] weightCnpj;


    //-------------------------------------------------------------------------
    //		Initialization blocks
    //-------------------------------------------------------------------------
    static {
        weightCpf = new int[] {11, 10, 9, 8, 7, 6, 5, 4, 3, 2};
        weightCnpj = new int[] {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
    }


    //-------------------------------------------------------------------------
    //		Constructor
    //-------------------------------------------------------------------------
    private BrazilianDocumentValidator() {
    }


    //-------------------------------------------------------------------------
    //		Methods
    //-------------------------------------------------------------------------
    public static boolean isValidCpf(String cpf) {
        if (!isValidCpfDigits(cpf)) {
            return false;
        }

        String cpfFirstDigits = cpf.substring(0, 9);
        String cpfFinalDigits = generateCpfFinalDigits(cpfFirstDigits);
        
        return cpf.equals(cpfFirstDigits + cpfFinalDigits);
    }

    private static boolean isValidCpfDigits(String cpf) {
        if (cpf == null) {
            return false;
        }

        return  (cpf.length() == 11) 
                && !cpf.matches(cpf.charAt(0) + "{11}");
    }

    private static String generateCpfFinalDigits(String cpfFirstDigits) {
        Integer digit1 = calculate(cpfFirstDigits, weightCpf);
        Integer digit2 = calculate(cpfFirstDigits + digit1, weightCpf);
        
        return digit1.toString() + digit2.toString();
    }

    private static int calculate(String str, int[] weight) {
        int sum = 0;
        
        for (int i = str.length()-1; i >= 0; i--) {
            int digit = Integer.parseInt(str.substring(i, i + 1));

            sum += digit * weight[weight.length - str.length() + i];
        }
        
        sum = 11 - sum % 11;
        
        return sum > 9 ? 0 : sum;
    }

    public static boolean isValidCnpj(String cnpj) {
        if (!isValidCnpjDigits(cnpj)) { 
            return false;
        }

        String cnpjFirstDigits = cnpj.substring(0, 12);
        String cnpjFinalDigits = generateCnpjFinalDigits(cnpjFirstDigits);

        return cnpj.equals(cnpjFirstDigits + cnpjFinalDigits);
    }

    private static boolean isValidCnpjDigits(String cnpj) {
        if (cnpj == null) {
            return false;
        }

        return  (cnpj.length() == 14) 
                && !cnpj.matches(cnpj.charAt(0) + "{14}");
    }

    private static String generateCnpjFinalDigits(String cnpjFirstDigits) {
        Integer digit1 = calculate(cnpjFirstDigits, weightCnpj);
        Integer digit2 = calculate(cnpjFirstDigits + digit1, weightCnpj);
        
        return digit1.toString() + digit2.toString();
    }
}
