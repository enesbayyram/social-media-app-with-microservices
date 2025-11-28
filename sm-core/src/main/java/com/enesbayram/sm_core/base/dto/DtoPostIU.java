package com.enesbayram.sm_core.base.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DtoPostIU extends DtoCrud {

    @NotBlank
    private String content;

    @NotBlank
    private String userDefId;
}
