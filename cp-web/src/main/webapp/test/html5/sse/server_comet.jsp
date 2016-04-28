<%
  response.setContentType("text/html; charset=UTF-8");
  response.setHeader("Cache-Control", "no-cache");

  int count =0;
  while (count<3){
    out.println(new java.util.Date().getTime() );
    out.println();
//    out.flush();
    Thread.sleep(1000);
    count++;
  }


%>