package org.shanzhaozhen.bestapi.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.shanzhaozhen.bestcommon.converter.RouteConverter;
import org.shanzhaozhen.bestcommon.form.RouteForm;
import org.shanzhaozhen.bestcommon.vo.ResultObject;
import org.shanzhaozhen.bestcommon.vo.RouteVO;
import org.shanzhaozhen.bestservice.service.RouteService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api("用户路由接口")
@RestController
public class RouteController {

    private final String GET_ALL_ROUTE_TREE = "/route/tree";
    private final String GET_ROUTE_BY_ID = "/route/{routeId}";
    private final String ADD_ROUTE = "/route";
    private final String UPDATE_ROUTE = "/route";
    private final String DELETE_ROUTE = "/route/{routeId}";

    private final RouteService routeService;

    public RouteController(RouteService routeService) {
        this.routeService = routeService;
    }

    @GetMapping(GET_ALL_ROUTE_TREE)
    @ApiOperation("获取所有路由信息（树状结构）")
    public ResultObject<List<RouteVO>> getAllRouteTree() {
        return ResultObject.build(result -> RouteConverter.toVO(routeService.getAllRouteTree()));
    }

    @GetMapping(GET_ROUTE_BY_ID)
    @ApiOperation("获取路由信息（通过路由id）")
    public ResultObject<RouteVO> getRouteByRouteId(@PathVariable("routeId") @ApiParam(name = "路由id", example = "1") Long routeId) {
        return ResultObject.build(result -> RouteConverter.toVO(routeService.getRouteById(routeId)));
    }

    @PostMapping(ADD_ROUTE)
    @ApiOperation("添加路由接口")
    public ResultObject<Long> addRoute(@RequestBody @Validated RouteForm routeForm) {
        return ResultObject.build(result -> routeService.addRoute(RouteConverter.toDTO(routeForm)));
    }

    @PutMapping(UPDATE_ROUTE)
    @ApiOperation("更新路由接口")
    public ResultObject<Long> updateRoute(@RequestBody @Validated RouteForm routeForm) {
        return ResultObject.build(result -> routeService.updateRoute(RouteConverter.toDTO(routeForm)));
    }

    @DeleteMapping(DELETE_ROUTE)
    @ApiOperation("删除路由接口")
    public ResultObject<Long> deleteRoute(@PathVariable("routeId") @ApiParam(name = "路由id", example = "1") Long routeId) {
        return ResultObject.build(result -> routeService.deleteRoute(routeId));
    }

}
