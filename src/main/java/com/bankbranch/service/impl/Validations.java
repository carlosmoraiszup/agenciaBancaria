package com.bankbranch.service.impl;

import com.bankbranch.service.exception.EmptyException;
import com.bankbranch.service.exception.InvalidAtributeException;
import com.bankbranch.service.exception.LengthCpfException;

public class Validations {

    /* CPF */

    private static final int[] pesoCPF = {11, 10, 9, 8, 7, 6, 5, 4, 3, 2};

    private static int calcularDigito(String str, int[] peso) {
        int soma = 0;
        for (int indice=str.length()-1, digito; indice >= 0; indice-- ) {
            digito = Integer.parseInt(str.substring(indice,indice+1));
            soma += digito*peso[peso.length-str.length()+indice];
        }
        soma = 11 - soma % 11;
        return soma > 9 ? 0 : soma;
    }

    public static boolean isValidCPF(String cpf) {
        if ((cpf==null) || (cpf.length()!=11)) throw new LengthCpfException("There must be exactly 11 digits!");
        Integer digito1 = calcularDigito(cpf.substring(0,9), pesoCPF);
        Integer digito2 = calcularDigito(cpf.substring(0,9) + digito1, pesoCPF);
        return cpf.equals(cpf.substring(0,9) + digito1.toString() + digito2.toString());
    }

    public static void validationName(String nameCustomer){
        if (nameCustomer == null || nameCustomer.isEmpty())
            throw new EmptyException("Name is null!");
    }


    public static void validationCpf(String cpf){
        if (!isValidCPF(cpf))
            throw new InvalidAtributeException("Cpf is invalid!");
    }



    /* Name is empty */

}
