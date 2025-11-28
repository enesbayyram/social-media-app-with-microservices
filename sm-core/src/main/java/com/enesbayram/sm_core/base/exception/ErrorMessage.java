package com.enesbayram.sm_core.base.exception;

import com.enesbayram.sm_core.base.enums.MessageType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.StringUtils;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorMessage {

    private MessageType messageType;

    private String ofStatic;

    public String prepareErrorMessage() {
        StringBuilder builder = new StringBuilder();
        builder.append(messageType.getMessage());
        if (StringUtils.hasText(ofStatic)) {
            builder.append(" : ").append(ofStatic);
        }
        return builder.toString();
    }
}
