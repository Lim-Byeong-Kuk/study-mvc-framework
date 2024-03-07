package org.example.mvc.view;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class JspView implements View{
    private final String name;

    public JspView(String name) {
        this.name = name;
    }

    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        // model 에 값이 있으면 request 에 setAttribute 해준다.
        model.forEach(request::setAttribute);

        // name(viewName) 으로 포워딩
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(name);
        requestDispatcher.forward(request, response);
    }
}
