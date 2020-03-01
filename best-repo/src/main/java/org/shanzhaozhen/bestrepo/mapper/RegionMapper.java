package org.shanzhaozhen.bestrepo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.shanzhaozhen.bestcommon.domain.sys.RegionDO;
import org.shanzhaozhen.bestcommon.dto.RegionDTO;

import java.util.List;

public interface RegionMapper extends BaseMapper<RegionDO> {

    @Select("select id, pid, name, code, level, " +
            "created_by, created_date, last_modified_by, last_modified_date " +
            "from sys_role where name like concat ('%', #{keyword}, '%') or code like concat ('%', #{keyword}, '%')")
    Page<RegionDTO> getRegionPage(Page<RegionDTO> page, @Param("keyword") String keyword);

    @Select("select id, pid, name, code, level, " +
            "created_by, created_date, last_modified_by, last_modified_date " +
            "from sys_role")
    List<RegionDTO> getAllRegions();

    @Select("select id, pid, name, code, level, " +
            "created_by, created_date, last_modified_by, last_modified_date " +
            "from sys_role where id = #{regionId} ")
    RegionDTO getRegionByRegionId(@Param("regionId") Long regionId);

    @Select("select id, pid, name, code, level, " +
            "created_by, created_date, last_modified_by, last_modified_date " +
            "from sys_role where code = #{code} ")
    RegionDTO getRegionByCode(@Param("code") String code);

    @Select("select id, pid, name, code, level, " +
            "created_by, created_date, last_modified_by, last_modified_date " +
            "from sys_role where id != #{regionId} and code = #{code} ")
    RegionDTO getRegionByIdNotEqualAndCodeEqual(@Param("regionId") Long regionId, @Param("code") String code);
}
