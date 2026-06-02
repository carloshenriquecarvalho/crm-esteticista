package br.com.pimentaestetica.crm.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record LoginResponse(
        @Schema(example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9\n" +
                ".eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ\n" +
                ".SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c")
        String token) {

}
