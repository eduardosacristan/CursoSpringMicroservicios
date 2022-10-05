package academy.digitallab.tore.serviceproduct.controller;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter @Setter @Builder
public class ErrorMessages {
    private String code;
    private List<Map<String, String>> messages;

}
