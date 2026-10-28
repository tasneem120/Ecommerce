package com.example.FirstProject.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {
@Schema(description = "Category ID ",example = "102")
    private Long categoryId;
    @Schema(description = "Category Name that you wish to create ",example = "Phones")

    private String categoryName;
}
