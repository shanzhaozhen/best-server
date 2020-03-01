package org.shanzhaozhen.bestservice.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.shanzhaozhen.bestcommon.converter.RegionConverter;
import org.shanzhaozhen.bestcommon.domain.sys.RegionDO;
import org.shanzhaozhen.bestcommon.dto.RegionDTO;
import org.shanzhaozhen.bestcommon.form.BaseSearchForm;
import org.shanzhaozhen.bestcommon.utils.CustomBeanUtils;
import org.shanzhaozhen.bestrepo.mapper.RegionMapper;
import org.shanzhaozhen.bestservice.service.RegionService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class RegionServiceImpl implements RegionService {

    private final RegionMapper regionMapper;

    public RegionServiceImpl(RegionMapper regionMapper) {
        this.regionMapper = regionMapper;
    }

    @Override
    public Page<RegionDTO> getRegionPage(BaseSearchForm<RegionDTO> baseSearchForm) {
        return regionMapper.getRegionPage(baseSearchForm.getPage(), baseSearchForm.getKeyword());
    }

    @Override
    public List<RegionDTO> getAllRegions() {
        return regionMapper.getAllRegions();
    }

    @Override
    public List<RegionDTO> getRegionTree() {
        
        return null;
    }

    @Override
    public RegionDTO getRegionById(Long regionId) {
        RegionDTO regionDTO = regionMapper.getRegionByRegionId(regionId);
        Assert.notNull(regionDTO, "获取失败：没有找到该区域信息或已被删除");
        return regionDTO;
    }

    @Override
    public Long addRegion(RegionDTO regionDTO) {
        RegionDTO regionInDB = regionMapper.getRegionByCode(regionDTO.getCode());
        Assert.isNull(regionInDB, "创建失败：区域编号已被占用");
        RegionDO regionDO = RegionConverter.toDO(regionDTO);
        regionMapper.insert(regionDO);
        return regionDO.getId();
    }

    @Override
    public Long updateRegion(RegionDTO regionDTO) {
        Assert.notNull(regionDTO.getId(), "区域信息id不能为空");
        RegionDTO regionInDB = regionMapper.getRegionByIdNotEqualAndCodeEqual(regionDTO.getId(), regionDTO.getCode());
        Assert.isNull(regionInDB, "更新失败：标识名称已被占用");
        RegionDO regionDO = regionMapper.selectById(regionDTO.getId());
        Assert.notNull(regionDO, "更新失败：没有找到该区域信息或已被删除");
        CustomBeanUtils.copyPropertiesExcludeMeta(regionDTO, regionDO);
        regionMapper.updateById(regionDO);
        return regionDO.getId();
    }

    @Override
    public Long deleteRegion(Long regionId) {
        regionMapper.deleteById(regionId);
        return regionId;
    }
}
