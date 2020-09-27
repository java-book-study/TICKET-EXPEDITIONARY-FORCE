package com.ticket.captain.swagger;

import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@Api(value = "SwaggerController V2")
@RequestMapping("/swagger")
public class SwaggerController {

    @ApiOperation(value = "swagger test", notes = "스웨커 테스트")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 404, message = "Not Found")
    })
    @GetMapping(value = "/test")
    public Map<String, String> selectSwagger(@ApiParam(value = "스웨거파라미터", required = true, example = "1") @RequestParam String swaggerNo) {
        Map<String, String> result = new HashMap<>();
        result.put("testId", "스웨거테스트ID");
        result.put("content", "스웨거테스트내용");
        return result;
    }
}