package org.shanzhaozhen.bestserver.service.impl;

import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import org.shanzhaozhen.bestserver.converter.ResourceConverter;
import org.shanzhaozhen.bestserver.domain.sys.ResourceDO;
import org.shanzhaozhen.bestserver.dto.ResourceDTO;
import org.shanzhaozhen.bestserver.mapper.ResourceMapper;
import org.shanzhaozhen.bestserver.service.ResourceService;
import org.shanzhaozhen.bestserver.utils.CustomBeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class ResourceServiceImpl implements ResourceService {

    private final ResourceMapper resourceMapper;

    public ResourceServiceImpl(ResourceMapper resourceMapper) {
        this.resourceMapper = resourceMapper;
    }

    @Override
    public List<ResourceDTO> getResourceRoleListByType(Integer type) {
        return resourceMapper.getResourceRoleListByTypeAndUserId(type, null);
    }

    @Override
    public List<ResourceDTO> getAllResourceTreeByType(Integer type) {
        List<ResourceDTO> resourceDTOList = this.getResourceRoleListByType(type);
        return ResourceConverter.builtResourceTree(resourceDTOList);
    }

    @Override
    public ResourceDTO getResourceById(Long resourceId) {
        ResourceDO resourceDO = resourceMapper.selectById(resourceId);
        Assert.notNull(resourceDO, "获取失败：没有找到该资源或已被删除");
        return ResourceConverter.toDTO(resourceDO);
    }

    @Override
    @Transactional
    public ResourceDTO addResource(ResourceDTO resourceDTO) {
        ResourceDO resourceDO = ResourceConverter.toDO(resourceDTO);
        resourceMapper.insert(resourceDO);
        return resourceDTO;
    }

    @Override
    @Transactional
    public ResourceDTO updateResource(ResourceDTO resourceDTO) {
        Assert.notNull(resourceDTO.getId(), "更新失败：资源id不能为空");
        Assert.isTrue(!resourceDTO.getId().equals(resourceDTO.getPid()), "更新失败：上级节点不能选择自己");
        if (resourceDTO.getPid() != null) {
            ResourceDO resourcePNode = resourceMapper.selectById(resourceDTO.getPid());
            Assert.notNull(resourcePNode, "更新失败：没有找到该资源的上级节点或已被删除");
            Assert.isTrue(!resourceDTO.getId().equals(resourcePNode.getPid()), "更新失败：节点之间不能互相引用");
        }
        ResourceDO resourceDO = resourceMapper.selectById(resourceDTO.getId());
        Assert.notNull(resourceDO, "更新失败：没有找到该资源或已被删除");
        CustomBeanUtils.copyPropertiesExcludeMeta(resourceDTO, resourceDO);
        resourceMapper.updateById(resourceDO);
        try {
            this.getAllResourceTreeByType(null);
        } catch (StackOverflowError e) {
            throw new IllegalArgumentException("更新失败：请检查资源的节点设置是否有问题");
        }
        return resourceDTO;
    }

    @Override
    @Transactional
    public Boolean deleteResource(Long resourceId) {
        return SqlHelper.retBool(resourceMapper.deleteById(resourceId));
    }

}
