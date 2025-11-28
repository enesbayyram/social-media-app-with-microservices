package com.enesbayram.sm_core.base.model;

import com.enesbayram.sm_core.base.enums.HttpMethodType;
import com.enesbayram.sm_core.base.model.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "http_trace_log")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HttpTraceLog extends BaseEntity {

    @Column(name = "request_path" , nullable = true)
    private String requestPath;

    @Column(name = "method_type")
    @Enumerated(EnumType.STRING)
    private HttpMethodType methodType;

    @Column(name = "status")
    private Integer status;

    @Lob
    @Column(name = "request")
    private String request;

    @Lob
    @Column(name = "response")
    private String response;
}
