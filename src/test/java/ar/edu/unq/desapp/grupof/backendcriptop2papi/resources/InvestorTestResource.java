package ar.edu.unq.desapp.grupof.backendcriptop2papi.resources;

import ar.edu.unq.desapp.grupof.backendcriptop2papi.model.Investor;

public class InvestorTestResource {

    public static final String ANY_NAME = "anyname";
    public static final String ANY_SURNAME = "surname";
    public static final String ANY_EMAIL = "validEmail@gmail.com";
    public static final String ANY_ADDRESS = "validAdress";
    public static final String ANY_PASSWORD = "ValidPassword123@";
    public static final String ANY_MERCADO_PAGO_CVU = "1234567891234567891234";
    public static final String ANY_WALLET_ADDRESS ="12345678";

    public static Investor anyInvestor(){
        return new Investor(
                ANY_NAME,
                ANY_SURNAME,
                ANY_EMAIL,
                ANY_ADDRESS,
                ANY_PASSWORD,
                ANY_MERCADO_PAGO_CVU,
                ANY_WALLET_ADDRESS
        );
    }
    public static Investor anyInvestorWithName(String name){
        return new Investor(
                name,
                ANY_SURNAME,
                ANY_EMAIL,
                ANY_ADDRESS,
                ANY_PASSWORD,
                ANY_MERCADO_PAGO_CVU,
                ANY_WALLET_ADDRESS
        );
    }

    public static Investor anyInvestorWithSurname(String surname){
        return new Investor(
                ANY_NAME,
                surname,
                ANY_EMAIL,
                ANY_ADDRESS,
                ANY_PASSWORD,
                ANY_MERCADO_PAGO_CVU,
                ANY_WALLET_ADDRESS
        );
    }

    public static Investor anyInvestorWithEmail(String email){
        return new Investor(
                ANY_NAME,
                ANY_SURNAME,
                email,
                ANY_ADDRESS,
                ANY_PASSWORD,
                ANY_MERCADO_PAGO_CVU,
                ANY_WALLET_ADDRESS
        );
    }

    public static Investor anyInvestorWithAddress(String address){
        return new Investor(
                ANY_NAME,
                ANY_SURNAME,
                ANY_EMAIL,
                address,
                ANY_PASSWORD,
                ANY_MERCADO_PAGO_CVU,
                ANY_WALLET_ADDRESS
        );
    }

    public static Investor anyInvestorWithPassword(String password){
        return new Investor(
                ANY_NAME,
                ANY_SURNAME,
                ANY_EMAIL,
                ANY_ADDRESS,
                password,
                ANY_MERCADO_PAGO_CVU,
                ANY_WALLET_ADDRESS
        );
    }

    public static Investor anyInvestorWithMercadoPagoCVU(String cvu){
        return new Investor(
                ANY_NAME,
                ANY_SURNAME,
                ANY_EMAIL,
                ANY_ADDRESS,
                ANY_PASSWORD,
                cvu,
                ANY_WALLET_ADDRESS
        );
    }

    public static Investor anyInvestorWithWalletAddress(String walletAddress){
        return new Investor(
                ANY_NAME,
                ANY_SURNAME,
                ANY_EMAIL,
                ANY_ADDRESS,
                ANY_PASSWORD,
                ANY_MERCADO_PAGO_CVU,
                walletAddress
        );
    }

}
