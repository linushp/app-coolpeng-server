<%
  response.setContentType("text/event-stream; charset=UTF-8");
  response.setHeader("Cache-Control", "no-cache");

  int count =0;
  while (count<3){
    out.println("data: >> server Time" + new java.util.Date() );
    out.println();
    out.flush();
    Thread.sleep(1000);
    count++;
  }


%>