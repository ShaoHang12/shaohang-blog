package com.xsh.blog.business.article.dto;

import lombok.Data;

@Data
public class PageDto {

    private int pagenum = 1;

    private int pagesize = 10;
}
