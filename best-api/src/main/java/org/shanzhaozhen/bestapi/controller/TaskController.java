package org.shanzhaozhen.bestapi.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;
import org.shanzhaozhen.bestcommon.converter.DynamicScheduledTaskConverter;
import org.shanzhaozhen.bestcommon.dto.DynamicScheduledTaskDTO;
import org.shanzhaozhen.bestcommon.form.BaseSearchForm;
import org.shanzhaozhen.bestcommon.form.DynamicScheduledTaskForm;
import org.shanzhaozhen.bestcommon.vo.DynamicScheduledTaskVO;
import org.shanzhaozhen.bestcommon.vo.ResultObject;
import org.shanzhaozhen.bestservice.service.TaskService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Api(value = "用户注册接口")
@RestController
public class TaskController {

    private final String GET_TASK_PAGE = "/task/page";
    private final String GET_TASK_BY_ID = "/task/{taskId}";
    private final String ADD_TASK = "/task";
    private final String UPDATE_TASK = "/task";
    private final String DELETE_TASK = "/task/{taskId}";
    private final String RUN_TASK = "/task/{taskId}/run";
    private final String START_TASK = "/task/{taskId}/start";
    private final String STOP_TASK = "/task/{taskId}/stop";

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping(GET_TASK_PAGE)
    @ApiOperation("获取任务信息（分页）")
    public ResultObject<Page<DynamicScheduledTaskVO>> getDynamicScheduledTaskPage(@RequestBody BaseSearchForm<DynamicScheduledTaskDTO> baseSearchForm) {
        return ResultObject.build(result -> DynamicScheduledTaskConverter.toVO(taskService.getTaskPage(baseSearchForm)));
    }

    @GetMapping(GET_TASK_BY_ID)
    @ApiOperation("获取任务信息（通过任务id）")
    public ResultObject<DynamicScheduledTaskVO> getDynamicScheduledTaskByDynamicScheduledTaskId(@PathVariable("taskId") @ApiParam(name = "任务id", example = "1") Long taskId) {
        return ResultObject.build(result -> DynamicScheduledTaskConverter.toVO(taskService.getTaskById(taskId)));
    }

    @PostMapping(ADD_TASK)
    @ApiOperation("添加定时任务接口")
    public ResultObject<DynamicScheduledTaskVO> addDynamicScheduledTask(@RequestBody @Validated({Insert.class}) DynamicScheduledTaskForm dynamicScheduledTaskForm) {
        return ResultObject.build(result -> DynamicScheduledTaskConverter.toVO(taskService.addTask(DynamicScheduledTaskConverter.toDTO(dynamicScheduledTaskForm))));
    }

    @PutMapping(UPDATE_TASK)
    @ApiOperation("更新定时任务接口")
    public ResultObject<DynamicScheduledTaskVO> updateDynamicScheduledTask(@RequestBody @Validated({Update.class}) DynamicScheduledTaskForm dynamicScheduledTaskForm) {
        return ResultObject.build(result -> DynamicScheduledTaskConverter.toVO(taskService.updateTask(DynamicScheduledTaskConverter.toDTO(dynamicScheduledTaskForm))));
    }

    @DeleteMapping(DELETE_TASK)
    @ApiOperation("删除定时任务接口")
    public ResultObject<Long> deleteDynamicScheduledTask(@PathVariable("taskId") @ApiParam(name = "任务id", example = "1") Long taskId) {
        return ResultObject.build(result -> taskService.deleteTask(taskId));
    }

    @GetMapping(RUN_TASK)
    @ApiOperation("运行定时任务接口")
    public ResultObject<Object> runDynamicScheduledTask(@PathVariable("taskId") @ApiParam(name = "任务id", example = "1") Long taskId) {
        return ResultObject.build(result -> taskService.runTask(taskId));
    }

    @GetMapping(START_TASK)
    @ApiOperation("开始定时任务接口")
    public ResultObject<Object> startDynamicScheduledTask(@PathVariable("taskId") @ApiParam(name = "任务id", example = "1") Long taskId) {
        return ResultObject.build(result -> taskService.startTask(taskId));
    }

    @GetMapping(STOP_TASK)
    @ApiOperation("停止定时任务接口")
    public ResultObject<Object> stopDynamicScheduledTask(@PathVariable("taskId") @ApiParam(name = "任务id", example = "1") Long taskId) {
        return ResultObject.build(result -> taskService.stopTask(taskId));
    }

}
