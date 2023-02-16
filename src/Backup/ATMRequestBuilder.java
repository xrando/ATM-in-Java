package ATM.Utilities;

import ATM.Constants.Constants.RequestBuilder;

public class ATMRequestBuilder {
    private StringBuilder stringBuilder = new StringBuilder();
    public ATMRequestBuilder(String type){
        this.stringBuilder.insert(0, type + RequestBuilder.Separator);
    }

    public ATMRequestBuilder add(String parameter){
        this.stringBuilder.append(parameter + RequestBuilder.Separator);
        return this;
    }

    public String toString(){
        return this.stringBuilder.toString();
    }
}
