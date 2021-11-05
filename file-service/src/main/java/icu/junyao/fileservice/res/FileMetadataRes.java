package icu.junyao.fileservice.res;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 文件元数据返回结果
 *
 * @author johnson
 * @date 2021-10-02
 */
@Data
public class FileMetadataRes {
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
    private LocalDateTime createdTime;
}
