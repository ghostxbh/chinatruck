package com.uzykj.chinatruck.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author xbh
 * @date 2020/12/8
 * @description
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
    private List<Node> children;
}
