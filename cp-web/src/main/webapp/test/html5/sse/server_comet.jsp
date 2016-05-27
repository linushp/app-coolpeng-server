<%
  response.setContentType("text/html; charset=UTF-8");
  response.setHeader("Cache-Control", "no-cache");

  int count =0;
  while (count<300){
    out.println(new java.util.Date().getTime() );
    out.println("<script>console.log('11',new Date())</script>");
    out.flush(); //不调用flush是不会输出的
    Thread.sleep(100);
    count++;
  }


%>