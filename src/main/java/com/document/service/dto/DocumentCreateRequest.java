package com.document.service.dto;

import java.util.List;

public record DocumentCreateRequest(
        String title,
        String content,
        List<String> tags
) {}
