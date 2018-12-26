package com.zw.design.modules.lookboard.produce.controller;

import com.zw.design.base.BaseDataTableModel;
import com.zw.design.base.BaseResponse;
import com.zw.design.modules.lookboard.produce.model.ProduceModel;
import com.zw.design.modules.lookboard.produce.query.ProduceQuery;
import com.zw.design.modules.lookboard.produce.service.ProduceService;
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
@RequestMapping("/board/produce")
public class ProduceController {

    private String prefix = "lookboard/produce";

    @Autowired
    private ProduceService produceService;
    
    /**
     * 多项目看板页面
     */
    @GetMapping("/page")
    @RequiresPermissions({"produce:list"})
    public String distributePage() {
        return prefix + "/list";
    }

    /**
     * 列表数据
     */
    @ResponseBody
    @PostMapping("/list")
    @RequiresPermissions({"produce:list"})
    public BaseResponse ddtList(ProduceQuery query) {
        BaseDataTableModel<ProduceModel> dto = produceService.findByQuery(query);
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setContent(dto);
        return baseResponse;
    }
}
