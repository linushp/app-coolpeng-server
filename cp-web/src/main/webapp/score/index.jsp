<%@ page isELIgnored="false" language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@ include file="/common/jsp/taglibs.jsp"%>
<tms:view>

  <style>
    #header{
      display: none;
    }

    .layout-container{
      border: 1px solid #CCCCCC;
      width: 700px;
      margin: 10px auto;
      padding: 20px;;
    }
  </style>
  <div style="height: 50px"></div>

  <form method="post" action="./score.shtml">
    <div class="layout-container" style="position: relative;">
      <div class="layout-row" style="height: 40px;line-height: 40px;font-size: 16px;font-family: 'Microsoft YaHei', Tahoma, Arial, 宋体, sans-serif; background: #DDDDDD;text-indent: 20px">
        上海千锋IOS1518班OC考试成绩查询
      </div>
      <div class="layout-row">
        <span class="layout-title">您的学号：</span>
        <input class="layout-input" name="stuNumber" type="text" >
      </div>
      <div class="layout-row">
        <span class="layout-title">您的姓名：</span>
        <input class="layout-input" name="stuName" type="text" >
      </div>
      <div class="layout-row">
        <span class="layout-title">&nbsp;</span>
        <input type="submit" class="layout-submit" style="float: left" value="查询分数">
        <div style="float: left;margin-left: 20px" >输入姓名或者学号都可以查询成绩</div>
      </div>
      <div class="clear"></div>
    </div>
  </form>

  <c:if test='${status=="ok"}'>
    <div class="layout-container">

      <div class="layout-row">
        <span class="layout-title">学号：</span>
          ${score.column1}
      </div>
      <div class="layout-row">
        <span class="layout-title">姓名：</span>
          ${score.column2}
      </div>

      <div class="clear" style="height: 10px"></div>

      <table class="layout-table" width="457" height="402">
        <col width="72" />
        <col width="72" />
        <col width="72" />
        <col width="72" />
        <col width="72" />
        <tr>
          <th width="119">分类</th>
          <th width="181">题目</th>
          <th width="53">分值</th>
          <th width="84">得分</th>
        </tr>
        <tr>
          <td width="119">1字符串处理</td>
          <td width="181">1字符串处理</td>
          <td width="53">10</td>
          <td width="84">${score.column3}</td>
        </tr>
        <tr>
          <td rowspan="2" width="119">2协议和代理</td>
          <td width="181">协议</td>
          <td  width="53">5</td>
          <td width="84">${score.column4}</td>
        </tr>
        <tr>
          <td width="181">代理</td>
          <td  width="53">5</td>
          <td width="84">${score.column5}</td>
        </tr>
        <tr>
          <td rowspan="8" width="119">3学生系统(27)</td>
          <td width="181">描述</td>
          <td  width="53">1</td>
          <td width="84">${score.column6}</td>
        </tr>
        <tr>
          <td width="181">添加</td>
          <td  width="53">2</td>
          <td width="84">${score.column7}</td>
        </tr>
        <tr>
          <td width="181">删除</td>
          <td  width="53">2</td>
          <td width="84">${score.column8}</td>
        </tr>
        <tr>
          <td width="181">打印</td>
          <td  width="53">4</td>
          <td width="84">${score.column9}</td>
        </tr>
        <tr>
          <td width="181">成绩排序（自己）</td>
          <td  width="53">4</td>
          <td width="84">${score.column10}</td>
        </tr>
        <tr>
          <td width="181">年龄排序（SEL）</td>
          <td  width="53">4</td>
          <td width="84">${score.column11}</td>
        </tr>
        <tr>
          <td width="181">查找</td>
          <td  width="53">5</td>
          <td width="84">${score.column12}</td>
        </tr>
        <tr>
          <td width="181">场景模拟</td>
          <td  width="53">5</td>
          <td width="84">${score.column13}</td>
        </tr>
        <tr>
          <td rowspan="3" width="119">4查找籍贯</td>
          <td width="181">plist解析</td>
          <td  width="53">6</td>
          <td width="84">${score.column14}</td>
        </tr>
        <tr>
          <td width="181">txt解析</td>
          <td  width="53">6</td>
          <td width="84">${score.column15}</td>
        </tr>
        <tr>
          <td width="181">查找</td>
          <td  width="53">6</td>
          <td width="84">${score.column16}</td>
        </tr>
        <tr>
          <td rowspan="4" width="119">5文件处理</td>
          <td width="181">文件夹合并</td>
          <td width="53">10</td>
          <td width="84">${score.column17}</td>
        </tr>
        <tr>
          <td width="181">json</td>
          <td  width="53">8</td>
          <td width="84">${score.column18}</td>
        </tr>
        <tr>
          <td width="181">xml</td>
          <td  width="53">9</td>
          <td width="84">${score.column19}</td>
        </tr>
        <tr>
          <td width="181">归档</td>
          <td  width="53">8</td>
          <td width="84">${score.column20}</td>
        </tr>
        <tr>
          <td width="119"></td>
          <td width="181">总计</td>
          <td width="53"></td>
          <td width="84">${scoreSum} %</td>
        </tr>
      </table>



    </div>
  </c:if>
  <c:if test='${status=="no_score"}'>
    <div class="layout-container" style="color: red;">
      输入的姓名或学号错误，请重新输入查询
    </div>
  </c:if>
</tms:view>