package com.uzykj.chinatruck.domain.vo;

import com.uzykj.chinatruck.utils.ToolUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author ghostxbh
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Node implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String name;
    private String type;
    private String[] data_id;
    private List<Node> children;
}
