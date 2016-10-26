package com.coolpeng.cloud.files.controller;


import com.alibaba.fastjson.JSONObject;
import com.coolpeng.appbase.RestBaseController;
import com.coolpeng.blog.entity.UserEntity;
import com.coolpeng.cloud.files.entity.CloudCode;
import com.coolpeng.cloud.files.entity.CloudFiles;
import com.coolpeng.framework.db.PageResult;
import com.coolpeng.framework.db.QueryCondition;
import com.coolpeng.framework.mvc.TMSResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/cloud/files", produces = "application/json; charset=UTF-8")
public class CloudFilesController extends RestBaseController {



    @ResponseBody
    @RequestMapping({"/saveCloudFile"})
    public TMSResponse saveCloudFile(@RequestBody JSONObject jsonObject) throws Exception {

        CloudFiles cloudFiles = jsonObject.getObject("CloudFiles", CloudFiles.class);
        /**************************************************/

        UserEntity currentUser = assertIsUserLoginIfToken(jsonObject);

        cloudFiles.setCreateUserId(currentUser.getId());
        cloudFiles.setUpdateUserId(currentUser.getId());

        CloudFiles.DAO.insert(cloudFiles);

        return TMSResponse.success(cloudFiles.getId());
    }


    @ResponseBody
    @RequestMapping({"/getCloudFileById"})
    public TMSResponse getCloudCodeById(@RequestBody JSONObject jsonObject) throws Exception {
        String id = jsonObject.getString("id");
        /**************************************************/

        assertIsUserLoginIfToken(jsonObject);

        CloudFiles entity = CloudFiles.DAO.queryById(id);

        return TMSResponse.success(entity);
    }



    @ResponseBody
    @RequestMapping({"/getCloudFileListByUserId"})
    public TMSResponse getCloudFileListByUserId(@RequestBody JSONObject jsonObject) throws Exception {
        String userId = jsonObject.getString("userId");
        int pageNumber = jsonObject.getInteger("pageNumber");
         int pageSize = jsonObject.getInteger("pageSize");
        /**************************************************/

        assertIsUserLoginIfToken(jsonObject);
        QueryCondition qc = new QueryCondition();
        qc.addEqualCondition("createUserId",userId);
        PageResult<CloudFiles> entity = CloudFiles.DAO.queryForPage(qc, pageNumber, pageSize, null);

        return TMSResponse.success(entity);
    }







}
