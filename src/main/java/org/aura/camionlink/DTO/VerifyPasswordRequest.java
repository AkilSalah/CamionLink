package org.aura.camionlink.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VerifyPasswordRequest {
    private Long id;
    private String currentPassword;
}