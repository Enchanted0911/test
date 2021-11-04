package icu.junyao.fileservice.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author johnson
 * @since 2021-11-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_file_metadata")
@ApiModel(value="FileMetadata对象", description="")
public class FileMetadata implements Serializable {

    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    @ApiModelProperty(value = "原始文件名")
    private String name;

    @ApiModelProperty(value = "文件大小, 单位Byte")
    private Long size;

    @ApiModelProperty(value = "文件类型")
    private String type;

    @ApiModelProperty(value = "文件保存目录地址")
    private String url;

    @ApiModelProperty(value = "文件上传的ip地址")
    private String ipAddress;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdTime;

    @ApiModelProperty(value = "删除时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime deletedTime;

    @ApiModelProperty(value = "逻辑删除 0 未删除 1 删除")
    @TableField("is_deleted")
    @TableLogic
    private Boolean deleted;


}
