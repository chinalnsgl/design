package com.zw.design.modules.integrate.files.controller;

import com.zw.design.base.BaseDataTableModel;
import com.zw.design.base.BaseResponse;
import com.zw.design.modules.integrate.files.model.FilesModel;
import com.zw.design.modules.integrate.files.query.FilesQuery;
import com.zw.design.modules.integrate.files.service.FilesService;
import com.zw.design.modules.lookboard.process.model.ProcessModel;
import com.zw.design.modules.lookboard.process.query.ProcessQuery;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Slf4j
@RequestMapping("/integrate/files")
public class FilesController {

    private String prefix = "integrate/files";

    @Autowired
    private FilesService filesService;

    /**
     * 项目文件查询页面
     */
    @GetMapping("/page")
    @RequiresPermissions({"files:list"})
    public String filesPage() {
        return prefix + "/list";
    }

    /**
     * 列表数据
     */
    @ResponseBody
    @PostMapping("/list")
    @RequiresPermissions({"files:list"})
    public BaseResponse ddtList(FilesQuery query) {
        BaseDataTableModel<FilesModel> dto = filesService.findByQuery(query);
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setContent(dto);
        return baseResponse;
    }
}
