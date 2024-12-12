package com.laurkan.curency.dto;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.Map;

@Data
public class CbrRateListResponse {
    @SerializedName("Valute")
    private Map<String, ValuteResponse> valute;

    @Data
    public static class ValuteResponse {
        @SerializedName("CharCode")
        private String charCode;

        @SerializedName("Value")
        private Double value;
    }
}
