package com.document.service.dto;

import java.util.List;

public record DocumentResponseDto(Long id, String title, String content, List<TagDto> tags) {}