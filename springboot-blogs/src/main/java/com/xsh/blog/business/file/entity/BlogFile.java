package com.xsh.blog.business.file.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xsh.blog.common.bean.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * <p>
 * 
 * </p>
 *
 *
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("file")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value="File对象", description="")
public class BlogFile extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_UUID)
    @ApiModelProperty(value = "文件编号")
    private String fileId;

    @ApiModelProperty(value = "文件名称")
    private String fileName;

    @ApiModelProperty(value = "文件名称")
    private String fileType;

    @ApiModelProperty(value = "文件位置")
    private String fileLocation;

    @ApiModelProperty(value = "文件大小")
    private Double fileSize;





}
