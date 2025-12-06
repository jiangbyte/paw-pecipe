package ${package.Service};

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import ${package.Entity}.${entity};
import ${package.Dto}.${entity}Dto;
import ${package.PageQuery}.${entity}PageQuery;

import java.util.List;

/**
* @author ${author}
* @version v1.0
* @date ${date}
* @description ${table.comment!} 服务类
*/
public interface ${entity}Service extends ${superServiceClass}<${entity}> {
    Page<${entity}> page(${entity}PageQuery req);

    void add(${entity}Dto req);

    void edit(${entity}Dto req);

    void delete(List<String> ids);

    ${entity} detail(String id);

    List<${entity}> latest(int n);

    List<${entity}> topN(int n);
}